package com.requst.wu.service;

import com.requst.wu.model.WuRequestChangePass;
import com.requst.wu.repository.WuRequestChangePassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("wuRequestChangePassService")
public class WuRequestChangePassService implements WuRequestChangePassServiceImp {

    @Qualifier("wuRequestChangePassRepository")
    @Autowired
    private WuRequestChangePassRepository wuRequestRepository;


    @Override
    public void saveWuRequest(WuRequestChangePass wuRequestChangePass) {
       wuRequestRepository.save(wuRequestChangePass);
    }

    @Override
    public WuRequestChangePass findByWuId(Integer id) {
        return wuRequestRepository.findByWuId(id);
    }

    @Override
    public Page<WuRequestChangePass> findByPrimaryWuDicUser(String email, Pageable pageRequest) {
        return wuRequestRepository.findByPrimaryWuDicUser(email,pageRequest);
    }

    @Override
    public void delete(WuRequestChangePass wuRequestChangePass) {
        wuRequestRepository.delete(wuRequestChangePass);
    }

    @Override
    @Transactional
    public WuRequestChangePass update(WuRequestChangePass wuRequestChangePass, Integer id) {
        WuRequestChangePass entity = wuRequestRepository.findByWuId(id);

        if (wuRequestChangePass.getDateRequest() != null) entity.setDateRequest(wuRequestChangePass.getDateRequest());

        if (wuRequestChangePass.getPrimaryState() != null)
            entity.setPrimaryState(wuRequestChangePass.getPrimaryState());

        return wuRequestRepository.save(entity);
    }


    @Override
    public Page<WuRequestChangePass> findBySearchParamsAndState( String email, int readAlReady, Pageable pageRequest) {
        return wuRequestRepository.findBySearchParamsAndState(email,readAlReady,pageRequest);
    }

    @Override
    public Page<WuRequestChangePass> findBySearchParams( String email, Pageable pageRequest) {
        return wuRequestRepository.findBySearchParams(email,pageRequest);
    }
}
