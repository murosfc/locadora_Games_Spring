package com.ongames.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public enum PlataformaEnum {
    NS("Nintendo Swtich"),
    PS4("Playstation 4"),
    PS5("Playstation 5"),
    XBOXONE("Xbox One"),
    XBOXSERIES("Xbox Series X/S");
    
    @NotNull
    @Size(min=2, max=50) 
    private String nome;

    private PlataformaEnum(String nome) {
        this.nome = nome;
    } 
    
}
