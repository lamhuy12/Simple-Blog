/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.roomtype;

import huyvl.ultites.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author HUYVU
 */
public class RoomTypeDAO implements Serializable{
    private List<RoomTypeDTO> typeList;
    public List<RoomTypeDTO> getTypeList(){
        return this.typeList;
    }
     public void getAllRoomType() 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT typeName "
                        + "FROM tblRoomType";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    String typeName = rs.getString("typeName");
                    RoomTypeDTO dto = new RoomTypeDTO(typeName);
                    if(this.typeList == null){
                        this.typeList = new ArrayList<>();
                    }
                    this.typeList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }

    public RoomTypeDTO getAllRoolTypeByID(String typeID) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT typeName "
                        + "FROM tblRoomType "
                        + "WHERE typeID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, typeID);
                rs = stm.executeQuery();
                if(rs.next()){
                    String typeName = rs.getString("typeName");
                    RoomTypeDTO dto = new RoomTypeDTO(typeName);
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
