/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.registration;

import java.io.Serializable;

/**
 *
 * @author HUYVU
 */
public class AccountDTO implements Serializable{
    private String username;
    private String password;
    private String fullname;
    private String address;
    private String createDate;
    private boolean active;
    private String role;

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, String fullname, String address, String createDate, boolean active, String role) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
        this.createDate = createDate;
        this.active = active;
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    
    
    public String getAddress() {
        return address;
    }

    

    public String getCreateDate() {
        return createDate;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isActive() {
        return active;
    }


    
    
}
