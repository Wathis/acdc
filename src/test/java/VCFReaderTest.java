import model.VCFFile;
import model.VCFTest;
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

public class VCFReaderTest {

    SessionFactory sessionFactory;

    @Test
    public void premierTest() {
        VCFReader reader = VCFReader.getSharedInstance();
        VCFFile vcfFile;
        try {
            vcfFile = reader.loadVCFFile("vcfFile.vcf");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1,1);
    }

    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch(HibernateException exception){
            System.out.println("Problem creating session factory");
            exception.printStackTrace();
        }
    }

    @Test
    public void testHibernate() {
        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save( new VCFTest(1,"Mathis"));
        session.getTransaction().commit();
        session.close();
        Assert.assertEquals(1,1);*/
    }

}
