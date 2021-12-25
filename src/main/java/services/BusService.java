package services;

import controlInputValidation.ControlInput;
import dao.BusDao;
import exceptions.BusNotFound;
import exceptions.InvalidInput;
import models.Bus;
import models.enums.BusType;

public class BusService {
    private BusDao busDao = new BusDao();


    public void addNewBasToDB(String busInfo) {
        if (ControlInput.isValidBusInfo(busInfo)) {
            //name,company,capacity,BusType
            String[] splitBusInfo = busInfo.split(",");
            Bus bus = new Bus();
            bus.setName(splitBusInfo[0]);
            bus.setCompany(splitBusInfo[1]);
            bus.setPassengerCapacity(Integer.parseInt(splitBusInfo[2]));
            bus.setType(BusType.valueOf(splitBusInfo[3]));
            busDao.addBus(bus);
        }
    }


    public Bus findByNameAndCompany(String busInfo) {
        try {
            String[] split = busInfo.split(",");
            String name = split[0];
            String companyName = split[1];

            if (ControlInput.isValidUserNameOrCompanyNameOrBusName(name) &
                    ControlInput.isValidUserNameOrCompanyNameOrBusName(companyName)) {
                Bus bus = busDao.findBusByNameAndCompanyName(name, companyName);

                if (bus == null) {
                    throw new BusNotFound();
                } else return bus;

            } else throw new InvalidInput("invalid Bus name Or company name ");
        } catch (InvalidInput | BusNotFound e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
