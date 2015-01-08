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
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author guillaumeperrin
 */
@Cacheable(false)
@Entity
@XmlRootElement
@Table(name = "Project")
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findByIdProject", query = "SELECT p FROM Project p WHERE p.idProject = :idProject"),
    @NamedQuery(name = "Project.findByGoal", query = "SELECT p FROM Project p WHERE p.goal = :goal"),
    @NamedQuery(name = "Project.findByTitle", query = "SELECT p FROM Project p WHERE p.title = :title"),
    @NamedQuery(name = "Project.findByDescription", query = "SELECT p FROM Project p WHERE p.description = :description"),
    @NamedQuery(name = "Project.findByCreationDate", query = "SELECT p FROM Project p WHERE p.creationDate = :creationDate"),
    @NamedQuery(name = "Project.findByEndDate", query = "SELECT p FROM Project p WHERE p.endDate = :endDate"),
    @NamedQuery(name = "Project.findByTags", query = "SELECT p FROM Project p WHERE p.tags = :tags"),
    @NamedQuery(name = "Project.findByTagsMatching", query = "SELECT p FROM Project p WHERE p.tags like :tag1 OR p.tags like :tag2 OR p.tags like :tag3 OR p.tags = :tag4"),
    @NamedQuery(name = "Project.findByFlagged", query = "SELECT p FROM Project p WHERE p.flagged = :flagged")})
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    private Integer idProject;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    private BigDecimal goal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20000)
    private String description;
    @Basic(optional = false)
    @Column(name = "creationDate", insertable = false, updatable = false)
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
    @Basic(optional = false)
    @NotNull
    private boolean transferDone;
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
    private Collection<Notification> notificationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProject")
    private Collection<Bonus> bonusCollection;

    public Project() {
    }

    public Project(Integer idProject) {
        this.idProject = idProject;
    }
    
    public Project(User Owner, BigDecimal goal, String title, String description, Date endDate, String tags) {
        this.idOwner = Owner;
        this.goal = goal;
        this.title = title;
        this.description = description;
        this.creationDate = null;
        this.endDate = endDate;
        this.tags = tags;
        this.flagged = false;
    }

    public Integer getIdProject() {
        return idProject;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
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
    
    public void transferDone() {
        this.transferDone = true;
    }
    
    public boolean alreadyTransferred() {
        return this.transferDone;
    }

    @XmlTransient
    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
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
