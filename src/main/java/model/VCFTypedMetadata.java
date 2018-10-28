package model;

import htsjdk.variant.vcf.VCFHeaderLineCount;
import htsjdk.variant.vcf.VCFHeaderLineType;
import lombok.Data;

@Data
public abstract class VCFTypedMetadata extends VCFMetadata {
    protected VCFHeaderLineType type;
    protected VCFHeaderLineCount number;
}
