package model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="VCFFilter")
public class VCFFilter extends VCFMetadata {
    @Id
    @GeneratedValue
    private Integer id_filter;
}
