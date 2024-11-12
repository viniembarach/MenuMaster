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
@Table(name = "mesas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesas.findAll", query = "SELECT m FROM Mesas m"),
    @NamedQuery(name = "Mesas.findByNumeromesa", query = "SELECT m FROM Mesas m WHERE m.numeromesa = :numeromesa")})
public class Mesas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numeromesa")
    private Integer numeromesa;

    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "nomemesa")
    private String nomemesa;

    public String getNomemesa() {
        return nomemesa;
    }

    public void setNomemesa(String nomemesa) {
        this.nomemesa = nomemesa;
    }

    public Mesas() {
        // Construtor padrão necessário para JPA
    }

    public Mesas(Integer numeromesa) {
        this.numeromesa = numeromesa;
    }

    public Integer getNumeromesa() {
        return numeromesa;
    }

    public void setNumeromesa(Integer numeromesa) {
        this.numeromesa = numeromesa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeromesa != null ? numeromesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesas)) {
            return false;
        }
        Mesas other = (Mesas) object;
        if ((this.numeromesa == null && other.numeromesa != null) || (this.numeromesa != null && !this.numeromesa.equals(other.numeromesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.menumaster.Entity.Mesas[ numeromesa=" + numeromesa + " ]";
    }

}
