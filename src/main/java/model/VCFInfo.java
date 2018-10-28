package model;

import lombok.Data;

@Data
public class VCFInfo extends VCFTypedMetadata {
	protected String source;
	protected String version;
}
