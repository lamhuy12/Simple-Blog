/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.room;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.naming.NamingException;
import huyvl.hotel.HotelDAO;
import huyvl.hotel.HotelDTO;
import huyvl.ultites.DBHelper;
import huyvl.roomtype.RoomTypeDAO;
import huyvl.roomtype.RoomTypeDTO;
import java.sql.Timestamp;

/**
 *
 * @author HUYVU
 */
public class RoomDAO implements Serializable {

    private List<RoomDTO> roomList;

    public List<RoomDTO> getRoomList() {
        return this.roomList;
    }

    public void getAllRoomExceptIsBooking(List<String> roomIsBooking)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                if (roomIsBooking != null) {
                    RoomTypeDAO typeDAO = new RoomTypeDAO();
                    HotelDAO hotelDAO = new HotelDAO();
                    String sql = "SELECT roomID,roomImage,hotelID, roomType, roomPrice"
                            + "FROM tblRoom "
                            + "WHERE roomID != ? ";
                    for (int i = 2; i <= roomIsBooking.size(); i++) {
                        sql = sql.concat(" AND roomID != ? ");
                    }
                    stm = con.prepareStatement(sql);
                    for (int i = 1; i <= roomIsBooking.size(); i++) {
                        stm.setString(i, roomIsBooking.get(i - 1));
                    }
                    rs = stm.executeQuery();
                    while (rs.next()) {
                        String roomID = rs.getString("roomID");
                        String roomImage = rs.getString("roomImage");
                        HotelDTO hotelID = hotelDAO.getHotelByID(rs.getString("hotelID"));
                        RoomTypeDTO roomType = typeDAO.getAllRoolTypeByID(rs.getString("roomType"));
                        String roomPrice = rs.getString("roomPrice");
                        RoomDTO dto = new RoomDTO(roomID, roomImage, hotelID, roomType, roomPrice);
                        if (this.roomList == null) {
                            this.roomList = new ArrayList<>();
                        }
                        this.roomList.add(dto);
                    }
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

    public List<String> getRoomHotelExceptByID(List<String> roomIsBooking)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            if (roomIsBooking == null) {
                return null;
            } else {
                con = DBHelper.makeConnection();
                if (con != null) {
                    String sql = "SELECT DISTINCT hotelID "
                            + "FROM tblRoom "
                            + "WHERE roomID != ? ";

                    if (roomIsBooking.size() > 1) {
                        for (int i = 2; i <= roomIsBooking.size(); i++) {
                            sql = sql.concat(" AND roomID != ? ");
                        }
                        stm = con.prepareStatement(sql);
                        for (int i = 1; i <= roomIsBooking.size(); i++) {
                            stm.setString(i, roomIsBooking.get(i - 1));
                        }
                    } else {
                        stm = con.prepareStatement(sql);
                        stm.setString(1, roomIsBooking.get(0));
                    }
                    rs = stm.executeQuery();
                    List<String> hotelList = new ArrayList<>();
                    while (rs.next()) {
                        String roomHotel = rs.getString("roomHotel");
                        hotelList.add(roomHotel);
                    }
                    return hotelList;
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

    public List<String> getRoomIsBooking(String checkinDate, String checkoutDate)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT roomID "
                        + "FROM tblBookingDetail,tblBooking "
                        + "WHERE ((? BETWEEN checkinDate AND checkoutDate) OR "
                        + "      (checkinDate BETWEEN ? AND ?)) AND "
                        + "      tblBooking.bookingID = tblBookingDetail.bookingID AND tblBooking.status = '1' ";
                stm = con.prepareStatement(sql);
                stm.setString(1, checkinDate);
                stm.setString(2, checkinDate);
                stm.setString(3, checkoutDate);
                rs = stm.executeQuery();
                List<String> roomIsBooking = new ArrayList<>();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    roomIsBooking.add(roomID);
                }
                return roomIsBooking;
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

    public String remakeDate(String date) {
        StringTokenizer stkCheckin = new StringTokenizer(date, "/");
        String day1 = stkCheckin.nextToken();
        String month1 = stkCheckin.nextToken();
        String year1 = stkCheckin.nextToken();
        date = month1 + "/" + day1 + "/" + year1;
        return date;
    }

    public boolean getRoomIsBooking(String roomID, String checkinDate, String checkoutDate)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT roomID "
                        + "FROM tblBookingDetail,tblBooking "
                        + "WHERE ((? BETWEEN checkinDate AND checkoutDate) OR "
                        + "      (checkinDate BETWEEN ? AND ?)) AND "
                        + "      tblBooking.bookingID = tblBookingDetail.bookingID AND "
                        + "      tblBooking.status = '0' AND "
                        + "      roomID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, remakeDate(checkinDate));
                stm.setString(2, remakeDate(checkinDate));
                stm.setString(3, remakeDate(checkoutDate));
                stm.setString(4, roomID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
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
        return false;
    }

    public void getAllRoom()
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                RoomTypeDAO typeDAO = new RoomTypeDAO();
                HotelDAO hotelDAO = new HotelDAO();
                String sql = "SELECT roomID,roomImage,hotelID, roomType, roomPrice "
                        + "FROM tblRoom";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    String roomImage = rs.getString("roomImage");
                    HotelDTO hotelID = hotelDAO.getHotelByID(rs.getString("hotelID"));
                    RoomTypeDTO roomType = typeDAO.getAllRoolTypeByID(rs.getString("roomType"));
                    String roomPrice = rs.getString("roomPrice");
                    RoomDTO dto = new RoomDTO(roomID, roomImage, hotelID, roomType, roomPrice);
                    if (this.roomList == null) {
                        this.roomList = new ArrayList<>();
                    }
                    this.roomList.add(dto);
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

    public void getAvaiableRoomWithAllFields(String hotelName, String hotelArea, java.util.Date checkinDate, java.util.Date checkoutDate, String amount)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                RoomTypeDAO typeDAO = new RoomTypeDAO();
                HotelDAO hotelDAO = new HotelDAO();
                String sql = "SELECT r.roomID, r.roomImage, r.roomType, roomPrice, h.hotelID, h.hotelName, h.hotelImage, h.hotelAddress, h.areaID\n"
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
                    String roomID = rs.getString("roomID");
                    String roomImage = rs.getString("roomImage");
                    HotelDTO hotelID = hotelDAO.getHotelByID(rs.getString("hotelID"));
                    RoomTypeDTO roomType = typeDAO.getAllRoolTypeByID(rs.getString("roomType"));
                    String roomPrice = rs.getString("roomPrice");
                    RoomDTO dto = new RoomDTO(roomID, roomImage, hotelID, roomType, roomPrice);
                    if (this.roomList == null) {
                        this.roomList = new ArrayList<>();
                    }
                    this.roomList.add(dto);
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
    
        public void getAvaiableRoomWithoutArea(String hotelName, java.util.Date checkinDate, java.util.Date checkoutDate, String amount)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                RoomTypeDAO typeDAO = new RoomTypeDAO();
                HotelDAO hotelDAO = new HotelDAO();
                String sql = "SELECT r.roomID, r.roomImage, r.roomType, roomPrice, h.hotelID, h.hotelName, h.hotelImage, h.hotelAddress, h.areaID\n"
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
                    String roomID = rs.getString("roomID");
                    String roomImage = rs.getString("roomImage");
                    HotelDTO hotelID = hotelDAO.getHotelByID(rs.getString("hotelID"));
                    RoomTypeDTO roomType = typeDAO.getAllRoolTypeByID(rs.getString("roomType"));
                    String roomPrice = rs.getString("roomPrice");
                    RoomDTO dto = new RoomDTO(roomID, roomImage, hotelID, roomType, roomPrice);
                    if (this.roomList == null) {
                        this.roomList = new ArrayList<>();
                    }
                    this.roomList.add(dto);
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
        
        public void getAvaiableRoomWithoutName(String hotelArea, java.util.Date checkinDate, java.util.Date checkoutDate, String amount)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                RoomTypeDAO typeDAO = new RoomTypeDAO();
                HotelDAO hotelDAO = new HotelDAO();
                String sql = "SELECT r.roomID, r.roomImage, r.roomType, roomPrice, h.hotelID, h.hotelName, h.hotelImage, h.hotelAddress, h.areaID\n"
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
                    String roomID = rs.getString("roomID");
                    String roomImage = rs.getString("roomImage");
                    HotelDTO hotelID = hotelDAO.getHotelByID(rs.getString("hotelID"));
                    RoomTypeDTO roomType = typeDAO.getAllRoolTypeByID(rs.getString("roomType"));
                    String roomPrice = rs.getString("roomPrice");
                    RoomDTO dto = new RoomDTO(roomID, roomImage, hotelID, roomType, roomPrice);
                    if (this.roomList == null) {
                        this.roomList = new ArrayList<>();
                    }
                    this.roomList.add(dto);
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

    public void getAllRoomByHotelID(List<HotelDTO> hotelList)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                RoomTypeDAO typeDAO = new RoomTypeDAO();
                HotelDAO hotelDAO = new HotelDAO();
                String sql = "SELECT roomID,roomImage,hotelID, roomType, roomPrice "
                        + "FROM tblRoom "
                        + "WHERE hotelID = ? ";
                for (int i = 2; i <= hotelList.size(); i++) {
                    sql = sql.concat(" OR hotelID = ? ");
                }
                stm = con.prepareStatement(sql);
                for (int i = 1; i <= hotelList.size(); i++) {
                    stm.setString(i, hotelList.get(i - 1).getHotelID());
                }
                rs = stm.executeQuery();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    String roomImage = rs.getString("roomImage");
                    HotelDTO hotelID = hotelDAO.getHotelByID(rs.getString("hotelID"));
                    RoomTypeDTO roomType = typeDAO.getAllRoolTypeByID(rs.getString("roomType"));
                    String roomPrice = rs.getString("roomPrice");
                    RoomDTO dto = new RoomDTO(roomID, roomImage, hotelID, roomType, roomPrice);
                    if (this.roomList == null) {
                        this.roomList = new ArrayList<>();
                    }
                    this.roomList.add(dto);
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

    public RoomDTO getRoomByID(String roomID)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                RoomTypeDAO typeDAO = new RoomTypeDAO();
                HotelDAO hotelDAO = new HotelDAO();
                String sql = "SELECT roomID,roomImage,hotelID, roomType, roomPrice "
                        + "FROM tblRoom "
                        + "WHERE roomID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, roomID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    roomID = rs.getString("roomID");
                    String roomImage = rs.getString("roomImage");
                    HotelDTO hotelID = hotelDAO.getHotelByID(rs.getString("hotelID"));
                    RoomTypeDTO roomType = typeDAO.getAllRoolTypeByID(rs.getString("roomType"));
                    String roomPrice = rs.getString("roomPrice");
                    RoomDTO dto = new RoomDTO(roomID, roomImage, hotelID, roomType, roomPrice);
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

    public String getHotelIDbyRoomID(String roomID)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT hotelID "
                        + "FROM tblRoom "
                        + "WHERE roomID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, roomID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getString("hotelID");
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

}
