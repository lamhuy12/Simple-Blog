/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.naming.NamingException;
import org.apache.commons.codec.digest.DigestUtils;
import huyvl.ultites.DBHelper;

/**
 *
 * @author HUYVU
 */
public class AccountDAO implements Serializable{
    private List<AccountDTO> accountList;
    public List<AccountDTO> getAccountList(){
        return this.accountList;
    }
    private String hashPassword(String password) {
        String myHashPassword = DigestUtils.sha256Hex(password);
        return myHashPassword;
    }
    public boolean registerAccount(String username, String password, String fullname, String address)
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "INSERT INTO tblAccount(username,password,fullname,address) "
                        + "VALUES (?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, hashPassword(password));
                stm.setString(3, fullname);
                stm.setString(4, address);
                int result = stm.executeUpdate();
                return result>0;
            }
        } finally {
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        
        return false;
    }
    public String getAccountRole(String role){
        if(role.equalsIgnoreCase("1")){
            return "ADMIN";
        }else if(role.equalsIgnoreCase("2")){
            return "PARTNER";
        }else if(role.equalsIgnoreCase("3")){
            return "CUSTOMER";
        }
        return null;
    }
    public AccountDTO checkLogin(String username, String password)
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT username, password, fullname, address, createDate, isActive, role "
                        + "FROM tblAccount "
                        + "WHERE username = ? AND password = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, hashPassword(password));
                rs = stm.executeQuery();
                if(rs.next()){
                    username = rs.getString("username");
                    password = rs.getString("password");
                    String fullname = rs.getString("fullname");
                    String address = rs.getString("address");
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String createDate = df.format(rs.getDate("createDate"));
                    boolean isActive = rs.getBoolean("isActive");
                    String role = rs.getString("role");
                    AccountDTO dto = new AccountDTO(username, password, fullname, address, createDate, isActive, getAccountRole(role));
                    return dto;
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        
        return null;
    }
    public AccountDTO getAccountByUsename(String username)
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT username, password, fullname, address, createDate, isActive, role "
                        + "FROM tblAccount "
                        + "WHERE username = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                if(rs.next()){
                    username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullname = rs.getString("fullname");
                    String address = rs.getString("address");
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String createDate = df.format(rs.getDate("createDate"));
                    boolean isActive = rs.getBoolean("isActive");
                    String role = rs.getString("role");
                    AccountDTO dto = new AccountDTO(username, password, fullname, address, createDate, isActive, getAccountRole(role));
                    return dto;
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        
        return null;
    }
    public boolean checkEmailIsExist(String username)
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT username "
                        + "FROM tblAccount "
                        + "WHERE username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                if(rs.next()){
                    return true;
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        return false;
    }
}
