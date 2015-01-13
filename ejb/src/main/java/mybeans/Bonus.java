/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author guillaumeperrin
 */
@Cacheable(false)
@Entity
@Table(name="Bonus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bonus.findAll", query = "SELECT b FROM Bonus b"),
    @NamedQuery(name = "Bonus.findByIdBonus", query = "SELECT b FROM Bonus b WHERE b.idBonus = :idBonus"),
    @NamedQuery(name = "Bonus.findByValue", query = "SELECT b FROM Bonus b WHERE b.value = :value"),
    @NamedQuery(name = "Bonus.findByTitle", query = "SELECT b FROM Bonus b WHERE b.title = :title"),
    @NamedQuery(name = "Bonus.findByDescription", query = "SELECT b FROM Bonus b WHERE b.description = :description"),
    @NamedQuery(name = "Bonus.findByIdProject", query = "SELECT b FROM Bonus b WHERE b.idProject = :idProject")})
public class Bonus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    private Integer idBonus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    private BigDecimal value;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    private String description;
    @JoinColumn(name = "idProject", referencedColumnName = "idProject")
    @ManyToOne(optional = false)
    private Project idProject;

    public Bonus() {
    }

    public Bonus(Integer idBonus) {
        this.idBonus = idBonus;
    }

    public Bonus(Integer idBonus, BigDecimal value, String title, String description) {
        this.idBonus = idBonus;
        this.value = value;
        this.title = title;
        this.description = description;
    }

    public Bonus(BigDecimal value, String title, String description, Project idProject) {
        this.value = value;
        this.title = title;
        this.description = description;
        this.idProject = idProject;
    }

    public Integer getIdBonus() {
        return idBonus;
    }

    public void setIdBonus(Integer idBonus) {
        this.idBonus = idBonus;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (idBonus != null ? idBonus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bonus)) {
            return false;
        }
        Bonus other = (Bonus) object;
        if ((this.idBonus == null && other.idBonus != null) || (this.idBonus != null && !this.idBonus.equals(other.idBonus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mybeans.Bonus[ idBonus=" + idBonus + " ]";
    }
    
}
