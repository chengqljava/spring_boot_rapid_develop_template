/*
Navicat Oracle Data Transfer
Oracle Client Version : 12.1.0.2.0

Source Server         : 10.0.13.7_HSSE
Source Server Version : 110200
Source Host           : 10.0.13.7:1521
Source Schema         : DOUBLESAFE

Target Server Type    : ORACLE
Target Server Version : 110200
File Encoding         : 65001

Date: 2019-09-16 11:33:46
*/


-- ----------------------------
-- Table structure for JOB
-- ----------------------------
DROP TABLE JOB;
CREATE TABLE JOB (
ID NVARCHAR2(32) NOT NULL ,
BEAN_NAME NVARCHAR2(200) NULL ,
METHOD_NAME NVARCHAR2(100) NULL ,
PARAMS NCLOB NULL ,
CRON_EXPRESSION NVARCHAR2(100) NULL ,
STATUS NUMBER(4) NULL ,
REMARK NVARCHAR2(255) NULL ,
CREATE_TIME DATE NULL ,
APPLIACTION_NAME VARCHAR2(255 BYTE) NULL ,
JOB_START_TIME DATE NULL ,
JOB_END_TIME DATE NULL ,
JOB_PLAN_KEY VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE JOB IS '定时任务';
COMMENT ON COLUMN JOB.ID IS '任务id';
COMMENT ON COLUMN JOB.BEAN_NAME IS 'spring bean名称';
COMMENT ON COLUMN JOB.METHOD_NAME IS '方法名';
COMMENT ON COLUMN JOB.PARAMS IS '参数';
COMMENT ON COLUMN JOB.CRON_EXPRESSION IS 'cron表达式';
COMMENT ON COLUMN JOB.STATUS IS '任务状态  0：正常  1：暂停';
COMMENT ON COLUMN JOB.REMARK IS '备注';
COMMENT ON COLUMN JOB.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN JOB.APPLIACTION_NAME IS '项目名称';
COMMENT ON COLUMN JOB.JOB_START_TIME IS '定时工作开始时间';
COMMENT ON COLUMN JOB.JOB_END_TIME IS '定时任务结束时间';
COMMENT ON COLUMN JOB.JOB_PLAN_KEY IS '每类计划的名称';

-- ----------------------------
-- Indexes structure for table JOB
-- ----------------------------

-- ----------------------------
-- Checks structure for table JOB
-- ----------------------------
ALTER TABLE JOB ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table JOB
-- ----------------------------
ALTER TABLE JOB ADD PRIMARY KEY (ID);
