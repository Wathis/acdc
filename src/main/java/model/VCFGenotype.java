package model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name="VCFGenotype")
public class VCFGenotype {

    @Id
    @GeneratedValue
    private Integer id_genotype;
    private String name;
    private String data;

}
