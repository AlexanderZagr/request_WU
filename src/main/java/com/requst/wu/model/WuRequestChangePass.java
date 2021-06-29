package com.requst.wu.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "wu_request_changepass", schema = "accord")
public class WuRequestChangePass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_request")
    private Date dateRequest;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable=false, updatable=false)
    private User primaryUser;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "email",nullable=false, updatable=false)
    private AcWuDictUserTermDiv primaryWuDicUser;



    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id",nullable=false, updatable=false)
    private DicState primaryState;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.util.Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(java.util.Date dateRequest) {
        this.dateRequest = dateRequest;
    }



    public void setPrimaryState(DicState primaryState) {
        this.primaryState = primaryState;
    }

    public DicState getPrimaryState() {
        return primaryState;
    }
    public void setPrimaryUser(User primaryUser) {
        this.primaryUser = primaryUser;
    }

    public void setPrimaryWuDicUser(AcWuDictUserTermDiv primaryWuDicUser) {
        this.primaryWuDicUser = primaryWuDicUser;
    }

    public User getPrimaryUser() {
        return primaryUser;
    }

    public AcWuDictUserTermDiv getPrimaryWuDicUser() {
        return primaryWuDicUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WuRequestChangePass that = (WuRequestChangePass) o;
        return id == that.id &&
                Objects.equals(dateRequest, that.dateRequest);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, dateRequest);
    }
}
