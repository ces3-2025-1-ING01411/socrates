package co.edu.poli.ces3.socrates.socrates.dao;

import java.util.Date;

public class User {
    private Integer id;
    private String names;
    private String lastName;
    private Date birthdate;
    private String email;
    private Boolean isActive;
    private String phone;
    private String gender; //crear enum
    private String password;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public User(String names, String lastName) {
        this.names = names;
        this.lastName = lastName;
    }

    public User(Integer id, String names, String lastName, Date birthdate, String email, Boolean isActive, String phone, String gender, String password, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.names = names;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.isActive = isActive;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
