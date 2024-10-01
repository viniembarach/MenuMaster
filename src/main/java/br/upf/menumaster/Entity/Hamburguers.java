/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author vinie
 */
@Entity
@Table(name = "hamburguers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hamburguers.findAll", query = "SELECT h FROM Hamburguers h"),
    @NamedQuery(name = "Hamburguers.findByIdingrediente", query = "SELECT h FROM Hamburguers h WHERE h.idingrediente = :idingrediente"),
    @NamedQuery(name = "Hamburguers.findByTipoingrediente", query = "SELECT h FROM Hamburguers h WHERE h.tipoingrediente = :tipoingrediente"),
    @NamedQuery(name = "Hamburguers.findByNomeingrediente", query = "SELECT h FROM Hamburguers h WHERE h.nomeingrediente = :nomeingrediente"),
    @NamedQuery(name = "Hamburguers.findByValoringrediente", query = "SELECT h FROM Hamburguers h WHERE h.valoringrediente = :valoringrediente"),
    @NamedQuery(name = "Hamburguers.findByDisponivelingrediente", query = "SELECT h FROM Hamburguers h WHERE h.disponivelingrediente = :disponivelingrediente")})
public class Hamburguers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idingrediente")
    private Integer idingrediente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "tipoingrediente")
    private String tipoingrediente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nomeingrediente")
    private String nomeingrediente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valoringrediente")
    private double valoringrediente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disponivelingrediente")
    private boolean disponivelingrediente;
    @OneToMany(mappedBy = "hamburguerpedido")
    private Collection<Pedidos> pedidosCollection;

    public Hamburguers() {
    }

    public Hamburguers(Integer idingrediente) {
        this.idingrediente = idingrediente;
    }

    public Hamburguers(Integer idingrediente, String tipoingrediente, String nomeingrediente, double valoringrediente, boolean disponivelingrediente) {
        this.idingrediente = idingrediente;
        this.tipoingrediente = tipoingrediente;
        this.nomeingrediente = nomeingrediente;
        this.valoringrediente = valoringrediente;
        this.disponivelingrediente = disponivelingrediente;
    }

    public Integer getIdingrediente() {
        return idingrediente;
    }

    public void setIdingrediente(Integer idingrediente) {
        this.idingrediente = idingrediente;
    }

    public String getTipoingrediente() {
        return tipoingrediente;
    }

    public void setTipoingrediente(String tipoingrediente) {
        this.tipoingrediente = tipoingrediente;
    }

    public String getNomeingrediente() {
        return nomeingrediente;
    }

    public void setNomeingrediente(String nomeingrediente) {
        this.nomeingrediente = nomeingrediente;
    }

    public double getValoringrediente() {
        return valoringrediente;
    }

    public void setValoringrediente(double valoringrediente) {
        this.valoringrediente = valoringrediente;
    }

    public boolean getDisponivelingrediente() {
        return disponivelingrediente;
    }

    public void setDisponivelingrediente(boolean disponivelingrediente) {
        this.disponivelingrediente = disponivelingrediente;
    }

    @XmlTransient
    public Collection<Pedidos> getPedidosCollection() {
        return pedidosCollection;
    }

    public void setPedidosCollection(Collection<Pedidos> pedidosCollection) {
        this.pedidosCollection = pedidosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idingrediente != null ? idingrediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hamburguers)) {
            return false;
        }
        Hamburguers other = (Hamburguers) object;
        if ((this.idingrediente == null && other.idingrediente != null) || (this.idingrediente != null && !this.idingrediente.equals(other.idingrediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.menumaster.Entity.Hamburguers[ idingrediente=" + idingrediente + " ]";
    }
    
}
