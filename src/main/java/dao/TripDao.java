package dao;

import models.Trip;
import models.enums.BusType;
import models.enums.City;
import models.roles.Manager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class TripDao extends DataBaseAccess {
    public void saveNewTrip(Trip trip) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(trip);
        transaction.commit();
        session.close();
    }

    public void updateTrip(Trip trip) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(trip);
        transaction.commit();
        session.close();
    }

    public List<Trip> findTripWithDestinationOrigin(int numOfPage, int limit, City origin, City destination) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From Trip trip where trip.origin=: origin and trip.destination =:destination");
        query.setParameter("origin", origin);
        query.setParameter("destination", destination);
        query.setFirstResult(numOfPage);
        query.setMaxResults(numOfPage + limit);
        List<Trip> results = query.getResultList();
        transaction.commit();
        session.close();
        return results;
    }


    public List<Trip> filterDynamic(int numOfPage, int pageSize, City origin,
                                    City destination, Date startDate, Date endDate,
                                    BusType busType, double minCast, double maxCast) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Trip.class, "trip");
        criteria.createAlias("trip.bus","bus");
        Criterion originFilter = Restrictions.eq("trip.origin", origin);
        Criterion destinationFilter = Restrictions.eq("trip.destination", destination);
        criteria.add(originFilter);
        criteria.add(destinationFilter);
        if (endDate !=null || startDate != null) {
            Criterion timeFilter = Restrictions.between("trip.date", startDate, endDate);
            criteria.add(timeFilter);
        }
        if (busType!= null) {
            Criterion busTypeFilter = Restrictions.eq("bus.type", busType);
            criteria.add(busTypeFilter);
        }
        if (minCast!= 0 || maxCast!=0 ) {
            Criterion costFilter = Restrictions.between("trip.cast", minCast, maxCast);
            criteria.add(costFilter);
        }
        criteria.setFirstResult(numOfPage);
        criteria.setMaxResults(pageSize);
        List found = criteria.list();
        transaction.commit();
        session.close();

        return found;
    }
}
