package view;

import controlInputValidation.ControlInput;
import exceptions.InvalidInput;
import exceptions.ManagerNotFound;
import exceptions.UserNotFound;
import models.Bus;
import models.Ticket;
import models.Trip;
import models.enums.*;
import models.roles.Manager;
import models.roles.User;
import services.BusService;
import services.PersonService;
import services.TicketService;
import services.TripService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class View {
    static final Scanner scanner = new Scanner(System.in);
    static final int pageSize = 3;
    private final BusService busService =  new BusService();
    private final PersonService personService = new PersonService();
    private final TripService tripService = new TripService();
    private final TicketService ticketService = new TicketService();

    public void addNewTrip(Manager manager) {
        try {
            City origin = chooseOriginOrDestinationCity();
            City destination = chooseOriginOrDestinationCity();

            System.out.println("Enter the Date :yyyy-M-dd hh:mm:ss ");
            scanner.nextLine();
             Date date = new SimpleDateFormat("yyyy-m-dd hh:mm:ss").parse(scanner.nextLine());

            System.out.println("Enter the bus Name & company  :name,company");
            String busInfo = scanner.next();
            Bus bus = busService.findByNameAndCompany(busInfo);
            System.out.println("cost:");
            double cost = scanner.nextDouble();
            tripService.addNewTripToDB(origin, destination, date, bus,cost);

        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }


    }

    public City chooseOriginOrDestinationCity() {
        City city = null;
        while (city == null) {
            System.out.println("enter city :\n1:TABRIZ\n2:TEHRAN\n3:MASHHAD\n4:SHIRAZ\n5:ESFAHAN;");
            int cityChoice = scanner.nextInt();
            city = chooseCity(cityChoice);
        }
        return city;
    }

    public City chooseCity(int cityChoice) {
        City city = null;
        switch (cityChoice) {
            case 1 -> {
                city = City.TABRIZ;
                break;
            }
            case 2 -> {
                city = City.TEHRAN;
                break;
            }
            case 3 -> {
                city = City.MASHHAD;
                break;
            }
            case 4 -> {
                city = City.SHIRAZ;
                break;
            }
            case 5 -> {
                city = City.ESFAHAN;
                break;
            }
            default -> System.out.println("wrong Number");

        }
        return city;
    }

    public void userMenu(User user) {

        int numOfPage = 0;
        System.out.println("origin :");
        City origin = chooseOriginOrDestinationCity();
        System.out.println("destination :");
        City destination = chooseOriginOrDestinationCity();
        List<Trip> onePageOfTrip;
        while (true) {
            onePageOfTrip = tripService.nextPageForSearchingOriginDestination(numOfPage, pageSize, origin, destination);
            System.out.println(onePageOfTrip);
            System.out.println("enter number of your action \n1: previous page \n2: next page \n3: filter the trips  \n4: select the Trip ");
            int actionType = scanner.nextInt();
            switch (actionType) {
                case 1 -> {
                    if (numOfPage != 0) {
                        onePageOfTrip = tripService.nextPageForSearchingOriginDestination(numOfPage--, pageSize, origin, destination);
                        System.out.println(onePageOfTrip);
                    } else System.out.println("previous page not found ");
                    break;
                }
                case 2 -> {
                    onePageOfTrip = tripService.nextPageForSearchingOriginDestination(numOfPage++, pageSize, origin, destination);
                    if (!onePageOfTrip.isEmpty()) {
                        System.out.println(onePageOfTrip);
                    } else System.out.println("next page not found ");
                    break;
                }
                case 3 -> {
                    filterTrips(origin, destination, user);
                }
                case 4 -> {
                    selectTrip(user, onePageOfTrip);
                }

            }
        }
    }

    public void logInPerson() {
        System.out.println("1:manager\n2:User");
        int roleType = scanner.nextInt();
        switch (roleType) {
            case 1 -> {
                Manager manager = managerLogIn();
                managerMenu(manager);
            }
            case 2 -> {
                User user = UserLogin();
                userMenu(user);

            }
        }
    }

    private User UserLogin() {
        System.out.println("enter nationalCode :");
        String nationalCode = scanner.next();
        System.out.println("enter password : ");
        String password = scanner.next();
        if (personService.isCorrectPassword(nationalCode, password, Role.USER)) {
            try {
                User userByNationalCode = personService.findUserByNationalCode(nationalCode);
                return userByNationalCode;
            } catch (UserNotFound e) {
                System.out.println(e.getMessage());
            }

        } else
            System.out.println("wrong password ");
        return null;

    }

    private void managerMenu(Manager manager) {
        outer:
        while (true) {
            System.out.println(" 1:add new trip \n 2: add new bus \n 3 :exit");
            int typeAction = scanner.nextInt();
            switch (typeAction) {
                case 1 -> {
                    addNewTrip(manager);
                }
                case 2 -> {
                    addNewBus(manager);
                }
                case 3 -> {
                    break outer;
                }
            }
        }
    }

    private void addNewBus(Manager manager) {
        System.out.println("enter bus info : name,company,capacity,BusType");
        String busInfo = scanner.next();
        busService.addNewBasToDB(busInfo);
    }

    private Manager managerLogIn() {
        System.out.println("enter nationalCode :");
        String nationalCode = scanner.next();
        System.out.println("enter password : ");
        String password = scanner.next();
        if (personService.isCorrectPassword(nationalCode, password, Role.MANAGER)) {
            try {
                Manager managerByNationalCode = personService.findManagerByNationalCode(nationalCode);
                return managerByNationalCode;
            } catch (ManagerNotFound e) {
                System.out.println(e.getMessage());
            }

        } else
            System.out.println("wrong password ");
        return null;

    }

    private void selectTrip(User user, List<Trip> trips) {
        System.out.println("select the id of trip ");
        int tripId = scanner.nextInt();
        Trip selectedTrip = trips.stream().filter(trip -> trip.getId() == tripId).findAny().get();
        if (selectedTrip.getTripStatus() != TripStatus.DONE && selectedTrip.getCapacityTripStatus() != CapacityTripStatus.FULL) {
            List<Integer> numberSeatSold = new ArrayList<>();

            for (Ticket ticket : selectedTrip.getTickets()) {
                System.out.println("these seat was seat :" + ticket.getSeatNumber());
                numberSeatSold.add(ticket.getSeatNumber());
            }

            System.out.println("enter the seat number :");
            int seatNumber = scanner.nextInt();

            if (!numberSeatSold.contains(seatNumber)) {
                ticketService.selectTripToBuyTicket(selectedTrip, user, seatNumber);
            }

        } else {
            System.out.println("select trip that have capacity ");
        }
        showTicketInfo(user);
    }

    private void filterTrips(City origin, City destination, User user) {
        try {
            Date startDate = null;
            Date endDate = null;
            BusType busType = null;
            double minCast = 0;
            double maxCast = 0;
            int numOfPage = 0;
            List<Trip> onePageOfFilteredTrip;

            System.out.println("enter type of filter \n 1:filter by Date ? y/n");
            char answer = scanner.next().charAt(0);
            if (ControlInput.isValidChar(answer)) {
                if (answer == 'y') {
                    System.out.println("start date : ");
                    String startDateString = scanner.next();
                    startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(startDateString);
                    System.out.println("end date : ");
                    String endDateString = scanner.next();
                    endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(endDateString);
                }
            }

            System.out.println("2 :filter by bus Type? y/n");
            answer = scanner.next().charAt(0);
            if (ControlInput.isValidChar(answer)) {
                if (answer == 'y') {
                    System.out.println(" bus type  : ");
                    String busTypeString = scanner.next();
                    if (ControlInput.isValidBusType(busTypeString))
                        busType = BusType.valueOf(busTypeString);
                }
            }

            System.out.println("2 :filter by cost ? y/n");
            answer = scanner.next().charAt(0);
            if (ControlInput.isValidChar(answer)) {
                if (answer == 'y') {
                    System.out.println("min cost  : ");
                    String minCostString = scanner.next();
                    minCast = Double.parseDouble(minCostString);
                    System.out.println("maxCost  : ");
                    String maxCostString = scanner.next();
                    maxCast = Double.parseDouble(maxCostString);
                }
            }
            outer:
            while (true) {
                onePageOfFilteredTrip = tripService.filterDynamic(numOfPage, pageSize, origin, destination, startDate, endDate, busType, minCast, maxCast);
                System.out.println(onePageOfFilteredTrip);
                System.out.println("enter number of your action \n 1: previous page \n2: next page \n3: select the Trip\n4:exit");
                int actionType = scanner.nextInt();
                switch (actionType) {
                    case 1 -> {
                        if (numOfPage != 0) {
                            onePageOfFilteredTrip = tripService.filterDynamic(numOfPage--, pageSize, origin, destination, startDate, endDate, busType, minCast, maxCast);
                            System.out.println(onePageOfFilteredTrip);
                        } else System.out.println("previous page not found ");
                    }
                    case 2 -> {
                        onePageOfFilteredTrip = tripService.filterDynamic(numOfPage++, pageSize, origin, destination, startDate, endDate, busType, minCast, maxCast);
                        if (!onePageOfFilteredTrip.isEmpty()) {
                            System.out.println(onePageOfFilteredTrip);
                        } else System.out.println("next page not found ");
                    }
                    case 3 -> selectTrip(user, onePageOfFilteredTrip);
                    case 4 -> {
                        break outer;
                    }
                }
            }
        } catch (InvalidInput | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showTicketInfo(User user) {
        System.out.println(personService.getUserDtoByUser(user));
    }


}
