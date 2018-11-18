package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="VCFFormat")
public class VCFFormat extends VCFTypedMetadata {
    @Id
    @GeneratedValue
    private Integer id_format;
}
