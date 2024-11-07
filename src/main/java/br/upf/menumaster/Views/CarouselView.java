
import br.upf.menumaster.Entity.Bebidas;
import jakarta.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static org.primefaces.component.carousel.CarouselBase.PropertyKeys.responsiveOptions;
import org.primefaces.model.ResponsiveOption;

public class CarouselView implements Serializable {

    private List<Bebidas> bebidasList;

    private Bebidas bebidas = new Bebidas();

    @PostConstruct
    public void init() {
        bebidas = bebidasList.getProducts(9);
        responsiveOptions = new ArrayList<>();
        responsiveOptions.add(new ResponsiveOption("1024px", 3, 3));
        responsiveOptions.add(new ResponsiveOption("768px", 2, 2));
        responsiveOptions.add(new ResponsiveOption("560px", 1, 1));
    }

    public List<Bebidas> getBebidasList() {
        return bebidasList;
    }

    public void setBebidasList(List<Bebidas> bebidasList) {
        this.bebidasList = bebidasList;
    }

    public Bebidas getBebidas() {
        return bebidas;
    }

    public void setBebidas(Bebidas bebidas) {
        this.bebidas = bebidas;
    }

}
