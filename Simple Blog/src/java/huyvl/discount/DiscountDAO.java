/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.discount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import huyvl.ultites.DBHelper;

/**
 *
 * @author HUYVU
 */
public class DiscountDAO implements Serializable{
    private List<DiscountDTO> discountList;
    public List<DiscountDTO> getDiscountList(){
        return this.discountList;
    }
    public void getAllDiscount()
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT discountID ,discountName, discountCost, expirationDate "
                        + "FROM tblDiscount";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    String discountID  = rs.getString("discountID");
                    String discountName = rs.getString("discountName");
                    String discountCost = rs.getString("discountCost");
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String expirationDate = df.format(rs.getDate("expirationDate"));
                    DiscountDTO dto = new DiscountDTO(discountID ,discountName, discountCost, expirationDate);
                    if(this.discountList == null){
                        this.discountList = new ArrayList<>();
                    }
                    this.discountList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
    public DiscountDTO getDiscountByID(String discountID)
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT discountID ,discountName, discountCost, expirationDate "
                        + "FROM tblDiscount "
                        + "WHERE discountID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountID);
                rs = stm.executeQuery();
                if(rs.next()){
                     discountID  = rs.getString("discountID");
                    String discountName = rs.getString("discountName");
                    String discountCost = rs.getString("discountCost");
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String expirationDate = df.format(rs.getDate("expirationDate"));
                    DiscountDTO dto = new DiscountDTO(discountID ,discountName, discountCost, expirationDate);
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
    public DiscountDTO getDiscountByName(String discountName)
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT discountID ,discountName, discountCost, expirationDate "
                        + "FROM tblDiscount "
                        + "WHERE discountName = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, discountName);
                rs = stm.executeQuery();
                if(rs.next()){
                    String discountID  = rs.getString("discountID");
                    discountName = rs.getString("discountName");
                    String discountCost = rs.getString("discountCost");
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String expirationDate = df.format(rs.getDate("expirationDate"));
                    DiscountDTO dto = new DiscountDTO(discountID ,discountName, discountCost, expirationDate);
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
}
