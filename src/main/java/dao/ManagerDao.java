/*
package dao;

import models.roles.Manager;
import models.roles.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ManagerDao extends DataBaseAccess{
    public void saveNewPerson(Person person) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(person);
        transaction.commit();
        session.close();
    }
    public void updatePerson(Person person) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(person);
        transaction.commit();
        session.close();
    }


    public Manager FindByNationalCode(String nationalCode){
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
*/
