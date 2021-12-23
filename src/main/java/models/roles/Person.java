package models.roles;

import lombok.Builder;
import lombok.Data;
import models.enums.Gender;
import models.enums.Role;

import javax.persistence.*;

@Data
@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName ;
    private String LastName ;
    private String username ;
    private String password ;
    private String nationalCode ;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Role role;
}
