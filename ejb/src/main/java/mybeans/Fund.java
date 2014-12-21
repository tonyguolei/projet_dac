/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guillaumeperrin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fund.findAll", query = "SELECT f FROM Fund f"),
    @NamedQuery(name = "Fund.findByIdFund", query = "SELECT f FROM Fund f WHERE f.idFund = :idFund"),
    @NamedQuery(name = "Fund.findByValue", query = "SELECT f FROM Fund f WHERE f.value = :value")})
public class Fund implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idFund;
    @Basic(optional = false)
    @NotNull
    private int value;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User idUser;
    @JoinColumn(name = "idProject", referencedColumnName = "idProject")
    @ManyToOne(optional = false)
    private Project idProject;

    public Fund() {
    }

    public Fund(Integer idFund) {
        this.idFund = idFund;
    }

    public Fund(Integer idFund, int value) {
        this.idFund = idFund;
        this.value = value;
    }

    public Integer getIdFund() {
        return idFund;
    }

    public void setIdFund(Integer idFund) {
        this.idFund = idFund;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Project getIdProject() {
        return idProject;
    }

    public void setIdProject(Project idProject) {
        this.idProject = idProject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFund != null ? idFund.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fund)) {
            return false;
        }
        Fund other = (Fund) object;
        if ((this.idFund == null && other.idFund != null) || (this.idFund != null && !this.idFund.equals(other.idFund))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mybeans.Fund[ idFund=" + idFund + " ]";
    }
    
}