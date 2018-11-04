package model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="VCFInfo")
public class VCFInfo extends VCFTypedMetadata {
	@Id
	@GeneratedValue
	private Integer id_info;
	protected String source;
	protected String version;
}
