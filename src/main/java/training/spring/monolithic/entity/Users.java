package training.spring.monolithic.entity;

import training.spring.monolithic.dto.UserDTO;
import training.spring.monolithic.dto.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(name = "fullname", length = 45)
    private String fullName;
    @ValidEmail
    @Column(name = "email", length = 45)
    private String email;
    @Column(name = "username", length = 45)
    private String userName;

    @Column(name = "password", length = 60)
    private String password;
    @CreationTimestamp
    @Column(name = "createdat", updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updatedat")
    private Date updatedAt;
    @Column(name = "status", columnDefinition = "int default 1")
    private int status;
    @Column(name = "enabled", columnDefinition = "boolean default true")
    private boolean enabled;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;


    public Users(UserDTO userDTO) {
        this.userId = userDTO.getUserId();
        this.fullName = userDTO.getFullName();
        this.email = userDTO.getEmail();
        this.userName = userDTO.getUserName();
        this.password = userDTO.getPassword();
        this.status = userDTO.getStatus();
    }
}
