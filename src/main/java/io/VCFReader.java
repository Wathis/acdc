package io;

import htsjdk.variant.variantcontext.CommonInfo;
import htsjdk.variant.variantcontext.LazyGenotypesContext;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.*;
import model.*;
import utils.HashUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class VCFReader {

	private static VCFReader sharedInstance = new VCFReader();

	private VCFReader() {}

	public static VCFReader getSharedInstance() {
		return sharedInstance;
	}

	public VCFFile loadVCFFile(String filename) throws URISyntaxException {
		URL resource = VCFReader.class.getResource(filename);
		File file = new File(resource.toURI());
		VCFFileReader fileReader = new VCFFileReader(file,false);
		VCFHeader header = fileReader.getFileHeader();
		return VCFFile.builder()
				.watermark(HashUtil.sha256File(file.getPath()))
				.filters(extractFilters(fileReader.getFileHeader()))
				.infos(extractInfos(fileReader.getFileHeader()))
				.formats(extractFormats(fileReader.getFileHeader()))
				.contigs(extractContig(fileReader.getFileHeader()))
				.body(extractBody(fileReader))
				.build();
	}

	private List<VCFFilter> extractFilters(VCFHeader fileHeader) {

		List<VCFFilterHeaderLine> filterHeaderLines = fileHeader.getFilterLines();
		List<VCFFilter> filters = new LinkedList<>();

		Iterator<VCFFilterHeaderLine> iterator = filterHeaderLines.iterator();
		while (iterator.hasNext()) {
			VCFFilterHeaderLine next = iterator.next();
			VCFFilter vcfFilter = new VCFFilter();
			vcfFilter.setID(next.getID());
			//vcfFilter.setDescription(next.getDescription());
			filters.add(vcfFilter);
		}
		return filters;
	}

	private List<VCFContig> extractContig(VCFHeader fileHeader) {
		List<VCFContigHeaderLine> contigsHeaderLines = fileHeader.getContigLines();
		List<VCFContig> contigs = new LinkedList<>();
		Iterator<VCFContigHeaderLine> iterator = contigsHeaderLines.iterator();
		while (iterator.hasNext()) {
			VCFContigHeaderLine next = iterator.next();
			String genericFields = next.toString();
			genericFields = genericFields.replace("contig=<","").replace(">","");
			HashMap<String, String> map = (HashMap<String, String>) Arrays.asList(genericFields.split(",")).stream()
					.map(s -> s.split("=")).collect(Collectors.toMap(e -> e[0], e -> e[1]));
			VCFContig vcfContig = VCFContig.builder()
					.ID(map.get("ID"))
					.length(Integer.parseInt(map.get("length")))
					.assembly(map.get("assembly"))
					.build();
			contigs.add(vcfContig);
		}
		return contigs;
	}


	private List<VCFInfo> extractInfos(VCFHeader fileHeader) {
		Collection<VCFInfoHeaderLine> filterHeaderLines = fileHeader.getInfoHeaderLines();
		List<VCFInfo> infos = new LinkedList<>();
		Iterator<VCFInfoHeaderLine> iterator = filterHeaderLines.iterator();
		while (iterator.hasNext()) {
			VCFInfoHeaderLine next = iterator.next();
			VCFInfo vcfInfo = new  VCFInfo();
			vcfInfo.setID(next.getID());
			vcfInfo.setNumber(next.getCountType());
			vcfInfo.setDescription(next.getDescription());
			vcfInfo.setType(next.getType());
			vcfInfo.setSource(null);
			vcfInfo.setVersion(null);
			infos.add(vcfInfo);
		}
		return infos;
	}

	private List<VCFFormat> extractFormats(VCFHeader fileHeader) {
		Collection<VCFFormatHeaderLine> formatHeaderLines = fileHeader.getFormatHeaderLines();
		List<VCFFormat> vcfFormats = new LinkedList<>();
		Iterator<VCFFormatHeaderLine> iterator = formatHeaderLines.iterator();
		while (iterator.hasNext()) {
			VCFFormatHeaderLine next = iterator.next();
			VCFFormat vcfFormat = new  VCFFormat();
			vcfFormat.setID(next.getID());
			vcfFormat.setNumber(next.getCountType());
			vcfFormat.setDescription(next.getDescription());
			vcfFormat.setType(next.getType());
			vcfFormats.add(vcfFormat);
		}
		return vcfFormats;
	}

	private List<VCFBody> extractBody(VCFFileReader fileReader) {
		Iterator<VariantContext> iterator = fileReader.iterator();
		List<VCFBody> vcfBodies = new LinkedList<>();
		while (iterator.hasNext()) {
			VariantContext next = iterator.next();
			//Extract Info
			String info = "";
			CommonInfo commonInfo = next.getCommonInfo();
			for (String key : commonInfo.getAttributes().keySet()) {
				info += key + "=" + commonInfo.getAttribute(key)+ ";";
			}
			//Extract genotypes
			LazyGenotypesContext genotypesContext = (LazyGenotypesContext) next.getGenotypes();
			String unparsedGenotypeData = (String) genotypesContext.getUnparsedGenotypeData();
			List<String> datas = Arrays.asList(unparsedGenotypeData.split("\t"));
			List<String> names = genotypesContext.getSampleNamesOrderedByName();
			String format = datas.get(0);
			int i = 1;
			List<VCFGenotype> genotypes = new LinkedList<>();
			for (String name : names) {
				genotypes.add(VCFGenotype.builder()
						.name(name)
						.data(datas.get(i))
						.build());
				i++;
			}
			String ID = next.getID();
			VCFBody vcfBody = VCFBody.builder()
					.chrom(next.getContig())
					.pos(next.getStart())
					.ID(next.getID())
					.ref(next.getReference().toString())
					.alt(next.getAlternateAlleles().toString()
							.replace("[","")
							.replace("]",""))
					.qual(next.getPhredScaledQual())
					.filters(next.getFilters().toString()
							.replace("[","")
							.replace("]",""))
					.info(info)
					.format(format)
					.genotypes(genotypes)
					.build();
			vcfBodies.add(vcfBody);
		}
		return vcfBodies;
	}
	
}
