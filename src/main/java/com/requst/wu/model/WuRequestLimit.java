package com.requst.wu.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Pattern;

@Entity
@Table(name = "wu_request_limit")
public class WuRequestLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "amount")
    @NotNull(message = " *Необхідно ввести суму")
    @Pattern(regexp = "^[0-9]*\\.[0-9]{2}", message=" *Необхідно ввести суму у форматі число з роздільником крапка (Наприклад - 15.00)")
    private String amount;
    @Column(name = "currency")
    @NotNull
    @Pattern(regexp="^(?=\\s*\\S).*$", message=" *Необхідно вибрати валюту переказу")
    private String currency;
    @Column(name = "amount_send")
    @NotNull(message = " *Необхідно ввести суму")
    @Pattern(regexp = "^[0-9]*\\.[0-9]{2}", message=" *Необхідно ввести суму у форматі число з роздільником крапка (Наприклад - 15.00)")
    private String amountSend;
    @Column(name = "coment")
    private String coment;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = true, insertable = true, updatable = true)
    private  java.util.Date dateRequest;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable=false, updatable=false)
    private User primaryUser;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id",nullable=false, updatable=false)
    private DicState primaryState;


    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "email",nullable=false, updatable=false)
    private AcWuDictUserTermDiv primaryWuDicUser;



    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "wu_request_limit_state",
            joinColumns = { @JoinColumn(name = "id_wu_request") },
            inverseJoinColumns = { @JoinColumn(name = "id_state") })

    private Set<RequestState> requestStates = new HashSet<>();


    public void setRequestStates(Set<RequestState> requestStates) {
        this.requestStates = requestStates;
    }

    public Set<RequestState> getRequestStates() {

        return requestStates;
    }

    public void setPrimaryWuDicUser(AcWuDictUserTermDiv primaryWuDicUser) {
        this.primaryWuDicUser = primaryWuDicUser;
    }

    public AcWuDictUserTermDiv getPrimaryWuDicUser() {
        return primaryWuDicUser;
    }


    public DicState getPrimaryState() {
        return primaryState;
    }

    public void setPrimaryState(DicState primaryState) {
        this.primaryState = primaryState;
    }


    public User getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(User primaryUser) {
        this.primaryUser = primaryUser;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "amount", nullable = true, precision = 0)
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "currency", nullable = true, length = 3)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "amount_send", nullable = false, length = 255)
    public String getAmountSend() {
        return amountSend;
    }

    public void setAmountSend(String amountSend) {
        this.amountSend = amountSend;
    }

    @Basic
    @Column(name = "coment", nullable = true, length = 255)
    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = true, insertable = true, updatable = true)
    public  java.util.Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(java.util.Date dateRequest) {
        this.dateRequest = dateRequest;
    }
}