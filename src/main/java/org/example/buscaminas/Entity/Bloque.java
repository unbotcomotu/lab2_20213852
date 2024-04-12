package org.example.buscaminas.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bloque {
    private String estado;
    private int numero;
    private boolean tieneBomba;

    public Bloque() {
    }

    public Bloque(String estado, int numero, boolean tieneBomba) {
        this.estado = estado;
        this.numero = numero;
        this.tieneBomba = tieneBomba;
    }
}
