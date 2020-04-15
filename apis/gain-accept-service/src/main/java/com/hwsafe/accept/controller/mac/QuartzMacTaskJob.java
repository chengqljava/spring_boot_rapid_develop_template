package com.hwsafe.accept.controller.mac;

/*
 * @Component("quartzMacTaskJob") public class QuartzMacTaskJob { // 初始化时间
 * private Date alarmHandleStart = new Date(); private Date alarmFaultStart =
 * new Date(); private Date pointHistoryStart = new Date(); private Date
 * pointHistory5mStart = new Date(); private Date pointHistory1hStart = new
 * Date();
 * 
 * @Autowired private MacRealtimeService macRealtimeService;
 * 
 * @Autowired private MacAlarmFaultService macAlarmFaultService;
 * 
 * @Autowired private MacAlarmHandleService macAlarmHandleService;
 * 
 * @Autowired private MacHistoryService macHistoryService;
 * 
 * @Autowired private MacHistory5mService macHistory5mService;
 * 
 * @Autowired private MacHistory1hService macHistory1hService;
 * 
 * @Autowired private TranspondConfig transpondConfig;
 * 
 * // 实时数据
 * 
 * @Scheduled(cron = "${realTime.cron}") public void realTime() {
 * System.err.println("实时数据 5秒一次"); List<MacRealtime> list =
 * macRealtimeService.list(); if (!list.isEmpty()) { OkHttpRequest okHttpRequest
 * = new OkHttpRequest(); try { okHttpRequest.post("http://" +
 * transpondConfig.getIp() + ":" + transpondConfig.getPort() +
 * transpondConfig.getRealTimeMethod(), JSONObject.toJSONString(list)); } catch
 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
 * }
 * 
 * // 故障报警处理数据
 * 
 * @Scheduled(cron = "${alarmHandle.cron}") public void alarmHandle() { Date
 * currentDate = new Date(); List<MacAlarmHandle> list =
 * macAlarmHandleService.list(DateUtil.format_yyyyMMddHHmm(alarmHandleStart),
 * DateUtil.format_yyyyMMddHHmm(currentDate)); if (!list.isEmpty()) {
 * OkHttpRequest okHttpRequest = new OkHttpRequest(); try {
 * okHttpRequest.post("http://" + transpondConfig.getIp() + ":" +
 * transpondConfig.getPort() + transpondConfig.getAlarmHandleMethod(),
 * JSONObject.toJSONString(list)); } catch (IOException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } } alarmHandleStart =
 * currentDate; }
 * 
 * // 故障报警数据 // @Scheduled(cron = "${alarmFault.cron}") public void alarmFault()
 * { Date currentDate = new Date(); List<MacAlarmFault> list =
 * macAlarmFaultService.list(DateUtil.format_yyyyMMddHHmm(alarmFaultStart),
 * DateUtil.format_yyyyMMddHHmm(currentDate)); if (!list.isEmpty()) {
 * OkHttpRequest okHttpRequest = new OkHttpRequest(); try {
 * okHttpRequest.post("http://" + transpondConfig.getIp() + ":" +
 * transpondConfig.getPort() + transpondConfig.getAlarmFaultMethod(),
 * JSONObject.toJSONString(list)); } catch (IOException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } } alarmFaultStart =
 * currentDate; }
 * 
 * // 点位实时数据 // @Scheduled(cron = "${pointHistory.cron}") public void
 * pointHistory() { Date currentDate = new Date(); List<MacHistory> list =
 * macHistoryService.list(DateUtil.format_yyyyMMddHHmm(pointHistoryStart),
 * DateUtil.format_yyyyMMddHHmm(currentDate)); if (!list.isEmpty()) {
 * OkHttpRequest okHttpRequest = new OkHttpRequest(); try {
 * okHttpRequest.post("http://" + transpondConfig.getIp() + ":" +
 * transpondConfig.getPort() + transpondConfig.getPointHistoryMethod(),
 * JSONObject.toJSONString(list)); } catch (IOException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } } pointHistoryStart =
 * currentDate; }
 * 
 * // 点位 5分钟数据 // @Scheduled(cron = "${pointHistory5m.cron}") public void
 * pointHistory5m() { Date currentDate = new Date(); List<MacHistory5m> list =
 * macHistory5mService.list(DateUtil.format_yyyyMMddHHmm(pointHistory5mStart),
 * DateUtil.format_yyyyMMddHHmm(currentDate)); if (!list.isEmpty()) {
 * OkHttpRequest okHttpRequest = new OkHttpRequest(); try {
 * okHttpRequest.post("http://" + transpondConfig.getIp() + ":" +
 * transpondConfig.getPort() + transpondConfig.getPointHistory5mMethod(),
 * JSONObject.toJSONString(list)); } catch (IOException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } } pointHistory5mStart =
 * currentDate; }
 * 
 * // 点位 1小时数据 // @Scheduled(cron = "${pointHistory1h.cron}") public void
 * pointHistory1h() { Date currentDate = new Date(); List<MacHistory1h> list =
 * macHistory1hService.list(DateUtil.format_yyyyMMddHHmm(pointHistory1hStart),
 * DateUtil.format_yyyyMMddHHmm(currentDate)); if (!list.isEmpty()) {
 * OkHttpRequest okHttpRequest = new OkHttpRequest(); try {
 * okHttpRequest.post("http://" + transpondConfig.getIp() + ":" +
 * transpondConfig.getPort() + transpondConfig.getPointHistory1hMethod(),
 * JSONObject.toJSONString(list)); } catch (IOException e) { // TODO
 * Auto-generated catch block e.printStackTrace(); } } pointHistory1hStart =
 * currentDate; }
 * 
 * }
 */
