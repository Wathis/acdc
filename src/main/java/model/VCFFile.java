package model;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
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
