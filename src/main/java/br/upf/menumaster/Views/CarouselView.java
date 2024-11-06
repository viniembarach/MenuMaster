package br.upf.menumaster.Views;

import br.upf.menumaster.Entity.Bebidas;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.ResponsiveOption;

@Named
@ViewScoped
public class CarouselView implements Serializable {

    private List<Bebidas> bebidas;

    private List<ResponsiveOption> responsiveOptions;

    @Inject
    private Bebidas bebidasEntity;

    @PostConstruct
    public void init() {
        bebidas = bebidasEntity.getBebidas(1);
        responsiveOptions = new ArrayList<>();
        responsiveOptions.add(new ResponsiveOption("1024px", 3, 3));
        responsiveOptions.add(new ResponsiveOption("768px", 2, 2));
        responsiveOptions.add(new ResponsiveOption("560px", 1, 1));
    }

    public List<Bebidas> getBebidas() {
        return bebidas;
    }

    public void setService(Bebidas bebidasEntity) {
        this.bebidasEntity = bebidasEntity;
    }

    public List<ResponsiveOption> getResponsiveOptions() {
        return responsiveOptions;
    }

    public void setResponsiveOptions(List<ResponsiveOption> responsiveOptions) {
        this.responsiveOptions = responsiveOptions;
    }
}