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
import huyvl.hotel.HotelDAO;
import huyvl.hotel.HotelDTO;
import huyvl.room.RoomDAO;
import huyvl.room.RoomDTO;
import java.util.Date;

/**
 *
 * @author HUYVU
 */
@WebServlet(name = "SearchHotelServlet", urlPatterns = {"/SearchHotelServlet"})
public class SearchHotelServlet extends HttpServlet {

    private final String HOME = "LoadHotelServlet";

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
        String searchName = request.getParameter("searchName");
        String searchArea = request.getParameter("searchArea");
        if (searchArea.equalsIgnoreCase("Hotel Area")) {
            searchArea = "";
        }
        String searchDate = request.getParameter("searchDate");
        String searchAmount = request.getParameter("searchAmount");
        String url = HOME;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  ;
        boolean isSearching = true;
        Date checkinDate = null;
        Date checkoutDate = null;
        try {
            String checkinDatetmp = null;
            String checkoutDatetmp = null;
            if (!searchDate.isEmpty()) {
                StringTokenizer stk = new StringTokenizer(searchDate, " - ");
                checkinDatetmp = (stk.nextToken());
                checkoutDatetmp = (stk.nextToken());
                StringTokenizer stkCheckin = new StringTokenizer(checkinDatetmp, "/");
                String day1 = stkCheckin.nextToken();
                String month1 = stkCheckin.nextToken();
                String year1 = stkCheckin.nextToken();
                checkinDatetmp = year1 + "-" + month1 + "-" + day1;
                checkinDate = formatter.parse(checkinDatetmp);
                StringTokenizer stkCheckout = new StringTokenizer(checkoutDatetmp, "/");
                String day2 = stkCheckout.nextToken();
                String month2 = stkCheckout.nextToken();
                String year2 = stkCheckout.nextToken();
                checkoutDatetmp = year2 + "-" + month2 + "-" + day2;
                checkoutDate = formatter.parse(checkoutDatetmp);
            }
            if (searchName.isEmpty() && searchArea.isEmpty() && searchDate.isEmpty() && searchAmount.isEmpty()) {
                isSearching = false;
            }
            if (isSearching) {
                HotelDAO hotelDAO = new HotelDAO();
                RoomDAO roomDAO = new RoomDAO();
                boolean isSearchDate = false;
                if (!searchName.isEmpty() && !searchArea.isEmpty() && !searchDate.isEmpty() && !searchAmount.isEmpty()) {
                    hotelDAO.searchHotelWithAllFields(searchName, searchArea, checkinDate, checkoutDate, searchAmount);
                    roomDAO.getAvaiableRoomWithAllFields(searchName, searchArea, checkinDate, checkoutDate, searchAmount);
                } //du field
                if(searchName.isEmpty() && !searchArea.isEmpty() && !searchDate.isEmpty() && !searchAmount.isEmpty()){
                    hotelDAO.searchHotelWithoutName(searchArea, checkinDate, checkoutDate, searchAmount);
                    roomDAO.getAvaiableRoomWithAllFields(searchName, searchArea, checkinDate, checkoutDate, searchAmount);
                } //ko co name
                if(!searchName.isEmpty() && searchArea.isEmpty() && !searchDate.isEmpty() && !searchAmount.isEmpty()){
                    hotelDAO.searchHotelWithoutArea(searchName, checkinDate, checkoutDate, searchAmount);
                    roomDAO.getAvaiableRoomWithoutArea(searchName, checkinDate, checkoutDate, searchAmount);
                } //ko co area
//               

                List<HotelDTO> hotelList = hotelDAO.getHotelList(); //get list avaiable room of hotel
                //roomDAO.getAllRoom();
                
                List<RoomDTO> roomList = roomDAO.getRoomList();

                request.removeAttribute("HOTELLIST");
                request.setAttribute("HOTELLIST", hotelList);
                request.removeAttribute("ROOMLIST");
                request.setAttribute("ROOMLIST", roomList);
                url = "DispatchServlet"
                        + "?btnAction=Result"
                        + "&searchName=" + searchName
                        + "&searchArea=" + searchArea
                        + "&searchDate=" + searchDate;
            } else {
                url = HOME;
            }
        } catch (NullPointerException ex) {
            log("SearchHotelServlet _ NullPointer " + ex.getMessage());
        } catch (NamingException ex) {
            log("SearchHotelServlet _ Naming " + ex.getMessage());
        } catch (SQLException ex) {
            log("SearchHotelServlet _ SQL " + ex.getMessage());
        } catch (ParseException ex) {
            log("SearchHotelServlet _ Parse " + ex.getMessage());
        } finally {
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
