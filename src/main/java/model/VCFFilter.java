package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="VCFFilter")
public class VCFFilter extends VCFMetadata {
    @Id
    @GeneratedValue
    private Integer id_filter;
}
