/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Controller;

import br.upf.menumaster.Entity.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable{
       @EJB
    private br.upf.menumaster.facade.UsuarioFacade ejbFacade;

    public LoginController() {
    }

    //objeto que representa uma pessoa
    private Usuario usuario;

    public void prepareAutenticarUsuario() {
        usuario = new Usuario();
    }

    /**
     * Método utilizado para inicializar métodos ao instanciar a classe...
     */
    @PostConstruct
    public void init() {
        prepareAutenticarUsuario();
    }

    /**
     * Método utilizado para validar login e senha.
     *
     * @return
     */
    public String validarLogin() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Usuario usuarioDB = ejbFacade.buscarPorEmail(usuario.getEmail(), usuario.getSenha());
        if ((usuarioDB != null && usuarioDB.getIdusuario()!= null)) {
            //caso as credenciais foram válidas, então direciona para página index
            session.setAttribute("usuarioLogado", usuarioDB);
            return "/admin/mesas.xhtml?faces-redirect=true";
        } else {
            //senão, exibe uma mensagem de falha...
            FacesMessage fm = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Falha no Login!",
                    "Email ou senha incorreto!");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            return null;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
       return "/login.xhtml?faces-redirect=true";
    }
}
