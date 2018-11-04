package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="VCFParent")
@AllArgsConstructor
@NoArgsConstructor
public class VCFParent {

    @Id
    @GeneratedValue
    private Integer id;
    // SELECT AES_DECRYPT(AES_ENCRYPT('mytext', 'mykeystring'),'mykeystring');
    @ColumnTransformer(read="AES_DECRYPT(encryptedField,'salut')" ,write="AES_ENCRYPT(?,'salut')")
    @Column(columnDefinition = "blob")
    private String encryptedField;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VCFChild> childs;

    public VCFParent(String encryptedField, List<VCFChild> childs) {
        this.encryptedField = encryptedField;
        this.childs = childs;
    }
}
