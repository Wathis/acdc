package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
@Entity
@Table(name="VCFFile")
@AllArgsConstructor
@NoArgsConstructor
public class VCFFile {
	@Id
	@GeneratedValue
	private Integer id_file;
	@ColumnTransformer(read="AES_DECRYPT(fileFormat,'salut')" ,write="AES_ENCRYPT(?,'salut')")
	@Column(columnDefinition = "blob")
	protected String fileFormat;
	@ColumnTransformer(read="AES_DECRYPT(fileDate,'salut')" ,write="AES_ENCRYPT(?,'salut')")
	@Column(columnDefinition = "blob")
	protected String fileDate;
	@ColumnTransformer(read="AES_DECRYPT(source,'salut')" ,write="AES_ENCRYPT(?,'salut')")
	@Column(columnDefinition = "blob")
	protected String source;
	@ColumnTransformer(read="AES_DECRYPT(reference,'salut')" ,write="AES_ENCRYPT(?,'salut')")
	@Column(columnDefinition = "blob")
	protected String reference;
	@ColumnTransformer(read="AES_DECRYPT(phasing,'salut')" ,write="AES_ENCRYPT(?,'salut')")
	@Column(columnDefinition = "blob")
	protected String phasing;
	protected String watermark;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<VCFInfo> infos;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<VCFFormat> formats;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<VCFFilter> filters;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<VCFContig> contigs;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<VCFBody> body;
}
