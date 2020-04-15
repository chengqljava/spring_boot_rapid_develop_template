package com.hwsafe.transpond.controller.mac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.monitor.domain.MacAlarmHandle;
import com.hwsafe.monitor.service.MacAlarmHandleService;

@RestController
@RequestMapping("/macAlarmHandle")
public class MacAlarmHandleController {
    @Autowired
    private MacAlarmHandleService macAlarmHandleService;

    @RequestMapping(value = "/list")
    public List<MacAlarmHandle> list(String startTime, String endTime) {
        return macAlarmHandleService.list(startTime, endTime);
    }
}
