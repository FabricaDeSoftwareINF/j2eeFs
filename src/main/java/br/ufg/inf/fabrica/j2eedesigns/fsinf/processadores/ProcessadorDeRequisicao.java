package br.ufg.inf.fabrica.j2eedesigns.fsinf.processadores;

import br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes.AcaoDoFormularioNaoInformado;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author danilloguimaraes
 */
public class ProcessadorDeRequisicao {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private final String actionForm = "actionForm";
    
    public ProcessadorDeRequisicao(HttpServletRequest request, 
            HttpServletResponse response){
        this.request = request;
        this.response = response;
    }
    
    public String processar() throws AcaoDoFormularioNaoInformado{
        validaParametros();
        throw new NotImplementedException();
    }
    
    private void validaParametros() throws AcaoDoFormularioNaoInformado{
        String acao = request.getParameter(actionForm);
        if(acao==null || acao.isEmpty()){
            throw new AcaoDoFormularioNaoInformado(
                    "Parâmetro 'actionForm' não informado no formulário");
        }
    }
    
    private boolean acaoDoFormularioFoiInformado(){
        throw new NotImplementedException();
    }

}
