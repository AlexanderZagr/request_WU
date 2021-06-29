package com.requst.wu.repository;

import com.requst.wu.model.WuRequestPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("wuRequestPaymentRepository")
public interface WuRequestPaymentRepository extends JpaRepository<WuRequestPayment, Long> {

    @Query("select b from WuRequestPayment b where b.id = :id")
    WuRequestPayment findByWuId(@Param("id") Integer id);


    @Query("SELECT t FROM WuRequestPayment t " +
            " WHERE t.primaryWuDicUserPayment.email =  :email"
    )
    Page<WuRequestPayment> findByPrimaryWuDicUser(
            @Param("email") String email,
            Pageable pageRequest
    );


    @Query("SELECT t FROM WuRequestPayment t WHERE " +
            "  LOWER(t.coment) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND  " +
            "  t.primaryWuDicUserPayment.email =  :email"+
            " AND t.primaryStatePayment.stateId in ( :readAlReady)")
    Page<WuRequestPayment> findBySearchParamsAndState(
            @Param("searchTerm") String searchTerm,
            @Param("email") String email,
            @Param("readAlReady") int readAlReady,
            Pageable pageRequest
    );


    @Query("SELECT t FROM WuRequestPayment t WHERE " +
            "  LOWER(t.coment) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND  " +
            "  t.primaryWuDicUserPayment.email =  :email")
    Page<WuRequestPayment> findBySearchParams(
            @Param("searchTerm") String searchTerm,
            @Param("email") String email,
            Pageable pageRequest
    );

}
