package training.spring.monolithic.dto;

import training.spring.monolithic.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long userId;

    @NotNull
    @NotEmpty
    private String fullName;


    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String userName;

    @NotNull
    @NotEmpty
    private String password;

    private int status;

    public UserDTO(Users users) {
        this.userId = users.getUserId();
        this.fullName = users.getFullName();
        this.email = users.getEmail();
        this.userName = users.getUserName();
        this.password = users.getPassword();
        this.status = users.getStatus();
    }
}
