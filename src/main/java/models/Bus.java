package models;

import lombok.Data;
import models.enums.BusType;

import javax.persistence.*;
import java.util.List;


@Data
    @Entity
    public class Bus {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id ;
        private String name ;
        @OneToMany(cascade = CascadeType.ALL , mappedBy = "bus")
        private List<Trip> trips;
        private int passengerCapacity ;
        @Enumerated (EnumType.STRING)
        private BusType type;
        private String company ;

}
