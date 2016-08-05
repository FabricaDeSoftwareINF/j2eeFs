package br.ufg.inf.fabrica.j2eedesigns.fsinf.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Danillo
 */
public class AtualizadorModelo {
    
    public void atualizar(HttpServletRequest request, HttpServletResponse 
            response){
        
        /**
         * Acessa lista de chaves e valores
         */
        
        /**
         * Percorre lista para atualizar os objetos.
         */
        
        for (String chave : request.getParameterMap().keySet()) {
            System.out.println(chave);
        }
    }
    
    private void validaReferenciaBeans(){
        
    }
}
