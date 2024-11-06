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
import java.util.List;

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
    @NamedQuery(name = "Bebidas.findByValorbebida", query = "SELECT b FROM Bebidas b WHERE b.valorbebida = :valorbebida"),
    @NamedQuery(name = "Bebidas.findByImagem", query = "SELECT b FROM Bebidas b WHERE b.imagem = :imagem"),
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
    @Column(name = "valorbebida")
    private double valorbebida;

    @Basic(optional = false)
    @NotNull
    @Column(name = "disponivelbebida")
    private boolean disponivelbebida = false; // o false é pra inicializar

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @OneToMany(mappedBy = "bebidapedido")
    private Collection<Pedidos> pedidosCollection;

    public Bebidas() {
    }

    public Bebidas(Integer idbebida) {
        this.idbebida = idbebida;
    }

    public Bebidas(Integer idbebida, String nomebebida, double valorbebida, boolean disponivelbebida, byte[] imagem) {
        this.idbebida = idbebida;
        this.nomebebida = nomebebida;
        this.valorbebida = valorbebida;
        this.disponivelbebida = disponivelbebida;
        this.imagem = imagem;
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

    public List<Bebidas> getBebidas(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
