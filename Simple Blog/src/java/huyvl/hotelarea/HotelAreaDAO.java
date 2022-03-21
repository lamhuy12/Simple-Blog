/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.hotelarea;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import huyvl.ultites.DBHelper;

/**
 *
 * @author HUYVU
 */
public class HotelAreaDAO implements Serializable{
    private List<HotelAreaDTO> hotelAreaList;
    public List<HotelAreaDTO> getHotelAreaList(){
        return this.hotelAreaList;
    }
    public HotelAreaDTO getAreaByID(String areaID)
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT areaName "
                        + "FROM tblHotelArea "
                        + "WHERE areaID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, areaID);
                rs = stm.executeQuery();
                if(rs.next()){
                    String areaName = rs.getString("areaName");
                    HotelAreaDTO dto = new HotelAreaDTO(areaName);
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
    public void getAllArea()
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT areaName "
                        + "FROM tblHotelArea";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    String areaName = rs.getString("areaName");
                    HotelAreaDTO dto = new HotelAreaDTO(areaName);
                    if(this.hotelAreaList == null){
                        this.hotelAreaList = new ArrayList<>();
                    }
                    this.hotelAreaList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
}
