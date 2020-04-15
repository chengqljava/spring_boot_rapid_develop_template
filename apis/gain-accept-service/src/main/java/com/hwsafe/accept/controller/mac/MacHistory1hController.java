package com.hwsafe.accept.controller.mac;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.hwsafe.accept.point.domain.MacHistory1h;
import com.hwsafe.accept.point.service.MacHistory1hService;
import com.hwsafe.utils.AjaxResult;

@RestController
@RequestMapping("/history1h")
public class MacHistory1hController {
    @Autowired
    private MacHistory1hService macHistory1hService;

    @RequestMapping(value = "/accept")
    public AjaxResult<String> accept(@RequestBody String json) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (StringUtils.isNoneBlank(json)) {
            List<MacHistory1h> list = JSONArray.parseArray(json,
                    MacHistory1h.class);
            macHistory1hService.saveBatch(list);
        }
        return ajaxResult;
    }
}
