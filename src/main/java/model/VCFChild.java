package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="VCFChild")
@Data
@AllArgsConstructor
public class VCFChild {
    @Id
    @GeneratedValue
    private Integer id_child;

    @Column
    private String name;

    /*@ManyToOne
    @JoinColumn(name="parent_id", nullable=false)
    private VCFParent parent;*/

    public VCFChild(String name) {
        this.name = name;
    }
}
