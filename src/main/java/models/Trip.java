package models;

import lombok.Data;
import models.enums.CapacityTripStatus;
import models.enums.City;
import models.enums.TripStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    @ManyToOne(fetch = FetchType.EAGER)
    private Bus bus ;
    private Date tripDate ;
    private City origin ;
    private City destination;
    @OneToMany(cascade = CascadeType.PERSIST ,mappedBy = "trip",fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<>();
    private TripStatus tripStatus ;
    private CapacityTripStatus capacityTripStatus;
    private double Cost ;

    @Override
    public String toString() {
        return
                "id : " + id +
                ", bus : " + bus +
                ", tripDate : " + tripDate +
                ", origin : " + origin +
                ", destination : " + destination +
                ", tripStatus : " + tripStatus +
                ", capacityTripStatus : " + capacityTripStatus +
                ", Cost : " + Cost +
                '\n';
    }
}
