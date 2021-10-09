drop database fund_one;

create database fund_one CHARACTER SET 'utf8'  COLLATE 'utf8_general_ci'; 
use fund_one;
create table User(
	user_id INT NOT NULL AUTO_INCREMENT ,   /* 用户ID */
	user_name VARCHAR(100) not null,        /* 用户名称  */  
	id_card_no VARCHAR(18) not null,        /* 身份证号码 */
	password VARCHAR(100) not null,         /* 密码 */
	phone VARCHAR(20) not null,             /* 电话号码 */
	email VARCHAR(50) ,                     /* 邮箱 */
	user_type TINYINT default 1,            /* 用户类型，0：管理员,1:投资者  */ 
    createdby INT,
    create_time DATETIME,	
	last_updatedby INT,
    last_update_time DATETIME,
	PRIMARY KEY(user_id)	
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 


create table User_Day_Money(                   
	user_id INT NOT NULL ,                  /* 用户ID */
	account_day DATE NOT NULL ,             /* 结算日 */
	total_money FLOAT(18,3) NOT NULL,       /* 总资金 */
	createdby INT,                             
    create_time DATETIME,	
	last_updatedby INT,
    last_update_time DATETIME,
	PRIMARY KEY(user_id,account_day)	
)ENGINE=InnoDB DEFAULT CHARSET=utf8; 

create table Invest_Data(
	invest_id INT NOT NULL AUTO_INCREMENT , /* 投资ID */
	user_id INT NOT NULL  ,                 /* 用户ID */
	invest_date date not null,              /* 投资日期 */
	invest_amount FLOAT(18,3) NOT NULL,     /* 投资金额 */
	invest_direction int not null ,         /* 投资方向 1:申购,-1:赎回  */
	createdby INT,                             
    create_time DATETIME,	
	last_updatedby INT,
    last_update_time DATETIME,	
   	PRIMARY KEY(invest_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8; 

create table fund_info(
    fund_id int not null AUTO_INCREMENT,    /* 基金ID */
	fund_name VARCHAR(100) not null,        /* 基金名称 */
	abbr_name  VARCHAR(50) ,                /* 基金简称*/
	manager VARCHAR(100) ,                  /* 基金经理 */
	recommend VARCHAR(255) ,                /* 基金介绍 */
	createdby INT,                              
    create_time DATETIME,	
	last_updatedby INT,
    last_update_time DATETIME,	
   	PRIMARY KEY(fund_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8; 

create table fund_income(
	fund_id int not null ,                 /* 基金Id */
	account_day date not null,             /* 结算日期 */  
	yield_rate DOUBLE(15,10),              /* 当日收益率 */
	today_book_value DOUBLE(20,2),         /* 当日账面净值 */
	createdby INT,                             
    create_time DATETIME,	
	last_updatedby INT,
    last_update_time DATETIME,	
	PRIMARY KEY(fund_id,account_day)
)ENGINE=InnoDB DEFAULT CHARSET=utf8; 

create table system_log(
	log_id BIGINT not null AUTO_INCREMENT,  /*日志ID */
	user_id int,                            /*操作用户 */ 
	log_time datetime,                      /*操作时间 */
	content varchar(255),                    /*日志内容 */
	PRIMARY KEY(log_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8; 

/*初始化数据*/
insert into user(
	user_name,id_card_no,password,phone,email,user_type,createdby,create_time,last_updatedby,last_update_time
 )values(
	"Admin",
	"123456789012345678",
	"123456",
	"13612345678",
	"abc@qq.com",
	0,
	0,
	CURRENT_TIMESTAMP(),
	0,
	CURRENT_TIMESTAMP()
);

insert into user(
	user_name,id_card_no,password,phone,email,user_type,createdby,create_time,last_updatedby,last_update_time
 )values(
	"No.2",
	"123456789012345601",
	"123456",
	"13612345678",
	"abc@qq.com",
	1,
	0,
	CURRENT_TIMESTAMP(),
	0,
	CURRENT_TIMESTAMP()
);

insert into user(
	user_name,id_card_no,password,phone,email,user_type,createdby,create_time,last_updatedby,last_update_time
 )values(
	"No.3",
	"123456789012345602",
	"123456",
	"13612345678",
	"abc@qq.com",
	1,
	0,
	CURRENT_TIMESTAMP(),
	0,
	CURRENT_TIMESTAMP()
);

insert into user(
	user_name,id_card_no,password,phone,email,user_type,createdby,create_time,last_updatedby,last_update_time
 )values(
	"No.4",
	"123456789012345602",
	"123456",
	"13612345678",
	"abc@qq.com",
	1,
	0,
	CURRENT_TIMESTAMP(),
	0,
	CURRENT_TIMESTAMP()
);

insert into user(
	user_name,id_card_no,password,phone,email,user_type,createdby,create_time,last_updatedby,last_update_time
 )values(
	"No.5",
	"123456789012345602",
	"123456",
	"13612345678",
	"abc@qq.com",
	1,
	0,
	CURRENT_TIMESTAMP(),
	0,
	CURRENT_TIMESTAMP()
);


/*模拟2号用户，每天（3天）都有数据*/
insert into 
  user_day_money(user_id,account_day,total_money) 
  values(2,'2017-01-11',10000);
insert into 
  user_day_money(user_id,account_day,total_money) 
  values(2,'2017-01-12',20000);
insert into 
  user_day_money(user_id,account_day,total_money) 
  values(2,'2017-01-13',30000);

/*模拟资金总额*/  
/*模拟3号用户，有两天数据*/  
insert into 
  user_day_money(user_id,account_day,total_money) 
  values(3,'2017-01-12',20000);
insert into 
  user_day_money(user_id,account_day,total_money) 
  values(3,'2017-01-13',30000);

/*模拟4号用户，有1天数据*/    
insert into 
  user_day_money(user_id,account_day,total_money) 
  values(4,'2017-01-13',10000);
  
 /*模拟5号用户，有1天数据*/    
 
 /*模拟投资情况*/
 insert into 
  invest_data(	user_id,invest_date,invest_amount ,	invest_direction )
  values(2,'2017-01-01',1000,1);
 insert into 
  invest_data(	user_id,invest_date,invest_amount ,	invest_direction )
  values(2,'2017-01-02',2000,1);  
 insert into 
  invest_data(	user_id,invest_date,invest_amount ,	invest_direction )
  values(2,'2017-01-02',1000,-1);  
 insert into 
  invest_data(	user_id,invest_date,invest_amount ,	invest_direction )
  values(2,'2017-01-03',2000,1);
 insert into 
  invest_data(	user_id,invest_date,invest_amount ,	invest_direction )
  values(2,'2017-01-03',3000,-1);  
  
 insert into 
  invest_data(	user_id,invest_date,invest_amount ,	invest_direction )
  values(3,'2017-01-12',1000,1);
 insert into 
  invest_data(	user_id,invest_date,invest_amount ,	invest_direction )
  values(3,'2017-01-13',1000,1);    
  
  insert into 
  invest_data(user_id,invest_date,invest_amount ,	invest_direction )
  values(4,'2017-01-12',1000,1);    
  insert into  
  invest_data(user_id,invest_date,invest_amount ,	invest_direction )
  values(5,'2017-01-13',1000,1); 
 
   
  



insert into fund_info(
	fund_name,
	abbr_name,
	manager,
	recommend,
	createdby,                          
    create_time,
	last_updatedby,
    last_update_time 
)values(
	'Fund_No1',
	'&#x57FA;&#x91D1;&#x58F9;&#x53F7;',
	'John',
	'asfdfddfssfsf',
	1,
	CURRENT_TIMESTAMP(),
	1,
	CURRENT_TIMESTAMP()
);  
  
 insert into fund_income(
    fund_id,
	account_day,  
	yield_rate,
	createdby,                            
    create_time ,
	last_updatedby,
    last_update_time)
	values(
	    1,
		'2017-01-01', 
		1.1,
		1,
		CURRENT_TIMESTAMP(),
		1,
		CURRENT_TIMESTAMP()
	);

 insert into fund_income(
    fund_id,
	account_day,  
	yield_rate,
	createdby,                            
    create_time ,
	last_updatedby,
    last_update_time)
	values(
	    1,
		'2017-01-02',
		1.1,
		1,
		CURRENT_TIMESTAMP(),
		1,
		CURRENT_TIMESTAMP()
	);
	
 insert into fund_income(
    fund_id,
	account_day,  
	yield_rate,
	createdby,                            
    create_time ,
	last_updatedby,
    last_update_time)
	values(
	    1,
		'2017-01-03',
		1.1,
		1,
		CURRENT_TIMESTAMP(),
		1,
		CURRENT_TIMESTAMP()
	);	
      