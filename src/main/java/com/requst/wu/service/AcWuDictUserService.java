package com.requst.wu.service;

import com.requst.wu.model.AcWuDictUserTermDiv;
import com.requst.wu.repository.AcWuDictUserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("AcWuDictUserService")
public class AcWuDictUserService implements AcWuDictUserServiceImpl {

    @Qualifier("AcWuDictUserRepository")
    @Autowired
    private AcWuDictUserRepository acWuDictUserRepository;


    @Override
    public AcWuDictUserTermDiv findByEmail(String email) {
        return acWuDictUserRepository.findByEmail(email);
    }
}
