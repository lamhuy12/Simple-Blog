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
import java.util.StringTokenizer;
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
import java.text.SimpleDateFormat;

/**
 *
 * @author HUYVU
 */
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {
    private final String VIEW_BOOKING_HISTORY = "BookingHistoryServlet";
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
        String searchID = request.getParameter("searchID");
        String searchDate = request.getParameter("searchDateHistory");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  ;
        String url = VIEW_BOOKING_HISTORY;
        try {
            HttpSession session = request.getSession();
            System.out.println("searchDate: " + searchDate);
            String bookingDate = null;
            if (!searchDate.isEmpty()) {
                StringTokenizer stkBookingDate = new StringTokenizer(searchDate, "-");
                String year = stkBookingDate.nextToken();
                String month = stkBookingDate.nextToken();
                String day = stkBookingDate.nextToken();
                bookingDate = day + "/" + month + "/" + year;
                searchDate = bookingDate;
                System.out.println("searchDateParse: " + searchDate);
            }
            if(session!=null){
                String email = (String)session.getAttribute("USERID");
                if(searchDate.isEmpty() && searchID.isEmpty()){
                    url = VIEW_BOOKING_HISTORY;
                }
                else{
                    BookingDAO bookingDAO = new BookingDAO();
                    BookingDetailDAO detailDAO = new BookingDetailDAO();
                    if(!searchDate.isEmpty() && !searchID.isEmpty()){
                        bookingDAO.searchBookingByAllField(searchID, searchDate,email);
                    }//du fields
                    if(searchDate.isEmpty() && !searchID.isEmpty()){
                        bookingDAO.searchBookingByID(searchID,email);
                    }//chi co id
                    if(!searchDate.isEmpty() && searchID.isEmpty()){
                        bookingDAO.searchBookingByBookingDate(searchDate,email);
                    }//chi co date
                    List<BookingDTO> bookingList = bookingDAO.getBookingList();
                    if(bookingList!=null){
                        for(BookingDTO dto : bookingList){
                            detailDAO.getAllBookingDetailByID(dto.getBookingID());
                        }
                        List<BookingDetailDTO> detailList = detailDAO.getBookingDetailList();
                        request.setAttribute("BOOKING", bookingList);
                        request.setAttribute("BOOKINGDETAIL", detailList);
                    }
                    url = "DispatchServlet"
                        + "?btnAction=History Result"
                        + "&searchID="+searchID
                        + "&searchDate="+searchDate;
                }
            }
            
        }catch (NullPointerException ex){
            log("SearchHistoryServlet _ NullPointer " + ex.getMessage());
        }catch (NamingException ex){
            log("SearchHistoryServlet _ Naming " + ex.getMessage());
        }catch (SQLException ex){
            log("SearchHistoryServlet _ SQL " + ex.getMessage()); 
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
