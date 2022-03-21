/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.hotel;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import huyvl.hotelarea.HotelAreaDAO;
import huyvl.hotelarea.HotelAreaDTO;
import huyvl.ultites.DBHelper;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author HUYVU
 */
public class HotelDAO implements Serializable {

    private List<HotelDTO> hotelList;

    public List<HotelDTO> getHotelList() {
        return this.hotelList;
    }

    public void getAllHotel()
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                HotelAreaDAO hotelAreaDAO = new HotelAreaDAO();
                String sql = "SELECT hotelID, hotelName, hotelImage,hotelAddress,areaID "
                        + "FROM tblHotel ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String hotelID = rs.getString("hotelID");
                    String hotelName = rs.getString("hotelName");
                    String hotelImage = rs.getString("hotelImage");
                    String hotelAddress = rs.getString("hotelAddress");
                    String areaID = rs.getString("areaID");
                    HotelAreaDTO hotelAreaDTO = hotelAreaDAO.getAreaByID(areaID);
                    HotelDTO dto = new HotelDTO(hotelID, hotelName, hotelImage, hotelAddress, hotelAreaDTO);
                    if (this.hotelList == null) {
                        this.hotelList = new ArrayList<>();
                    }
                    this.hotelList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public HotelDTO getHotelByID(String hotelID)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                HotelAreaDAO hotelAreaDAO = new HotelAreaDAO();
                String sql = "SELECT hotelID ,hotelName, hotelImage,hotelAddress, areaID "
                        + "FROM tblHotel "
                        + "WHERE hotelID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, hotelID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    hotelID = rs.getString("hotelID");
                    String hotelName = rs.getString("hotelName");
                    String hotelImage = rs.getString("hotelImage");
                    String hotelAddress = rs.getString("hotelAddress");
                    String areaID = rs.getString("areaID");
                    HotelAreaDTO hotelAreaDTO = hotelAreaDAO.getAreaByID(areaID);
                    HotelDTO dto = new HotelDTO(hotelID, hotelName, hotelImage, hotelAddress, hotelAreaDTO);
                    return dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public String getHotelAreaID(String hotelArea)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT areaID "
                        + "FROM tblHotelArea "
                        + "WHERE areaName=? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, hotelArea);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getString("areaID");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public void searchHotelWithAllFields(String hotelName, String hotelArea, Date checkinDate, Date checkoutDate, String amount)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                HotelAreaDAO hotelAreaDAO = new HotelAreaDAO();
                String sql = "SELECT h.hotelID, h.hotelName, h.hotelImage, h.hotelAddress, h.areaID\n"
                        + "FROM tblRoom r inner join tblHotel h on r.hotelID = h.hotelID \n"
                        + "				inner join tblRoomType rt on r.roomType = rt.typeID	\n"
                        + "				inner join tblHotelArea ha on ha.areaID = h.areaID\n"
                        + "WHERE hotelName Like ? And areaName = ? And h.roomAmount - ? >= 0 and roomID NOT IN \n"
                        + "	(\n"
                        + "		SELECT r.roomID \n"
                        + "		FROM tblRoom r\n"
                        + "				inner join tblBookingDetail d on r.roomID = d.roomID\n"
                        + "				inner join tblBooking b on b.bookingID = d.bookingID\n"
                        + "		WHERE ((Convert(Date, checkinDate) <= Convert(Date, ?) AND Convert(Date, checkoutDate) >= Convert(Date, ?)) \n"
                        + "				OR (Convert(Date, checkinDate) < Convert(Date, ?) AND Convert(Date, checkoutDate) >= Convert(Date, ?)) \n"
                        + "				OR (Convert(Date, ?) <= Convert(Date, checkinDate) AND Convert(Date, ?) >= Convert(Date, checkoutDate))) \n"
                        + ")";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + hotelName + "%");
                stm.setString(2, hotelArea);
                stm.setString(3, amount);
                stm.setTimestamp(4, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(5, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(6, new Timestamp(checkoutDate.getTime()));
                stm.setTimestamp(7, new Timestamp(checkoutDate.getTime()));
                stm.setTimestamp(8, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(9, new Timestamp(checkoutDate.getTime()));
                rs = stm.executeQuery();
                while (rs.next()) {
                    String hotelID = rs.getString("hotelID");
                    hotelName = rs.getString("hotelName");
                    String hotelImage = rs.getString("hotelImage");
                    String hotelAddress = rs.getString("hotelAddress");
                    String areaID = rs.getString("areaID");
                    System.out.println("areaID:" + areaID);
                    HotelAreaDTO hotelAreaDTO = hotelAreaDAO.getAreaByID(areaID);
                    HotelDTO dto = new HotelDTO(hotelID, hotelName, hotelImage, hotelAddress, hotelAreaDTO);
                    if (this.hotelList == null) {
                        this.hotelList = new ArrayList<>();
                    }
                    System.out.println(dto.toString());
                    this.hotelList.add(dto);
                    hotelList.size();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void searchHotelWithoutArea(String hotelName, Date checkinDate, Date checkoutDate, String amount)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                HotelAreaDAO hotelAreaDAO = new HotelAreaDAO();
                String sql = "SELECT h.hotelID, h.hotelName, h.hotelImage, h.hotelAddress, h.areaID\n"
                        + "FROM tblRoom r inner join tblHotel h on r.hotelID = h.hotelID \n"
                        + "				inner join tblRoomType rt on r.roomType = rt.typeID	\n"
                        + "				inner join tblHotelArea ha on ha.areaID = h.areaID\n"
                        + "WHERE hotelName Like ? And h.roomAmount - ? >= 0 and roomID NOT IN \n"
                        + "	(\n"
                        + "		SELECT r.roomID \n"
                        + "		FROM tblRoom r\n"
                        + "				inner join tblBookingDetail d on r.roomID = d.roomID\n"
                        + "				inner join tblBooking b on b.bookingID = d.bookingID\n"
                        + "		WHERE ((Convert(Date, checkinDate) <= Convert(Date, ?) AND Convert(Date, checkoutDate) >= Convert(Date, ?)) \n"
                        + "				OR (Convert(Date, checkinDate) < Convert(Date, ?) AND Convert(Date, checkoutDate) >= Convert(Date, ?)) \n"
                        + "				OR (Convert(Date, ?) <= Convert(Date, checkinDate) AND Convert(Date, ?) >= Convert(Date, checkoutDate))) \n"
                        + ")";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + hotelName + "%");
                stm.setString(2, amount);
                stm.setTimestamp(3, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(4, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(5, new Timestamp(checkoutDate.getTime()));
                stm.setTimestamp(6, new Timestamp(checkoutDate.getTime()));
                stm.setTimestamp(7, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(8, new Timestamp(checkoutDate.getTime()));
                rs = stm.executeQuery();
                while (rs.next()) {
                    String hotelID = rs.getString("hotelID");
                    hotelName = rs.getString("hotelName");
                    String hotelImage = rs.getString("hotelImage");
                    String hotelAddress = rs.getString("hotelAddress");
                    String areaID = rs.getString("areaID");
                    System.out.println("areaID:" + areaID);
                    HotelAreaDTO hotelAreaDTO = hotelAreaDAO.getAreaByID(areaID);
                    HotelDTO dto = new HotelDTO(hotelID, hotelName, hotelImage, hotelAddress, hotelAreaDTO);
                    if (this.hotelList == null) {
                        this.hotelList = new ArrayList<>();
                    }
                    System.out.println(dto.toString());
                    this.hotelList.add(dto);
                    hotelList.size();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
        public void searchHotelWithoutName(String hotelArea, Date checkinDate, Date checkoutDate, String amount)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                HotelAreaDAO hotelAreaDAO = new HotelAreaDAO();
                String sql = "SELECT h.hotelID, h.hotelName, h.hotelImage, h.hotelAddress, h.areaID\n"
                        + "FROM tblRoom r inner join tblHotel h on r.hotelID = h.hotelID \n"
                        + "				inner join tblRoomType rt on r.roomType = rt.typeID	\n"
                        + "				inner join tblHotelArea ha on ha.areaID = h.areaID\n"
                        + "WHERE areaName = ? And h.roomAmount - ? >= 0 and roomID NOT IN \n"
                        + "	(\n"
                        + "		SELECT r.roomID \n"
                        + "		FROM tblRoom r\n"
                        + "				inner join tblBookingDetail d on r.roomID = d.roomID\n"
                        + "				inner join tblBooking b on b.bookingID = d.bookingID\n"
                        + "		WHERE ((Convert(Date, checkinDate) <= Convert(Date, ?) AND Convert(Date, checkoutDate) >= Convert(Date, ?)) \n"
                        + "				OR (Convert(Date, checkinDate) < Convert(Date, ?) AND Convert(Date, checkoutDate) >= Convert(Date, ?)) \n"
                        + "				OR (Convert(Date, ?) <= Convert(Date, checkinDate) AND Convert(Date, ?) >= Convert(Date, checkoutDate))) \n"
                        + ")";
                stm = con.prepareStatement(sql);
                stm.setString(1, hotelArea);
                stm.setString(2, amount);
                stm.setTimestamp(3, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(4, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(5, new Timestamp(checkoutDate.getTime()));
                stm.setTimestamp(6, new Timestamp(checkoutDate.getTime()));
                stm.setTimestamp(7, new Timestamp(checkinDate.getTime()));
                stm.setTimestamp(8, new Timestamp(checkoutDate.getTime()));
                rs = stm.executeQuery();
                while (rs.next()) {
                    String hotelID = rs.getString("hotelID");
                    String hotelName = rs.getString("hotelName");
                    String hotelImage = rs.getString("hotelImage");
                    String hotelAddress = rs.getString("hotelAddress");
                    String areaID = rs.getString("areaID");
                    HotelAreaDTO hotelAreaDTO = hotelAreaDAO.getAreaByID(areaID);
                    HotelDTO dto = new HotelDTO(hotelID, hotelName, hotelImage, hotelAddress, hotelAreaDTO);
                    if (this.hotelList == null) {
                        this.hotelList = new ArrayList<>();
                    }
                    System.out.println(dto.toString());
                    this.hotelList.add(dto);
                    hotelList.size();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
