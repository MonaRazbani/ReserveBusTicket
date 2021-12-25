package dao;

import models.Bus;
import models.roles.Manager;
import models.roles.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class BusDao extends DataBaseAccess {
    public void addBus(Bus bus) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(bus);
        transaction.commit();
        session.close();
    }

    public Bus findBusByNameAndCompanyName(String name, String companyName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Bus> query = session.createQuery("FROM Bus bus WHERE bus.name=:name and bus.company=:company");
        query.setParameter("name", name);
        query.setParameter("company", companyName);
        Bus result = query.getSingleResult();
        transaction.commit();
        session.close();
        return result;
    }
}

