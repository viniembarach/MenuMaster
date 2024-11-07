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
import br.upf.menumaster.Entity.Bebidas;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.IOUtils;

/**
 *
 * @author oroca
 */
@Named(value = "bebidasController")
@SessionScoped
public class BebidasController implements Serializable {

    @EJB
    private br.upf.menumaster.facade.BebidasFacade ejbFacade;
    private Bebidas bebidas = new Bebidas();
    private List<Bebidas> bebidasList = new ArrayList<>();
    private Bebidas selected;
    private Bebidas current;
    private StreamedContent bebidasImagem;
    private UploadedFile file;

    public List<Bebidas> getBebidasList() {
        if (bebidasList == null || bebidasList.isEmpty()) {
            bebidasList = ejbFacade.buscarTodos();
        }
        return bebidasList;
    }

    public List<Bebidas> bebidasAll() {
        return ejbFacade.buscarTodos();
    }

    //public List<Bebidas> getBebidasList() {;
    //   return bebidasList;
    //}
    public void setBebidasList(List<Bebidas> bebidasList) {
        this.bebidasList = bebidasList;
    }

    public Bebidas getSelected() {
        return selected;
    }

    public void setSelected(Bebidas selected) {
        this.selected = selected;
    }

    public Bebidas getBebidas() {
        return bebidas;
    }

    public void setBebidas(Bebidas bebidas) {
        this.bebidas = bebidas;
    }

    public Bebidas getBebidas(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    private DefaultStreamedContent imagem;

    public void setImagem(DefaultStreamedContent imagem) {
        this.imagem = imagem;
    }

    public DefaultStreamedContent getImagem() {
        return imagem;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void prepararImagem(FileUploadEvent bebida) {
        try {
            // Atualiza o componente com a imagem carregada
            setImagem(DefaultStreamedContent.builder()
                    .stream(() -> {
                        try {
                            return bebida.getFile().getInputStream(); // Recupera o InputStream do arquivo
                        } catch (IOException e) {
                            e.printStackTrace(); // Tratamento do erro
                            return null; // Retorna null em caso de erro
                        }
                    })
                    .build());

            // Converte o InputStream para um array de bytes e seta no campo `imagem`
            current.setImagem(IOUtils.toByteArray(bebida.getFile().getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace(); // Tratamento do erro de IO
        }
    }

    @FacesConverter(forClass = Bebidas.class)
    public static class BebidasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BebidasController controller
                    = (BebidasController) facesContext.getApplication().getELResolver().
                            getValue(facesContext.getELContext(),
                                    null, "bebidasController");
            return controller.getBebidas(getKey(value));
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
            if (object instanceof Bebidas) {
                Bebidas o = (Bebidas) object;
                return getStringKey(o.getIdbebida());
            } else {
                return null;
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            // Recebe o arquivo de imagem e armazena no objeto selecionado
            UploadedFile uploadedFile = event.getFile();
            byte[] imagemBytes = uploadedFile.getContent(); // Pega o conteúdo do arquivo
            selected.setImagem(imagemBytes); // Armazena a imagem no objeto selected

            // Exibir uma mensagem de sucesso, se desejado
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Imagem enviada com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao enviar a imagem."));
            e.printStackTrace();
        }
    }

    public void getBebidasImagemUpload() {
        if (file != null && file.getContent() != null) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new ByteArrayInputStream(file.getContent()));
            } catch (IOException e) {
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "png", out);
            } catch (IOException e) {
            }
            selected.setImagem(out.toByteArray());
        }
    }

    public Bebidas prepareAdicionar() {
        bebidas = new Bebidas();
        return bebidas;
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
                        ejbFacade.createReturn(bebidas);
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
        persist(PersistAction.CREATE, "Registro incluído com sucesso!");
        bebidasList = ejbFacade.buscarTodos(); // Atualiza a lista
    }

    public void editar() {
        persist(PersistAction.UPDATE, "Registro alterado com sucesso!");
        bebidasList = ejbFacade.buscarTodos(); // Atualiza a lista
    }

    public void deletar() {
        persist(PersistAction.DELETE, "Registro excluído com sucesso!");
        bebidasList = ejbFacade.buscarTodos(); // Atualiza a lista
    }

}
