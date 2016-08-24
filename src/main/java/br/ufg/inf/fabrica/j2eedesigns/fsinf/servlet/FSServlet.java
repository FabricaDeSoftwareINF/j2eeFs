package br.ufg.inf.fabrica.j2eedesigns.fsinf.servlet;

import br.ufg.inf.fabrica.j2eedesigns.fsinf.anotacoes.FsBean;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsBeans;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsEscopo;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsJavaBeanSupporter;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.AcaoDoFormularioNaoInformado;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.AtributoInformadoInexistente;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.BeanNaoRegistradoException;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.FalhaInstanciacaoException;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.processadores.ProcessadorDeRequisicao;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Danillo
 */
public class FSServlet extends HttpServlet {

    private String classPath;
    private final String FORM_ACTION = "formAction";
    private final String PAGINA_FALHA_NO_SISTEMA = "falhaNoSistema.jsp";
    private FsBeans beans;

    @Override
    public void init() throws ServletException {
        super.init();
        this.classPath = getServletContext().getRealPath("/WEB-INF/classes");
        this.beans = new FsBeans();
        registraBeans(classPath, "");
    }

    private void registraBeans(String path, String fullName) {
        File file = new File(path);
        if (file.isDirectory()) {
            for (String childName : file.list()) {
                File child = new File(path + File.separator + childName);
                String childFullName;
                if (fullName.isEmpty()) {
                    childFullName = childName;
                } else {
                    childFullName = fullName + "." + childName;
                }
                registraBeans(child.getPath(), childFullName);
            }
        } else if (path.endsWith(".class")) {
            try {
                String name = fullName.substring(0, fullName.length() - 6);
                Class k = getServletContext().getClassLoader().loadClass(name);
                if (k.isAnnotationPresent(FsBean.class)) {
                    FsBean beanAnnotated = (FsBean) k.getAnnotation(FsBean.class);
                    String nome = beanAnnotated.nome();
                    if (nome.isEmpty()) {
                        nome = k.getSimpleName().substring(0, 1).toLowerCase();
                        if (k.getSimpleName().length() > 1) {
                            nome += k.getSimpleName().substring(1);
                        }
                    }
                    FsEscopo escopo = beanAnnotated.escopo();
                    beans.registerDeclaredBean(nome, k, escopo);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FSServlet.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException,
            BeanNaoRegistradoException, FalhaInstanciacaoException,
            NoSuchFieldException, AtributoInformadoInexistente, NoSuchMethodException {

        /**
         * Pensar no tratamento do Servlet para envio de arquivos
         */
        ProcessadorDeRequisicao processador = new ProcessadorDeRequisicao(
                request, response);
        String resultado;
        try {
            resultado = processador.processar();
            if (resultado == null || resultado.isEmpty()) {
                resultado = "index.jsp";
            }
            RequestDispatcher dispatcher
                    = request.getRequestDispatcher(resultado);
            dispatcher.forward(request, response);
        } catch (AcaoDoFormularioNaoInformado ex) {
            Logger.getLogger(FSServlet.class.getName()).log(Level.SEVERE, null, ex);
            RequestDispatcher dispatcher
                    = request.getRequestDispatcher(PAGINA_FALHA_NO_SISTEMA);
            dispatcher.forward(request, response);
        }

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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (BeanNaoRegistradoException | FalhaInstanciacaoException |
                NoSuchFieldException | AtributoInformadoInexistente |
                NoSuchMethodException ex) {
            Logger.getLogger(FSServlet.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (BeanNaoRegistradoException | FalhaInstanciacaoException |
                NoSuchFieldException | AtributoInformadoInexistente |
                NoSuchMethodException ex) {
            Logger.getLogger(FSServlet.class.getName()).log(Level.SEVERE, null,
                    ex);
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
