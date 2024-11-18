/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.facade;

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

    public List<Pedidos> buscarPedidosComStatusPagamento() {
        List<Pedidos> pedidosComStatus = new ArrayList<>();
        try {
            // Consulta JPQL para buscar pedidos onde o status de pagamento não é nulo
            Query query = getEntityManager().createQuery("SELECT p FROM Pedidos p WHERE p.statuspagamento = 'NAO_PAGO' ORDER BY p.idpedido");
            pedidosComStatus = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return pedidosComStatus;
    }

}
