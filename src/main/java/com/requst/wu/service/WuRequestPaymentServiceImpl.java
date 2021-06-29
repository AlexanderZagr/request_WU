package com.requst.wu.service;


import com.requst.wu.model.WuRequestPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface WuRequestPaymentServiceImpl {

    void saveWuRequest(WuRequestPayment wuRequestPayment);
    WuRequestPayment findByWuId(Integer id);

    Page<WuRequestPayment> findByPrimaryWuDicUser(String email, Pageable pageRequest);

    void delete(WuRequestPayment wuRequestPayment);
    WuRequestPayment update(WuRequestPayment wuRequestPayment, Integer id);

    WuRequestPayment uploadFileData(WuRequestPayment wuRequestPayment, MultipartFile file) throws IOException;

    Page<WuRequestPayment> findBySearchParamsAndState(
            String searchTerm,
            String email,
            int readAlReady,
            Pageable pageRequest);

    Page<WuRequestPayment> findBySearchParams(
            String searchTerm,
            String email,
            Pageable pageRequest);
}
