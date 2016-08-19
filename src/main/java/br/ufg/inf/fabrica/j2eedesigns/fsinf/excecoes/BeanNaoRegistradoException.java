package br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes;

/**
 *
 * @author Danillo
 */
public class BeanNaoRegistradoException extends Exception {

    /**
     * Creates a new instance of <code>BeanNaoRegistradoException</code> without
     * detail message.
     */
    public BeanNaoRegistradoException() {
    }

    /**
     * Constructs an instance of <code>BeanNaoRegistradoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public BeanNaoRegistradoException(String msg) {
        super(msg);
    }
}
