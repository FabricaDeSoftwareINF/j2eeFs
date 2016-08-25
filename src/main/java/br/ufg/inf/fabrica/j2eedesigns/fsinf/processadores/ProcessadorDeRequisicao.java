package br.ufg.inf.fabrica.j2eedesigns.fsinf.processadores;

import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsBean;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsBeans;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsJavaBeanSupporter;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.AcaoDoFormularioNaoInformado;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.BeanNaoRegistradoException;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.ParametroInvalidoException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author danilloguimaraes
 */
public class ProcessadorDeRequisicao {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final FsBeans fsBeans;
    private final String regExpPonto = "\\.";
    private final String actionForm = "actionForm";
    private AcaoFormulario acaoForm = null;

    public ProcessadorDeRequisicao(HttpServletRequest request,
            HttpServletResponse response, FsBeans fsBeans) {
        this.request = request;
        this.response = response;
        this.fsBeans = fsBeans;
    }

    public String processar() throws AcaoDoFormularioNaoInformado,
            BeanNaoRegistradoException,
            NoSuchMethodException,
            ParametroInvalidoException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
        List<ParametroValido> parametrosValidos = validarParametros();
        atualizarParametrosInformados(parametrosValidos);
        return executarFuncaoFormulario();
    }

    private List<ParametroValido> validarParametros() 
            throws AcaoDoFormularioNaoInformado,
            BeanNaoRegistradoException,
            NoSuchMethodException,
            ParametroInvalidoException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
        String acao = request.getParameter(actionForm);
        validaAcaoFormularioInformado(acao);
        List<ParametroValido> parametrosValidos = new ArrayList<>();
        for (Map.Entry<String, String[]> entry
                : request.getParameterMap().entrySet()) {
            String parametro = entry.getKey();
            if (parametro.equals(actionForm)) {
                continue;
            }
            parametrosValidos.add(validarParametro(parametro));
        }
        return parametrosValidos;
    }

    private ParametroValido validarParametro(String parametro)
            throws BeanNaoRegistradoException, NoSuchMethodException,
            ParametroInvalidoException, IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException {
        String[] caminho = parametro.split(regExpPonto);

        String beanName = caminho[0];
        FsBean fsBean = fsBeans.get(beanName);
        
        if (fsBean == null) {
            throw new BeanNaoRegistradoException("Bean "
                    + beanName + " não registrado");
        }
        Object hostInstance = buscarBean(fsBean);
        Object attrInstance = null;
        Class hostClass = fsBean.getKlass();
        Class attrClass = null;
        String attrName = null;
        
        for (int i = 1; i < caminho.length; i++) {
            if(attrClass!=null){
                hostClass = attrClass;
                hostInstance = attrInstance;
            }
            attrName = caminho[i];
            attrClass = FsJavaBeanSupporter.getTipoDeAtributo(hostClass,
                    attrName);
            attrInstance = FsJavaBeanSupporter.get(hostInstance, attrName);
        }
        if (!FsJavaBeanSupporter.isTipoBasico(attrClass)) {
            throw new ParametroInvalidoException(
                    "Parâmetro informado não é um tipo básico (Tipo primitivo, "
                    + "Boolean, Byte, Character, Short, Integer, Long, "
                    + "Double ou String");
        }
        Method setter = FsJavaBeanSupporter.getSetter(hostClass, attrName, 
                attrClass);
        String valor = request.getParameter(parametro);
        return new ParametroValido(parametro, setter, hostInstance, valor, 
                attrClass);
    }

    private void validaAcaoFormularioInformado(String acao)
            throws AcaoDoFormularioNaoInformado, BeanNaoRegistradoException, 
            NoSuchMethodException, IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException {
        if (acao == null || acao.isEmpty()) {
            throw new AcaoDoFormularioNaoInformado(
                    "Parâmetro 'actionForm' não informado no formulário");
        }
        String[] caminho = acao.split(regExpPonto);

        String beanName = caminho[0];
        FsBean fsBean = fsBeans.get(beanName);
        
        if (fsBean == null) {
            throw new BeanNaoRegistradoException("Bean "
                    + beanName + " não registrado");
        }
        Object hostInstance = buscarBean(fsBean);
        Object attrInstance = null;
        Class hostClass = fsBean.getKlass();
        Class attrClass = null;
        String attrName = null;
        
        for (int i = 1; i < caminho.length; i++) {
            if(attrClass!=null){
                hostClass = attrClass;
                hostInstance = attrInstance;
            }
            attrName = caminho[i];
            if((i+1)==caminho.length){
                Method metodo = FsJavaBeanSupporter.getMethod(hostClass, 
                        caminho[i]);
                acaoForm = new AcaoFormulario(acao, metodo, hostInstance);
            } else {
                attrClass = FsJavaBeanSupporter.getTipoDeAtributo(hostClass,
                    attrName);
                attrInstance = FsJavaBeanSupporter.get(hostInstance, attrName);
            }
        }
        System.out.println("teste");
        //VERIFICAR SE É UM MÉTODO E RETORNA STRING
        
    }

    private void atualizarParametrosInformados(
            List<ParametroValido> parametrosValidos) 
            throws IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException{
        for (ParametroValido parametro : parametrosValidos) {
            parametro.atualiza();
        }
    }
    
    private Object buscarBean(FsBean fsBean) throws NoSuchMethodException {
        Object bean;
        try {
            switch (fsBean.getEscopo()) {
                case REQUISICAO:
                    bean = request.getAttribute(fsBean.getName());
                    if (bean == null) {
                        bean = FsJavaBeanSupporter.constroi(fsBean.getKlass());
                        request.setAttribute(fsBean.getName(), bean);
                    }
                    break;
                case SESSAO:
                    bean = request.getSession().getAttribute(fsBean.getName());
                    if (bean == null) {
                        bean = FsJavaBeanSupporter.constroi(fsBean.getKlass());
                        request.getSession().setAttribute(fsBean.getName(), 
                                bean);
                    }
                    break;
                default: //APLICACAO
                    bean = request.getServletContext().getAttribute(
                            fsBean.getName());
                    if (bean == null) {
                        bean = FsJavaBeanSupporter.constroi(fsBean.getKlass());
                        request.getServletContext().setAttribute(fsBean.getName(),
                                bean);
                    }
                    break;
            }
        } catch (InstantiationException | IllegalAccessException | 
                IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ProcessadorDeRequisicao.class.getName()).log(
                    Level.SEVERE, null, ex);
            return null;
        }
        return bean;
    }

    public String executarFuncaoFormulario() throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        return acaoForm.executar();
    }
}
