package com.chengql.transpond.controller.mac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chengql.monitor.domain.MacRealtime;
import com.chengql.monitor.service.MacRealtimeService;

@RestController
@RequestMapping("/macRealtime")
public class MacRealtimeController {
    @Autowired
    private MacRealtimeService macRealtimeService;

    @RequestMapping(value = "/list")
    public List<MacRealtime> list(String startTime, String endTime) {
        return macRealtimeService.list();
    }
}
