package com.hwsafe.accept.controller.mac;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.hwsafe.accept.point.domain.MacHistory;
import com.hwsafe.accept.point.service.MacHistoryService;
import com.hwsafe.utils.AjaxResult;

@RestController
@RequestMapping("/history")
public class MacHistoryController {
    @Autowired
    private MacHistoryService macHistoryService;

    @RequestMapping(value = "/accept")
    public AjaxResult<String> accept(@RequestBody String json) {
        AjaxResult<String> ajaxResult = new AjaxResult<String>();
        if (StringUtils.isNoneBlank(json)) {
            List<MacHistory> list = JSONArray.parseArray(json,
                    MacHistory.class);
            macHistoryService.saveBatch(list);
        }
        return ajaxResult;
    }
}
