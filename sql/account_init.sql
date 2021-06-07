CREATE DEFINER=`root`@`%` PROCEDURE `accountInit`()
begin
  insert into t_account(username,phone,createTime) values('test1','168',now());
  insert into t_account(username,phone,createTime) values('test2','168',now());
  insert into t_account(username,phone,createTime) values('test3','168',now());
end