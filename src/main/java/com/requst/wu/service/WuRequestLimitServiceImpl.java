package com.requst.wu.service;


import com.requst.wu.model.WuRequestLimit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WuRequestLimitServiceImpl {

    
    void saveWuRequest(WuRequestLimit wuRequestLimit);
    WuRequestLimit findByWuId(Integer id);

    WuRequestLimit update(WuRequestLimit wuRequestLimit, Integer id);

    Page<WuRequestLimit> findByAmount(String name, Pageable page);
    Page<WuRequestLimit> listWuRequest(Pageable page);

    Page<WuRequestLimit> findByPrimaryState(int ready,Pageable pageRequest);
    Page<WuRequestLimit> findByPrimaryWuDicUser(String email,Pageable pageRequest);

    void delete(WuRequestLimit wuRequestLimit);
    Page<WuRequestLimit> findBySearchParamsAndState(
            String searchTerm,
            String email,
            int readAlReady,
            Pageable pageRequest);

    Page<WuRequestLimit> findBySearchParams(
            String searchTerm,
            String email,
            Pageable pageRequest);


}
