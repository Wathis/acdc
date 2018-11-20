package model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

import static utils.AESUtil.AES_SECRET_KEY;

@Data
@Builder
@Entity
@Table(name="VCFGenotype")
public class VCFGenotype {

    @Id
    @GeneratedValue
    private Integer id_genotype;

    @ColumnTransformer(read="AES_DECRYPT(name,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
    @Column(columnDefinition = "blob")
    private String name;

    @ColumnTransformer(read="AES_DECRYPT(data,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
    @Column(columnDefinition = "blob")
    private String data;

}
