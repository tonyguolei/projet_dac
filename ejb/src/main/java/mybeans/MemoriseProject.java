/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guillaumeperrin
 */
@Cacheable(false)
@Entity
@Table(name="MemoriseProject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemoriseProject.findAll", query = "SELECT m FROM MemoriseProject m"),
    @NamedQuery(name = "MemoriseProject.deleteByProjectUser", query = "DELETE FROM MemoriseProject WHERE idUser = :idUser AND idProject = :idProject"),
    @NamedQuery(name = "MemoriseProject.findProjectByUser", query = "SELECT p FROM MemoriseProject m left join m.idProject p WHERE m.idUser = :idUser"),
    @NamedQuery(name = "MemoriseProject.findByIdMemoriseProject", query = "SELECT m FROM MemoriseProject m WHERE m.idMemoriseProject = :idMemoriseProject")})
public class MemoriseProject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    private Integer idMemoriseProject;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User idUser;
    @JoinColumn(name = "idProject", referencedColumnName = "idProject")
    @ManyToOne(optional = false)
    private Project idProject;

    public MemoriseProject() {
    }

    public MemoriseProject(User user, Project project) {
        this.idUser = user;
        this.idProject = project;
    }

    public Integer getIdMemoriseProject() {
        return idMemoriseProject;
    }

    public User getIdUser() {
        return idUser;
    }

    public Project getIdProject() {
        return idProject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMemoriseProject != null ? idMemoriseProject.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemoriseProject)) {
            return false;
        }
        MemoriseProject other = (MemoriseProject) object;
        if ((this.idMemoriseProject == null && other.idMemoriseProject != null) || (this.idMemoriseProject != null && !this.idMemoriseProject.equals(other.idMemoriseProject))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mybeans.MemoriseProject[ idMemoriseProject=" + idMemoriseProject + " ]";
    }
    
}

