package com.requst.wu.service;

import com.requst.wu.model.RequestState;
import com.requst.wu.repository.RequestStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("requestStateService")
public class RequestStateService implements RequestStateServiceImpl {

    @Qualifier("requestStateRepository")
    @Autowired
    private RequestStateRepository requestStateRepository;


    @Override
    public RequestState findByWuStateId(Integer id) {
        return requestStateRepository.findByWuStateId(id);
    }
}
