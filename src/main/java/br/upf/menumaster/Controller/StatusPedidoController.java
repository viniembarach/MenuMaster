/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Controller;

import br.upf.menumaster.enumeration.StatusPedido;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author vinie
 */

@Named(value = "statuspedidoController")
@SessionScoped
public class StatusPedidoController implements Serializable{
    
    public SelectItem[] getStatusPedido(){
        SelectItem[] items = new SelectItem[StatusPedido.values().length];
        int i = 0;
        for (StatusPedido t : StatusPedido.values()){
            items[i++] = new SelectItem(t, t.getDescricao());
        }
        return items;
    }
}
