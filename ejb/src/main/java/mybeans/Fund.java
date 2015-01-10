/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guillaumeperrin
 */
@Cacheable(false)
@Entity
@XmlRootElement
@Table(name = "Fund")
@NamedQueries({
    @NamedQuery(name = "Fund.findAll", query = "SELECT f FROM Fund f"),
    @NamedQuery(name = "Fund.findByIdFund", query = "SELECT f FROM Fund f WHERE f.idFund = :idFund"),
    @NamedQuery(name = "Fund.getFundLevel", query = "SELECT coalesce(sum(f.value),0) FROM Fund f WHERE f.idProject = :idProject"),
    @NamedQuery(name = "Fund.findByValue", query = "SELECT f FROM Fund f WHERE f.value = :value"),
    @NamedQuery(name = "Fund.findByUser", query = "SELECT f FROM Fund f WHERE f.idUser = :idUser"),
    @NamedQuery(name = "Fund.findAFund", query = "SELECT f FROM Fund f WHERE f.idUser = :idUser AND f.idProject = :idProject")})
public class Fund implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    private Integer idFund;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Min(value=0)
    @Basic(optional = false)
    @NotNull
    private BigDecimal value;
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

    public Fund(Integer idFund, BigDecimal value) {
        this.idFund = idFund;
        this.value = value;
    }

    public Fund(User user, Project project, BigDecimal value) {
        this.idProject = project;
        this.idUser = user;
        this.value = value;
    }

    public Integer getIdFund() {
        return idFund;
    }

    public void setIdFund(Integer idFund) {
        this.idFund = idFund;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void addValue(BigDecimal value) {
        this.value = this.value.add(value);
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
