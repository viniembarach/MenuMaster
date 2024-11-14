/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.upf.menumaster.enumeration;

/**
 *
 * @author vinie
 */
public enum StatusPagamento {
    NAO_PAGO("Não Pago"),
    PAGO("Pago");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
