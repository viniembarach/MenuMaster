/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Controller;

import br.upf.menumaster.Enumeration.mesasEnum;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author oroca
 */
@Named("MesasEnumController")
@SessionScoped
public class MesasEnumController implements Serializable{
        
    public SelectItem[] getMesasEnum() {
        SelectItem[] items = new SelectItem[mesasEnum.values().length];
        int i = 0;
        for (mesasEnum t : mesasEnum.values()) {
            items[i++] = new SelectItem(t, t.getValue());
        }
        return items;
    }

}
