/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tib
 */
@Cacheable(false)
@Entity
@Table(name = "PrivateMessage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrivateMessage.findAll", query = "SELECT p FROM PrivateMessage p"),
    @NamedQuery(name = "PrivateMessage.findByIdPrivateMessage", query = "SELECT p FROM PrivateMessage p WHERE p.idPrivateMessage = :idPrivateMessage"),
    @NamedQuery(name = "PrivateMessage.findByMessage", query = "SELECT p FROM PrivateMessage p WHERE p.message = :message"),
    @NamedQuery(name = "PrivateMessage.findNotReadByDest", query = "SELECT count(pm) FROM PrivateMessage pm WHERE pm.dest = :dest AND pm.isRead = false"),
    @NamedQuery(name = "PrivateMessage.findByIsRead", query = "SELECT p FROM PrivateMessage p WHERE p.isRead = :isRead"),
    @NamedQuery(name = "PrivateMessage.findAllNotReadByUserid", query = "SELECT pm FROM PrivateMessage pm WHERE pm.dest = :dest AND pm.isRead = false"),
    @NamedQuery(name = "PrivateMessage.conversations", query = "SELECT pm FROM PrivateMessage pm WHERE pm.exp = :user OR pm.dest = :user ORDER BY pm.idPrivateMessage DESC"),
    @NamedQuery(name = "PrivateMessage.conversation", query = "SELECT pm FROM PrivateMessage pm WHERE (pm.exp = :exp AND pm.dest = :dest) OR (pm.exp = :dest AND pm.dest = :exp) ORDER BY pm.idPrivateMessage ASC")})
public class PrivateMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    private Integer idPrivateMessage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    private String message;
    @Basic(optional = false)
    @NotNull
    private boolean isRead;
    @JoinColumn(name = "dest", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User dest;
    @JoinColumn(name = "exp", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User exp;

    public PrivateMessage() {
    }
    
    public PrivateMessage(PrivateMessage pm) {
        this.idPrivateMessage = pm.idPrivateMessage;
        this.exp = pm.exp;
        this.dest = pm.dest;
        this.message = pm.message;
        this.isRead = pm.isRead;
    }

    public PrivateMessage(Integer idPrivateMessage) {
        this.idPrivateMessage = idPrivateMessage;
    }
    
    public PrivateMessage(User exp, User dest, String message) {
        this.exp = exp;
        this.dest = dest;
        this.message = message;
        this.isRead = false;
    }

    public PrivateMessage(Integer idPrivateMessage, String message, boolean isRead) {
        this.idPrivateMessage = idPrivateMessage;
        this.message = message;
        this.isRead = isRead;
    }

    public Integer getIdPrivateMessage() {
        return idPrivateMessage;
    }

    public void setIdPrivateMessage(Integer idPrivateMessage) {
        this.idPrivateMessage = idPrivateMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public User getDest() {
        return dest;
    }

    public void setDest(User dest) {
        this.dest = dest;
    }

    public User getExp() {
        return exp;
    }

    public void setExp(User exp) {
        this.exp = exp;
    }
    
    /**
     * Return true if pm and this are in the same conversation
     * @param pm
     * @return 
     */
    public boolean sameConversation(PrivateMessage pm) {
        if ((this.exp.equals(pm.exp) && this.dest.equals(pm.dest)) ||
                (this.exp.equals(pm.dest) && this.dest.equals(pm.exp))) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrivateMessage != null ? idPrivateMessage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrivateMessage)) {
            return false;
        }
        PrivateMessage other = (PrivateMessage) object;
        if ((this.idPrivateMessage == null && other.idPrivateMessage != null) || (this.idPrivateMessage != null && !this.idPrivateMessage.equals(other.idPrivateMessage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mybeans.PrivateMessage[ idPrivateMessage=" + idPrivateMessage + " ]";
    }
    
}
