package br.ufg.inf.fabrica.j2eedesigns.fsinf.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Danillo
 */
public class Validador {

    public void validarRequisicao(HttpServletRequest request){
        
        /**
         * Verificar se os beans informados na requisição são anotados e 
         * e se o framework os conhecem
         */
        
        /**
         * Validar se o método final foi informado. O método final esta 
         * representado por um parâmetro que se chama "action" e se seu valor 
         * aponta para um bean registrado. Se houver mais um parâmetro com o 
         * nome, um erro deve ser lançado.
         */
    }
}
