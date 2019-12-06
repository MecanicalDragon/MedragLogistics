package net.medrag.domain.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Simple JavaBean domain object, that represents a Customer
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Entity
@Table(name = "CUSTOMER")
public class Customer extends Identifier{

    @NaturalId
    @Column(name = "PASSPORT")
    private String passport;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "owner")
    private Set<Cargo> cargoSet;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer that = (Customer) o;

        if (getId() != null) {
            return getId().equals(that.getId());
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : super.hashCode();
    }
}
