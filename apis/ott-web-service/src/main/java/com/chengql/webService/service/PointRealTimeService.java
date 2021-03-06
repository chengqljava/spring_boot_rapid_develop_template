package com.chengql.webService.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.chengql.monitor.domain.bo.PointRealTimeGroupBO;

@WebService
public interface PointRealTimeService {
    // @WebMethod(action = "dataList")
    // @WebResult
    // public List<PointRealTimeGroupBO> dataList();

    @WebMethod(action = "getRealTimeData")
    public List<PointRealTimeGroupBO> getRealTimeData(String corpFlag);

    // @WebMethod(action = "test")
    // public String test();

}
