/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author guillaumeperrin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findByIdProject", query = "SELECT p FROM Project p WHERE p.idProject = :idProject"),
    @NamedQuery(name = "Project.findByGoal", query = "SELECT p FROM Project p WHERE p.goal = :goal"),
    @NamedQuery(name = "Project.findByDescription", query = "SELECT p FROM Project p WHERE p.description = :description"),
    @NamedQuery(name = "Project.findByCreationDate", query = "SELECT p FROM Project p WHERE p.creationDate = :creationDate"),
    @NamedQuery(name = "Project.findByEndDate", query = "SELECT p FROM Project p WHERE p.endDate = :endDate"),
    @NamedQuery(name = "Project.findByTags", query = "SELECT p FROM Project p WHERE p.tags = :tags"),
    @NamedQuery(name = "Project.findByFlagged", query = "SELECT p FROM Project p WHERE p.flagged = :flagged")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idProject;
    @Basic(optional = false)
    @NotNull
    private int goal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20000)
    private String description;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    private String tags;
    @Basic(optional = false)
    @NotNull
    private boolean flagged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProject")
    private Collection<MemoriseProject> memoriseProjectCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProject")
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProject")
    private Collection<Fund> fundCollection;
    @JoinColumn(name = "idOwner", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User idOwner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProject")
    private Collection<Bonus> bonusCollection;

    public Project() {
    }

    public Project(Integer idProject) {
        this.idProject = idProject;
    }

    public Project(Integer idProject, int goal, String description, Date creationDate, Date endDate, String tags, boolean flagged) {
        this.idProject = idProject;
        this.goal = goal;
        this.description = description;
        this.creationDate = creationDate;
        this.endDate = endDate;
        this.tags = tags;
        this.flagged = flagged;
    }

    public Integer getIdProject() {
        return idProject;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean getFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    @XmlTransient
    public Collection<MemoriseProject> getMemoriseProjectCollection() {
        return memoriseProjectCollection;
    }

    public void setMemoriseProjectCollection(Collection<MemoriseProject> memoriseProjectCollection) {
        this.memoriseProjectCollection = memoriseProjectCollection;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @XmlTransient
    public Collection<Fund> getFundCollection() {
        return fundCollection;
    }

    public void setFundCollection(Collection<Fund> fundCollection) {
        this.fundCollection = fundCollection;
    }

    public User getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(User idOwner) {
        this.idOwner = idOwner;
    }

    @XmlTransient
    public Collection<Bonus> getBonusCollection() {
        return bonusCollection;
    }

    public void setBonusCollection(Collection<Bonus> bonusCollection) {
        this.bonusCollection = bonusCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProject != null ? idProject.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.idProject == null && other.idProject != null) || (this.idProject != null && !this.idProject.equals(other.idProject))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mybeans.Project[ idProject=" + idProject + " ]";
    }
    
}
