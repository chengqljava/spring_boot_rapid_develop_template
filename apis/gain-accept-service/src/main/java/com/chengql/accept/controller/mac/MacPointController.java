package com.hwsafe.accept.controller.mac;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.hwsafe.accept.point.domain.MacProbe;
import com.hwsafe.accept.point.service.MacProbeService;
import com.hwsafe.utils.AjaxResult;

@RestController
@RequestMapping("/probe")
public class MacPointController {
    @Autowired
    private MacProbeService macProbeService;

    @RequestMapping(value = "/accept")
    public AjaxResult<String> accept(@RequestBody String json) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (StringUtils.isNoneBlank(json)) {
            List<MacProbe> list = JSONArray.parseArray(json, MacProbe.class);
            macProbeService.saveOrUpdateBatch(list);
        }
        return ajaxResult;
    }
}
