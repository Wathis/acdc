package io;

import htsjdk.variant.variantcontext.CommonInfo;
import htsjdk.variant.variantcontext.LazyGenotypesContext;
import htsjdk.variant.variantcontext.VariantContext;
import htsjdk.variant.vcf.*;
import model.*;
import utils.HashUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe permettant de parser un fichier VCF. Utilisation de la librairie htsjdk
 * Accès par singleton
 */
public class VCFReader {

	private static VCFReader sharedInstance = new VCFReader();

	private VCFReader() {}

	/**
	 * Singleton permettant d'accéder au VCFReader
	 */
	public static VCFReader getSharedInstance() {
		return sharedInstance;
	}

	/**
	 * Permet de convertir un fichier VCF en objet VCFFile
	 * @param file fichier a lire
	 */
	public VCFFile loadVCFFile(File file) throws URISyntaxException {
		VCFFileReader fileReader = new VCFFileReader(file,false);
		return VCFFile.builder()
				.watermark(HashUtil.sha256File(file.getPath()))
				.filters(extractFilters(fileReader.getFileHeader()))
				.infos(extractInfos(fileReader.getFileHeader()))
				.formats(extractFormats(fileReader.getFileHeader()))
				.contigs(extractContig(fileReader.getFileHeader()))
				.body(extractBody(fileReader))
				.build();
	}

	/**
	 * Permet d'extraire les filtres d'un header de fichier vcf
	 * @param fileHeader
	 * @return Une liste de filtres
	 */
	private List<VCFFilter> extractFilters(VCFHeader fileHeader) {
		List<VCFFilterHeaderLine> filterHeaderLines = fileHeader.getFilterLines();
		List<VCFFilter> filters = new LinkedList<>();

		Iterator<VCFFilterHeaderLine> iterator = filterHeaderLines.iterator();
		while (iterator.hasNext()) {
			VCFFilterHeaderLine next = iterator.next();
			VCFFilter vcfFilter = new VCFFilter();
			vcfFilter.setID(next.getID());
			filters.add(vcfFilter);
		}
		return filters;
	}

	/**
	 * Permet d'extraire les lignes contig d'un header de fichier vcf
	 * @param fileHeader
	 * @return Une liste de contig
	 */
	private List<VCFContig> extractContig(VCFHeader fileHeader) {
		List<VCFContigHeaderLine> contigsHeaderLines = fileHeader.getContigLines();
		List<VCFContig> contigs = new LinkedList<>();
		Iterator<VCFContigHeaderLine> iterator = contigsHeaderLines.iterator();
		while (iterator.hasNext()) {
			VCFContigHeaderLine next = iterator.next();
			String genericFields = next.toString();
			HashMap<String, String> map = genericFieldsStringToMap(genericFields);
			VCFContig vcfContig = VCFContig.builder()
					.ID(map.get("ID"))
					.length(Integer.parseInt(map.get("length")))
					.assembly(map.get("assembly"))
					.build();
			contigs.add(vcfContig);
		}
		return contigs;
	}

	/**
	 * Permet d'extraire les infos depuis le header d'un fichier VCF
	 * @param fileHeader
	 */
	private List<VCFInfo> extractInfos(VCFHeader fileHeader) {
		Collection<VCFInfoHeaderLine> filterHeaderLines = fileHeader.getInfoHeaderLines();
		List<VCFInfo> infos = new LinkedList<>();
		Iterator<VCFInfoHeaderLine> iterator = filterHeaderLines.iterator();
		while (iterator.hasNext()) {
			VCFInfoHeaderLine next = iterator.next();
			VCFInfo vcfInfo = new VCFInfo();
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

	/**
	 * Permet d'extraire le format depuis le header d'un fichier VCF
	 * @param fileHeader
	 */
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

	/**
	 * Permet d'extraire le body et chaque ligne de données du fichier VCF
	 * @param fileReader Le file reader de la librairie htsjdk
	 */
	private List<VCFBody> extractBody(VCFFileReader fileReader) {
		Iterator<VariantContext> iterator = fileReader.iterator();
		List<VCFBody> vcfBodies = new LinkedList<>();
		while (iterator.hasNext()) {
			VariantContext next = iterator.next();
			String info = extractInfo(next);
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

	/**
	 * Permet d'extraire l'attribut INFO de chaque ligne du body
	 * @param next est une ligne du body
	 */
	private String extractInfo(VariantContext next) {
		String info = "";
		CommonInfo commonInfo = next.getCommonInfo();
		for (String key : commonInfo.getAttributes().keySet()) {
			info += key + "=" + commonInfo.getAttribute(key)+ ";";
		}
		return info;
	}

	/**
	 * Permet de convertir les generics fields en une HashMap
	 * ( Ex generic fields : contig=<ID=1,length=249250621,assembly=b37> )
	 * @param genericFields
	 */
	public static HashMap<String,String> genericFieldsStringToMap(String genericFields) {
		genericFields = genericFields.replace("contig=<","").replace(">","");
		List<String> genericFiedsParts = Arrays.asList(genericFields.split(","));
		HashMap<String, String> map = (HashMap<String, String>) genericFiedsParts.stream()
				.map(s -> s.split("=")).collect(Collectors.toMap(e -> e[0], e -> e[1]));
		return map;
	}

}
