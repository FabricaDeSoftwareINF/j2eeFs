/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.j2eedesigns.fsinf.processadores;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author danilloguimaraes
 */
public class AcaoFormulario {
    private final String parametro;
    private final Method metodo;
    private final Object instancia;
    
    public AcaoFormulario(String parametro, Method metodo, Object instancia){
        this.parametro = parametro;
        this.metodo = metodo;
        this.instancia = instancia;
    }
    
    public String executar() throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        return (String) metodo.invoke(instancia, null);
    }

    public String getParametro() {
        return parametro;
    }

    public Method getMetodo() {
        return metodo;
    }

    public Object getInstancia() {
        return instancia;
    }

}
