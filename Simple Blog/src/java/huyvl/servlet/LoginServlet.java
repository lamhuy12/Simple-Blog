/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import huyvl.registration.AccountDAO;
import huyvl.registration.AccountDTO;
import huyvl.cart.Cart;

/**
 *
 * @author HUYVU
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String currentPage = request.getParameter("currentPage");
        String url = currentPage;
        try {
            AccountDAO dao = new AccountDAO();
            AccountDTO result = dao.checkLogin(username, password);
            HttpSession session = request.getSession(false);
            Cart cart = new Cart();
            if (session != null) {
                cart = (Cart)session.getAttribute("CART");
                session.invalidate();
            }
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            session = request.getSession();
            if(result!=null){
                if(session!=null){
                    Cookie newCookies = new Cookie(username, password);
                    newCookies.setMaxAge(60*1);
                    response.addCookie(newCookies);
                    session.setAttribute("FULLNAME", result.getFullname());
                    session.setAttribute("USERID", result.getUsername());
                    session.setAttribute("ISLOGIN", "TRUE");
                    if(result.getRole().equalsIgnoreCase("ADMIN") || result.getRole().equalsIgnoreCase("PARTNER")){
                        session.setAttribute("ADMIN", "True");
                    }else{
                        url = currentPage;
                        if(cart!=null){
                            session.setAttribute("CART", cart);
                        }
                        session.setAttribute("ADMIN", "False");
                    }
                }
            }else{
                session.setAttribute("CART", cart);
                request.setAttribute("ERRORLOGIN", "Username or password is not correct");
            }
            
            
        }catch(NullPointerException ex){
            log("LoginServlet _ NullPointer " + ex.getMessage());
        }catch(SQLException ex){
            log("LoginServlet _ SQL " + ex.getMessage());
        }catch(NamingException ex){
            log("LoginServlet _ Naming " + ex.getMessage());
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
