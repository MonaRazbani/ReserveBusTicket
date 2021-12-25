package services;

import dao.TicketDao;
import dao.TripDao;
import models.Ticket;
import models.Trip;
import models.enums.CapacityTripStatus;
import models.roles.User;

public class TicketService {

    private  TicketDao ticketDao = new TicketDao();
    private TripDao tripDao = new TripDao();

    public void selectTripToBuyTicket (Trip selectedTrip, User user, int seatNumber){
        Ticket ticket = new Ticket();
        ticket.setTrip(selectedTrip);
        ticket.setUser(user);
        ticket.setSeatNumber(seatNumber);

        selectedTrip.getTickets().add(ticket);
        if (selectedTrip.getTickets().size()==selectedTrip.getBus().getPassengerCapacity())
        selectedTrip.setCapacityTripStatus(CapacityTripStatus.FULL);
        else selectedTrip.setCapacityTripStatus(CapacityTripStatus.NOT_FULL);
        tripDao.updateTrip(selectedTrip);
        ticketDao.addTicket(ticket);
    }
}
