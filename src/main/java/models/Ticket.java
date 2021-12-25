package models;

import lombok.Builder;
import lombok.Data;
import models.enums.City;
import models.roles.User;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    @ManyToOne
    private User user;
    private int seatNumber ;
    @ManyToOne(fetch = FetchType.EAGER)
    private Trip trip ;

}
