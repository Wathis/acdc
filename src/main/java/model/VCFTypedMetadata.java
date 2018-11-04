package model;

import htsjdk.variant.vcf.VCFHeaderLineCount;
import htsjdk.variant.vcf.VCFHeaderLineType;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class VCFTypedMetadata extends VCFMetadata {
    protected VCFHeaderLineType type;
    protected VCFHeaderLineCount number;
}
