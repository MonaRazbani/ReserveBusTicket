package models.Dto;

import lombok.Data;
import models.Ticket;

import java.util.List;
@Data
public class UserDto extends PersonDto {
    private List<Ticket> tickets ;
}
