package com.javamonkeys.entity.useraccessgroup;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "UserAccessGroups")
public class UserAccessGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "isAdmin")
    private Boolean isAdmin;

    public UserAccessGroup(){}

    public UserAccessGroup(String name, Boolean isAdmin){
        setName(name);
        setIsAdmin(isAdmin);
    }

    /**
     * Get user access group ID.
     * @return current user access group ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set user access group ID
     * @param id new user access group ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get user access group name.
     * @return current user access group name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user access group name.
     * @param name new user access group name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get user access group "is admin" attribute.
     * @return current user access group "is admin" attribute
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Set user access group "is admin" attribute
     * @param isAdmin new user access group "is admin" attribute
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccessGroup that = (UserAccessGroup) o;

        if (isAdmin != that.isAdmin) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isAdmin ? 1 : 0);
        return result;
    }
}
