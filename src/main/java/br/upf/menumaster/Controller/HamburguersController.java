/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Controller;

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
import br.upf.menumaster.Entity.Hamburguers;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author oroca
 */
@Named(value = "hamburguersController")
@SessionScoped
public class HamburguersController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.HamburguersFacade ejbFacade;

    private Hamburguers hamburguers = new Hamburguers();
    private List<Hamburguers> hamburguersList = new ArrayList<>();
    private Hamburguers selected;
    private StreamedContent hamburguersImagem;
    private StreamedContent logoHamburguer;
    private UploadedFile file;

    public List<Hamburguers> HamburguersAll() {
        return ejbFacade.buscarTodos();
    }

    public List<Hamburguers> getHamburguersList() {
        if (hamburguersList == null || hamburguersList.isEmpty()) {
            hamburguersList = ejbFacade.buscarTodos();
        }
        return hamburguersList;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) {
        setFile(event.getFile());

        // Verifica o tipo MIME do arquivo carregado
        if (file != null && file.getContent() != null) {
            try {
                // Detecta o tipo MIME do arquivo
                String contentType = Files.probeContentType(Paths.get(file.getFileName()));
                if (contentType != null && (contentType.equals("image/png") || contentType.equals("image/jpeg"))) {
                    // Se o tipo for PNG ou JPEG, processa o arquivo
                    getLogoHamburguerUpload();
                } else {
                    // Se não for uma imagem válida, exibe uma mensagem de erro
                    addErrorMessage("Por favor, carregue um arquivo de imagem PNG ou JPEG.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                addErrorMessage("Erro ao tentar verificar o tipo do arquivo.");
            }
        }
    }

    public void getLogoHamburguerUpload() {
        try {
            // Tenta ler o conteúdo da imagem
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(file.getContent()));
            if (image != null) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                try {
                    // Converte a imagem para o formato PNG e armazena em um array de bytes
                    ImageIO.write(image, "png", out);
                    selected.setImagem(out.toByteArray());
                } catch (IOException e) {
                    // Exceção ao escrever a imagem, trate-a aqui
                    e.printStackTrace();
                }
            } else {
                // Erro ao tentar ler a imagem, arquivo pode estar corrompido ou não ser uma imagem válida
                addErrorMessage("Erro: O arquivo não é uma imagem válida.");
            }
        } catch (IOException ex) {
            Logger.getLogger(BebidasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StreamedContent getLogoMarca() {
        if (hamburguersImagem == null || hamburguersImagem.getStream() == null || hamburguersImagem.getStream().toString().isEmpty() || !hamburguersImagem.getStream().equals(selected.getImagem())) {
            getSelectedImagem();
        }
        return hamburguersImagem;
    }

    public void setHamburguersImagem(StreamedContent hamburguersImagem) {
        this.hamburguersImagem = hamburguersImagem;
    }

    public void getSelectedImagem() {
        if (selected.getImagem() != null) {
            InputStream is = new ByteArrayInputStream((byte[]) selected.getImagem());
            setHamburguersImagem(DefaultStreamedContent.builder().contentType("image/png").stream(() -> is).build());
        }
    }

    public StreamedContent getHamburguersImagem() {
        if (logoHamburguer == null || logoHamburguer.getStream() == null || logoHamburguer.getStream().toString().isEmpty() || !logoHamburguer.getStream().equals(selected.getImagem())) {
            getLogoHamburguer();
        }
        return logoHamburguer;
    }

    public void getLogoHamburguer() {
        if (selected.getImagem() != null) {
            InputStream is = new ByteArrayInputStream((byte[]) selected.getImagem());
            setHamburguersImagem(DefaultStreamedContent.builder().contentType("image/jpeg").stream(() -> is).build());
        } else {
            setLogoHamburguer(null);
        }
    }

    public void setLogoHamburguer(StreamedContent logoHamburguer) {
        this.logoHamburguer = logoHamburguer;
    }

    public void setHamburguersList(List<Hamburguers> hamburguersList) {
        this.hamburguersList = hamburguersList;
    }

    public Hamburguers getSelected() {
        return selected;
    }

    public void setSelected(Hamburguers selected) {
        this.selected = selected;
    }

    public Hamburguers getHamburguers() {
        return hamburguers;
    }

    public void setHamburguers(Hamburguers hamburguers) {
        this.hamburguers = hamburguers;
    }

    public Hamburguers getHamburguers(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Hamburguers.class)
    public static class HamburguersControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HamburguersController controller
                    = (HamburguersController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "hamburguersController");
            return controller.getHamburguers(getKey(value));
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
            if (object instanceof Hamburguers) {
                Hamburguers o = (Hamburguers) object;
                return getStringKey(o.getIdhamburguer());
            } else {
                return null;
            }
        }
    }

    public Hamburguers prepareCreate() {
        selected = new Hamburguers();
        return selected;
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
                        ejbFacade.createReturn(selected);
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
        hamburguersList = ejbFacade.buscarTodos();
    }

    public void editar() {
        persist(PersistAction.UPDATE,
                "Registro alterado com sucesso!");
        hamburguersList = ejbFacade.buscarTodos();
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Registro excluído com sucesso!");
        // Atualizar a lista de usuários após deletar
        hamburguersList = ejbFacade.buscarTodos();
    }
}
