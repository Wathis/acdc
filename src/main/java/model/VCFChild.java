package model;

import javax.persistence.*;

@Entity(name="VCFChild")
@Table(name="VCFChild")
public class VCFChild {
    @Id
    @GeneratedValue
    private Integer child_id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="parent_id", nullable=false)
    private VCFParent parent;

    public VCFChild(String name) {
        this.name = name;
    }
}
