package com.hwsafe.webService.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwsafe.enterprise.domain.EntBusinessinfo;
import com.hwsafe.enterprise.service.EntBusinessinfoService;
import com.hwsafe.exception.ErrorTipTemplate;
import com.hwsafe.monitor.domain.bo.PointRealTimeGroupBO;
import com.hwsafe.monitor.service.MacRealtimeService;
import com.hwsafe.validate.Check;
import com.hwsafe.webService.service.PointRealTimeService;

@WebService(targetNamespace = "http://service.webService.hwsafe.com/", endpointInterface = "com.hwsafe.webService.service.PointRealTimeService")
@Component
public class PointRealTimeServiceImpl implements PointRealTimeService {
    @Autowired
    private MacRealtimeService macRealtimeService;
    @Autowired
    private EntBusinessinfoService entBusinessinfoService;

    /*
     * @Override public List<PointRealTimeGroupBO> dataList() {
     * List<EntBusinessinfo> businessinfos = entBusinessinfoService.list();
     * List<PointRealTimeGroupBO> pointRealTimeGroups =
     * macRealtimeService.pointRealTimeGroupList(); if
     * (!businessinfos.isEmpty()) { if (!pointRealTimeGroups.isEmpty()) { for
     * (PointRealTimeGroupBO bo : pointRealTimeGroups) {
     * bo.setCorpName(businessinfos.get(0).getEntname()); } } } return
     * pointRealTimeGroups; }
     */

    @Override
    public List<PointRealTimeGroupBO> getRealTimeData(String corpFlag) {

        Check.checkNotBlank(corpFlag, ErrorTipTemplate.PARAMETER_NOT_NULL);
        Check.checkArgument("HWDZ".equals(corpFlag),
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        List<EntBusinessinfo> businessinfos = entBusinessinfoService.list();
        List<PointRealTimeGroupBO> pointRealTimeGroups = macRealtimeService
                .pointRealTimeGroupList();
        if (!businessinfos.isEmpty()) {
            if (!pointRealTimeGroups.isEmpty()) {
                for (PointRealTimeGroupBO bo : pointRealTimeGroups) {
                    bo.setCorpName(businessinfos.get(0).getEntname());
                }
            }
        }
        return pointRealTimeGroups;
    }

    /*
     * @Override public String test() { return "HKR:"; }
     */
}
