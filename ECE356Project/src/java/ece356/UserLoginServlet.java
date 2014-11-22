/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import databaseTools.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Patient;

/**
 *
 * @author Stuart Alldritt
 */
public class UserLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        
        String url = "/login_failed.jsp";
        
        Statement stmt;
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            if("staff".equals(request.getParameter("usertype")))
            {
                String query = new StringBuilder().
                        append("SELECT * FROM Staff WHERE username = '").
                        append(request.getParameter("username")).
                        append("' AND password = '").
                        append(request.getParameter("password")).
                        append("' AND deleted_datetime IS NULL LIMIT 1").
                        toString();
            
                ResultSet result = stmt.executeQuery(query);
                
                if (result.next())
                {
                    UserData data = new UserData();
                    data.setPassword(request.getParameter("password"));        
                    data.setUsername(request.getParameter("username"));
                    data.setUserType(request.getParameter("usertype"));
                    data.setUserVariant(result.getNString("type"));

                    session.setAttribute("userData", data);
                    
                    if(result.getNString("type").equals("DOCTOR"))
                    {
                        url = "/DoctorMain";
                    } else if(result.getNString("type").equals("STAFF")) {
                        url = "/StaffMain.jsp";
                    }
                }
            }
            else if("patient".equals(request.getParameter("usertype")))
            {
                String query = new StringBuilder().
                        append("SELECT * FROM Patient WHERE health_card = '").
                        append(request.getParameter("username")).
                        append("' AND password = '").
                        append(request.getParameter("password")).
                        append("' AND deleted_datetime IS NULL LIMIT 1").
                        toString();
            
                ResultSet result = stmt.executeQuery(query);
                
                if (result.next())
                {
                    UserData data = new UserData();
                    data.setPassword(request.getParameter("password"));        
                    data.setUsername(request.getParameter("username"));
                    data.setUserType(request.getParameter("usertype"));

                    session.setAttribute("userData", data);
                
                    url = "/PatientMain";
                }
            }
                
            con.close();
        }
        catch(Exception e) 
        {
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
