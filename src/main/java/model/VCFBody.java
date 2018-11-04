package model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name="VCFBody")
public class VCFBody {
	@Id
	@GeneratedValue
	private Integer id_body;
	protected String chrom;
	protected int pos;
	protected String ID;
	protected String ref;
	protected String alt;
	protected double qual;
	protected String info;
	protected String format;
	protected String filters;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VCFGenotype> genotypes;
	
}
