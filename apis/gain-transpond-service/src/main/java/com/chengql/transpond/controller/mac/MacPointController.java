package com.hwsafe.transpond.controller.mac;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.monitor.domain.MacPoint;
import com.hwsafe.monitor.service.MacPointService;
import com.hwsafe.transpond.base.config.TranspondConfig;
import com.hwsafe.transpond.controller.mac.dto.MacProbDTO;
import com.hwsafe.utils.BeanMapper;

@RestController
@RequestMapping("/macPoint")
public class MacPointController {
    @Autowired
    private MacPointService macPointService;
    @Autowired
    private TranspondConfig transpondConfig;

    @RequestMapping(value = "/list")
    public List<MacProbDTO> list(String startTime, String endTime) {
        List<MacPoint> list = macPointService.list();
        List<MacProbDTO> probList = new ArrayList<>();
        if (!list.isEmpty()) {
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
                macProbDTO
                        .setSuperlowalarmvalue(macPoint.getEarlywarningvalue());
                macProbDTO.setSuperhighalarmvalue(
                        macPoint.getHigherwarningvalue());
                macProbDTO.setProbehostid(transpondConfig.getProbehostid());
                probList.add(macProbDTO);
            }
        }
        return probList;
    }
}
