/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.upf.menumaster.Enumeration;

/**
 *
 * @author oroca
 */
public enum mesasEnum {
    Mesa1("Mesa 1"),
    Mesa2("Mesa 2"),
    Mesa3("Mesa 3");

    private final String value;

    private mesasEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
