/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.facade;

import br.upf.menumaster.Entity.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario>{
    /**
     * Definindo a unidade de persistencia
     */
    @PersistenceContext(unitName = "MenuMasterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Construtor que passa para superclasse a instância de Usuario
     */
    public UsuarioFacade() {
        super(Usuario.class);
    }

    private List<Usuario> entityList;

    public List<Usuario> buscarTodos() {
        entityList = new ArrayList<>();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager().createQuery("SELECT a FROM Usuario a order by a.nome");
            //verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                entityList = (List<Usuario>) query.getResultList();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return entityList;
    }

    /**
     * Buscar uma usuario por email
     * @param email
     * @param senha
     * @return 
     */
    public Usuario buscarPorEmail(String email, String senha) {
        Usuario usuario = new Usuario();
        try {
            //utilizando JPQL para construir a query 
            Query query = getEntityManager()
                    .createQuery("SELECT p FROM Usuario p WHERE p.email = :email AND p.senha = :senha");
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            //verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                usuario = (Usuario) query.getSingleResult();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return usuario;
    }
    
}
