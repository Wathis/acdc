package model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import java.util.List;


@Data
@Builder
@Entity(name = "VCFFile")
public class VCFFile {
	protected String fileFormat;
	protected String fileDate;
	protected String source;
	protected String reference;
	protected String phasing; 
	protected List<VCFInfo> infos;
	protected List<VCFFormat> formats;
	protected List<VCFFilter> filters;
	protected List<VCFContig> contigs;
	protected List<VCFBody> body;
}
