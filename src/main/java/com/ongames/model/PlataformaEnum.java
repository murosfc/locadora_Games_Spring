package com.ongames.model;

public enum PlataformaEnum {
    NS("Nintendo Swtich"),
    PS4("Playstation 4"),
    PS5("Playstation 5"),
    XBOXONE("Xbox One"),
    XBOXSERIESXS("Xbox Series X/S");
    
    @NotNull
    @Min(2)
    @Max(50)
    private String nome;

    private PlataformaEnum(String nome) {
        this.nome = nome;
    }
    
}
