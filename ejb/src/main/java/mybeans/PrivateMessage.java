/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guillaumeperrin
 */
@Cacheable(false)
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrivateMessage.findAll", query = "SELECT p FROM PrivateMessage p"),
    @NamedQuery(name = "PrivateMessage.findByIdPrivateMessage", query = "SELECT p FROM PrivateMessage p WHERE p.idPrivateMessage = :idPrivateMessage"),
    @NamedQuery(name = "PrivateMessage.findByMessage", query = "SELECT p FROM PrivateMessage p WHERE p.message = :message"),
    @NamedQuery(name = "PrivateMessage.findByIsRead", query = "SELECT p FROM PrivateMessage p WHERE p.isRead = :isRead")})
public class PrivateMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idPrivateMessage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    private String message;
    @Basic(optional = false)
    @NotNull
    private boolean isRead;
    @JoinColumn(name = "to", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User to;
    @JoinColumn(name = "from", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User from1;

    public PrivateMessage() {
    }

    public PrivateMessage(Integer idPrivateMessage) {
        this.idPrivateMessage = idPrivateMessage;
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

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom1() {
        return from1;
    }

    public void setFrom1(User from1) {
        this.from1 = from1;
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
