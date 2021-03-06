package com.chengql.transpond.controller.mac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chengql.monitor.domain.MacAlarmFault;
import com.chengql.monitor.domain.MacAlarmHandle;
import com.chengql.monitor.domain.MacHistory;
import com.chengql.monitor.domain.MacHistory1h;
import com.chengql.monitor.domain.MacHistory5m;
import com.chengql.monitor.domain.MacPoint;
import com.chengql.monitor.domain.MacRealtime;
import com.chengql.monitor.service.MacAlarmFaultService;
import com.chengql.monitor.service.MacAlarmHandleService;
import com.chengql.monitor.service.MacHistory1hService;
import com.chengql.monitor.service.MacHistory5mService;
import com.chengql.monitor.service.MacHistoryService;
import com.chengql.monitor.service.MacPointService;
import com.chengql.monitor.service.MacRealtimeService;
import com.chengql.transpond.base.config.TranspondConfig;
import com.chengql.transpond.controller.mac.dto.MacProbDTO;
import com.chengql.utils.BeanMapper;
import com.chengql.utils.DateUtil;
import com.chengql.utils.OkHttpRequest;

@Component("quartzMacTaskJob")
public class QuartzMacTaskJob {
    // 初始化时间
    private Date alarmHandleStart = new Date();
    private Date alarmFaultStart = new Date();
    private Date pointHistoryStart = new Date();
    private Date pointHistory5mStart = new Date();
    private Date pointHistory1hStart = new Date();
    @Autowired
    private MacRealtimeService macRealtimeService;
    @Autowired
    private MacAlarmFaultService macAlarmFaultService;
    @Autowired
    private MacAlarmHandleService macAlarmHandleService;
    @Autowired
    private MacHistoryService macHistoryService;
    @Autowired
    private MacHistory5mService macHistory5mService;
    @Autowired
    private MacHistory1hService macHistory1hService;
    @Autowired
    private TranspondConfig transpondConfig;
    @Autowired
    private MacPointService macPointService;

