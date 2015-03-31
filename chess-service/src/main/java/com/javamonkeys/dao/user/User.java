package com.javamonkeys.dao.user;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "email", unique=true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "token")
    private String token;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "userAccessGroup_id")
    private UserAccessGroup userAccessGroup;

    protected User(){}

    User(String email, String password){
        setEmail(email);
        setPassword(password);
    }

    User(String email, String password, Date birthDate){
        setEmail(email);
        setPassword(password);
        setBirthDate(birthDate);
    }

    User(String email, String password, UserAccessGroup userAccessGroup){
        setEmail(email);
        setPassword(password);
        setUserAccessGroup(userAccessGroup);
    }

    User(String email, String password, Date birthDate, UserAccessGroup userAccessGroup){
        setEmail(email);
        setPassword(password);
        setBirthDate(birthDate);
        setUserAccessGroup(userAccessGroup);
    }

    /**
     * Get user ID.
     * @return current user ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set user ID.
     * @param id new user ID
     */
    public void setId(int id) {
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
     * Get token.
     * @return current user token
     */
    public String getToken() {
        return token;
    }

    /**
     * Set user token.
     * @param token new user token
     */
    public void setToken(String token) {
        this.token = token;
    }

}
