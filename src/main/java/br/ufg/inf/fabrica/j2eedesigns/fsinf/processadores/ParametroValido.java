/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.j2eedesigns.fsinf.processadores;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 *
 * @author danilloguimaraes
 */
public class ParametroValido{
    private final String parametro;
    private final Method metodo;
    private final Object instancia;
    private final Object argumento;
    
    public ParametroValido(String parametro, Method metodo, Object instancia, Object argumento){
        this.parametro = parametro;
        this.metodo = metodo;
        this.instancia = instancia;
        this.argumento = argumento;
    }
    
    public void atualiza() throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        metodo.invoke(instancia, argumento);
    }
}
