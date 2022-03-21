/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HUYVU
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {
    private final String HOME_PAGE = "LoadHotelServlet";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String REGISTER_CONTROLLER = "RegisterServlet";
    
    private final String ADD_TO_CART = "AddToCartServlet";
    private final String REMOVE_FROM_CART = "RemoveFromCartServlet";
    private final String UPDATE_CART = "UpdateCartServlet";
    private final String VERIFY_BOOKING = "VerifyBookingServlet";
    private final String CHECKOUT_ORDER_CONTROLLER = "CheckoutServlet";
    private final String VIEW_BOOKING_HISTORY = "BookingHistoryServlet";
    private final String SEARCH_BOOKING_HISTORY = "SearchHistoryServlet";
    private final String CANCEL_BOOKING_HISTORY = "CancelBookingServlet";
    private final String RATING_BOOKING = "FeedbackBookingServlet";
    private final String SEARCH_CONTROLLER = "SearchHotelServlet";
    private final String RESULT_PAGE = "home.jsp";
    private final String VIEW_CART = "viewCart.jsp";
    private final String HISTORY_RESULT = "viewBooking.jsp";
    
    
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
        String url = HOME_PAGE;
        String button = request.getParameter("btnAction");
        try  {
            if(button!=null){
                switch(button){
                    case "Login":
                        url = LOGIN_CONTROLLER;
                        break;
                    case "Logout":
                        url = LOGOUT_CONTROLLER;
                        break;
                    case "Register":
                        url = REGISTER_CONTROLLER;
                        break;
                    case "Add To Wishlist":
                        url = ADD_TO_CART;
                        break;
                    case "Search":
                        url = SEARCH_CONTROLLER;
                        break;
                    case "Result":
                        url = RESULT_PAGE;
                        break;
                    case "View Cart":
                        url = VIEW_CART;
                        break;
                    case "Remove":
                        url = REMOVE_FROM_CART;
                        break;
                    case "Submit":
                        url = CHECKOUT_ORDER_CONTROLLER;
                        break;
                    case "Update":
                        url = UPDATE_CART;
                        break;
                    case "Check out":
                        url = VERIFY_BOOKING;
                        break;
                    case "My Order":
                        url = VIEW_BOOKING_HISTORY;
                        break;
                    case "Search History":
                        url = SEARCH_BOOKING_HISTORY;
                        break;
                    case "History Result":
                        url = HISTORY_RESULT;
                        break;
                    case "Cancel Booking":
                        url = CANCEL_BOOKING_HISTORY;
                        break;
                    case "Rating":
                        url = RATING_BOOKING;
                        break;
                    case "Home":
                        url = HOME_PAGE;
                        break;
                    default:
                        url = HOME_PAGE;
                        break;
                }
            }
            
            
        }catch(NullPointerException ex){
            log("DispatchServlet _ Nullpoint " + ex.getMessage());
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
