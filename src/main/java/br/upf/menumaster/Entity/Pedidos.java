/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.upf.menumaster.Entity;

import br.upf.menumaster.enumeration.StatusPagamento;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "pedidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedidos.findAll", query = "SELECT p FROM Pedidos p"),
    @NamedQuery(name = "Pedidos.findByIdpedido", query = "SELECT p FROM Pedidos p WHERE p.idpedido = :idpedido"),
    @NamedQuery(name = "Pedidos.findByValorpedido", query = "SELECT p FROM Pedidos p WHERE p.valorpedido = :valorpedido"),
    @NamedQuery(name = "Pedidos.findByStatuspedido", query = "SELECT p FROM Pedidos p WHERE p.statuspedido = :statuspedido"),
    @NamedQuery(name = "Pedidos.findByStatuspagamento", query = "SELECT p FROM Pedidos p WHERE p.statuspagamento = :statuspagamento")})
public class Pedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpedido")
    private Integer idpedido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorpedido")
    private double valorpedido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "statuspedido")
    private String statuspedido;
    @OneToMany(mappedBy = "pedidoconta")
    private Collection<Contas> contasCollection;
    @JoinColumn(name = "bebidapedido", referencedColumnName = "idbebida")
    @ManyToOne
    private Bebidas bebidapedido;
    @JoinColumn(name = "hamburguerpedido", referencedColumnName = "idingrediente")
    @ManyToOne
    private Hamburguers hamburguerpedido;
    @JoinColumn(name = "lanchepedido", referencedColumnName = "idlanche")
    @ManyToOne
    private Lanches lanchepedido;
    @JoinColumn(name = "mesapedido", referencedColumnName = "numeromesa")
    @ManyToOne
    private Mesas mesapedido;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "statuspagamento")
    private String statuspagamento = "NAO_PAGO";

    public Pedidos() {
    }

    public Pedidos(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public Pedidos(Integer idpedido, double valorpedido, String statuspedido) {
        this.idpedido = idpedido;
        this.valorpedido = valorpedido;
        this.statuspedido = statuspedido;
    }

    public Integer getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(Integer idpedido) {
        this.idpedido = idpedido;
    }

    public double getValorpedido() {
        return valorpedido;
    }

    public void setValorpedido(double valorpedido) {
        this.valorpedido = valorpedido;
    }

    public String getStatuspedido() {
        return statuspedido;
    }

    public void setStatuspedido(String statuspedido) {
        this.statuspedido = statuspedido;
    }

    @XmlTransient
    public Collection<Contas> getContasCollection() {
        return contasCollection;
    }

    public void setContasCollection(Collection<Contas> contasCollection) {
        this.contasCollection = contasCollection;
    }

    public Bebidas getBebidapedido() {
        return bebidapedido;
    }

    public void setBebidapedido(Bebidas bebidapedido) {
        this.bebidapedido = bebidapedido;
    }

    public Hamburguers getHamburguerpedido() {
        return hamburguerpedido;
    }

    public void setHamburguerpedido(Hamburguers hamburguerpedido) {
        this.hamburguerpedido = hamburguerpedido;
    }

    public Lanches getLanchepedido() {
        return lanchepedido;
    }

    public void setLanchepedido(Lanches lanchepedido) {
        this.lanchepedido = lanchepedido;
    }

    public Mesas getMesapedido() {
        return mesapedido;
    }

    public void setMesapedido(Mesas mesapedido) {
        this.mesapedido = mesapedido;
    }

    public String getStatuspagamento() {
        return statuspagamento;
    }

    public void setStatuspagamento(String statuspagamento) {
        this.statuspagamento = statuspagamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpedido != null ? idpedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedidos)) {
            return false;
        }
        Pedidos other = (Pedidos) object;
        if ((this.idpedido == null && other.idpedido != null) || (this.idpedido != null && !this.idpedido.equals(other.idpedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.menumaster.Entity.Pedidos[ idpedido=" + idpedido + " ]";
    }

}
