/*package com.chengql.accept.controller.mac;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.chengql.accept.point.domain.MacAlarmFault;
import com.chengql.accept.point.service.MacAlarmFaultService;
import com.chengql.utils.AjaxResult;

@RestController
@RequestMapping("/alarmFault")
public class MacAlarmFaultController {
    @Autowired
    private MacAlarmFaultService macAlarmFaultService;

    @RequestMapping(value = "/accept")
    public AjaxResult<String> accept(@RequestBody String json) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (StringUtils.isNoneBlank(json)) {
            List<MacAlarmFault> list = JSONArray.parseArray(json,
                    MacAlarmFault.class);
            macAlarmFaultService.saveBatch(list);
        }
        return ajaxResult;
    }
}
*/