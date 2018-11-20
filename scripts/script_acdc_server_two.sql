# ************************************************************
# Script pour créer la deuxième base de données
# Base de donnée doit être créée et nommée acdc_server_two
# ************************************************************

# VCFFile_VCFBody
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFBody`;

CREATE TABLE `VCFFile_VCFBody` (
  `VCFFile_id_file` int(11) NOT NULL,
  `body_id_body` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFFile_VCFContig
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFContig`;

CREATE TABLE `VCFFile_VCFContig` (
  `VCFFile_id_file` int(11) NOT NULL,
  `contigs_id_contig` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFFile_VCFFilter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFFilter`;

CREATE TABLE `VCFFile_VCFFilter` (
  `VCFFile_id_file` int(11) NOT NULL,
  `filters_id_filter` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




# VCFFile_VCFFormat
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFFormat`;

CREATE TABLE `VCFFile_VCFFormat` (
  `VCFFile_id_file` int(11) NOT NULL,
  `formats_id_format` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFFile_VCFInfo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFInfo`;

CREATE TABLE `VCFFile_VCFInfo` (
  `VCFFile_id_file` int(11) NOT NULL,
  `infos_id_info` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFFilter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFilter`;

CREATE TABLE `VCFFilter` (
  `id_filter` int(11) NOT NULL,
  `ID` blob,
  `description` blob,
  PRIMARY KEY (`id_filter`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFFormat
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFormat`;

CREATE TABLE `VCFFormat` (
  `id_format` int(11) NOT NULL,
  `ID` blob,
  `description` blob,
  `number` blob,
  `type` blob,
  PRIMARY KEY (`id_format`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFGenotype
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFGenotype`;

CREATE TABLE `VCFGenotype` (
  `id_genotype` int(11) NOT NULL,
  `data` blob,
  `name` blob,
  PRIMARY KEY (`id_genotype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# VCFInfo
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFInfo`;

CREATE TABLE `VCFInfo` (
  `id_info` int(11) NOT NULL,
  `ID` blob,
  `description` blob,
  `number` blob,
  `type` blob,
  `source` blob,
  `version` blob,
  PRIMARY KEY (`id_info`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





