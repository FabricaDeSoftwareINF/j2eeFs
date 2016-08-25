package br.ufg.inf.fabrica.j2eedesigns.fsinf.processadores;

import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsBean;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsBeans;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.domain.FsJavaBeanSupporter;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.AcaoDoFormularioNaoInformado;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.BeanNaoRegistradoException;
import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.ParametroInvalidoException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    
    public ProcessadorDeRequisicao(HttpServletRequest request, 
            HttpServletResponse response, FsBeans fsBeans){
        this.request = request;
        this.response = response;
        this.fsBeans = fsBeans;
    }
    
    public String processar() throws AcaoDoFormularioNaoInformado, 
            BeanNaoRegistradoException,
            NoSuchMethodException,
            ParametroInvalidoException{
        validarNomesParametros();
        throw new NotImplementedException();
    }
    
    private void validarNomesParametros() throws AcaoDoFormularioNaoInformado, 
            BeanNaoRegistradoException,
            NoSuchMethodException,
            ParametroInvalidoException{
        String acao = request.getParameter(actionForm);
        validaAcaoFormularioInformado(acao);
        for (Map.Entry<String, String[]> entry:
                request.getParameterMap().entrySet()) {
            String parametro = entry.getKey();
            if(parametro.equals(actionForm)){
                continue;
            }
            validarNomeParametro(parametro);
        }
    }

    private void validarNomeParametro(String parametro) 
            throws BeanNaoRegistradoException, NoSuchMethodException, 
            ParametroInvalidoException {
        String[] caminho = parametro.split(regExpPonto);
        
        String beanName = caminho[0];
        FsBean fsBean = fsBeans.get(beanName);
        
        if(fsBean==null){
            throw new BeanNaoRegistradoException("Bean " +
                    beanName + " não registrado");
        }
        Class hostClass = fsBean.getKlass();
        for (int i = 1; i < caminho.length; i++) {
            String attrName = caminho[i];
            hostClass = FsJavaBeanSupporter.getTipoDeAtributo(hostClass, 
                    attrName);
        }
        if(!FsJavaBeanSupporter.isTipoBasico(hostClass)){
            throw new ParametroInvalidoException(
                    "Parâmetro informado não é um tipo básico (Tipo primitivo, "
                            + "Boolean, Byte, Character, Short, Integer, Long, "
                            + "Double ou String");
        }
        
    }

    private void validaAcaoFormularioInformado(String acao) 
            throws AcaoDoFormularioNaoInformado {
        if(acao==null || acao.isEmpty()){
            throw new AcaoDoFormularioNaoInformado(
                    "Parâmetro 'actionForm' não informado no formulário");
        }
    }
    
    private boolean acaoDoFormularioFoiInformado(){
        throw new NotImplementedException();
    }

}
