/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import huyvl.booking.BookingDAO;
import huyvl.bookingdetail.BookingDetailDAO;
import huyvl.cart.Cart;
import huyvl.cart.CartObj;
import huyvl.discount.DiscountDAO;
import huyvl.discount.DiscountDTO;
import huyvl.room.RoomDAO;

/**
 *
 * @author HUYVU
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {
    private final String HOME_PAGE = "LoadHotelServlet";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String address = request.getParameter("address");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String payment = request.getParameter("payment");
        String verifyCode = request.getParameter("verifyCode");
        String time = request.getParameter("time");
        String discount = request.getParameter("discount").trim();
        String totalCash = request.getParameter("totalCash");
        String url = HOME_PAGE;
        try {
            HttpSession session = request.getSession();
            if(session!=null){
                String email = (String)session.getAttribute("USERID");
                String otpCode = (String)session.getAttribute("VERIFYCODE");
                if(time.equals("0:00")){
                    url = "DispatchServlet"
                        + "?btnAction=View Cart"
                        + "&address="+address
                        + "&fullname="+fullname
                        + "&phone="+phone
                        + "&payment="+payment
                        + "&discount="+discount;
                    request.setAttribute("ERROTP", "Verify code has been expired");
                }
                else{
                    if(otpCode.equals(verifyCode)){
                        Cart cart = (Cart)session.getAttribute("CART");
                        RoomDAO roomDAO = new RoomDAO();
                        BookingDAO bookingDAO = new BookingDAO();
                        BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                        Map<Integer, CartObj> items = cart.getItems();
                        boolean isBooking = false;
                        String room = "";
                        String hotel = "";
                        for(CartObj obj : items.values()){
                            if(roomDAO.getRoomIsBooking(obj.getRoom().getRoomID(), obj.getCheckinDate(), obj.getCheckoutDate())){
                                isBooking = true;
                                room = obj.getRoom().getRoomType().getTypeName();
                                hotel = obj.getRoom().getRoomHotel().getHotelName();
                                break;
                            }
                        }
                        if(isBooking){
                            url = "DispatchServlet"
                                    + "?btnAction=View Cart"
                                    + "&address="+address
                                    + "&fullname="+fullname
                                    + "&phone="+phone
                                    + "&payment="+payment
                                    + "&discount="+discount;
                            request.setAttribute("ERRBOOKING",room+" Room from "+hotel+" Hotel has already been booked ");
                        }
                        else{
                            DiscountDAO discountDAO = new DiscountDAO();
                            DiscountDTO discountDTO = discountDAO.getDiscountByName(discount);
                            if(discountDTO!=null){
                                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                Date expirationDate = df.parse(discountDTO.getExpirationDate());
                                Date today = df.parse(df.format(new Date()));
                                boolean isExpirationDiscount = false;
                                if(expirationDate.compareTo(today)<=0){
                                    isExpirationDiscount = true;
                                }
                                if(isExpirationDiscount){
                                    url = "DispatchServlet"
                                        + "?btnAction=View Cart"
                                        + "&address="+address
                                        + "&fullname="+fullname
                                        + "&phone="+phone
                                        + "&payment="+payment
                                        + "&discount="+discount;
                                    request.setAttribute("ERRBOOKING","Discount code "+discount+" is expired");
                                }
                                else{
                                    String totalAfterDiscount;
                                    if(discountDTO.getDiscountID().equals("1")){
                                        totalAfterDiscount = totalCash;
                                    }
                                    else{
                                        totalAfterDiscount = Float.toString(Float.parseFloat(totalCash) - Float.parseFloat(totalCash)*(Float.parseFloat(discountDTO.getDiscountCost()))/100);
                                    }
                                    boolean isBookingSuccess = bookingDAO.createBooking(email, discountDTO.getDiscountID(), totalCash, totalAfterDiscount);
                                    if(isBookingSuccess){
                                        String lastedBookingID = bookingDAO.getLastedBookingID();
                                        for(CartObj obj : items.values()){
                                            bookingDetailDAO.insertBookingDetailByID(lastedBookingID, obj.getRoom().getRoomID(), obj.getCheckinDate(), obj.getCheckoutDate());
                                            url = "DispatchServlet"
                                                + "?btnAction=View Cart";
                                            request.setAttribute("SUCCESS", "Booking Successful");
                                            session.removeAttribute("CART");
                                            session.removeAttribute("HOTELID");
                                            session.removeAttribute("DATE");
                                        }
                                    }
                                }
                            }else{
                                url = "DispatchServlet"
                                    + "?btnAction=View Cart"
                                    + "&address="+address
                                    + "&fullname="+fullname
                                    + "&phone="+phone
                                    + "&payment="+payment
                                    + "&discount="+discount;
                                request.setAttribute("ERRBOOKING","Discount code "+discount+" is not existed");
                            }
                        }
                    }else{
                        url = "DispatchServlet"
                            + "?btnAction=View Cart"
                            + "&address="+address
                            + "&fullname="+fullname
                            + "&phone="+phone
                            + "&payment="+payment
                            + "&discount="+discount;
                        request.setAttribute("ERROTP", "Verify code not matched");
                    }
                }
            }
        }catch(ParseException ex){
            log("CheckoutServlet _ Parse " + ex.getMessage());
        }catch(SQLException ex){
            log("CheckoutServlet _ SQL " + ex.getMessage());
        }catch(NamingException ex){
            log("CheckoutServlet _ Naming " + ex.getMessage()); 
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
