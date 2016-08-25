package br.ufg.inf.fabrica.j2eedesigns.fsinf.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;

/**
 *
 * @author Danillo
 */
public class FsJavaBeanSupporter {

    public static <T> T constroi(Class klass) throws NoSuchMethodException, 
            InstantiationException, IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        Constructor constructor = klass.getConstructor(klass);
        return (T) constructor.newInstance(null);
    }
    
    public static Class getTipoDeAtributo(Class hostClass, String nomeAtributo) throws NoSuchMethodException{
        if(hostClass==null || nomeAtributo==null || nomeAtributo.isEmpty()){
            throw new InvalidParameterException("Argumentos obrigatórios não informados");
        }
        String getterName = "get" + nomeAtributo.substring(0,1).toUpperCase() + 
                nomeAtributo.substring(1);
        Method method = hostClass.getMethod(getterName, null);
        return method.getReturnType();
    }

    public static boolean isTipoBasico(Class klass){
        return klass.isPrimitive() ||
                klass.equals(Boolean.class) || 
                klass.equals(Byte.class) || 
                klass.equals(Character.class) || 
                klass.equals(Short.class) || 
                klass.equals(Integer.class) || 
                klass.equals(Long.class) || 
                klass.equals(Double.class) || 
                klass.equals(String.class);
    }
}