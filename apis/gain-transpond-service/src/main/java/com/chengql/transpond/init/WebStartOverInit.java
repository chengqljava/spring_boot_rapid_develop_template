package com.chengql.transpond.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chengql.monitor.domain.MacPoint;
import com.chengql.monitor.service.MacPointService;
import com.chengql.transpond.base.config.TranspondConfig;
import com.chengql.transpond.controller.mac.dto.MacProbDTO;
import com.chengql.utils.BeanMapper;
import com.chengql.utils.OkHttpRequest;

/**
 * @author chengql 服务启动完成以后 执行 run
 *
 */
@Component
public class WebStartOverInit implements CommandLineRunner {

    @Autowired
    private TranspondConfig transpondConfig;
    @Autowired
    private MacPointService macPointService;

    @Override
    public void run(String... args) throws Exception {
        List<MacPoint> list = macPointService.list();
        if (!list.isEmpty()) {
            List<MacProbDTO> probList = new ArrayList<>();
            MacProbDTO macProbDTO = null;
            for (MacPoint macPoint : list) {
                macProbDTO = BeanMapper.map(macPoint, MacProbDTO.class);
                macProbDTO.setProbeid(macPoint.getPointid());
                macProbDTO.setProbenum(macPoint.getPointnum());
                macProbDTO.setProbename(macPoint.getPointname());
                macProbDTO.setProbegroup(macPoint.getUnit());
                macProbDTO.setTop(macPoint.getLocationx());
                macProbDTO.setLeft(macPoint.getLocationy());
                macProbDTO.setRangemax(macPoint.getUpperrangevalue());
                macProbDTO.setRangelow(macPoint.getLowerrangervalue());
                macProbDTO.setLowalarmvalue(macPoint.getLowerwarningvalue());
                macProbDTO.setHighalarmvalue(macPoint.getHighwarningvalue());
                macProbDTO.setSuperlowalarmvalue(macPoint.getEarlywarningvalue());
                macProbDTO.setSuperhighalarmvalue(macPoint.getHigherwarningvalue());
                macProbDTO.setProbehostid(transpondConfig.getProbehostid());
                probList.add(macProbDTO);
            }

            try {
                OkHttpRequest.getInstance().post("http://" + transpondConfig.getIp() + ":" + transpondConfig.getPort() + transpondConfig.getPointMethod(), JSONObject.toJSONString(probList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
