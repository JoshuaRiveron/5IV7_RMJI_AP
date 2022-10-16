/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import cifrado.DES;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.FileUtils;

/**
 *
 * @author rodri
 */
@MultipartConfig
@WebServlet("/download")
public class main extends HttpServlet {

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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet main</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet main at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        
        
        String clave = request.getParameter("clave");
        Part archivo = request.getPart("archivo");
        
        if(!archivo.getSubmittedFileName().equals("") && clave.length()==8){
            
            if(archivo.getSubmittedFileName().endsWith(".txt") || archivo.getSubmittedFileName().endsWith(".des")){
                InputStream io = archivo.getInputStream();
                File archivoF = new File("/opt/tomcat/bin/files", archivo.getSubmittedFileName());
                Files.copy(io, archivoF.toPath(), StandardCopyOption.REPLACE_EXISTING);
                try {
                    DES des = new DES(archivoF.getPath(), clave);
                    String end = ".txt";
                    if(archivoF.getName().endsWith(".txt")){
                        des.cifrar();
                        end=".des";
                    }else{
                        des.descifrar();
                    }
                    try(InputStream in = new FileInputStream(new File("/opt/tomcat/bin/files/"+archivoF.getName().substring(0, archivoF.getName().length()-4)+end))){
                        OutputStream output = response.getOutputStream();

                        byte []buffer = new byte[1024];
                        int bytesLeidos=0;
                        System.out.println("aver");
                        while((bytesLeidos=in.read(buffer))>0){
                            System.out.println("entro o ne?");
                            output.write(buffer, 0, bytesLeidos);
                        }
                    }
                    
                    response.setContentType("application/x-download");
                    response.setHeader( "Content-Disposition", "filename=\""+archivoF.getName().substring(0, archivoF.getName().length()-4)+end+"\"" );
                    

                    File f = new File("/opt/tomcat/bin/files");

                    FileUtils.cleanDirectory(f);
                    
                }catch(Exception e){
                    
                }
            }else{
                request.setAttribute("error1", "error");
                request.getRequestDispatcher("./index.jsp").forward(request, response);
            }
            
        }else{
            request.setAttribute("error2", "error");
            request.getRequestDispatcher("./index.jsp").forward(request, response);
        }
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
