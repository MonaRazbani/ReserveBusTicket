package models;

import lombok.Data;
import models.enums.CapacityTripStatus;
import models.enums.City;
import models.enums.TripStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    @ManyToOne(cascade = CascadeType.ALL )
    private Bus bus ;
    private Date tripDate ;
    private City origin ;
    private City destination;
    @OneToMany(cascade = CascadeType.ALL , mappedBy = "trip")
    private List<Ticket> tickets ;
    private TripStatus tripStatus ;
    private CapacityTripStatus capacityTripStatus;
}
