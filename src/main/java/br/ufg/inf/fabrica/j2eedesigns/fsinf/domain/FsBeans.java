package br.ufg.inf.fabrica.j2eedesigns.fsinf.domain;

import java.util.HashMap;

/**
 *
 * @author Danillo
 */
public class FsBeans extends HashMap<String, FsBean>{
    
    public void registerDeclaredBean(String nome, Class klass, 
            FsEscopo escopo){
        FsBean bean = new FsBean(nome, klass, escopo);
        put(nome, bean);
    }
    
    public FsBean unRegisterDeclaredBean(String nome){
        return remove(nome);
    }
    
    public boolean isBeanRegistrado(String nomeBean){
        if(nomeBean==null || nomeBean.isEmpty()){
            return false;
        }
        return !(get(nomeBean)==null);
    }

}
