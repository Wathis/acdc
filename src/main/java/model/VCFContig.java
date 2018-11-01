package model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VCFContig {
    private String ID;
    private int length;
    private String assembly;
}
