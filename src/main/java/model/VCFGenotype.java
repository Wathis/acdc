package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VCFGenotype {

    private String name;
    private String data;

}
