/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requst.wu.repository;

import com.requst.wu.model.DicState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author zagraevskaya
 */
@Repository("dicStateRepository")
public interface DicStateRepository extends JpaRepository<DicState, Long>{


    @Query("select b from DicState b where b.stateId = :state_id")    //This is using a named query method
    DicState findByStateId(@Param("state_id") Integer state_id);

}
