drop table if exists demo_user;
create table demo_user (
  user_id varchar(32) not null primary key ,
  user_name varchar (16) default '',
  user_code varchar (32) default '',
  user_password varchar (32) not null ,
  user_email varchar (32) default '',
  nick_name varchar (16) default '',
  user_img varchar (32) default '',
  create_date datetime(3) default null ,
  update_date datetime(3) default null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists demo_user_role;
create table demo_user_role(
  role_id varchar(32) not null primary key ,
  user_id varchar(32) not null comment '用户id',
  role_name varchar (16) not null comment '角色名称',
  role_coe varchar (16) not null comment '角色编码',
  start_date datetime not null comment '开始时间',
  end_date datetime not null comment '结束日期'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;