    // 实时数据
    @Scheduled(cron = "${realTime.cron}")
    public void realTime() {
        System.err.println("实时数据 5秒一次");
        List<MacRealtime> list = macRealtimeService.list();
        if (!list.isEmpty()) {
            try {
                OkHttpRequest.getInstance().post("http://" + transpondConfig.getIp() + ":" + transpondConfig.getPort() + transpondConfig.getRealTimeMethod(), JSONObject.toJSONString(list));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // 故障报警处理数据
    @Scheduled(cron = "${alarmHandle.cron}")
    public void alarmHandle() {
        Date currentDate = new Date();
        List<MacAlarmHandle> list = macAlarmHandleService.list(DateUtil.format_yyyyMMddHHmmss(alarmHandleStart), DateUtil.format_yyyyMMddHHmmss(currentDate));
        if (!list.isEmpty()) {
            try {
                OkHttpRequest.getInstance().post("http://" + transpondConfig.getIp() + ":" + transpondConfig.getPort() + transpondConfig.getAlarmHandleMethod(), JSONObject.toJSONString(list));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        alarmHandleStart = currentDate;
    }

    // 故障报警数据
    @Scheduled(cron = "${alarmFault.cron}")
    public void alarmFault() {
        Date currentDate = new Date();
        List<MacAlarmFault> list = macAlarmFaultService.list();

        macAlarmFaultService.list(DateUtil.format_yyyyMMddHHmmss(alarmFaultStart), DateUtil.format_yyyyMMddHHmmss(currentDate));
        if (!list.isEmpty()) {
            try {
                OkHttpRequest.getInstance().post("http://" + transpondConfig.getIp() + ":" + transpondConfig.getPort() + transpondConfig.getAlarmFaultMethod(), JSONObject.toJSONString(list));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        alarmFaultStart = currentDate;
    }

    // 点位实时数据
    @Scheduled(cron = "${pointHistory.cron}")
    public void pointHistory() {
        Date currentDate = new Date();
        List<MacHistory> list = macHistoryService.list(DateUtil.format_yyyyMMddHHmmss(pointHistoryStart), DateUtil.format_yyyyMMddHHmmss(currentDate));
        if (!list.isEmpty()) {
            for (MacHistory macHistory : list) {
                macHistory.setBusinessinfoid(transpondConfig.getBusinessinfoid());
            }
            try {
                OkHttpRequest.getInstance().post("http://" + transpondConfig.getIp() + ":" + transpondConfig.getPort() + transpondConfig.getPointHistoryMethod(), JSONObject.toJSONString(list));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        pointHistoryStart = currentDate;
    }

    // 点位 5分钟数据
    @Scheduled(cron = "${pointHistory5m.cron}")
    public void pointHistory5m() {
        Date currentDate = new Date();
        List<MacHistory5m> list = macHistory5mService.list(DateUtil.format_yyyyMMddHHmmss(pointHistory5mStart), DateUtil.format_yyyyMMddHHmmss(currentDate));
        if (!list.isEmpty()) {
            for (MacHistory5m macHistory : list) {
                macHistory.setBusinessinfoid(transpondConfig.getBusinessinfoid());
            }
            try {
                OkHttpRequest.getInstance().post("http://" + transpondConfig.getIp() + ":" + transpondConfig.getPort() + transpondConfig.getPointHistory5mMethod(), JSONObject.toJSONString(list));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        pointHistory5mStart = currentDate;
    }

    // 点位 1小时数据
    @Scheduled(cron = "${pointHistory1h.cron}")
    public void pointHistory1h() {
        Date currentDate = new Date();
        List<MacHistory1h> list = macHistory1hService.list(DateUtil.format_yyyyMMddHHmmss(pointHistory1hStart), DateUtil.format_yyyyMMddHHmmss(currentDate));
        if (!list.isEmpty()) {
            for (MacHistory1h macHistory : list) {
                macHistory.setBusinessinfoid(transpondConfig.getBusinessinfoid());
            }
            try {
                OkHttpRequest.getInstance().post("http://" + transpondConfig.getIp() + ":" + transpondConfig.getPort() + transpondConfig.getPointHistory1hMethod(), JSONObject.toJSONString(list));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        pointHistory1hStart = currentDate;
    }

    // 实时点位
    /**
     * 点位1小时
     */
    @Scheduled(cron = "${point.cron}")
    public void point() {
        List<MacPoint> list = macPointService.list();
        if (!list.isEmpty()) {
            List<MacProbDTO> probList = new ArrayList<>();
            MacProbDTO macProbDTO = null;
            for (MacPoint macPoint : list) {
                macProbDTO = BeanMapper.map(macPoint, MacProbDTO.class);
                macProbDTO.setProbeid(macPoint.getPointid());
                macProbDTO.setProbenum(macPoint.getPointnum());
                macProbDTO.setProbename(macPoint.getPointname());
                macProbDTO.setProbegroup(macPoint.getUnit());
                macProbDTO.setTop(macPoint.getLocationx());
                macProbDTO.setLeft(macPoint.getLocationy());
                macProbDTO.setRangemax(macPoint.getUpperrangevalue());
                macProbDTO.setRangelow(macPoint.getLowerrangervalue());
                macProbDTO.setLowalarmvalue(macPoint.getLowerwarningvalue());
                macProbDTO.setHighalarmvalue(macPoint.getHighwarningvalue());
                macProbDTO.setSuperlowalarmvalue(macPoint.getEarlywarningvalue());
                macProbDTO.setSuperhighalarmvalue(macPoint.getHigherwarningvalue());
                macProbDTO.setProbehostid(transpondConfig.getProbehostid());
                probList.add(macProbDTO);
            }
            try {
                OkHttpRequest.getInstance().post("http://" + transpondConfig.getIp() + ":" + transpondConfig.getPort() + transpondConfig.getPointMethod(), JSONObject.toJSONString(probList));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
