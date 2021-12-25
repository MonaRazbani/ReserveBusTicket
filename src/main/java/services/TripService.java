package services;

import dao.TripDao;
import exceptions.TripNotFound;
import models.Bus;
import models.Trip;
import models.enums.BusType;
import models.enums.City;
import models.roles.User;

import java.util.Date;
import java.util.List;

public class TripService {
    private TripDao tripDao = new TripDao();

    public void addNewTripToDB(City origin , City destination , Date date , Bus bus,double cost){
        Trip trip = new Trip();
        trip.setBus(bus);
        trip.setOrigin(origin);
        trip.setDestination(destination);
        trip.setTripDate(date);
        trip.setCost(cost);
        tripDao.saveNewTrip(trip);
    }

    public List<Trip> nextPageForSearchingOriginDestination (int numOfPage , int pageSize , City origin , City destination ) {
         List <Trip > trips = tripDao.findTripWithDestinationOrigin(numOfPage , pageSize , origin , destination);
         try {
             if ( trips.isEmpty())
                 throw new TripNotFound();
         }catch (TripNotFound e){
             System.out.println(e.getMessage());
         }
         return trips ;
    }

    public List<Trip> filterDynamic(int numOfPage, int pageSize, City origin,
                                    City destination, Date startDate, Date endDate,
                                    BusType busType, double minCast, double maxCast){
        List<Trip> trips = tripDao.filterDynamic(numOfPage, pageSize, origin, destination, startDate, endDate, busType, minCast, maxCast);
        try {
            if ( trips.isEmpty())
                throw new TripNotFound();
        }catch (TripNotFound e){
            System.out.println(e.getMessage());
        }
        return trips ;
    }



}
