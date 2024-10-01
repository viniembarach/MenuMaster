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
@Table(name = "bebidas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bebidas.findAll", query = "SELECT b FROM Bebidas b"),
    @NamedQuery(name = "Bebidas.findByIdbebida", query = "SELECT b FROM Bebidas b WHERE b.idbebida = :idbebida"),
    @NamedQuery(name = "Bebidas.findByNomebebida", query = "SELECT b FROM Bebidas b WHERE b.nomebebida = :nomebebida"),
    @NamedQuery(name = "Bebidas.findByQntbebida", query = "SELECT b FROM Bebidas b WHERE b.qntbebida = :qntbebida"),
    @NamedQuery(name = "Bebidas.findByValorbebida", query = "SELECT b FROM Bebidas b WHERE b.valorbebida = :valorbebida"),
    @NamedQuery(name = "Bebidas.findByDisponivelbebida", query = "SELECT b FROM Bebidas b WHERE b.disponivelbebida = :disponivelbebida")})
public class Bebidas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbebida")
    private Integer idbebida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nomebebida")
    private String nomebebida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qntbebida")
    private int qntbebida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorbebida")
    private double valorbebida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disponivelbebida")
    private boolean disponivelbebida;
    @OneToMany(mappedBy = "bebidapedido")
    private Collection<Pedidos> pedidosCollection;

    public Bebidas() {
    }

    public Bebidas(Integer idbebida) {
        this.idbebida = idbebida;
    }

    public Bebidas(Integer idbebida, String nomebebida, int qntbebida, double valorbebida, boolean disponivelbebida) {
        this.idbebida = idbebida;
        this.nomebebida = nomebebida;
        this.qntbebida = qntbebida;
        this.valorbebida = valorbebida;
        this.disponivelbebida = disponivelbebida;
    }

    public Integer getIdbebida() {
        return idbebida;
    }

    public void setIdbebida(Integer idbebida) {
        this.idbebida = idbebida;
    }

    public String getNomebebida() {
        return nomebebida;
    }

    public void setNomebebida(String nomebebida) {
        this.nomebebida = nomebebida;
    }

    public int getQntbebida() {
        return qntbebida;
    }

    public void setQntbebida(int qntbebida) {
        this.qntbebida = qntbebida;
    }

    public double getValorbebida() {
        return valorbebida;
    }

    public void setValorbebida(double valorbebida) {
        this.valorbebida = valorbebida;
    }

    public boolean getDisponivelbebida() {
        return disponivelbebida;
    }

    public void setDisponivelbebida(boolean disponivelbebida) {
        this.disponivelbebida = disponivelbebida;
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
        hash += (idbebida != null ? idbebida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bebidas)) {
            return false;
        }
        Bebidas other = (Bebidas) object;
        if ((this.idbebida == null && other.idbebida != null) || (this.idbebida != null && !this.idbebida.equals(other.idbebida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.menumaster.Entity.Bebidas[ idbebida=" + idbebida + " ]";
    }
    
}
