package dao;

import lombok.Data;
import models.Bus;
import models.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TicketDao extends DataBaseAccess {

    public void addTicket(Ticket ticket) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(ticket);
        transaction.commit();
        session.close();
    }
}
