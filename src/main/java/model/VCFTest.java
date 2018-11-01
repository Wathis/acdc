package model;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VCFTest")
@AllArgsConstructor
public class VCFTest {
    @Id
    @GeneratedValue
    private Integer id;
    private String test;
}
