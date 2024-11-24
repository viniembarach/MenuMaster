package br.upf.menumaster.Controller;

import br.upf.menumaster.Entity.Bebidas;
import br.upf.menumaster.Entity.Hamburguers;
import br.upf.menumaster.Entity.Lanches;
import br.upf.menumaster.Entity.Pedidos;
import br.upf.menumaster.Entity.Mesas;
import br.upf.menumaster.enumeration.StatusPedido;
import br.upf.menumaster.enumeration.StatusPagamento;
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
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named(value = "pedidosController")
@SessionScoped
public class PedidosController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.PedidosFacade ejbFacade;

    private Pedidos pedido;
    private List<Pedidos> pedidosList;
    private Pedidos selected;
//    private Bebidas bebida;
//    private Lanches lanche;
//    private Hamburguers hamburguer;
    private Mesas mesa;

    private List<Pedidos> pedidosListNaoPagos;

    private List<Pedidos> pedidosCozinha;

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
        pedido = new Pedidos();
        pedidosList = ejbFacade.buscarTodos();

        pedidosListNaoPagos = ejbFacade.buscarPedidosComStatusPagamento();
        pedidosCozinha = ejbFacade.buscarPedidosCozinha();
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

    public Pedidos getPedidos(Integer id) {
        return ejbFacade.find(id);
    }

    public List<Pedidos> getPedidosListNaoPagos() {
        return pedidosListNaoPagos;
    }

    public void setPedidosListNaoPagos(List<Pedidos> pedidosListNaoPagos) {
        this.pedidosListNaoPagos = pedidosListNaoPagos;
    }

    public List<Pedidos> getPedidosCozinha() {
        return pedidosCozinha;
    }

    public void setPedidosCozinha(List<Pedidos> pedidosCozinha) {
        this.pedidosCozinha = pedidosCozinha;
    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
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
                        ejbFacade.createReturn(pedido);
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

    public void adicionarBebida(Bebidas bebida) {
        pedido.setBebidapedido(bebida);
        pedido.setValorpedido(bebida.getValorbebida());
        persist(PersistAction.CREATE, "Registro incluído com sucesso!");
    }

    public void adicionarLanche(Lanches lanche) {
        pedido.setLanchepedido(lanche);
        pedido.setValorpedido(lanche.getValorlanche());
        persist(PersistAction.CREATE, "Registro incluído com sucesso!");
    }

    public void adicionarHamburguer(Hamburguers hamburguer) {
        pedido.setHamburguerpedido(hamburguer);
        pedido.setValorpedido(hamburguer.getValorhamburguer());
        persist(PersistAction.CREATE, "Registro incluído com sucesso!");
    }

    public void editar() {
        persist(PersistAction.UPDATE, "Registro alterado com sucesso!");
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Registro excluído com sucesso!");
    }

    public String prepareCreate() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        pedido = new Pedidos();
        pedido.setStatuspagamento("Não Pago");
        pedido.setStatuspedido("Aberto");

        //buscando a mesa da sessão
        mesa = (Mesas) session.getAttribute("mesa");
        pedido.setMesapedido(mesa);

        return "cardapioBebidas.xhtml?faces-redirect=true";

    }

    public void alterarStatusPagamento(Pedidos pedido) {
        if (pedido != null) {
            try {
                // Verifica o status atual e alterna usando o enum
                StatusPagamento.fromString(pedido.getStatuspagamento());

                pedido.setStatuspagamento(StatusPagamento.PAGO.getDescricao()); // Muda para Pago

                // Salva a alteração no banco de dados
                ejbFacade.edit(pedido);

                // Atualiza a lista de pedidos, se necessário
                pedidosListNaoPagos = ejbFacade.buscarPedidosComStatusPagamento();

                // Mensagem de sucesso
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Status atualizado com sucesso!", null));
            } catch (Exception e) {
                // Mensagem de erro
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Erro ao atualizar o status!", e.getMessage()));
            }
        }
    }

    public void alterarStatusPedido(Pedidos pedido) {
        if (pedido != null) {
            try {
                // Obtém o status atual do pedido
                StatusPedido statusAtual = StatusPedido.fromString(pedido.getStatuspedido());

                if (null != statusAtual) // Verifica qual o novo status a ser atribuído com base no valor selecionado
                switch (statusAtual) {
                    case ABERTO:
                        pedido.setStatuspedido(StatusPedido.ABERTO.getDescricao());
                        break;
                    case EM_PREPARACAO:
                        pedido.setStatuspedido(StatusPedido.EM_PREPARACAO.getDescricao());
                        break;
                    case PRONTO:
                        pedido.setStatuspedido(StatusPedido.PRONTO.getDescricao());
                        break;
                    default:
                        break;
                }

                // Salva a alteração no banco de dados
                ejbFacade.edit(pedido);

                // Atualiza a lista de pedidos, se necessário
                pedidosListNaoPagos = ejbFacade.buscarPedidosCozinha();

                // Mensagem de sucesso
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Status atualizado com sucesso!", null));
            } catch (Exception e) {
                // Mensagem de erro
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Erro ao atualizar o status!", e.getMessage()));
            }
        }
    }

}
