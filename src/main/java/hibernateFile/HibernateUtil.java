package hibernateFile;

import com.mysql.cj.xdevapi.Client;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/reserveBusTicket");
                properties.put(Environment.USER, "mona_razbani");
                properties.put(Environment.PASS, "Pendi_Pengi142131");
                properties.put(Environment.DIALECT, ">org.hibernate.dialect.MySQL8Dialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(Client.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
