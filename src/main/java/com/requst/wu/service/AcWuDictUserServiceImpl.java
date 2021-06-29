package com.requst.wu.service;

import com.requst.wu.model.AcWuDictUserTermDiv;

public interface AcWuDictUserServiceImpl {

   AcWuDictUserTermDiv findByEmail(String email);
}
