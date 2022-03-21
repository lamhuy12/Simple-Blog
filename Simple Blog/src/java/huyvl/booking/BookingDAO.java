/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.booking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import huyvl.registration.AccountDAO;
import huyvl.registration.AccountDTO;
import huyvl.discount.DiscountDAO;
import huyvl.discount.DiscountDTO;
import huyvl.ultites.DBHelper;

/**
 *
 * @author HUYVU
 */
public class BookingDAO implements Serializable{
    private List<BookingDTO> bookingList;
    public List<BookingDTO> getBookingList(){
        return this.bookingList;
    }
    public String getRandomCode() {
        Random rd = new Random();
        int code = rd.nextInt(999999);
        return String.format("%06d", code);
    }
    public int getRatingByID(String bookingID)
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT rating "
                        + "FROM tblFeedback "
                        + "WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                rs = stm.executeQuery();
                if(rs.next()){
                    return rs.getInt("rating");
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        return 0;
    }
    public boolean sendEmail(String toEmail, String randomCode) throws AddressException, MessagingException {
        boolean test = false;

        String fromEmail = "huyv46@gmail.com";
        String password = "thehuan1204";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        Message mess = new MimeMessage(session);
        mess.setFrom(new InternetAddress(fromEmail));
        mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        mess.setSubject("Booking Verification");
        mess.setText("Your Verify Code: " + randomCode);
        Transport.send(mess);
        test = true;
        return test;
    }
    public void getAllBooking() 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT bookingID,username, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount,status "
                        + "FROM tblBooking";
                AccountDAO accountDAO = new AccountDAO();
                DiscountDAO discountDAO = new DiscountDAO();
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while(rs.next()){
                    String bookingID = rs.getString("bookingID");
                    AccountDTO account = accountDAO.getAccountByUsename(rs.getString("username"));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String bookingDate = df.format(rs.getDate("bookingDate"));
                    DiscountDTO discount = discountDAO.getDiscountByID(rs.getString("discount"));
                    String totalCostBeforeDiscount = rs.getString("totalCostBeforeDiscount");
                    String totalCostAfterDiscount = rs.getString("totalCostAfterDiscount");
                    int rating = getRatingByID(bookingID);
                    boolean status = rs.getBoolean("status");
                    BookingDTO dto = new BookingDTO(bookingID,account, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount, status,rating);
                    if(this.bookingList == null){
                        this.bookingList = new ArrayList<>();
                    }
                    this.bookingList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
    public void getAllBookingByUsername(String username) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT bookingID,username, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount,status "
                        + "FROM tblBooking "
                        + "WHERE username = ? "
                        + "ORDER BY bookingDate DESC";
                AccountDAO accountDAO = new AccountDAO();
                DiscountDAO discountDAO = new DiscountDAO();
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                while(rs.next()){
                    String bookingID = rs.getString("bookingID");
                    AccountDTO account = accountDAO.getAccountByUsename(rs.getString("username"));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String bookingDate = df.format(rs.getDate("bookingDate"));
                    DiscountDTO discount = discountDAO.getDiscountByID(rs.getString("discount"));
                    String totalCostBeforeDiscount = rs.getString("totalCostBeforeDiscount");
                    String totalCostAfterDiscount = rs.getString("totalCostAfterDiscount");
                    boolean status = rs.getBoolean("status");
                    int rating = getRatingByID(bookingID);
                    BookingDTO dto = new BookingDTO(bookingID,account, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount, status,rating);
                    if(this.bookingList == null){
                        this.bookingList = new ArrayList<>();
                    }
                    this.bookingList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
    public BookingDTO getBookingByID(String bookingID) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT bookingID,username, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount,status "
                        + "FROM tblBooking";
                AccountDAO accountDAO = new AccountDAO();
                DiscountDAO discountDAO = new DiscountDAO();
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                rs = stm.executeQuery();
                if(rs.next()){
                    bookingID = rs.getString("bookingID");
                    String totalCostBeforeDiscount = rs.getString("totalCostBeforeDiscount");
                    String totalCostAfterDiscount = rs.getString("totalCostAfterDiscount");
                    AccountDTO account = accountDAO.getAccountByUsename(rs.getString("username"));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String bookingDate = df.format(rs.getDate("bookingDate"));
                    DiscountDTO discount = discountDAO.getDiscountByID(rs.getString("discount"));
                    boolean status = rs.getBoolean("status");
                    int rating = getRatingByID(bookingID);
                    
                    BookingDTO dto = new BookingDTO(bookingID,account, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount, status,rating);
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
    public String getLastedBookingID() 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT MAX(bookingID) as lastedBookingID "
                        + "FROM tblBooking";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if(rs.next()){
                    return rs.getString("lastedBookingID");
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        return null;
    }
    public boolean createBooking(String username, String discount, String totalCostBeforeDiscount,String totalCostAfterDiscount) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "INSERT INTO tblBooking(username, discount, totalCostBeforeDiscount, totalCostAfterDiscount ) "
                        + "VALUES (?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, discount);
                stm.setString(3, totalCostBeforeDiscount);
                stm.setString(4, totalCostAfterDiscount);
                int result = stm.executeUpdate();
                return result>0;
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        return false;
    }
    public boolean cancelBooking(String bookingID,String username) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "UPDATE tblBooking "
                        + "SET status = 0 "
                        + "WHERE bookingID = ? AND username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                stm.setString(2, username);
                int result = stm.executeUpdate();
                return result>0;
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        return false;
    }
    public boolean ratingBooking(String bookingID,int hotelID,int rating) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "INSERT INTO tblFeedback(bookingID,hotelID,rating) "
                        + "VALUES (?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                stm.setInt(2, hotelID);
                stm.setInt(3, rating);
                int result = stm.executeUpdate();
                return result>0;
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
        return false;
    }
    
