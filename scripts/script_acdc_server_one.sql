# ************************************************************
# Script pour créer la première base de données
# Base de donnée doit être créée et nommée acdc_server_one
# ************************************************************

# VCFBody
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFBody`;

CREATE TABLE `VCFBody` (
  `id_body` int(11) NOT NULL,
  `ID` blob,
  `alt` blob,
  `chrom` blob,
  `filters` blob,
  `format` blob,
  `info` blob,
  `pos` blob,
  `qual` blob,
  `ref` blob,
  PRIMARY KEY (`id_body`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFBody_VCFGenotype
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFBody_VCFGenotype`;

CREATE TABLE `VCFBody_VCFGenotype` (
  `VCFBody_id_body` int(11) NOT NULL,
  `genotypes_id_genotype` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFContig
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFContig`;

CREATE TABLE `VCFContig` (
  `id_contig` int(11) NOT NULL,
  `ID` blob,
  `assembly` blob,
  `length` blob,
  PRIMARY KEY (`id_contig`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFFile
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile`;

CREATE TABLE `VCFFile` (
  `id_file` int(11) NOT NULL,
  `fileDate` blob,
  `fileFormat` blob,
  `phasing` blob,
  `reference` blob,
  `source` blob,
  `watermark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_file`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
