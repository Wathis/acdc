package model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class VCFBody {
	
	protected String chrom;
	protected int pos;
	protected String id;
	protected String ref;
	protected String alt;
	protected double qual;
	protected String info;
	protected String format;
	protected Set<String> filters;
	List<VCFGenotype> genotypes;
	
}
