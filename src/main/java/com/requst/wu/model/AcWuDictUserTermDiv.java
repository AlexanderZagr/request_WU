package com.requst.wu.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ac_wu_dict_user_term_div", schema = "accord")
public class AcWuDictUserTermDiv {


    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "wrk_id", nullable = true)
    private Integer wrkId;
    @Column(name = "div_id", nullable = true)
    private Integer divId;
    @Column(name = "tt", nullable = true)
    private Integer tt;
    @Column(name = "city", nullable = true, length = 100)
    private String city;
    @Column(name = "address", nullable = true, length = 255)
    private String address;
    @Column(name = "code_usd", nullable = true, length = 9)
    private String codeUsd;
    @Column(name = "code_uah", nullable = true, length = 9)
    private String codeUah;
    @Column(name = "code_eur", nullable = true, length = 9)
    private String codeEur;
    @Column(name = "code_rub", nullable = true, length = 9)
    private String codeRub;
    @Column(name = "operator_no", nullable = true)
    private Integer operatorNo;
    @Column(name = "operator_fio", nullable = true, length = 255)
    private String operatorFio;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Column(name = "pwd_function", nullable = true, length = 1)
    private String pwdFunction;
    @Column(name = "code_terminal", nullable = true, length = 4)
    private String codeTerminal;
    @Column(name = "info_status", nullable = true, length = 255)
    private String infoStatus;
    @Column(name = "status_id", nullable = true)
    private Integer statusId;
    @Column(name = "term_id", nullable = true)
    private Integer termId;
    
    @OneToMany(mappedBy = "primaryWuDicUser")
    private Set<WuRequestLimit> allAcWuDictUserTermDiv;


    @OneToMany(mappedBy = "primaryWuDicUserPayment")
    private Set<WuRequestPayment> allAcWuDictUserTermDivPayment;

    public Set<WuRequestPayment> getAllAcWuDictUserTermDivPayment() {
        return allAcWuDictUserTermDivPayment;
    }

    public void setAllAcWuDictUserTermDivPayment(Set<WuRequestPayment> allAcWuDictUserTermDivPayment) {
        this.allAcWuDictUserTermDivPayment = allAcWuDictUserTermDivPayment;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<WuRequestLimit> getAllAcWuDictUserTermDiv() {
        return allAcWuDictUserTermDiv;
    }

    public void setAllAcWuDictUserTermDiv(Set<WuRequestLimit> allAcWuDictUserTermDiv) {
        this.allAcWuDictUserTermDiv = allAcWuDictUserTermDiv;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "wrk_id")
    public Integer getWrkId() {
        return wrkId;
    }

    public void setWrkId(Integer wrkId) {
        this.wrkId = wrkId;
    }


    @Basic
    @Column(name = "div_id")
    public Integer getDivId() {
        return divId;
    }

    public void setDivId(Integer divId) {
        this.divId = divId;
    }


    @Basic
    @Column(name = "tt")
    public Integer getTt() {
        return tt;
    }

    public void setTt(Integer tt) {
        this.tt = tt;
    }


    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Basic
    @Column(name = "code_usd")
    public String getCodeUsd() {
        return codeUsd;
    }

    public void setCodeUsd(String codeUsd) {
        this.codeUsd = codeUsd;
    }


    @Basic
    @Column(name = "code_uah")
    public String getCodeUah() {
        return codeUah;
    }

    public void setCodeUah(String codeUah) {
        this.codeUah = codeUah;
    }


    @Basic
    @Column(name = "code_eur")
    public String getCodeEur() {
        return codeEur;
    }

    public void setCodeEur(String codeEur) {
        this.codeEur = codeEur;
    }

    @Basic
    @Column(name = "code_rub")
    public String getCodeRub() {
        return codeRub;
    }

    public void setCodeRub(String codeRub) {
        this.codeRub = codeRub;
    }


    @Basic
    @Column(name = "operator_no")
    public Integer getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(Integer operatorNo) {
        this.operatorNo = operatorNo;
    }

    @Basic
    @Column(name = "operator_fio")
    public String getOperatorFio() {
        return operatorFio;
    }

    public void setOperatorFio(String operatorFio) {
        this.operatorFio = operatorFio;
    }

    public String getOperatorEmail() {
        return email;
    }

    public void setOperatorEmail(String email) {
        this.email = email;
    }

     @Basic
     @Column(name = "pwd_function")
     public String getPwdFunction() {
        return pwdFunction;
    }

    public void setPwdFunction(String pwdFunction) {
        this.pwdFunction = pwdFunction;
    }

    @Basic
    @Column(name = "code_terminal")
    public String getCodeTerminal() {
        return codeTerminal;
    }

    public void setCodeTerminal(String codeTerminal) {
        this.codeTerminal = codeTerminal;
    }

    @Basic
    @Column(name = "info_status")
    public String getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(String infoStatus) {
        this.infoStatus = infoStatus;
    }

      @Basic
      @Column(name = "status_id")
      public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }


    @Basic
    @Column(name = "term_id")
    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcWuDictUserTermDiv that = (AcWuDictUserTermDiv) o;
        return id == that.id &&
                Objects.equals(wrkId, that.wrkId) &&
                Objects.equals(divId, that.divId) &&
                Objects.equals(tt, that.tt) &&
                Objects.equals(city, that.city) &&
                Objects.equals(address, that.address) &&
                Objects.equals(codeUsd, that.codeUsd) &&
                Objects.equals(codeUah, that.codeUah) &&
                Objects.equals(codeEur, that.codeEur) &&
                Objects.equals(codeRub, that.codeRub) &&
                Objects.equals(operatorNo, that.operatorNo) &&
                Objects.equals(operatorFio, that.operatorFio) &&
                Objects.equals(email, that.email) &&
                Objects.equals(pwdFunction, that.pwdFunction) &&
                Objects.equals(codeTerminal, that.codeTerminal) &&
                Objects.equals(infoStatus, that.infoStatus) &&
                Objects.equals(statusId, that.statusId) &&
                Objects.equals(termId, that.termId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, wrkId, divId, tt, city, address, codeUsd, codeUah, codeEur, codeRub, operatorNo, operatorFio,email, pwdFunction, codeTerminal, infoStatus, statusId, termId);
    }
}
