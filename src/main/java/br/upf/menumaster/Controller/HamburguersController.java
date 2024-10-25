/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Controller;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import br.upf.menumaster.Entity.Hamburguers;

/**
 *
 * @author oroca
 */
@Named(value = "hamburguersController")
@SessionScoped
public class HamburguersController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.HamburguersFacade ejbFacade;

    private Hamburguers hamburguers = new Hamburguers();
    private List<Hamburguers> hamburguersList = new ArrayList<>();
    private Hamburguers selected;

    public List<Hamburguers> HamburguersAll() {
        return ejbFacade.buscarTodos();
    }

    public List<Hamburguers> getHamburguersList() {
        if(hamburguersList == null || hamburguersList.isEmpty()){
            hamburguersList = ejbFacade.buscarTodos();
        }
        return hamburguersList;
    }

    public void setHamburguersList(List<Hamburguers> hamburguersList) {
        this.hamburguersList = hamburguersList;
    }

    public Hamburguers getSelected() {
        return selected;
    }

    public void setSelected(Hamburguers selected) {
        this.selected = selected;
    }

    public Hamburguers getHamburguers() {
        return hamburguers;
    }

    public void setHamburguers(Hamburguers hamburguers) {
        this.hamburguers = hamburguers;
    }

    public Hamburguers getHamburguers(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Hamburguers.class)
    public static class HamburguersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HamburguersController controller
                    = (HamburguersController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "lanchesController");
            return controller.getHamburguers(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext,
                UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Hamburguers) {
                Hamburguers o = (Hamburguers) object;
                return getStringKey(o.getIdingrediente());
            } else {
                return null;
            }
        }
    }

    public Hamburguers prepareAdicionar() {
        hamburguers = new Hamburguers();
        return hamburguers;
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    public void persist(PersistAction persistAction, String successMessage) {
        try {
            if (null != persistAction) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(hamburguers);
                        break;

                    case UPDATE:
                        ejbFacade.edit(selected);
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        selected = null;
                        break;
                    default:
                        break;
                }
            }
            addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                addErrorMessage(msg);
            } else {
                addErrorMessage(ex.getLocalizedMessage());
            }
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void adicionar() {
        persist(PersistAction.CREATE,
                "Registro incluído com sucesso!");
    }

    public void editar() {
        persist(PersistAction.UPDATE,
                "Registro alterado com sucesso!");
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Registro excluído com sucesso!");
        // Atualizar a lista de usuários após deletar
        hamburguersList = ejbFacade.buscarTodos();
}
}
