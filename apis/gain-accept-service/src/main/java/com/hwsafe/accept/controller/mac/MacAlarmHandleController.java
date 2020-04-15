package com.hwsafe.accept.controller.mac;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.hwsafe.accept.point.domain.MacAlarmHandle;
import com.hwsafe.accept.point.service.MacAlarmHandleService;
import com.hwsafe.utils.AjaxResult;

@RestController
@RequestMapping("/alarmHandle")
public class MacAlarmHandleController {
    @Autowired
    private MacAlarmHandleService macAlarmHandleService;

    @RequestMapping(value = "/accept")
    public AjaxResult<String> accept(@RequestBody String json) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (StringUtils.isNoneBlank(json)) {
            List<MacAlarmHandle> list = JSONArray.parseArray(json,
                    MacAlarmHandle.class);
            macAlarmHandleService.saveBatch(list);
        }
        return ajaxResult;
    }
}
