import models.Bus;
import models.Trip;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {
    static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    public static void main(String[] args) {
        Bus bus = new Bus();
        Trip trip = new Trip();
        trip.setBus(bus);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
       session.save(bus);
        session.save(trip);
        transaction.commit();
        session.close();
    }
}
