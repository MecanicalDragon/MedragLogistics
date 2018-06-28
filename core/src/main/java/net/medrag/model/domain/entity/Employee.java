package net.medrag.model.domain.entity;

import net.medrag.model.domain.enums.Role;

import javax.persistence.*;

/**
 * JavaBean domain object, that represents an employee
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "PERSONAL_NUMBER")
    private String personalNumber;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "enum('ROLE_DRIVER', 'ROLE_MANAGER')")
    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", personalNumber='" + personalNumber + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
