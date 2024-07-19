package ua.denys.carrentalservice.loginapp.common.db.user.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.denys.carrentalservice.loginapp.common.db.ModelPattern;

@Builder
@Getter
@Setter
public class User extends ModelPattern {
    Long id;
    String username;
    String password;
}
