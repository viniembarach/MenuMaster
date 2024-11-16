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
import jakarta.persistence.Lob;
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
    @NamedQuery(name = "Hamburguers.findByIdhamburguer", query = "SELECT h FROM Hamburguers h WHERE h.idhamburguer = :idhamburguer"),
    @NamedQuery(name = "Hamburguers.findByNomehamburguer", query = "SELECT h FROM Hamburguers h WHERE h.nomehamburguer = :nomehamburguer"),
    @NamedQuery(name = "Hamburguers.findByValorhamburguer", query = "SELECT h FROM Hamburguers h WHERE h.valorhamburguer = :valorhamburguer"),
    @NamedQuery(name = "Hamburguers.findByDisponivelhamburguer", query = "SELECT h FROM Hamburguers h WHERE h.disponivelhamburguer = :disponivelhamburguer")})
public class Hamburguers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhamburguer")
    private Integer idhamburguer;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nomehamburguer")
    private String nomehamburguer;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorhamburguer")
    private double valorhamburguer;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "disponivelhamburguer")
    private boolean disponivelhamburguer;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @OneToMany(mappedBy = "hamburguerpedido")
    private Collection<Pedidos> pedidosCollection;

    public Hamburguers() {
    }

    public Hamburguers(Integer idhamburguer) {
        this.idhamburguer = idhamburguer;
    }

    public Hamburguers(Integer idhamburguer, String nomehamburguer, double valorhamburguer, boolean disponivelhamburguer, byte[] imagem) {
        this.idhamburguer = idhamburguer;
        this.nomehamburguer = nomehamburguer;
        this.valorhamburguer = valorhamburguer;
        this.disponivelhamburguer = disponivelhamburguer;
        this.imagem = imagem;
    }

    public Integer getIdhamburguer() {
        return idhamburguer;
    }

    public void setIdhamburguer(Integer idhamburguer) {
        this.idhamburguer = idhamburguer;
    }

    public String getNomehamburguer() {
        return nomehamburguer;
    }

    public void setNomehamburguer(String nomehamburguer) {
        this.nomehamburguer = nomehamburguer;
    }

    public double getValorhamburguer() {
        return valorhamburguer;
    }

    public void setValorhamburguer(double valorhamburguer) {
        this.valorhamburguer = valorhamburguer;
    }

    public boolean isDisponivelhamburguer() {
        return disponivelhamburguer;
    }

    public void setDisponivelhamburguer(boolean disponivelhamburguer) {
        this.disponivelhamburguer = disponivelhamburguer;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
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
        hash += (idhamburguer != null ? idhamburguer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hamburguers)) {
            return false;
        }
        Hamburguers other = (Hamburguers) object;
        if ((this.idhamburguer == null && other.idhamburguer != null) || (this.idhamburguer != null && !this.idhamburguer.equals(other.idhamburguer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.menumaster.Entity.Hamburguers[ idhamburguer=" + idhamburguer + " ]";
    }

}
