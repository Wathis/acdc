import model.VCFFile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import io.VCFReader;
import utils.HashUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.fail;

public class VCFReaderTest {

    SessionFactory sessionFactory;

    @Test
    public void test01LoadFile() {
        VCFReader reader = VCFReader.getSharedInstance();
        VCFFile vcfFile = null;
        try {
            vcfFile = reader.loadVCFFile("vcfFile.vcf");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(vcfFile);
        Assert.assertTrue(vcfFile.getBody().size() != 0);
    }

    @Test
    public void test02Hibernate() {
        Session session = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            VCFReader reader = VCFReader.getSharedInstance();
            VCFFile vcfFile = reader.loadVCFFile("vcfFile.vcf");
            session.save(vcfFile);
            session.getTransaction().commit();
            Query query = session.createQuery("FROM VCFFile");
            List resultList = query.getResultList();
            Assert.assertTrue(resultList.size() > 0);
        } catch (HibernateException e) {
            e.printStackTrace();
            fail("Fail due to : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Fail due to : " + e.getMessage());
        } finally {
            if (session != null)
                session.close();
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
