package session;

import exception.UnopenedSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;

/**
 * Session permettant de sauvegarder un objet et d'accéder à la base de données.
 * Accès a la classe par singleton
 */
public class VCFSession {

    private SessionFactory sessionFactory;
    private Session session = null;
    private static VCFSession sharedInstance;

    private VCFSession() {

    }

    public static VCFSession getSharedInstance() {
        if (sharedInstance == null)
            sharedInstance = new VCFSession();
        return sharedInstance;
    }


    public void checkSession() throws UnopenedSession {
        if (session == null)
            throw new UnopenedSession();
    }

    /**
     * Sauvegarder un objet en base de données
     * @param object
     * @throws UnopenedSession
     */
    public void save(Object object) throws UnopenedSession  {
        checkSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
    }

    /**
     * Obtenir la session ouverte à la base de données
     * @return
     * @throws UnopenedSession quand aucune session n'a été ouverte
     */
    public Session getSession() throws UnopenedSession  {
        checkSession();
        return session;
    }

    /**
     * Ouvrir la connexion à la base de données
     */
    public void open() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (HibernateException e) {
            e.printStackTrace();
            fail("Fail due to : " + e.getMessage());
        }
    }

    /**
     * Fermer la connexion
     */
    public void close() {
        session.close();
    }

}
