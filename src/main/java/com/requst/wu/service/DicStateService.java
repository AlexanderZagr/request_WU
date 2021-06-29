/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requst.wu.service;

import com.requst.wu.model.DicState;
import com.requst.wu.repository.DicStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author zagraevskaya!!!!!
 */
@Service("dicStateService")
public class DicStateService implements DicStateServiceImpl{

    @Qualifier("dicStateRepository")
    @Autowired
    private DicStateRepository dicStateRepository;
    
    @Override
    public DicState findByStateId(Integer state_id) {
      return dicStateRepository.findByStateId(state_id);
    }
    
}
