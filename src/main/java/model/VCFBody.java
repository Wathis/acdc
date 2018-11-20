package model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.List;

import static utils.AESUtil.AES_SECRET_KEY;

// TODO Création d'une DB LINK pour la fragmentation

@Data
@Builder
@Entity
@Table(name="VCFBody")
public class VCFBody {
	@Id
	@GeneratedValue
	private Integer id_body;

	@ColumnTransformer(read="AES_DECRYPT(chrom,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String chrom;

	@ColumnTransformer(read="AES_DECRYPT(pos,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected int pos;

	@ColumnTransformer(read="AES_DECRYPT(ID,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String ID;

	@ColumnTransformer(read="AES_DECRYPT(ref,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String ref;

	@ColumnTransformer(read="AES_DECRYPT(alt,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String alt;

	@ColumnTransformer(read="AES_DECRYPT(qual,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected double qual;

	@ColumnTransformer(read="AES_DECRYPT(info,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String info;

	@ColumnTransformer(read="AES_DECRYPT(format,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String format;

	@ColumnTransformer(read="AES_DECRYPT(filters,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
	@Column(columnDefinition = "blob")
	protected String filters;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VCFGenotype> genotypes;
	
}
