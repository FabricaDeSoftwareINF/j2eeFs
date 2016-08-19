package br.ufg.inf.fabrica.j2eedesigns.fsinf.servlet;

import br.ufg.inf.fabrica.j2eedesigns.fsinf.anotacoes.FsBean;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsBeans;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsEscopo;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsJavaBeanSupporter;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.AtributoInformadoInexistente;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.BeanNaoRegistradoException;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.FalhaInstanciacaoException;
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

    private final String CLASSPATH
            = getServletContext().getRealPath("/WEB-INF/classes");
    private final String FORM_ACTION = "formAction";
    private FsBeans beans;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        this.beans = new FsBeans();
        registraBeans(CLASSPATH, "");
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
                    FsBean a = (FsBean) k.getAnnotation(FsBean.class);
                    String nome = a.nome();
                    if (nome.isEmpty()) {
                        nome = k.getSimpleName().substring(0, 1).toLowerCase();
                        if (k.getSimpleName().length() > 1) {
                            nome += k.getSimpleName().substring(1);
                        }
                    }
                    FsEscopo escopo = a.escopo();
                    beans.registerDeclaredBean(name, k, FsEscopo.SESSAO);
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
        processar(request, response);
        /**
         * Repassa a requisição para a view adequada
         */
        RequestDispatcher dispatcher
                = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void processar(HttpServletRequest request, 
            HttpServletResponse response) throws BeanNaoRegistradoException, 
            FalhaInstanciacaoException, NoSuchFieldException, 
            AtributoInformadoInexistente, NoSuchMethodException {

        /**
         * Percorre todos parametros e verifica se: - parâmetro formAction foi
         * informado - parâmetro formAction aponta para método válido em um bean
         * registrado - se todos parâmetros apontam para propriedades válidas em
         * um bean registrado.
         */
        Enumeration<String> nomesDosParametros = request.getParameterNames();
        boolean formActionValido = false;
        while (nomesDosParametros.hasMoreElements()) {
            String nomeParametro = nomesDosParametros.nextElement();

            if (nomeParametro.equals(FORM_ACTION)) {
                /**
                 * Verifica se esse parâmetro aponta para um bean registrado e
                 * um método válido dentro do bean.
                 */
                String[] parts = nomeParametro.split(".");
                String nomeBean = parts[0];

                if (beans.isBeanRegistrado(nomeBean)) {
                    throw new BeanNaoRegistradoException(nomeBean + 
                            " não registrado.");
                }
                br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsBean fsBean
                        = beans.get(nomeBean);
                Object bean = buscaBean(fsBean, request);
                Object attributeValue = bean;
                for (int i = 1; i < parts.length; i++){
                    String part = parts[i];
                    
                    Class hostClass = attributeValue.getClass();
                    Class attributeClass = hostClass.getDeclaredField(part).
                            getClass();
                    if(attributeClass.isPrimitive() || attributeClass.
                            equals(String.class)){
                        
                        if(parts.length<=(i+1)){
                            throw new AtributoInformadoInexistente(nomeParametro 
                                    + " inexistente");
                        }
                        Method m = hostClass.getMethod("get" + 
                                part.substring(0, 1) + part.substring(1), null);
                    } else {
                        
                    }
                    String methodName = "get" + part.substring(0, 1).
                            toUpperCase() + part.substring(1);
                    
                }

            }

        }
    }

    private Object buscaBean(
            br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsBean fsBean, 
            HttpServletRequest request) throws FalhaInstanciacaoException {
        Object bean;
        switch (fsBean.getEscopo()) {
            case APLICACAO:
                bean = request.getServletContext().
                        getAttribute(fsBean.getName());
                if (bean == null) {
                    try {
                        bean = FsJavaBeanSupporter.
                                constroi(fsBean.getKlass());
                        request.getServletContext().
                                setAttribute(fsBean.getName(), bean);
                    } catch (NoSuchMethodException | InstantiationException |
                            IllegalAccessException | IllegalArgumentException |
                            InvocationTargetException ex) {
                        Logger.getLogger(FSServlet.class.getName()).
                                log(Level.SEVERE, null, ex);
                        throw new FalhaInstanciacaoException(ex.getMessage());
                    }
                }
                break;
            case SESSAO:
                bean = request.getSession().
                        getAttribute(fsBean.getName());
                if (bean == null) {
                    try {
                        bean = FsJavaBeanSupporter.
                                constroi(fsBean.getKlass());
                        request.getSession().
                                setAttribute(fsBean.getName(), bean);
                    } catch (NoSuchMethodException | InstantiationException |
                            IllegalAccessException | IllegalArgumentException |
                            InvocationTargetException ex) {
                        Logger.getLogger(FSServlet.class.getName()).
                                log(Level.SEVERE, null, ex);
                        throw new FalhaInstanciacaoException(ex.getMessage());
                    }
                }
                break;
            default: //case REQUISICAO:
                bean = request.getAttribute(fsBean.getName());
                if (bean == null) {
                    try {
                        bean = FsJavaBeanSupporter.
                                constroi(fsBean.getKlass());
                        request.setAttribute(fsBean.getName(), bean);
                    } catch (NoSuchMethodException | InstantiationException |
                            IllegalAccessException | IllegalArgumentException |
                            InvocationTargetException ex) {
                        Logger.getLogger(FSServlet.class.getName()).
                                log(Level.SEVERE, null, ex);
                        throw new FalhaInstanciacaoException(ex.getMessage());
                    }
                }
                break;
        }
        return bean;
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
