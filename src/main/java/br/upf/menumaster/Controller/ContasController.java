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
import br.upf.menumaster.Entity.Contas;

/**
 *
 * @author oroca
 */
@Named(value = "contasController")
@SessionScoped
public class ContasController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.ContaFacade ejbFacade;

    private Contas contas = new Contas();
    private List<Contas> contasList = new ArrayList<>();
    private Contas selected;

    public List<Contas> ContasAll() {
        return ejbFacade.buscarTodos();
    }

    public List<Contas> getContasList() {
        return contasList;
    }

    public void setContasList(List<Contas> contasList) {
        this.contasList = contasList;
    }

    public Contas getSelected() {
        return selected;
    }

    public void setSelected(Contas selected) {
        this.selected = selected;
    }

    public Contas getContas() {
        return contas;
    }

    public void setContas(Contas contas) {
        this.contas = contas;
    }

    public Contas getContas(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Contas.class)
    public static class ContasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContasController controller
                    = (ContasController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "contasController");
            return controller.getContas(getKey(value));
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
            if (object instanceof Contas) {
                Contas o = (Contas) object;
                return getStringKey(o.getIdconta());
            } else {
                return null;
            }
        }
    }

    public Contas prepareAdicionar() {
        contas = new Contas();
        return contas;
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
                        ejbFacade.createReturn(contas);
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
