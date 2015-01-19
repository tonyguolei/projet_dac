/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybeans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.getProjects", query = "SELECT p FROM Project p WHERE p.idOwner = :idOwner"),
    @NamedQuery(name = "User.findByIdUser", query = "SELECT u FROM User u WHERE u.idUser = :idUser"),
    @NamedQuery(name = "User.findByMail", query = "SELECT u FROM User u WHERE u.mail = :mail"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByBalance", query = "SELECT u FROM User u WHERE u.balance = :balance"),
    @NamedQuery(name = "User.findByDeleted", query = "SELECT u FROM User u WHERE u.deleted = :deleted"),
    @NamedQuery(name = "User.findByBanned", query = "SELECT u FROM User u WHERE u.banned = :banned"),
    @NamedQuery(name = "User.findByIsAdmin", query = "SELECT u FROM User u WHERE u.isAdmin = :isAdmin")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    private Integer idUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String mail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String password;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    private BigDecimal balance;
    @Basic(optional = false)
    @NotNull
    private boolean deleted;
    @Basic(optional = false)
    @NotNull
    private boolean banned;
    @Basic(optional = false)
    @NotNull
    private boolean isAdmin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dest")
    private Collection<PrivateMessage> privateMessageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exp")
    private Collection<PrivateMessage> privateMessageCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<MemoriseProject> memoriseProjectCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<Fund> fundCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOwner")
    private Collection<Project> projectCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<Notification> notificationCollection;

    public User() {
    }
    
    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
        this.balance = BigDecimal.ZERO;
        this.deleted = false;
        this.banned = false;
        this.isAdmin = false;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    
    public void addBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mybeans.User[ idUser=" + idUser + " ]";
    }
    
}
