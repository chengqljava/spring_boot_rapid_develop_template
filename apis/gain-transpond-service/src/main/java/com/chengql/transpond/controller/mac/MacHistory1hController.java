package com.hwsafe.transpond.controller.mac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.monitor.domain.MacHistory1h;
import com.hwsafe.monitor.service.MacHistory1hService;

@RestController
@RequestMapping("/macHistory1h")
public class MacHistory1hController {
    @Autowired
    private MacHistory1hService macHistory1hService;

    @RequestMapping(value = "/list")
    public List<MacHistory1h> list(String startTime, String endTime) {
        return macHistory1hService.list(startTime, endTime);
    }
}
