/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Controller;

import br.upf.menumaster.Entity.Lanches;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
@Named(value = "lanchesController")
@SessionScoped
public class LanchesController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.LanchesFacade ejbFacade;

    private Lanches lanches = new Lanches();
    private List<Lanches> lanchesList = new ArrayList<>();
    private Lanches selected;
    private StreamedContent lanchesImagem;
    private StreamedContent logoLanches;
    private UploadedFile file;

    public List<Lanches> LanchesAll() {
        return ejbFacade.buscarTodos();
    }

    public List<Lanches> getLanchesList() {
        if (lanchesList == null || lanchesList.isEmpty()) {
            lanchesList = ejbFacade.buscarTodos();
        }
        return lanchesList;
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
                    getLogoLanchesUpload();
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

    public void getLogoLanchesUpload() {
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
        if (lanchesImagem == null || lanchesImagem.getStream() == null || lanchesImagem.getStream().toString().isEmpty() || !lanchesImagem.getStream().equals(selected.getImagem())) {
            getSelectedImagem();
        }
        return lanchesImagem;
    }

    public void setLanchesImagem(StreamedContent lanchesImagem) {
        this.lanchesImagem = lanchesImagem;
    }

    public void getSelectedImagem() {
        if (selected.getImagem() != null) {
            InputStream is = new ByteArrayInputStream((byte[]) selected.getImagem());
            setLanchesImagem(DefaultStreamedContent.builder().contentType("image/png").stream(() -> is).build());
        }
    }

    public StreamedContent getLanchesImagem() {
        if (logoLanches == null || logoLanches.getStream() == null || logoLanches.getStream().toString().isEmpty() || !logoLanches.getStream().equals(selected.getImagem())) {
            getLogoLanches();
        }
        return logoLanches;
    }

    public void getLogoLanches() {
        if (selected.getImagem() != null) {
            InputStream is = new ByteArrayInputStream((byte[]) selected.getImagem());
            setLanchesImagem(DefaultStreamedContent.builder().contentType("image/jpeg").stream(() -> is).build());
        } else {
            setLogoLanches(null);
        }
    }

    public void setLogoLanches(StreamedContent logoLanches) {
        this.logoLanches = logoLanches;
    }
    
    public void setLanchesList(List<Lanches> lanchesList) {
        this.lanchesList = lanchesList;
    }

    public Lanches getSelected() {
        return selected;
    }

    public void setSelected(Lanches selected) {
        this.selected = selected;
    }

    public Lanches getLanches() {
        return lanches;
    }

    public void setLanches(Lanches lanches) {
        this.lanches = lanches;
    }

    public Lanches getLanches(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Lanches.class)
    public static class LanchesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LanchesController controller
                    = (LanchesController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "lanchesController");
            return controller.getLanches(getKey(value));
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
            if (object instanceof Lanches) {
                Lanches o = (Lanches) object;
                return getStringKey(o.getIdlanche());
            } else {
                return null;
            }
        }
    }

    public Lanches prepareCreate() {
        selected = new Lanches();
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
        lanchesList = ejbFacade.buscarTodos();
    }

    public void editar() {
        persist(PersistAction.UPDATE,
                "Registro alterado com sucesso!");
        lanchesList = ejbFacade.buscarTodos();
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Registro excluído com sucesso!");
        // Atualizar a lista de usuários após deletar
        lanchesList = ejbFacade.buscarTodos();
}

}
