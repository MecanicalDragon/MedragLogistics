package net.medrag.model.domain.dto;

import java.util.List;

/**
 * Data Transfer Object of {net.medrag.model.domain.entity.Customer}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class CustomerDto implements Dto{

    private Integer id;

    private String passport;

    private String name;

    private String surname;

    private String phone;

    private String email;

    private List<CargoDto> parcelList;



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

    public List<CargoDto> getParcelList() {
        return parcelList;
    }

    public void setParcelList(List<CargoDto> parcelList) {
        this.parcelList = parcelList;
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
        CustomerDto that = (CustomerDto) o;

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
