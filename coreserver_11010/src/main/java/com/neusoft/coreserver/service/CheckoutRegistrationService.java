package com.neusoft.coreserver.service;

import com.neusoft.coreserver.entity.CheckoutRegistration;
import com.neusoft.coreserver.entity.PageResponseBean;
import com.neusoft.coreserver.entity.ResponseBean;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface CheckoutRegistrationService {

    PageResponseBean<List<CheckoutRegistration>> page(@RequestBody Map<String, Object> request);

    ResponseBean<Integer> update(@RequestBody CheckoutRegistration data);

}
