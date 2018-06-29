package net.medrag.model.domain.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a Customer
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@javax.persistence.Entity
@Table(name = "customer")
public class Customer implements Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NaturalId
    @Column(name = "passport")
    String passport;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @Column(name = "phone")
    String phone;

    @Column(name = "email")
    String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    private Set<Cargo> cargoSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Cargo> getCargoSet() {
        return cargoSet;
    }

    public void setCargoSet(Set<Cargo> cargoSet) {
        this.cargoSet = cargoSet;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", passport='" + passport + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
