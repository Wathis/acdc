package model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class VCFBody {
	
	protected String chrom;
	protected String pos;
	protected String id;
	protected String ref;
	protected String alt;
	protected String qual;
	protected String filter;
	protected String info;
	protected String format;
	protected String NA00001;
	protected String NA00002;
	protected String NA00003;
	
}
