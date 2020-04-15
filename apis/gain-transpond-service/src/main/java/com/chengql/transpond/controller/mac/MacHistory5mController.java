package com.hwsafe.transpond.controller.mac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.monitor.domain.MacHistory5m;
import com.hwsafe.monitor.service.MacHistory5mService;

@RestController
@RequestMapping("/macHistory5m")
public class MacHistory5mController {
    @Autowired
    private MacHistory5mService macHistory5mService;

    @RequestMapping(value = "/list")
    public List<MacHistory5m> list(String startTime, String endTime) {
        return macHistory5mService.list(startTime, endTime);
    }
}
