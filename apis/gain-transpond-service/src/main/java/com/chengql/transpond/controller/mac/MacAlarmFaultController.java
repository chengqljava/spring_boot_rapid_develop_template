package com.hwsafe.transpond.controller.mac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.monitor.domain.MacAlarmFault;
import com.hwsafe.monitor.service.MacAlarmFaultService;

@RestController
@RequestMapping("/macAlarmFault")
public class MacAlarmFaultController {
    @Autowired
    private MacAlarmFaultService macAlarmFaultService;

    @RequestMapping(value = "/list")
    public List<MacAlarmFault> list(String startTime, String endTime) {
        return macAlarmFaultService.list(startTime, endTime);
    }
}
