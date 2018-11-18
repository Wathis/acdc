package model;

import htsjdk.variant.vcf.VCFHeaderLineCount;
import htsjdk.variant.vcf.VCFHeaderLineType;
import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import static utils.AESUtils.AES_SECRET_KEY;

@Data
@MappedSuperclass
public abstract class VCFTypedMetadata extends VCFMetadata {
    @ColumnTransformer(read="AES_DECRYPT(type,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
    @Column(columnDefinition = "blob")
    protected VCFHeaderLineType type;
    @ColumnTransformer(read="AES_DECRYPT(number,'" + AES_SECRET_KEY + "')" ,write="AES_ENCRYPT(?,'" + AES_SECRET_KEY + "')")
    @Column(columnDefinition = "blob")
    protected VCFHeaderLineCount number;
}
