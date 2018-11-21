package session;

import exception.UnopenedSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
/**
 * Session permettant de sauvegarder un objet et d'accéder à la base de données.
 * Accès a la classe par singleton
 */
public class VCFSession {

    private SessionFactory sessionFactory = null;
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
     * Ouvrir une session avec différents identifiants que ceux par défaut
     * @param username
     * @param password
     */
    public void open(String username, String password) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml"); //hibernate config xml file name
        cfg.getProperties().setProperty("hibernate.connection.password",password);
        cfg.getProperties().setProperty("hibernate.connection.username",username);
        sessionFactory = cfg.buildSessionFactory();
        open();
    }

    /**
     * Ouvrir la connexion à la base de données
     */
    public void open() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            if (sessionFactory == null)
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Fail due to : " + e.getMessage());
        }
    }

    /**
     * Fermer la connexion
     */
    public void close() {
        session.close();
        sessionFactory.close();
    }

}
