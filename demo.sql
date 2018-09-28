drop table if exists demo_user;
create table demo_user (
  user_id varchar(32) not null primary key ,
  user_name varchar (16) default '',
  user_code varchar (32) default '',
  user_password varchar (32) not null ,
  user_email varchar (32) default '',
  nick_name varchar (16) default '',
  user_img varchar (64) default '',
  create_date datetime(3) default null ,
  update_date datetime(3) default null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
drop table if exists demo_role;
create table demo_role(
  role_id varchar(32) primary key,
  role_code varchar (32) not null ,
  role_name varchar (16) not null comment '角色名称',
  start_date datetime not null comment '开始时间',
  end_date datetime not null comment '结束日期',
  valid tinyint(1) default 1 comment '是否有效，1有效，0无效'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '角色信息表';
drop table if exists demo_user_role;
create table demo_user_role(
  user_role_id varchar(32) primary key,
  role_id varchar(32) not null  ,
  user_id varchar(32) not null comment '用户id',
  role_name varchar (16) not null comment '角色名称',
  role_code varchar (16) not null comment '角色编码',
  start_date datetime not null comment '开始时间',
  end_date datetime not null comment '结束日期',
  valid tinyint(1) default 1 comment '是否有效，1有效，0无效'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '用户角色信息表';

drop table  if exists demo_menu;
create table demo_menu(
  menu_id varchar (32) primary  key ,
  menu_code varchar (64) not  null comment '菜单编码',
  menu_name varchar (32) not null comment '菜单名称',
  parent_menu_id varchar(32) not null comment '父菜单Id',
  menu_addr varchar (64) not null  comment '菜单地址',
  create_date datetime(3) not  null ,
  create_code varchar(32) not null,
  valid tinyint(1) default 1 comment '是否有效，1有效，0无效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '菜单信息表';

drop table if exists demo_role_menu;
create table demo_role_menu(
  role_menu_id varchar (32) primary key,
  role_id varchar (32) not null ,
  role_code varchar (32) not null ,
  menu_id varchar (32) not null ,
  menu_addr varchar (64) not null ,
  valid tinyint(1) default 1 comment '1 有效，0无效'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '菜单角色对应表';

drop table if exists demo_product;
create table demo_product(
  product_id varchar (32) primary key,
  product_code varchar (16) not null default '' comment '产品编号',
  product_desc varchar(64) default '' comment '描述',
  create_date datetime default null,
  create_by varchar(16) default '',
  last_update_date datetime default null,
  last_update_by varchar(16),
  product_status tinyint(1) default 1 comment '是否有效；1有效；0无效'

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists demo_product_items;
create table demo_product_items(
	item_id varchar(32) primary key,
	item_code varchar(16) not null,
	product_id varchar(32) not null comment 'demo_product主键',
	item_price int(11) default 0 comment '金额，分',
	item_desc varchar(64) default '' comment '单品描述',
	create_date datetime default null,
	create_by varchar(16) default '',
	last_update_date datetime default null,
	last_update_by varchar(16),
	item_status tinyint(1) default 1 comment '是否有效；1有效；0无效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists demo_attachment_info;
create table demo_attachment_info(
	attactment_id int(11) primary key auto_increment,
	attactment_url varchar(64) default '' comment '地址',
	attactment_size int(11) default 0 comment '文件大小',
	attactment_belong_to varchar(16) not null comment '附件归属表',
	attactment_belong_to_id varchar(32) not null comment '附件归属表主键',
	attactment_type varchar(8) default 'png' comment '文件类型',
	create_date datetime default null,
	create_by varchar(16) default '',
	last_update_date datetime default null,
	last_update_by varchar(16),
	attachment_status tinyint(1) default 1 comment '是否有效；1有效；0无效'
)ENGINE=InnoDB comment '附件信息' DEFAULT CHARSET=utf8;

drop table if exists demo_code;
create table demo_code(
  id int(11) primary key auto_increment,
  code_type varchar (32) default null ,
  code_suffix varchar(16) not null comment '编码后缀',
  num_int int(11) default 1 comment '编码',
  table_ref varchar(16) not null comment '属于哪个表',
  code_prefix varchar(8) not null comment '编码前缀',
  code_middle varchar(32) not null comment '编码中段',
  create_date datetime default null,
	create_by varchar(16) default '',
	last_update_date datetime default null,
	last_update_by varchar(16)

)ENGINE=InnoDB comment '编码' DEFAULT CHARSET=utf8;