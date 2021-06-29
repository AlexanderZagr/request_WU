package com.requst.wu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "wu_request_payment", schema = "accord")
public class WuRequestPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "coment", nullable = true, length = 255)
    private String coment;



    @Column(name = "image_str", nullable = true, length = 255)
    private String singleFileUploadInput;

    @Lob
    @Column(name="image_data")
    private byte[] imageData;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_request", nullable = true)
    private java.util.Date dateRequest;


    @ManyToOne(optional = false, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable=false, updatable=false)
    private User primaryUserPayment;


    @ManyToOne(optional = false, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id",nullable=false, updatable=true)
    @JsonIgnoreProperties(value = "allWuRequestPayment", allowSetters = true)
    private DicState primaryStatePayment;


    @ManyToOne(optional = false, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "email",nullable=false, updatable=false)
    private AcWuDictUserTermDiv primaryWuDicUserPayment;

    public String getSingleFileUploadInput() {
        return singleFileUploadInput;
    }

    public void setSingleFileUploadInput(String singleFileUploadInput) {
        this.singleFileUploadInput = singleFileUploadInput;
    }

    public void setPrimaryUserPayment(User primaryUserPayment) {
        this.primaryUserPayment = primaryUserPayment;
    }

    public void setPrimaryStatePayment(DicState primaryStatePayment) {
        this.primaryStatePayment = primaryStatePayment;
    }

    public void setPrimaryWuDicUserPayment(AcWuDictUserTermDiv primaryWuDicUserPayment) {
        this.primaryWuDicUserPayment = primaryWuDicUserPayment;
    }

    public User getPrimaryUserPayment() {
        return primaryUserPayment;
    }

    public DicState getPrimaryStatePayment() {
        return primaryStatePayment;
    }

    public AcWuDictUserTermDiv getPrimaryWuDicUserPayment() {
        return primaryWuDicUserPayment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }




    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public java.util.Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(java.util.Date dateRequest) {
        this.dateRequest = dateRequest;
    }

}
