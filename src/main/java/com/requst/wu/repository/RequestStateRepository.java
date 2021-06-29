package com.requst.wu.repository;

import com.requst.wu.model.RequestState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("requestStateRepository")
public interface RequestStateRepository extends JpaRepository<RequestState, Integer> {


    @Query("select b from RequestState b where b.id = :id")
    RequestState findByWuStateId(@Param("id") Integer id);
}
