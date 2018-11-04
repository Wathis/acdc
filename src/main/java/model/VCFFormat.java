package model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="VCFFormat")
public class VCFFormat extends VCFTypedMetadata {
    @Id
    @GeneratedValue
    private Integer id_format;
}
