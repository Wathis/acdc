package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.List;

import static utils.AESUtils.AES_SECRET_KEY;


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

	@ColumnTransformer(read="AES_DECRYPT(fileFormat,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String fileFormat;

	@ColumnTransformer(read="AES_DECRYPT(fileDate,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String fileDate;

	@ColumnTransformer(read="AES_DECRYPT(source,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String source;

	@ColumnTransformer(read="AES_DECRYPT(reference,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String reference;

	@ColumnTransformer(read="AES_DECRYPT(phasing,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
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
