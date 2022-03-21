/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import huyvl.cart.Cart;
import huyvl.cart.CartObj;
import huyvl.room.RoomDAO;

/**
 *
 * @author HUYVU
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

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
        String roomID = request.getParameter("roomID");
        String date = request.getParameter("daterange");
        String daterangeBackup = request.getParameter("daterangeBackup");
        String searchName = request.getParameter("searchName");
        String searchArea = request.getParameter("searchArea");
        String searchDate = request.getParameter("searchDate");
        String urlRewriting = HOME_PAGE;
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            HttpSession session = request.getSession(true);
            RoomDAO roomDAO = new RoomDAO();
            if (session != null) {
                String oldhotelID = (String) session.getAttribute("HOTELID");
                boolean isBooked = true;
                if (oldhotelID != null) {
                    isBooked = oldhotelID.equalsIgnoreCase(roomDAO.getHotelIDbyRoomID(roomID));
                }
                if (isBooked) {
                    boolean isExisted = false;
                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart != null) {
                        Map<Integer, CartObj> items = cart.getItems();
                        if (items != null) {
                            for (CartObj obj : items.values()) {
                                if (obj.getRoom().getRoomID().equalsIgnoreCase(roomID)) {
                                    isExisted = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (isExisted) {
                        request.setAttribute("ERR", "This room is already in your cart");
                    } else {
                        if (date == null) {
                            date = daterangeBackup;
                            if (date.equals("")) {
                                date = searchDate;
                            }
                        } else if (daterangeBackup == null) {
                            date = searchDate;
                        }

                        StringTokenizer stk = new StringTokenizer(date, " - ");
                        String checkinDate = stk.nextToken();
                        String checkoutDate = stk.nextToken();
                        Date chkin = df.parse(checkinDate);
                        Date chkout = df.parse(checkoutDate);
                        long diff = chkout.getTime() - chkin.getTime();
                        float days = (diff / (1000 * 60 * 60 * 24));
                        int dayBetween = (int) days;
                        CartObj cartItem = new CartObj(roomDAO.getRoomByID(roomID), checkinDate, checkoutDate, Integer.toString(dayBetween));
                        if (cart == null) {
                            cart = new Cart();
                        }
                        cart.addItemToCard(cartItem);
                        session.setAttribute("CART", cart);
                        session.setAttribute("HOTELID", roomDAO.getHotelIDbyRoomID(roomID));
                        session.setAttribute("DATE", date);
                        System.out.println(date);
                        session.setAttribute("HOTELNAME", roomDAO.getRoomByID(roomID).getRoomHotel().getHotelName());
                    }
                } else {
                    request.setAttribute("ERR", "You only can choose one hotel at the same booking");
                }
                if (!searchName.isEmpty() || !searchArea.isEmpty() || !searchDate.isEmpty()) {
                    urlRewriting = "DispatchServlet"
                            + "?btnAction=Search"
                            + "&searchName=" + searchName
                            + "&searchArea=" + searchArea
                            + "&searchDate=" + searchDate;
                } else {
                    urlRewriting = HOME_PAGE;
                }

            }
        } catch (ParseException ex) {
            log("AddToCartServlet _ Parse " + ex.getMessage());
        } catch (SQLException ex) {
            log("AddToCartServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("AddToCartServlet _ Naming " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(urlRewriting);
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
