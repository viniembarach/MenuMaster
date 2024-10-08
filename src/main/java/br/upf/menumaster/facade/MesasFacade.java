/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.facade;

import br.upf.menumaster.Entity.Mesas;
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
public class MesasFacade extends AbstractFacade<Mesas>{
    @PersistenceContext(unitName = "DB_MenuMaster")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MesasFacade() {
        super(Mesas.class);
    }

    private List<Mesas> entityList;

    /**
     * método responsável por buscar na base de dados, todas as cidades cadastradas
     * @return 
     */
    public List<Mesas> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager().
                    createQuery("SELECT p FROM Mesas p ORDER BY p.numeromesa");
            //verifica se existe algum resultado para não gerar excessão
            entityList = (List<Mesas>) query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }   
}
