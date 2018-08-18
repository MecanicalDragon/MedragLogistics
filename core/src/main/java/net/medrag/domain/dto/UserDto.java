package net.medrag.domain.dto;

import net.medrag.domain.entity.User;
import net.medrag.domain.enums.UserRole;

/**
 * Data Transfer Object of {@link User}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class UserDto implements Dto {

    private Integer id;

    private String username;

    private String email;

    private UserRole role;

    private Integer driverId;


    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", driverId='" + driverId + '\'' +
                '}';
    }
}
