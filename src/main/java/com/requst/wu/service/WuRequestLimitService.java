package com.requst.wu.service;

import com.requst.wu.model.WuRequestLimit;
import com.requst.wu.repository.WuRequestLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("wuRequestLimitService")
public class WuRequestLimitService implements WuRequestLimitServiceImpl {

    @Qualifier("wuRequestLimitRepository")
    @Autowired
    private WuRequestLimitRepository wuRequestRepository;

    @Override
    public void saveWuRequest(WuRequestLimit wuRequestLimit) {
        wuRequestRepository.save(wuRequestLimit);
    }

    @Override
    public Page<WuRequestLimit> findByAmount(String name, Pageable page) {
        return wuRequestRepository.findByCurrency(name,page);
    }

    @Override
    public Page<WuRequestLimit> listWuRequest(Pageable page) {
        return wuRequestRepository.listWuRequest(page);
    }

    @Override
    public Page<WuRequestLimit> findByPrimaryState(int ready, Pageable pageRequest) {
        return wuRequestRepository.findByPrimaryState(ready,pageRequest);
    }

    @Override
    public void delete(WuRequestLimit wuRequestLimit) {
        wuRequestRepository.delete(wuRequestLimit);
    }


    public WuRequestLimit findByWuId(Integer id) {
        return wuRequestRepository.findByWuId(id);
    }

    @Override
    public WuRequestLimit update(WuRequestLimit wuRequestLimit, Integer id) {
        WuRequestLimit entity = wuRequestRepository.findByWuId(id);

        if (wuRequestLimit.getDateRequest() != null) entity.setDateRequest(wuRequestLimit.getDateRequest());
        if (wuRequestLimit.getComent() != null) entity.setComent(wuRequestLimit.getComent());

        if (wuRequestLimit.getPrimaryState() != null)
            entity.setPrimaryState(wuRequestLimit.getPrimaryState());

        return wuRequestRepository.save(entity);
    }

    @Override
    public Page<WuRequestLimit> findByPrimaryWuDicUser(String email, Pageable pageRequest) {
       return wuRequestRepository.findByPrimaryWuDicUser(email, pageRequest);
    }

    @Override
    public Page<WuRequestLimit> findBySearchParamsAndState(
            String searchTerm,
            String email,
            int readAlReady,
            Pageable pageRequest){
        return wuRequestRepository.findBySearchParamsAndState(searchTerm,email,readAlReady,pageRequest);
    }

    @Override
    public Page<WuRequestLimit> findBySearchParams(String searchTerm, String email, Pageable pageRequest) {
        return wuRequestRepository.findBySearchParams(searchTerm,email,pageRequest);
    }
}



