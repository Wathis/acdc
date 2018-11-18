package model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import static utils.AESUtils.AES_SECRET_KEY;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class VCFMetadata {
	@ColumnTransformer(read="AES_DECRYPT(ID,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String ID;
	@ColumnTransformer(read="AES_DECRYPT(description,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String description;
}
