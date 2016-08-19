package br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes;

/**
 *
 * @author Danillo
 */
public class FalhaInstanciacaoException extends Exception {

    /**
     * Creates a new instance of <code>FalhaInstanciacaoException</code> without
     * detail message.
     */
    public FalhaInstanciacaoException() {
    }

    /**
     * Constructs an instance of <code>FalhaInstanciacaoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public FalhaInstanciacaoException(String msg) {
        super(msg);
    }
}
