
select M.*,
       N.user_id as userId,
	   N.totalInvest
  from (
			select X.user_id,
				   Z.user_name as userName,
				   X.total_money as totalMoney,
				   Y.yield_rate as todayYieldRate
			  from user_day_money X
			  left join fund_income Y on (X.account_day=Y.account_day) 
			  left join user Z on(X.user_id = Z.user_id)
			 where X.user_id=3
			   and X.account_day=(select max(account_day) 
								  from user_day_money
								 where user_id=3 )
	   ) M
left join (
			select user_id,
				   sum(invest_amount* invest_direction) as totalInvest
			  from invest_data 
			 where user_id=3
			 group by user_id
			 
		  ) N on(M.user_id=N.user_id)  	   
				   

 

 
 








--昨天各个用户总金额

select user_id,
       total_money *1.1 as today_money,
       account_day,
	   '2017-01-03' as today 
  from user_day_money
 where account_day=(select max(account_day) 
                      from user_day_money
					 where account_day<'2017-01-03')
					 
					 
select user_id,
       sum(invest_amount*invest_direction) as dayTotalMoney
  from invest_data
 where invest_date='2017-01-03'	  
 group by user_id 
					 

insert into user_day_money(user_id,account_day,total_money,createdby,create_time,last_updatedby,last_update_time) 					 
select X.user_id, 
	   '2017-01-04' as account_day,
       (X.init_money+ IFNULL(Y.today_money,0) + IFNULL(z.dayTotalMoney,0)) as total_money,
	   1 as createdBy,
	   CURRENT_TIMESTAMP() as create_time,
	   1 as last_updatedby,
	   CURRENT_TIMESTAMP() as last_update_time

  from (select user_id, 
               0 as init_money 
          from user
		 where user_type!=0
		) X
left join (select user_id,
			   total_money *1.1 as today_money,
			   account_day,
			   '2017-01-03' as today 
		  from user_day_money
		 where account_day=(select max(account_day) 
							  from user_day_money
							 where account_day<'2017-01-03')
        ) y on(X.user_id=Y.user_id)
left join (select user_id,
				  sum(invest_amount*invest_direction) as dayTotalMoney
			 from invest_data
			where invest_date='2017-01-03'	  
			group by user_id 
           ) z	on(X.user_id=Z.user_id)

	   