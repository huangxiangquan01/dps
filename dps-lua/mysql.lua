local luasql = require "luasql.mysql"

--创建环境对象
local env= luasql.mysql()
print(env)
--连接数据库
local conn=env:connect("bbsgo_db","root","Hxq@123456_","124.221.90.139", 3306)
print(conn)
--设置数据库的编码格式
conn:execute"SET NAMES UTF-8"

--执行数据库操作
local cur=conn:execute("select * from t_user")

local row=cur:fetch({},"a")

while row do

    var=string.format("%d%s\n",row.id, row.username)

    print(var)

    row=cur:fetch(row,"a")
end

conn:close()--关闭数据库连接
env:close()--关闭数据库环境