    public String remakeDate(String date){
        StringTokenizer stkCheckin = new StringTokenizer(date,"/");
        String day1 = stkCheckin.nextToken();
        String month1 = stkCheckin.nextToken();
        String year1 = stkCheckin.nextToken();
        date = month1+"/"+day1+"/"+year1;
        return date;
    }
    public void searchBookingByID(String bookingID,String username) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT bookingID,username, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount,status "
                        + "FROM tblBooking "
                        + "WHERE bookingID = ? AND username = ? "
                        + "ORDER BY bookingDate DESC";
                AccountDAO accountDAO = new AccountDAO();
                DiscountDAO discountDAO = new DiscountDAO();
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                stm.setString(2, username);
                rs = stm.executeQuery();
                while(rs.next()){
                    bookingID = rs.getString("bookingID");
                    AccountDTO account = accountDAO.getAccountByUsename(rs.getString("username"));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String bookingDate = df.format(rs.getDate("bookingDate"));
                    DiscountDTO discount = discountDAO.getDiscountByID(rs.getString("discount"));
                    String totalCostBeforeDiscount = rs.getString("totalCostBeforeDiscount");
                    String totalCostAfterDiscount = rs.getString("totalCostAfterDiscount");
                    boolean status = rs.getBoolean("status");
                    int rating = getRatingByID(bookingID);
                    BookingDTO dto = new BookingDTO(bookingID,account, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount, status,rating);
                    if(this.bookingList == null){
                        this.bookingList = new ArrayList<>();
                    }
                    this.bookingList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
    public void searchBookingByBookingDate(String bookingDate,String username) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT bookingID,username, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount,status "
                        + "FROM tblBooking "
                        + "WHERE bookingDate = ?  AND username = ? ";
                AccountDAO accountDAO = new AccountDAO();
                DiscountDAO discountDAO = new DiscountDAO();
                stm = con.prepareStatement(sql);
                stm.setString(1, remakeDate(bookingDate));
                stm.setString(2, username);
                rs = stm.executeQuery();
                while(rs.next()){
                    String bookingID = rs.getString("bookingID");
                    AccountDTO account = accountDAO.getAccountByUsename(rs.getString("username"));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    bookingDate = df.format(rs.getDate("bookingDate"));
                    DiscountDTO discount = discountDAO.getDiscountByID(rs.getString("discount"));
                    String totalCostBeforeDiscount = rs.getString("totalCostBeforeDiscount");
                    String totalCostAfterDiscount = rs.getString("totalCostAfterDiscount");
                    boolean status = rs.getBoolean("status");
                    int rating = getRatingByID(bookingID);
                    BookingDTO dto = new BookingDTO(bookingID,account, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount, status,rating);
                    if(this.bookingList == null){
                        this.bookingList = new ArrayList<>();
                    }
                    this.bookingList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
    public void searchBookingByAllField(String bookingID,String bookingDate,String username) 
        throws NamingException,SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if(con!=null){
                String sql = "SELECT bookingID,username, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount,status "
                        + "FROM tblBooking "
                        + "WHERE bookingID = ? AND bookingDate = ?  AND username = ? ";
                AccountDAO accountDAO = new AccountDAO();
                DiscountDAO discountDAO = new DiscountDAO();
                stm = con.prepareStatement(sql);
                stm.setString(1, bookingID);
                stm.setString(2, remakeDate(bookingDate));
                stm.setString(3, username);
                rs = stm.executeQuery();
                while(rs.next()){
                    bookingID = rs.getString("bookingID");
                    AccountDTO account = accountDAO.getAccountByUsename(rs.getString("username"));
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    bookingDate = df.format(rs.getDate("bookingDate"));
                    DiscountDTO discount = discountDAO.getDiscountByID(rs.getString("discount"));
                    String totalCostBeforeDiscount = rs.getString("totalCostBeforeDiscount");
                    String totalCostAfterDiscount = rs.getString("totalCostAfterDiscount");
                    boolean status = rs.getBoolean("status");
                    int rating = getRatingByID(bookingID);
                    BookingDTO dto = new BookingDTO(bookingID,account, bookingDate, discount, totalCostBeforeDiscount,totalCostAfterDiscount, status,rating);
                    if(this.bookingList == null){
                        this.bookingList = new ArrayList<>();
                    }
                    this.bookingList.add(dto);
                }
            }
        } finally {
            if(rs!=null) rs.close();
            if(stm!=null) stm.close();
            if(con!=null) con.close();
        }
    }
}
