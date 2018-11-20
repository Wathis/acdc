# ************************************************************
# Script pour créer la base de données principale du projet
# Base de donnée doit être créée et nommée acdc
# ************************************************************

##############################################################
#                          A LIRE                            # 
# /!\ CHANGER LES ADRESSES ET IDENTIFIANTS                   # 
# /!\ CHANGER UNIQUEMENT : user:pass@host:port               #
# /!\ CONSEIL : Utiliser un Search and Replace               #
# /!\ EXECUTER auparavant script_acdc_server_one.sql et      #
#     script_acdc_server_two.sql                             #
##############################################################

# VCFBody FEDERATED
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
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_one/VCFBody' DEFAULT CHARSET=utf8;



# VCFBody_VCFGenotype FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFBody_VCFGenotype`;

CREATE TABLE `VCFBody_VCFGenotype` (
  `VCFBody_id_body` int(11) NOT NULL,
  `genotypes_id_genotype` int(11) NOT NULL
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_one/VCFBody_VCFGenotype' DEFAULT CHARSET=utf8;



# VCFContig FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFContig`;

CREATE TABLE `VCFContig` (
  `id_contig` int(11) NOT NULL,
  `ID` blob,
  `assembly` blob,
  `length` blob,
  PRIMARY KEY (`id_contig`)
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_one/VCFContig' DEFAULT CHARSET=utf8;



# VCFFile FEDERATED
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
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_one/VCFFile' DEFAULT CHARSET=utf8;

# ------------------------------------------------------------
#                     linked to acdc_server_two
# ------------------------------------------------------------

# VCFFile_VCFBody FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFBody`;

CREATE TABLE `VCFFile_VCFBody` (
  `VCFFile_id_file` int(11) NOT NULL,
  `body_id_body` int(11) NOT NULL
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFFile_VCFBody' DEFAULT CHARSET=utf8;


 
# VCFFile_VCFContig FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFContig`;

CREATE TABLE `VCFFile_VCFContig` (
  `VCFFile_id_file` int(11) NOT NULL,
  `contigs_id_contig` int(11) NOT NULL
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFFile_VCFContig' DEFAULT CHARSET=utf8;



# VCFFile_VCFFilter FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFFilter`;

CREATE TABLE `VCFFile_VCFFilter` (
  `VCFFile_id_file` int(11) NOT NULL,
  `filters_id_filter` int(11) NOT NULL
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFFile_VCFFilter' DEFAULT CHARSET=utf8;




# VCFFile_VCFFormat FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFFormat`;

CREATE TABLE `VCFFile_VCFFormat` (
  `VCFFile_id_file` int(11) NOT NULL,
  `formats_id_format` int(11) NOT NULL
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFFile_VCFFormat' DEFAULT CHARSET=utf8;



# VCFFile_VCFInfo FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFile_VCFInfo`;

CREATE TABLE `VCFFile_VCFInfo` (
  `VCFFile_id_file` int(11) NOT NULL,
  `infos_id_info` int(11) NOT NULL
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFFile_VCFInfo' DEFAULT CHARSET=utf8;



# VCFFilter FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFilter`;

CREATE TABLE `VCFFilter` (
  `id_filter` int(11) NOT NULL,
  `ID` blob,
  `description` blob,
  PRIMARY KEY (`id_filter`)
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFFilter' DEFAULT CHARSET=utf8;



# VCFFormat FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFFormat`;

CREATE TABLE `VCFFormat` (
  `id_format` int(11) NOT NULL,
  `ID` blob,
  `description` blob,
  `number` blob,
  `type` blob,
  PRIMARY KEY (`id_format`)
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFFormat' DEFAULT CHARSET=utf8;



# VCFGenotype FEDERATED
# ------------------------------------------------------------

DROP TABLE IF EXISTS `VCFGenotype`;

CREATE TABLE `VCFGenotype` (
  `id_genotype` int(11) NOT NULL,
  `data` blob,
  `name` blob,
  PRIMARY KEY (`id_genotype`)
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFGenotype' DEFAULT CHARSET=utf8;



# VCFInfo FEDERATED
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
) ENGINE=FEDERATED CONNECTION='mysql://root:root@127.0.0.1/acdc_server_two/VCFInfo' DEFAULT CHARSET=utf8;





