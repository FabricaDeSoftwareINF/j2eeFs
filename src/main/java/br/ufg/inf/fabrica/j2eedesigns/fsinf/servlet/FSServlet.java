package br.ufg.inf.fabrica.j2eedesigns.fsinf.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Danillo
 */
@WebServlet(name = "NewServlet", urlPatterns = {"/NewServlet"}, loadOnStartup = 0)
public class FSServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        
        /**
         * Realiza o scan das classes anotadas como bean e inicializa o 
         * repositório de beans
         */
        
    }
    
    
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        /**
         * Pensar no tratamento do Servlet para envio de arquivos
         */
        /**
         * Acessar constantes e outros valores pré configurados
         */
        /**
         * Utiliza um helper para acessar alguma informação específica de
         * parâmetro
         */
        /**
         * Atualiza modelo de objetos
         */
        AtualizadorModelo atualizador = new AtualizadorModelo();
        atualizador.atualizar(request, response);

        /**
         * Realiza operação destino
         */
        /**
         * Repassa a requisição para a view adequada
         */
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
