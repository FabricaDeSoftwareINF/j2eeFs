package br.ufg.inf.fabrica.j2eedesigns.fsinf.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
}
