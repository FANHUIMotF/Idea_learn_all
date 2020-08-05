# 定时任务表
drop table crontab.crontask;
create table crontab.crontask
(
	ClassName varchar(100) not null comment '任务执行类简称（spring中注入的名称）',
	IsUser char null comment '是否启用；0-不启用，1-启用',
	Version int default 1 null comment '执行版本',
	Corn varchar(80) null comment 'corn表达式',
	LastTime datetime null comment '最近一次执行时间',
	NextTime datetime null comment '下一次执行时间',
	Status char not null comment '当前状态；0-等待执行，1-执行中',
	Result char(1) not null comment '执行结果.0:失败，1：成功',
	ResultDesc varchar(256) null comment '结果描述.',
	Remark varchar(256) null comment '备注',
	primary key (ClassName)
) engine = innodb DEFAULT CHARSET=utf8;

