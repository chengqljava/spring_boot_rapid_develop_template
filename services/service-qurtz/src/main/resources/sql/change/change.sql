/**
*2019-06-21   SQL语句
*/
#JOB_LOG增加ip_host_name字段
alert table job_log add `ip_host_name` varchar(255)   comment 'ip 用户名称';  
