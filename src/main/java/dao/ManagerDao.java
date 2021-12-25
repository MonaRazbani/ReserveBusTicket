package dao;

import models.roles.Manager;
import models.roles.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ManagerDao extends DataBaseAccess{
    public void saveNewManager(Manager manager) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(manager);
        transaction.commit();
        session.close();
    }
    public void updateManager(Manager manager) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(manager);
        transaction.commit();
        session.close();
    }


    public Manager findByNationalCode(String nationalCode){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Manager> query = session.createQuery("FROM Manager manager WHERE manager.nationalCode=:nationalCode ");
        query.setParameter("nationalCode", nationalCode);
        Manager result = query.getSingleResult();
        transaction.commit();
        session.close();
        return result;
    }


}
