/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Controller;

import br.upf.menumaster.Entity.Lanches;
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

/**
 *
 * @author oroca
 */
@Named(value = "lanchesController")
@SessionScoped
public class LanchesController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.LanchesFacade ejbFacade;

    private Lanches lanches = new Lanches();
    private List<Lanches> lanchesList = new ArrayList<>();
    private Lanches selected;

    public List<Lanches> LanchesAll() {
        return ejbFacade.buscarTodos();
    }

    public List<Lanches> getLanchesList() {
        if (lanchesList == null || lanchesList.isEmpty()) {
            lanchesList = ejbFacade.buscarTodos();
        }
        return lanchesList;
    }
    
    public void setLanchesList(List<Lanches> lanchesList) {
        this.lanchesList = lanchesList;
    }

    public Lanches getSelected() {
        return selected;
    }

    public void setSelected(Lanches selected) {
        this.selected = selected;
    }

    public Lanches getLanches() {
        return lanches;
    }

    public void setLanches(Lanches lanches) {
        this.lanches = lanches;
    }

    public Lanches getLanches(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Lanches.class)
    public static class LanchesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LanchesController controller
                    = (LanchesController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "lanchesController");
            return controller.getLanches(getKey(value));
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
            if (object instanceof Lanches) {
                Lanches o = (Lanches) object;
                return getStringKey(o.getIdlanche());
            } else {
                return null;
            }
        }
    }

    public Lanches prepareAdicionar() {
        lanches = new Lanches();
        return lanches;
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
                        ejbFacade.createReturn(lanches);
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
        persist(PersistAction.DELETE,
                "Registro excluído com sucesso!");
    }

}
