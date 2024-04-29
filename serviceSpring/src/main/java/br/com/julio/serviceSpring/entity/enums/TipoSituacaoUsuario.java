package br.com.julio.serviceSpring.entity.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoSituacaoUsuario {

    ATIVO ("A","Ativo"),
    INATIVO ("I", "Inativo"),
    PENDENTE ("P", "Pendente");

    private String codigo;
    private String descricao;

    private TipoSituacaoUsuario(String codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @JsonValue
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    @JsonCreator
    public static TipoSituacaoUsuario doValor(String codigo){
        return switch (codigo) {
            case "A" -> ATIVO;
            case "I" -> INATIVO;
            case "P" -> PENDENTE;
            default -> null;
        };
    }

}
