/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.facade;

import br.upf.menumaster.Entity.Mesas;
import br.upf.menumaster.Entity.Pedidos;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oroca
 */
@Stateless
public class PedidosFacade extends AbstractFacade<Pedidos> {

    @PersistenceContext(unitName = "DB_MenuMaster")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidosFacade() {
        super(Pedidos.class);
    }

    private List<Pedidos> entityList;

    /**
     * método responsável por buscar na base de dados, todas as cidades
     * cadastradas
     *
     * @return
     */
    public List<Pedidos> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager().
                    createQuery("SELECT p FROM Pedidos p ORDER BY p.idpedido");
            //verifica se existe algum resultado para não gerar excessão
            entityList = (List<Pedidos>) query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    public List<Pedidos> buscarPedidosMesa(Mesas mesa) {
        List<Pedidos> pedidosMesa = new ArrayList<>();
        try {
            // Validação para garantir que a mesa não é nula
            if (mesa == null) {
                throw new IllegalArgumentException("A mesa fornecida é nula.");
            }

            // Consulta JPQL para buscar pedidos relacionados à mesa
            Query query = getEntityManager().createQuery(
                    "SELECT p FROM Pedidos p WHERE p.mesapedido = :mesa AND p.statuspagamento = 'Não Pago' ORDER BY p.idpedido"
            );
            query.setParameter("mesa", mesa);

            pedidosMesa = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao buscar pedidos da mesa: " + e.getMessage());
            e.printStackTrace(); // Para detalhes do erro no log
        }
        return pedidosMesa;
    }

    public List<Pedidos> buscarPedidosComStatusPagamento() {
        List<Pedidos> pedidosComStatus = new ArrayList<>();
        try {
            // Consulta JPQL para buscar pedidos onde o status de pagamento não é nulo
            Query query = getEntityManager().createQuery("SELECT p FROM Pedidos p WHERE p.statuspagamento = 'Não Pago' ORDER BY p.idpedido");
            pedidosComStatus = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return pedidosComStatus;
    }

    public List<Pedidos> buscarPedidosCozinha() {
        List<Pedidos> pedidosCozinha = new ArrayList<>();
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM Pedidos p WHERE p.statuspagamento = 'Não Pago' AND p.statuspedido <> 'Pronto' ORDER BY p.idpedido");
            pedidosCozinha = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return pedidosCozinha;
    }

}
