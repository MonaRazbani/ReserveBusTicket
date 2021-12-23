package models.roles;

import lombok.Data;
import models.Ticket;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User extends Person{
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , mappedBy = "user")
    private List<Ticket> tickets ;
    //todo add what to be needed ;
}
