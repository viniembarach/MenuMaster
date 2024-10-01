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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author vinie
 */
@Entity
@Table(name = "contas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contas.findAll", query = "SELECT c FROM Contas c"),
    @NamedQuery(name = "Contas.findByIdconta", query = "SELECT c FROM Contas c WHERE c.idconta = :idconta"),
    @NamedQuery(name = "Contas.findByValorconta", query = "SELECT c FROM Contas c WHERE c.valorconta = :valorconta")})
public class Contas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconta")
    private Integer idconta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorconta")
    private double valorconta;
    @JoinColumn(name = "mesaconta", referencedColumnName = "numeromesa")
    @ManyToOne
    private Mesas mesaconta;
    @JoinColumn(name = "pedidoconta", referencedColumnName = "idpedido")
    @ManyToOne
    private Pedidos pedidoconta;

    public Contas() {
    }

    public Contas(Integer idconta) {
        this.idconta = idconta;
    }

    public Contas(Integer idconta, double valorconta) {
        this.idconta = idconta;
        this.valorconta = valorconta;
    }

    public Integer getIdconta() {
        return idconta;
    }

    public void setIdconta(Integer idconta) {
        this.idconta = idconta;
    }

    public double getValorconta() {
        return valorconta;
    }

    public void setValorconta(double valorconta) {
        this.valorconta = valorconta;
    }

    public Mesas getMesaconta() {
        return mesaconta;
    }

    public void setMesaconta(Mesas mesaconta) {
        this.mesaconta = mesaconta;
    }

    public Pedidos getPedidoconta() {
        return pedidoconta;
    }

    public void setPedidoconta(Pedidos pedidoconta) {
        this.pedidoconta = pedidoconta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconta != null ? idconta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contas)) {
            return false;
        }
        Contas other = (Contas) object;
        if ((this.idconta == null && other.idconta != null) || (this.idconta != null && !this.idconta.equals(other.idconta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.menumaster.Entity.Contas[ idconta=" + idconta + " ]";
    }
    
}
