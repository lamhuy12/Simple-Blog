/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {
    private final String VIEW_CART = "viewCart.jsp";
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
        String date = request.getParameter("daterange");
        String url = VIEW_CART;
        try {
            StringTokenizer stk = new StringTokenizer(date," - ");
            String checkinDate = (stk.nextToken());
            String checkoutDate = (stk.nextToken());
            HttpSession session = request.getSession();
            if(session!=null){
                Cart cart = (Cart)session.getAttribute("CART");
                if(cart!=null){
                    Map<Integer, CartObj> items = cart.getItems();
                    String hotel = (String)session.getAttribute("HOTELNAME");
                    String oldDate = (String)session.getAttribute("DATE");
                    StringTokenizer stk1 = new StringTokenizer(oldDate," - ");
                    String checkinDateOld = (stk1.nextToken());
                    String checkoutDateOld = (stk1.nextToken());
                    boolean isBooking = false;
                    int pos = -1;
                    RoomDAO roomDAO = new RoomDAO();
                    for(int i : items.keySet()){
                        isBooking = roomDAO.getRoomIsBooking(Integer.toString(i), checkinDate, checkoutDate);
                        if(isBooking){
                            pos = i;
                            break;
                        }
                    }
                    String room = "";
                    if(pos>=0){
                        room = items.get(pos).getRoom().getRoomType().getTypeName();
                    }
                    if(checkinDateOld.equalsIgnoreCase(checkinDate) && checkoutDateOld.equalsIgnoreCase(checkoutDate)){
                        
                    }
                    else{
                        if(isBooking){
                            request.setAttribute("ERRBOOKING",room+" Room from "+hotel+" Hotel has already been booked");
                        }
                        
                        else{
                            CartObj obj;
                            for(int i : items.keySet()){
                                obj = items.get(i);
                                obj.setCheckinDate(checkinDate);
                                obj.setCheckoutDate(checkoutDate);
                                cart.updateItemCart(i, obj);
                            }
                            if(items.isEmpty()){
                                session.removeAttribute("CART");
                            }
                            else{
                                session.setAttribute("CART", cart);
                                session.setAttribute("DATE", checkinDate + " - "+checkoutDate);
                            } 
                        }
                    }
                }
            }
        }catch (NamingException ex){
            log("UpdateCartServlet _ Naming " + ex.getMessage());
        }catch (SQLException ex){
            log("UpdateCartServlet _ SQL " + ex.getMessage()); 
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
