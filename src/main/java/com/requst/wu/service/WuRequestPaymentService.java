package com.requst.wu.service;


import com.requst.wu.model.WuRequestPayment;
import com.requst.wu.repository.WuRequestPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("wuRequestPaymentService")
public class WuRequestPaymentService implements WuRequestPaymentServiceImpl{

    @Qualifier("wuRequestPaymentRepository")
    @Autowired
    private WuRequestPaymentRepository wuRequestRepository;

    @Override
    public void saveWuRequest(WuRequestPayment wuRequestPayment) {
        wuRequestRepository.save(wuRequestPayment);
    }

    @Override
    public WuRequestPayment findByWuId(Integer id) {
        return wuRequestRepository.findByWuId(id);
    }

    @Override
    public Page<WuRequestPayment> findByPrimaryWuDicUser(String email, Pageable pageRequest) {
        return wuRequestRepository.findByPrimaryWuDicUser(email,pageRequest);
    }

    @Override
    public void delete(WuRequestPayment wuRequestPayment) {
        wuRequestRepository.delete(wuRequestPayment);
    }

    @Override
    @Transactional
    public WuRequestPayment update(WuRequestPayment wuRequestPayment, Integer id) {
        WuRequestPayment entity = wuRequestRepository.findByWuId(id);

        if (wuRequestPayment.getDateRequest() != null) entity.setDateRequest(wuRequestPayment.getDateRequest());
        if (wuRequestPayment.getComent() != null) entity.setComent(wuRequestPayment.getComent());

        if (wuRequestPayment.getPrimaryStatePayment() != null)
         entity.setPrimaryStatePayment(wuRequestPayment.getPrimaryStatePayment());

        return wuRequestRepository.save(entity);
    }

    @Override
    public WuRequestPayment uploadFileData(WuRequestPayment wuRequestPayment, MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            String fileName = file.getOriginalFilename();

            wuRequestPayment.setSingleFileUploadInput(fileName);
        }
        return wuRequestPayment;
    }

    @Override
    public Page<WuRequestPayment> findBySearchParamsAndState(
            String searchTerm,
            String email,
            int readAlReady,
            Pageable pageRequest){
        return wuRequestRepository.findBySearchParamsAndState(searchTerm,email,readAlReady,pageRequest);
    }

    @Override
    public Page<WuRequestPayment> findBySearchParams(String searchTerm, String email, Pageable pageRequest) {
        return wuRequestRepository.findBySearchParams(searchTerm,email,pageRequest);
    }
}
