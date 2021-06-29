package com.requst.wu.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "request_state", schema = "accord")
public class RequestState {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "name_state", nullable = false, length = 255)
    private String nameState;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "requestStates")


    private Set<WuRequestLimit> wuRequestLimits = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameState() {
        return nameState;
    }


    public void setNameState(String nameState) {
        this.nameState = nameState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestState that = (RequestState) o;
        return id == that.id &&
                Objects.equals(nameState, that.nameState);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nameState);
    }
}
