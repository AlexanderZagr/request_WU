package com.requst.wu.repository;

import com.requst.wu.model.WuRequestChangePass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("wuRequestChangePassRepository")
public interface WuRequestChangePassRepository extends JpaRepository<WuRequestChangePass, Long> {

    @Query("select b from WuRequestChangePass b where b.id = :id")
    WuRequestChangePass findByWuId(@Param("id") Integer id);


    @Query("SELECT t FROM WuRequestChangePass t " +
            " WHERE t.primaryWuDicUser.email =  :email"
    )
    Page<WuRequestChangePass> findByPrimaryWuDicUser(
            @Param("email") String email,
            Pageable pageRequest
    );

    @Query("SELECT t FROM WuRequestChangePass t WHERE " +
            "  t.primaryWuDicUser.email =  :email"+
            " AND t.primaryState.stateId in ( :readAlReady)")
    Page<WuRequestChangePass> findBySearchParamsAndState(
            @Param("email") String email,
            @Param("readAlReady") int readAlReady,
            Pageable pageRequest
    );

    @Query("SELECT t FROM WuRequestChangePass t WHERE " +
            "  t.primaryWuDicUser.email =  :email")
    Page<WuRequestChangePass> findBySearchParams(
            @Param("email") String email,
            Pageable pageRequest
    );

}
