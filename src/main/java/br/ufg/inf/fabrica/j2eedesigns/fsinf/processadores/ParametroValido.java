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
public class ParametroValido{
    private final String parametro;
    private final Method metodo;
    private final Object instancia;
    private final String argumento;
    private final Class tipoArgumento;
    
    public ParametroValido(String parametro, Method metodo, Object instancia, 
            String argumento, Class tipoArgumento){
        this.parametro = parametro;
        this.metodo = metodo;
        this.instancia = instancia;
        this.argumento = argumento;
        this.tipoArgumento = tipoArgumento;
    }
    
    public void atualiza() throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        Object valorArgumento = null;
        if(tipoArgumento.equals(boolean.class) || tipoArgumento.equals(Boolean.class)){
            valorArgumento = Boolean.parseBoolean(argumento);
        } else if(tipoArgumento.equals(int.class) || tipoArgumento.equals(Integer.class)){
            valorArgumento = Integer.parseInt(argumento);
        } else if(tipoArgumento.equals(byte.class) || tipoArgumento.equals(Byte.class)){
            valorArgumento = Byte.parseByte(argumento);
        } else if(tipoArgumento.equals(char.class) || tipoArgumento.equals(Character.class)){
            valorArgumento = argumento.charAt(0);
        } else if(tipoArgumento.equals(short.class) || tipoArgumento.equals(Short.class)){
            valorArgumento = Short.parseShort(argumento);
        } else if(tipoArgumento.equals(int.class) || tipoArgumento.equals(Integer.class)){
            valorArgumento = Integer.parseInt(argumento);
        }   else if(tipoArgumento.equals(long.class) || tipoArgumento.equals(Long.class)){
            valorArgumento = Long.parseLong(argumento);
        }  else if(tipoArgumento.equals(double.class) || tipoArgumento.equals(Double.class)){
            valorArgumento = Double.parseDouble(argumento);
        }  else if(tipoArgumento.equals(String.class)){
            valorArgumento = argumento;
        }
        metodo.invoke(instancia, valorArgumento);
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

    public String getArgumento() {
        return argumento;
    }

    public Class getTipoArgumento() {
        return tipoArgumento;
    }

}
