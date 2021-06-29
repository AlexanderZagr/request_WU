package com.requst.wu.service;

import com.requst.wu.model.WuRequestChangePass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WuRequestChangePassServiceImp {
    void saveWuRequest(WuRequestChangePass wuRequestChangePass);
    WuRequestChangePass findByWuId(Integer id);

    Page<WuRequestChangePass> findByPrimaryWuDicUser(String email, Pageable pageRequest);

    void delete(WuRequestChangePass wuRequestChangePass);
    WuRequestChangePass update(WuRequestChangePass wuRequestChangePass, Integer id);


    Page<WuRequestChangePass> findBySearchParamsAndState(
            String email,
            int readAlReady,
            Pageable pageRequest);

    Page<WuRequestChangePass> findBySearchParams(
            String email,
            Pageable pageRequest);
}
