package model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@Entity
@Table(name="VCFContig")
public class VCFContig {
    @Id
    @GeneratedValue
    private Integer id_contig;
    private String ID;
    private int length;
    private String assembly;
}
