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

Date: 2019-09-16 11:35:53
*/


-- ----------------------------
-- Table structure for JOB_LOG
-- ----------------------------
DROP TABLE JOB_LOG;
CREATE TABLE JOB_LOG (
ID NVARCHAR2(32) NOT NULL ,
JOB_ID NVARCHAR2(32) NOT NULL ,
BEAN_NAME NVARCHAR2(200) NULL ,
METHOD_NAME NVARCHAR2(100) NULL ,
PARAMS NCLOB NULL ,
STATUS NUMBER(4) NOT NULL ,
ERROR NCLOB NULL ,
TIMES NUMBER(11) NOT NULL ,
CREATE_TIME DATE NULL ,
APPLIACTION_NAME VARCHAR2(255 BYTE) NULL ,
IP_HOST_NAME VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE JOB_LOG IS '定时任务日志';
COMMENT ON COLUMN JOB_LOG.ID IS '任务日志id';
COMMENT ON COLUMN JOB_LOG.JOB_ID IS '任务id';
COMMENT ON COLUMN JOB_LOG.BEAN_NAME IS 'spring bean名称';
COMMENT ON COLUMN JOB_LOG.METHOD_NAME IS '方法名';
COMMENT ON COLUMN JOB_LOG.PARAMS IS '参数';
COMMENT ON COLUMN JOB_LOG.STATUS IS '任务状态    0：成功    1：失败';
COMMENT ON COLUMN JOB_LOG.ERROR IS '失败信息';
COMMENT ON COLUMN JOB_LOG.TIMES IS '耗时(单位：毫秒)';
COMMENT ON COLUMN JOB_LOG.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN JOB_LOG.APPLIACTION_NAME IS '项目名称';
COMMENT ON COLUMN JOB_LOG.IP_HOST_NAME IS 'ip地址 主机名称';

-- ----------------------------
-- Indexes structure for table JOB_LOG
-- ----------------------------
CREATE INDEX job_id
ON JOB_LOG (JOB_ID ASC)
LOGGING
VISIBLE;

-- ----------------------------
-- Checks structure for table JOB_LOG
-- ----------------------------
ALTER TABLE JOB_LOG ADD CHECK (ID IS NOT NULL);
ALTER TABLE JOB_LOG ADD CHECK (JOB_ID IS NOT NULL);
ALTER TABLE JOB_LOG ADD CHECK (STATUS IS NOT NULL);
ALTER TABLE JOB_LOG ADD CHECK (TIMES IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table JOB_LOG
-- ----------------------------
ALTER TABLE JOB_LOG ADD PRIMARY KEY (ID);
