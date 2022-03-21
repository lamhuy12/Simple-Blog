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
import huyvl.booking.BookingDAO;
import huyvl.booking.BookingDTO;
import huyvl.bookingdetail.BookingDetailDAO;
import huyvl.bookingdetail.BookingDetailDTO;

/**
 *
 * @author HUYVU
 */
@WebServlet(name = "BookingHistoryServlet", urlPatterns = {"/BookingHistoryServlet"})
public class BookingHistoryServlet extends HttpServlet {
    private final String BOOKING_HISTORY_PAGE = "viewBooking.jsp";
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
        String url = BOOKING_HISTORY_PAGE;
        try {
            HttpSession session = request.getSession();
            if(session!=null){
                String email = (String)session.getAttribute("USERID");
                BookingDAO bookingDAO = new BookingDAO();
                BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                
                bookingDAO.getAllBookingByUsername(email);
                List<BookingDTO> bookingList = bookingDAO.getBookingList();
                if(bookingList!=null){
                    for(BookingDTO dto : bookingList){
                        bookingDetailDAO.getAllBookingDetailByID(dto.getBookingID());
                    }
                    List<BookingDetailDTO> detailList = bookingDetailDAO.getBookingDetailList();
                    if(detailList!=null){
                        request.setAttribute("BOOKING", bookingList);
                        request.setAttribute("BOOKINGDETAIL", detailList);
                    }
                }
            }
        }catch(NullPointerException ex){
            log("BookingHistoryServlet _ NullPoint " + ex.getMessage());
        }catch(SQLException ex){
            log("BookingHistoryServlet _ SQL " + ex.getMessage());
        }catch(NamingException ex){
            log("BookingHistoryServlet _ Naming " + ex.getMessage());  
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
