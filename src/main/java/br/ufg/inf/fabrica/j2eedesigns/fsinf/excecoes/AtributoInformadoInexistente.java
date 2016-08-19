package br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes;

/**
 *
 * @author Danillo
 */
public class AtributoInformadoInexistente extends Exception {

    /**
     * Creates a new instance of <code>AtributoInformadoInexistente</code>
     * without detail message.
     */
    public AtributoInformadoInexistente() {
    }

    /**
     * Constructs an instance of <code>AtributoInformadoInexistente</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AtributoInformadoInexistente(String msg) {
        super(msg);
    }
}
