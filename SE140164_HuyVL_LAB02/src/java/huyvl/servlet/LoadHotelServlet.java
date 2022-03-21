/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import huyvl.cart.Cart;
import huyvl.feedback.FeedbackDAO;
import huyvl.feedback.FeedbackDTO;
import huyvl.hotel.HotelDAO;
import huyvl.hotel.HotelDTO;
import huyvl.hotelarea.HotelAreaDAO;
import huyvl.hotelarea.HotelAreaDTO;
import huyvl.room.RoomDAO;
import huyvl.room.RoomDTO;
import huyvl.roomtype.RoomTypeDAO;
import huyvl.roomtype.RoomTypeDTO;

/**
 *
 * @author HUYVU
 */
@WebServlet(name = "LoadHotelServlet", urlPatterns = {"/LoadHotelServlet"})
public class LoadHotelServlet extends HttpServlet {
    private final String HOME_PAGE = "home.jsp";
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
        try {
            RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
            HotelAreaDAO hotelAreaDAO = new HotelAreaDAO();
            HotelDAO hotelDAO = new HotelDAO();
            RoomDAO roomDAO = new RoomDAO();
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            
            roomTypeDAO.getAllRoomType();
            hotelAreaDAO.getAllArea();
            hotelDAO.getAllHotel();
            roomDAO.getAllRoom();
            feedbackDAO.getAllFeedback();
            
            List<RoomTypeDTO> roomTypeList = roomTypeDAO.getTypeList();
            List<HotelAreaDTO> hotelAreaList = hotelAreaDAO.getHotelAreaList();
            List<HotelDTO> hotelList = hotelDAO.getHotelList();
            List<RoomDTO> roomList = roomDAO.getRoomList();
            System.out.println("roome:" + roomList.toString());
            List<FeedbackDTO> fbList = feedbackDAO.getFBList();
            if(roomTypeList!=null || hotelList!=null || roomList!=null || hotelAreaList!=null || fbList!=null){
                HttpSession session = request.getSession();
                Cart cart = (Cart)session.getAttribute("CART");
                session.setAttribute("CART", cart);
                session.removeAttribute("TYPELIST");
                session.setAttribute("TYPELIST", roomTypeList);
                session.removeAttribute("AREALIST");
                session.setAttribute("AREALIST", hotelAreaList);
                session.removeAttribute("FBLIST");
                session.setAttribute("FBLIST", fbList);
                request.removeAttribute("HOTELLIST");
                request.setAttribute("HOTELLIST", hotelList);
                request.removeAttribute("ROOMLIST");
                request.setAttribute("ROOMLIST", roomList);
                
            }
            
        }catch(NullPointerException ex){
            log("LoadHotelServlet _ NullPoint " + ex.getMessage());
        }catch(SQLException ex){
            log("LoadHotelServlet _ SQL " + ex.getMessage());
        }catch(NamingException ex){
            log("LoadHotelServlet _ Naming " + ex.getMessage());  
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
