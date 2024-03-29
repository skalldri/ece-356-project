/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package patient;

import databaseTools.Constants;
import ece356.UserData;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Prescription;

/**
 *
 * @author Bo
 */
public class PrescriptionRecords extends HttpServlet {

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
        
        String ohip = request.getParameter("ohip");
        
        if(((UserData)request.getSession().getAttribute("userData")).getUserType().equals("patient"))
        {
            ohip = ((UserData)request.getSession().getAttribute("userData")).getUsername();
        }
        
        Statement stmt;
        Connection con;
        
        List<Prescription> prescriptions = new ArrayList<Prescription>();
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            String query = new StringBuilder().
                    append("SELECT * FROM Prescription WHERE health_card='").
                    append(ohip).
                    append("' AND deleted_datetime='0000-00-00 00:00:00'").
                    toString();
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                prescriptions.add(
                        new Prescription(
                        rs.getNString("doctor_username"), 
                        rs.getNString("health_card"),
                        rs.getNString("drug_name"),
                        rs.getInt("refills"),
                        rs.getDate("start_datetime"),
                        rs.getDate("end_datetime")
                        ));
            }
            
            con.close();
            
        }
        catch(Exception e)
        {
            PrintWriter out = response.getWriter();
            try {
                /* TODO output your page here. You may use following sample code. */
                out.println("<html>");
                out.println("<head>");
                out.println("<title>PrescriptionRecords</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Error: " + e.toString() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            } finally {            
                out.close();
            }
            
            return;
        }
        
        request.getSession().setAttribute("prescriptions", prescriptions);
        
        request.getRequestDispatcher("PrescriptionRecords.jsp").forward(request, response);
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
