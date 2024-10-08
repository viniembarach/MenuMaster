/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.facade;
import br.upf.menumaster.Entity.Bebidas;
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
public class BebidasFacade extends AbstractFacade<Bebidas>{
    
    @PersistenceContext(unitName = "DB_MenuMaster")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BebidasFacade() {
        super(Bebidas.class);
    }

    private List<Bebidas> entityList;

    /**
     * método responsável por buscar na base de dados, todas as cidades cadastradas
     * @return 
     */
    public List<Bebidas> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager().
                    createQuery("SELECT p FROM Bebidas p ORDER BY p.nomebebida");
            //verifica se existe algum resultado para não gerar excessão
            entityList = (List<Bebidas>) query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

}
