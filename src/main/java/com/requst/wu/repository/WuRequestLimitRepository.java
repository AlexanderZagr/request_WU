package com.requst.wu.repository;


import com.requst.wu.model.WuRequestLimit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("wuRequestLimitRepository")
public interface WuRequestLimitRepository extends JpaRepository<WuRequestLimit, Long> {
    
    @Query("select b from WuRequestLimit b where b.id = :id")
    WuRequestLimit findByWuId(@Param("id") Integer id);


    Page<WuRequestLimit> findByCurrency(String name, Pageable page);
    
    @Query("SELECT t FROM WuRequestLimit t ")
    Page<WuRequestLimit> listWuRequest(
            Pageable pageRequest
    );

    @Query("SELECT t FROM WuRequestLimit t " +
           " WHERE t.primaryState.id =  :ready"
    )
    Page<WuRequestLimit> findByPrimaryState(
            @Param("ready") int ready,
            Pageable pageRequest
    );

     @Query("SELECT t FROM WuRequestLimit t " +
           " WHERE t.primaryWuDicUser.email =  :email"
    )
    Page<WuRequestLimit> findByPrimaryWuDicUser(
            @Param("email") String email,
            Pageable pageRequest
    );
    

    @Query("SELECT t FROM WuRequestLimit t WHERE " +
            "  LOWER(t.coment) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND  " +
            "  t.primaryWuDicUser.email =  :email"+
            " AND t.primaryState.stateId in ( :readAlReady)")
    Page<WuRequestLimit> findBySearchParamsAndState(
            @Param("searchTerm") String searchTerm,
            @Param("email") String email,
            @Param("readAlReady") int readAlReady,
            Pageable pageRequest
    );

    @Query("SELECT t FROM WuRequestLimit t WHERE " +
            "  LOWER(t.coment) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND  " +
            "  t.primaryWuDicUser.email =  :email")
    Page<WuRequestLimit> findBySearchParams(
            @Param("searchTerm") String searchTerm,
            @Param("email") String email,
            Pageable pageRequest
    );
}
