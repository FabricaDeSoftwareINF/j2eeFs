package br.ufg.inf.fabrica.j2eedesigns.fsinf.domain;

/**
 *
 * @author Danillo
 */
public class FsBean {
    private final String name;
    private final Class klass;
    private final FsEscopo escopo;

    public FsBean(String nome, Class klass, FsEscopo escopo) {
        this.name = nome;
        this.klass = klass;
        this.escopo = escopo;
    }
    
    public String getName() {
        return name;
    }

    public Class getKlass() {
        return klass;
    }

    public FsEscopo getEscopo() {
        return escopo;
    }
}
