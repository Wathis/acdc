package model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

import static utils.AESUtil.AES_SECRET_KEY;

@Builder
@Data
@Entity
@Table(name="VCFContig")
public class VCFContig {
    @Id
    @GeneratedValue
    private Integer id_contig;

    @ColumnTransformer(read="AES_DECRYPT(ID,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
    @Column(columnDefinition = "blob")
    private String ID;

    @ColumnTransformer(read="AES_DECRYPT(length,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
    @Column(columnDefinition = "blob")
    private int length;

    @ColumnTransformer(read="AES_DECRYPT(assembly,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
    @Column(columnDefinition = "blob")
    private String assembly;
}
