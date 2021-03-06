package com.chengql.transpond.controller.mac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chengql.monitor.domain.MacHistory;
import com.chengql.monitor.service.MacHistoryService;

@RestController
@RequestMapping("/macHistory")
public class MacHistoryController {
    @Autowired
    private MacHistoryService macHistoryService;

    @RequestMapping(value = "/list")
    public List<MacHistory> list(String startTime, String endTime) {
        return macHistoryService.list(startTime, endTime);
    }
}
