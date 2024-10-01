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
@Table(name = "lanches")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lanches.findAll", query = "SELECT l FROM Lanches l"),
    @NamedQuery(name = "Lanches.findByIdlanche", query = "SELECT l FROM Lanches l WHERE l.idlanche = :idlanche"),
    @NamedQuery(name = "Lanches.findByNomelanche", query = "SELECT l FROM Lanches l WHERE l.nomelanche = :nomelanche"),
    @NamedQuery(name = "Lanches.findByValorlanche", query = "SELECT l FROM Lanches l WHERE l.valorlanche = :valorlanche"),
    @NamedQuery(name = "Lanches.findByDisponivellanche", query = "SELECT l FROM Lanches l WHERE l.disponivellanche = :disponivellanche")})
public class Lanches implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlanche")
    private Integer idlanche;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nomelanche")
    private String nomelanche;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorlanche")
    private double valorlanche;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disponivellanche")
    private boolean disponivellanche;
    @OneToMany(mappedBy = "lanchepedido")
    private Collection<Pedidos> pedidosCollection;

    public Lanches() {
    }

    public Lanches(Integer idlanche) {
        this.idlanche = idlanche;
    }

    public Lanches(Integer idlanche, String nomelanche, double valorlanche, boolean disponivellanche) {
        this.idlanche = idlanche;
        this.nomelanche = nomelanche;
        this.valorlanche = valorlanche;
        this.disponivellanche = disponivellanche;
    }

    public Integer getIdlanche() {
        return idlanche;
    }

    public void setIdlanche(Integer idlanche) {
        this.idlanche = idlanche;
    }

    public String getNomelanche() {
        return nomelanche;
    }

    public void setNomelanche(String nomelanche) {
        this.nomelanche = nomelanche;
    }

    public double getValorlanche() {
        return valorlanche;
    }

    public void setValorlanche(double valorlanche) {
        this.valorlanche = valorlanche;
    }

    public boolean getDisponivellanche() {
        return disponivellanche;
    }

    public void setDisponivellanche(boolean disponivellanche) {
        this.disponivellanche = disponivellanche;
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
        hash += (idlanche != null ? idlanche.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lanches)) {
            return false;
        }
        Lanches other = (Lanches) object;
        if ((this.idlanche == null && other.idlanche != null) || (this.idlanche != null && !this.idlanche.equals(other.idlanche))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.menumaster.Entity.Lanches[ idlanche=" + idlanche + " ]";
    }
    
}
