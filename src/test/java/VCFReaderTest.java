import model.VCFChild;
import model.VCFFile;
import model.VCFParent;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.Test;
import utils.VCFReader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class VCFReaderTest {

    SessionFactory sessionFactory;

    @Test
    public void premierTest() {
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
    public void testHibernate() {
        Session session = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save( new VCFParent("Mathis", Arrays.asList(new VCFChild("Child 1"),new VCFChild("Child 2"))));
            session.getTransaction().commit();
            VCFParent vcfParent = (VCFParent) session.createQuery("FROM VCFParent").getSingleResult();
            Assert.assertTrue("Mathis".equals(vcfParent.getEncryptedField()));
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

}
