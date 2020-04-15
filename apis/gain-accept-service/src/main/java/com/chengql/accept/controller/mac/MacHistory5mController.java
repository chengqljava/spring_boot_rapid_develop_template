package com.hwsafe.accept.controller.mac;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.hwsafe.accept.point.domain.MacHistory5m;
import com.hwsafe.accept.point.service.MacHistory5mService;
import com.hwsafe.utils.AjaxResult;

@RestController
@RequestMapping("/history5m")
public class MacHistory5mController {
    @Autowired
    private MacHistory5mService macHistory5mService;

    @RequestMapping(value = "/accept")
    public AjaxResult<String> accept(@RequestBody String json) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (StringUtils.isNoneBlank(json)) {
            List<MacHistory5m> list = JSONArray.parseArray(json,
                    MacHistory5m.class);
            macHistory5mService.saveBatch(list);
        }
        return ajaxResult;
    }
}
