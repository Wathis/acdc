package utils;

import htsjdk.variant.variantcontext.CommonInfo;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.*;
import model.*;

import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
		return VCFFile.builder()
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
			vcfFilter.setId(next.getID());
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
			VCFContig vcfContig = VCFContig.builder()
					.ID(next.getID())
					.length(next.getContigIndex())
					.assembly(next.getSAMSequenceRecord().getAssembly())
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
			vcfInfo.setId(next.getID());
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
			vcfFormat.setId(next.getID());
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
			String info = "";
			CommonInfo commonInfo = next.getCommonInfo();
			for (String key : commonInfo.getAttributes().keySet()) {
				info += key + "=" + commonInfo.getAttribute(key)+ ";";
			}

			VCFBody vcfBody = VCFBody.builder()
					.chrom(next.getContig())
					.pos(next.getStart())
					.id(next.getID())
					.ref(next.getReference().toString())
					.alt(next.getAlternateAlleles().toString())
					.qual(next.getPhredScaledQual())
					.filters(next.getFilters())
					.info(info)
					.format(null)
					.genotypes(null)
					.build();
			vcfBodies.add(vcfBody);
		}
		return vcfBodies;
	}
	
}
