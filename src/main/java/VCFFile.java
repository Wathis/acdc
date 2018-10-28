import java.util.List;

public class VCFFile {
	
	protected String fileFormat;
	protected String fileDate;
	protected String source;
	protected String reference;
	protected String contig;
	protected String phasing; 
	protected List<VCFInfo> infos;
	protected List<VCFFormat> formats;
	protected List<VCFFilter> filters;
	protected List<VCFBody> body;
	
}
