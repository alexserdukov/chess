package com.javamonkeys.entity.user;

import com.javamonkeys.entity.useraccessgroup.UserAccessGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "birthDate")
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "userAccessGroupId")
    private UserAccessGroup userAccessGroup;

    public User(){}

    public User(String email, String password){
        setEmail(email);
        setPassword(password);
    }

    public User(String email, String password, Date birthDate, UserAccessGroup userAccessGroup){
        this(email, password);
        setBirthDate(birthDate);
        setUserAccessGroup(userAccessGroup);
    }

    /**
     * Get user ID.
     * @return current user ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set user ID.
     * @param id new user ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get user email.
     * @return current user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user email.
     * @param email new user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get user birth date.
     * @return current user birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Set user birth date.
     * @param birthDate new user birth date
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Get user access group.
     * @return current user access group
     */
    public UserAccessGroup getUserAccessGroup() {
        return userAccessGroup;
    }

    /**
     * Set user access group.
     * @param userAccessGroup new user access group
     */
    public void setUserAccessGroup(UserAccessGroup userAccessGroup) {
        this.userAccessGroup = userAccessGroup;
    }

    /**
     * Get user password.
     * @return current user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user password.
     * @param password new user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user name.
     * @return current user name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name.
     * @param name new user name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (birthDate != null ? !birthDate.equals(user.birthDate) : user.birthDate != null) return false;
        return !(userAccessGroup != null ? !userAccessGroup.equals(user.userAccessGroup) : user.userAccessGroup != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (userAccessGroup != null ? userAccessGroup.hashCode() : 0);
        return result;
    }
}
