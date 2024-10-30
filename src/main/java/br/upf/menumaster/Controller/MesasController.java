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
import br.upf.menumaster.Entity.Mesas;

/**
 *
 * @author oroca
 */
@Named(value = "mesasController")
@SessionScoped
public class MesasController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.MesasFacade ejbFacade;

    private Mesas mesas = new Mesas(Integer.BYTES);
    private List<Mesas> mesasList = new ArrayList<>();
    private Mesas selected;

    public List<Mesas> MesasAll() {
        return ejbFacade.buscarTodos();
    }

    public List<Mesas> getMesasList() {
        return mesasList;
    }

    public void setMesasList(List<Mesas> mesasList) {
        this.mesasList = mesasList;
    }

    public Mesas getSelected() {
        return selected;
    }

    public void setSelected(Mesas selected) {
        this.selected = selected;
    }

    public Mesas getMesas() {
        return mesas;
    }

    public void setMesas(Mesas mesas) {
        this.mesas = mesas;
    }

    public Mesas getMesas(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    public void selecionarMesa(Mesas mesas) {
        this.mesaSelecionada = mesas;
    }

    public String confirmarMesa() {
        return "pedidos.xhtml?faces-redirect=true"; // Redireciona para outra página com a mesa selecionada
    }

    public Mesas getMesaSelecionada() {
        return mesaSelecionada;
    }

    public void setMesaSelecionada(Mesas mesaSelecionada) {
        this.mesaSelecionada = mesaSelecionada;
    }

    @FacesConverter(forClass = Mesas.class)
    public static class MesasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MesasController controller
                    = (MesasController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "mesasController");
            return controller.getMesas(getKey(value));
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
            if (object instanceof Mesas) {
                Mesas o = (Mesas) object;
                return getStringKey(o.getNumeromesa());
            } else {
                return null;
            }
        }
    }

    public Mesas prepareAdicionar() {
        mesas = new Mesas(Integer.BYTES);
        return mesas;
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
                        ejbFacade.createReturn(mesas);
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
