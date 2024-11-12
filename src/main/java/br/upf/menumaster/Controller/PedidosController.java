package br.upf.menumaster.Controller;

import br.upf.menumaster.Entity.Pedidos;
import br.upf.menumaster.Entity.Mesas;  // Adicione esta importação
import jakarta.annotation.PostConstruct;
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

@Named(value = "pedidosController")
@SessionScoped
public class PedidosController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.PedidosFacade ejbFacade;

    private Pedidos pedidos;
    private List<Pedidos> pedidosList;
    private Pedidos selected;

    private Mesas mesaSelecionada;

    @PostConstruct
    public void init() {
        // Recuperando a mesa selecionada da sessão
        mesaSelecionada = (Mesas) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mesaSelecionada");
        if (mesaSelecionada == null) {
            // Caso a mesa não esteja disponível, redirecionar ou mostrar mensagem de erro
            addErrorMessage("Nenhuma mesa foi selecionada.");
        }

        // Inicializa o objeto `pedidos` e carrega a lista de pedidos ao iniciar o controlador
        pedidos = new Pedidos();
        pedidosList = ejbFacade.buscarTodos();
    }

    public List<Pedidos> getPedidosList() {
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

    public Pedidos getPedidos(Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Pedidos.class)
    public static class PedidosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.isEmpty()) {
                return null;
            }
            PedidosController controller = (PedidosController) facesContext.getApplication().getELResolver()
                    .getValue(facesContext.getELContext(), null, "pedidosController");
            return controller.getPedidos(getKey(value));
        }

        Integer getKey(String value) {
            return Integer.valueOf(value);
        }

        String getStringKey(Integer value) {
            return value.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pedidos) {
                Pedidos o = (Pedidos) object;
                return getStringKey(o.getIdpedido());
            } else {
                throw new IllegalArgumentException("Objeto " + object + " do tipo " + object.getClass().getName()
                        + "; tipo esperado: " + Pedidos.class.getName());
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

    public enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    public void persist(PersistAction persistAction, String successMessage) {
        try {
            if (persistAction != null) {
                switch (persistAction) {
                    case CREATE:
                        ejbFacade.createReturn(pedidos);
                        pedidosList = ejbFacade.buscarTodos(); // Atualiza a lista após criar
                        break;
                    case UPDATE:
                        ejbFacade.edit(selected);
                        pedidosList = ejbFacade.buscarTodos(); // Atualiza a lista após editar
                        selected = null;
                        break;
                    case DELETE:
                        ejbFacade.remove(selected);
                        pedidosList = ejbFacade.buscarTodos(); // Atualiza a lista após excluir
                        selected = null;
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
            if (msg != null && !msg.isEmpty()) {
                addErrorMessage(msg);
            } else {
                addErrorMessage(ex.getLocalizedMessage());
            }
        } catch (Exception ex) {
            addErrorMessage(ex.getLocalizedMessage());
        }
    }

    public void adicionar() {
        persist(PersistAction.CREATE, "Registro incluído com sucesso!");
    }

    public void editar() {
        persist(PersistAction.UPDATE, "Registro alterado com sucesso!");
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Registro excluído com sucesso!");
    }
    
    public String IniciarPedidoBebidas() {
        if (selected != null) {
            // Realize a lógica necessária com a mesa selecionada, por exemplo, redirecionar para a página de pedidos
            return "cardapioBebidas.xhtml?faces-redirect=true";
        }
        return null;
    }
}
