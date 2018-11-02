package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="VCFParent")
@AllArgsConstructor
public class VCFParent {
    @Id
    @GeneratedValue
    private Integer parent_id;
    // SELECT AES_DECRYPT(AES_ENCRYPT('mytext', 'mykeystring'),'mykeystring');
    @ColumnTransformer(read="AES_DECRYPT(encryptedField,'salut')" ,write="AES_ENCRYPT(?,'salut')")
    @Column(columnDefinition = "blob")
    private String encryptedField;

    @OneToMany(mappedBy = "parent")
    private List<VCFChild> childs;

    public VCFParent(String encryptedField, List<VCFChild> childs) {
        this.encryptedField = encryptedField;
        this.childs = childs;
    }
}
