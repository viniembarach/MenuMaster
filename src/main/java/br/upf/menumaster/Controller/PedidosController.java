package br.upf.menumaster.Controller;

import br.upf.menumaster.Entity.Pedidos;
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
@Named(value = "pedidosController")
@SessionScoped
public class PedidosController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.PedidosFacade ejbFacade;

    private Pedidos pedidos = new Pedidos();
    private List<Pedidos> pedidosList = new ArrayList<>();
    private Pedidos selected;

    public List<Pedidos> PedidosAll() {
        return ejbFacade.buscarTodos();
    }

    public List<Pedidos> getUsuarioList() {
        return pedidosList;
    }

    public void setPedidosList(List<Pedidos> pedidosList) {
        this.pedidosList = pedidosList;
    }

    public Pedidos getSelected() {
        return selected;
    }

    public void setSelected(Pedidos selected) {
        this.selected = selected;
    }

    public Pedidos getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedidos pedidos) {
        this.pedidos = pedidos;
    }

    public Pedidos getPedidos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Pedidos.class)
    public static class PedidosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PedidosController controller
                    = (PedidosController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "pedidosController");
            return controller.getPedidos(getKey(value));
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
            if (object instanceof Pedidos) {
                Pedidos o = (Pedidos) object;
                return getStringKey(o.getIdpedido());
            } else {
                return null;
            }
        }
    }

    public Pedidos prepareAdicionar() {
        pedidos = new Pedidos();
        return pedidos;
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
                        ejbFacade.createReturn(pedidos);
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
