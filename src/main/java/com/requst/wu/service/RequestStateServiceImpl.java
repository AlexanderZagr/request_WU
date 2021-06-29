package com.requst.wu.service;

import com.requst.wu.model.RequestState;

public interface RequestStateServiceImpl {
    RequestState findByWuStateId(Integer id);
}
