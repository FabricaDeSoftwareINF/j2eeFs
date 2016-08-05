package br.ufg.inf.fabrica.j2eedesigns.fsinf.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//TO-DO

/**
 * Por padrão o nome do bean é o nome simples da classe e o escopo do bean é 
 * sessão. Para modificar o nome, informe explicitamente o valor do atributo
 * "nome". Para modificar o escopo, informe explicitamente o valor do atributo 
 * "escopo".
 * @author Danillo
 */
@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FsBean {
    String nome() default "";
    EscopoBeanFs escopo() default EscopoBeanFs.SESSAO;
}
