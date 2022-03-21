/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.bookingdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.naming.NamingException;
import huyvl.room.RoomDAO;
import huyvl.room.RoomDTO;
import huyvl.ultites.DBHelper;

/**
 *
 * @author HUYVU
 */
public class BookingDetailDAO implements Serializable{
    private List<BookingDetailDTO> bookingDetailList;
    public List<BookingDetailDTO> getBookingDetailList(){
        return this.bookingDetailList;
    }
    public String remakeDate(String date){
        StringTokenizer stkCheckin = new StringTokenizer(date,"/");
        String day1 = stkCheckin.nextToken();
        String month1 = stkCheckin.nextToken();
        String year1 = stkCheckin.nextToken();
        date = month1+"/"+day1+"/"+year1;
        return date;
    }
    public void insertBookingDetailByID(String bookingID, String roomID, String checkinDate, String checkoutDate)
        throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "INSERT INTO tblBookingDetail(bookingID,roomID,checkinDate,checkoutDate) "
                           + "VALUES(?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                stm.setString(2, roomID);
                stm.setString(3, remakeDate(checkinDate));
                stm.setString(4, remakeDate(checkoutDate));
                stm.executeUpdate();
            }
        } finally {
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
    public void getAllBookingDetailByID(String bookingID) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT bookingDetailID,bookingID, roomID, checkinDate, checkoutDate "
                        + "FROM tblBookingDetail "
                        + "WHERE bookingID = ?";
                RoomDAO roomDAO = new RoomDAO();
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                rs = stm.executeQuery();
                while(rs.next()){
                    String bookingDetailID = rs.getString("bookingDetailID");
                    RoomDTO roomDTO = roomDAO.getRoomByID(rs.getString("roomID"));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String checkinDate = df.format(rs.getDate("checkinDate"));
                    String checkoutDate = df.format(rs.getDate("checkoutDate"));
                    BookingDetailDTO dto = new BookingDetailDTO(bookingDetailID,bookingID, roomDTO, checkinDate,checkoutDate);
                    if(this.bookingDetailList == null){
                        this.bookingDetailList = new ArrayList<>();
                    }
                    this.bookingDetailList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
    public boolean checkIsValidDate(String checkinDate, String checkoutDate)
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT roomID "
                        + "FROM tblBookingDetail "
                        + "WHERE ? >= checkinDate AND ";
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        return false;
    }
}
