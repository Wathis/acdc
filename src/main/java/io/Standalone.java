package io;

import exception.UnopenedSession;
import model.VCFFile;
import session.VCFSession;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Standalone {

    /**
     * How to use :
     * java -jar standalone.jar mysql_user mysql_password path_to_vcf_file
     * OR
     * java -jar standalone.jar path_to_vcf_file (Default mysql will be root:root)
     * @param args
     */
    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        VCFSession vcfSession = VCFSession.getSharedInstance();
        String filepath;
        System.out.println("[I] Opening database session");
        if (args.length == 3) {
            vcfSession.open(args[0],args[1]);
            filepath = args[2];
        } else {
            vcfSession.open();
            filepath = args[0];
        }
        System.out.println("[I] Database session opened");
        VCFFile vcfFile = extractVCFFile(filepath);
        insertFileInDatabase(vcfSession,vcfFile);
        vcfSession.close();
        System.out.println("[I] Database session closed");
        System.exit(0);
    }

    public static void insertFileInDatabase(VCFSession vcfSession, VCFFile file) {
        try {
            System.out.println("[I] Saving datas");
            vcfSession.save(file);
            System.out.println("[I] Saving in database succeed");
        } catch (UnopenedSession unopenedSession) {
            unopenedSession.printStackTrace();
        }
    }

    public static VCFFile extractVCFFile(String filepath) {
        System.out.println("[I] Extracting datas from " + filepath);
        VCFReader reader = VCFReader.getSharedInstance();
        VCFFile vcfFile = null;
        try {
            vcfFile = reader.loadVCFFile(new File(filepath));
            System.out.println("[I] Extracting succeed");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return vcfFile;
    }

}
