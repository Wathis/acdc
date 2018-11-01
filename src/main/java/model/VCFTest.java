package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;

@Entity
@Data
@Table(name="VCFTest")
@AllArgsConstructor
public class VCFTest {
    @Id
    @GeneratedValue
    private Integer id;
    // SELECT AES_DECRYPT(AES_ENCRYPT('mytext', 'mykeystring'),'mykeystring');
    @ColumnTransformer(read="AES_DECRYPT(encryptedField,'salut')" ,write="AES_ENCRYPT(?,'salut')")
    @Column(columnDefinition = "blob")
    private String encryptedField;

    public VCFTest(String encryptedField) {
        this.encryptedField = encryptedField;
    }
}
