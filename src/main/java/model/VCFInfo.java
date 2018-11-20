package model;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

import static utils.AESUtil.AES_SECRET_KEY;

@Data
@Entity
@Table(name="VCFInfo")
public class VCFInfo extends VCFTypedMetadata {
	@Id
	@GeneratedValue
	private Integer id_info;

	@ColumnTransformer(read="AES_DECRYPT(source,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String source;

	@ColumnTransformer(read="AES_DECRYPT(version,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String version;
}
