/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.fabrica.j2eedesigns.fsinf.excecoes;

/**
 *
 * @author danilloguimaraes
 */
public class AcaoDoFormularioNaoInformado extends Exception {

    /**
     * Creates a new instance of <code>AcaoDoFormularioNaoInformado</code>
     * without detail message.
     */
    public AcaoDoFormularioNaoInformado() {
    }

    /**
     * Constructs an instance of <code>AcaoDoFormularioNaoInformado</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AcaoDoFormularioNaoInformado(String msg) {
        super(msg);
    }
}
