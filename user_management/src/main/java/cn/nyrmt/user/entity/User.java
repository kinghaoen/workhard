package cn.nyrmt.user.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    private String id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    private String permission;

    public User() {
    }

    public User(String id, String username, String password, String permission) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }

    public boolean hasPermission(String method){
        boolean result;
        if(StringUtils.equalsIgnoreCase(method,"get")){
            result = StringUtils.containsIgnoreCase(permission,"r");
        }else{
            result = StringUtils.containsIgnoreCase(permission,"w");
        }
        return result;
    }
}
