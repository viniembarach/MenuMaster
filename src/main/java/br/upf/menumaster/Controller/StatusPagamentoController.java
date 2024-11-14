/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Controller;

import br.upf.menumaster.enumeration.StatusPagamento;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author vinie
 */

@Named(value = "statuspagamentoController")
@SessionScoped
public class StatusPagamentoController implements Serializable{
    
    public SelectItem[] getStatusPags(){
        SelectItem[] items = new SelectItem[StatusPagamento.values().length];
        int i = 0;
        for (StatusPagamento t : StatusPagamento.values()){
            items[i++] = new SelectItem(t, t.getDescricao());
        }
        return items;
    }
}
