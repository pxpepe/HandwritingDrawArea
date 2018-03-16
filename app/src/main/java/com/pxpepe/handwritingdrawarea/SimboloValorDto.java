package com.pxpepe.handwritingdrawarea;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pxpep on 16/03/2018.
 */

public class SimboloValorDto implements Serializable {

    private String simbolo;
    private List<String> valoresPossiveis;

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public List<String> getValoresPossiveis() {
        return valoresPossiveis;
    }

    public void setValoresPossiveis(List<String> valoresPossiveis) {
        this.valoresPossiveis = valoresPossiveis;
    }
}
