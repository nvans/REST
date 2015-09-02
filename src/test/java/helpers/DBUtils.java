package helpers;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Utilities for testing database
 *
 * @author Ivan Konovalov
 */
public class DBUtils {

    private static ApplicationContext ctx =
                    new ClassPathXmlApplicationContext("META-INF/applicationContext.xml");

    private static SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");

    /**
     * Drop schema
     */
    // -->
    public static void dropTestDB() {
        System.out.println("*** DROP TESTING DATABASE ***");
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("DROP SCHEMA PUBLIC CASCADE");
        query.executeUpdate();
        System.out.println("*** DATABASE IS DROPPED ***");

    }
    // <--
}
