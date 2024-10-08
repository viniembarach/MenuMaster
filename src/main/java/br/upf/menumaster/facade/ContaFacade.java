/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.facade;

import br.upf.menumaster.Entity.Contas;
import br.upf.menumaster.Entity.Lanches;
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
public class ContaFacade extends AbstractFacade<Contas>{
       
    @PersistenceContext(unitName = "DB_MenuMaster")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContaFacade() {
        super(Contas.class);
    }

    private List<Contas> entityList;

    /**
     * método responsável por buscar na base de dados, todas as cidades cadastradas
     * @return 
     */
    public List<Contas> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager().
                    createQuery("SELECT p FROM Contas p ORDER BY p.mesaconta");
            //verifica se existe algum resultado para não gerar excessão
            entityList = (List<Contas>) query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }   
}
