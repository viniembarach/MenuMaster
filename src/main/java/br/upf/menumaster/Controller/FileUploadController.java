import br.upf.menumaster.Entity.Bebidas;
import br.upf.menumaster.facade.BebidasFacade;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.primefaces.model.file.UploadedFile; // Para receber o arquivo via PrimeFaces
import org.primefaces.event.FileUploadEvent;
import java.io.*;

@Named
@RequestScoped
public class FileUploadController {

    @Inject
    private BebidasFacade bebidasFacade;  // Injeção do EJB para persistir a entidade

    private UploadedFile file;  // A variável para armazenar o arquivo enviado

    public void upload(FileUploadEvent event) {
        file = event.getFile();  // Captura o arquivo do evento

        FacesMessage msg = new FacesMessage("Success!", file.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        try {
            // Converte o arquivo em byte[]
            byte[] imageData = file.getContent();  // Se a versão do PrimeFaces suportar, use isso

            // Cria a nova entidade Bebidas e define a imagem
            Bebidas bebida = new Bebidas();
            bebida.setImagem(imageData);  // Armazena os dados da imagem na entidade

            // Persiste a entidade no banco de dados
            bebidasFacade.create(bebida);  // Supondo que 'create' seja o método para persistir

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

