/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.upf.menumaster.enumeration;

/**
 *
 * @author vinie
 */
public enum StatusPedido {
    ABERTO("Aberto"),
    EM_PREPARACAO("Em Preparação"),
    PRONTO("Pronto");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusPedido fromString(String label) {
        for (StatusPedido status : StatusPedido.values()) {
            if (status.getDescricao().equalsIgnoreCase(label)) {
                return status;
            }
        }
        return null;
    }
}
