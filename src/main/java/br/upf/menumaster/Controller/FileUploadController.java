import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.primefaces.model.file.UploadedFile; // Para receber o arquivo via PrimeFaces
import org.primefaces.event.FileUploadEvent;
import java.io.*;

@Named
@RequestScoped
public class FileUploadController {

    private String destination = "C:\\Program Files\\NetBeans-21";  // Defina o destino correto onde salvar os arquivos

    public void upload(FileUploadEvent event) {  // O evento esperado pelo PrimeFaces é FileUploadEvent
        UploadedFile file = event.getFile();  // Captura o arquivo do evento
        FacesMessage msg = new FacesMessage("Success!", file.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        try {
            copyFile(file.getFileName(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("Novo arquivo criado: " + fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
