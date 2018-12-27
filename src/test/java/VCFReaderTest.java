import model.VCFFile;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import io.VCFReader;
import session.VCFSession;
import utils.HashUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.fail;

public class VCFReaderTest {

    public File getVCFFile() {
        URL resource = VCFReader.class.getResource("vcfFile.vcf");
        File file = null;
        try {
            file = new File(resource.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Test
    public void test01LoadFile() {
        VCFReader reader = VCFReader.getSharedInstance();
        VCFFile vcfFile = null;
        try {
            vcfFile = reader.loadVCFFile(getVCFFile());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(vcfFile);
        Assert.assertTrue(vcfFile.getBody().size() != 0);
    }

    @Test
    public void test02Hibernate() {
        try {
            VCFReader reader = VCFReader.getSharedInstance();
            VCFSession vcfSession = VCFSession.getSharedInstance();
            vcfSession.open();
            VCFFile vcfFile = reader.loadVCFFile(getVCFFile());
            vcfSession.save(vcfFile);
            Query query = vcfSession.getSession().createQuery("FROM VCFFile");
            List resultList = query.getResultList();
            Assert.assertTrue(resultList.size() > 0);
            vcfSession.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            fail("Fail due to : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Fail due to : " + e.getMessage());
        }
    }

    @Test
    public void test03Sha256() {
        URL resource = VCFReaderTest.class.getResource("vcfFile.vcf");
        try {
            File file = new File(resource.toURI());
            String sha = HashUtil.sha256File(file.getPath());
            Assert.assertEquals("87597695d204da5dd52e543ff3e281e807ef85aa4424cb103568f0f215cb2a18",sha);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
