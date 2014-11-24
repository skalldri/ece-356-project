/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prescriptiondrugs;

import databaseTools.Constants;
import ece356.UserData;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Prescription;

/**
 *
 * @author Bo
 */
public class NewPrescription extends HttpServlet {

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
        
        // get the doctor info
        String doctorUsername = ((UserData)request.getSession().getAttribute("userData")).getUsername(); 
        
        String ohip = request.getParameter("health_card").toUpperCase();
        String drug = request.getParameter("drug_name").toUpperCase();
        Integer refills = Integer.parseInt(request.getParameter("refills"));
        String startDate = request.getParameter("start_datetime");
        String endDate = request.getParameter("end_datetime");
        
        Statement stmt;
        Connection con;
        
        boolean hasPermission = false;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            // check if staff has permission to do this
            // check to see if doctor is main doc first
            
            String query = new StringBuilder().
                    append("SELECT * FROM Patient WHERE health_card='").
                    append(ohip).
                    append("' and deleted_datetime IS 0000-00-00 00:00:00").
                    toString();
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                if (rs.getNString("default_doctor_username").equals(doctorUsername))
                {
                    hasPermission = true;
                }
            }
            
            // if not main doctor check if they are a sub doctor
            if (!hasPermission)
            {
                query = new StringBuilder().
                        append("SELECT * FROM Staff_Permissions WHERE username='").
                        append(doctorUsername).
                        append("' AND health_card='").
                        append(ohip).
                        append("' AND deleted_datetime IS 0000-00-00 00:00:00").
                        toString();
                
                rs = stmt.executeQuery(query);
                
                // if there is an entry then the staff has permission already
                hasPermission = rs.next();
            }
            
            con.close();
        }
        catch(Exception e)
        {
            
        }
        
        if (!hasPermission)
        {
            request.getRequestDispatcher("NewPrescription.jsp").forward(request, response);
        }
        
        // make sure drug exists
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            String query = new StringBuilder().
                    append("SELECT * FROM Drug WHERE drug_name='").
                    append(drug).
                    append("' AND deleted_datetime IS 0000-00-00 00:00:00").
                    toString();
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (!rs.next())
                request.getRequestDispatcher("NewPrescription.jsp").forward(request, response);
            
            con.close();
        }
        catch(Exception e)
        {
            
        }
        
        // add to db
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Constants.url, Constants.user, Constants.pwd);
            stmt = con.createStatement();
            
            
            String query = new StringBuilder().
                    append("INSERT INTO Prescription").
                    append("(doctor_username, health_card, drug_name, refills, start_datetime, end_datetime)").
                    append("VALUES ('").
                    append(doctorUsername).
                    append("', '").
                    append(ohip).
                    append("', '").
                    append(drug).
                    append("', '").
                    append(refills).
                    append("', '").
                    append(startDate).append(" 00:00:00").
                    append("', '").
                    append(endDate).append(" 00:00:00").
                    append("')").
                    toString();
            
            stmt.executeUpdate(query);
            
            con.close();
        }
        catch (Exception e) 
        {
            
        }
        
        // clear the temp user saved so next time wont show up again
        request.getSession().setAttribute("prescriptionPatient", null);
        
        request.getRequestDispatcher("NewPrescription.jsp").forward(request, response);
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewPrescription</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewPrescription at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
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
