package com.requst.wu.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dic_state", schema = "accord")
public class DicState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "state_id")
    private int stateId;

    @Column(name = "name_state", nullable = false)
    private String nameState;

    @Basic
    @Column(name = "name_state", nullable = false, length = 255)
    public String getNameState() {
        return nameState;
    }


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<WuRequestLimit> allWuRequest;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<WuRequestPayment> allWuRequestPayment;

    public void setAllWuRequest(Set<WuRequestLimit> allWuRequest) {
        this.allWuRequest = allWuRequest;
    }

    public void setAllWuRequestPayment(Set<WuRequestPayment> allWuRequestPayment) {
        this.allWuRequestPayment = allWuRequestPayment;
    }

    public Set<WuRequestLimit> getAllWuRequest() {
        return allWuRequest;
    }

    public Set<WuRequestPayment> getAllWuRequestPayment() {
        return allWuRequestPayment;
    }

    public void setNameState(String nameState) {
        this.nameState = nameState;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
}
