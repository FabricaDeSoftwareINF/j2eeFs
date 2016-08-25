package br.ufg.inf.fabrica.j2eedesigns.fsinf.domain;

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
        return (T) klass.newInstance();
    }
    
    public static Class getTipoDeAtributo(Class hostClass, String nomeAtributo) 
            throws NoSuchMethodException{
        if(hostClass==null || nomeAtributo==null || nomeAtributo.isEmpty()){
            throw new InvalidParameterException(
                    "Argumentos obrigatórios não informados");
        }
        String getterName = getNomeMetodoGetter(nomeAtributo);
        Method method = hostClass.getMethod(getterName, null);
        return method.getReturnType();
    }

    public static Object get(Object instancia, String nomeAtributo) 
            throws NoSuchMethodException, IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException{
        String getterName = getNomeMetodoGetter(nomeAtributo);
        Method method = instancia.getClass().getMethod(getterName, null);
        return method.invoke(instancia, null);
    }
    
    public static Method getSetter(Class instanceClass, String nomeAtributo, 
            Class argumentType) throws NoSuchMethodException{
        String setterName = "set" + nomeAtributo.substring(0, 1).toUpperCase() +
                nomeAtributo.substring(1);
        return instanceClass.getMethod(setterName, argumentType);
    }
    
    public static Method getMethod(Class instanceClass, String nomeMetodo) throws NoSuchMethodException{
        return instanceClass.getMethod(nomeMetodo, null);
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

    private static String getNomeMetodoGetter(String nomeAtributo){
        return "get" + nomeAtributo.substring(0,1).toUpperCase() + 
                nomeAtributo.substring(1);
    }

}