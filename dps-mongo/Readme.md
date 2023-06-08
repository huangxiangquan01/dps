```shell
docker run -itd --name mongo -v 
/Users/huangxq/docker/mongodb/data:/data/db 
-p 27017:27017 mongo --auth

# 5.0+ 使用mongosh
docket exec -it mongo mongosh admin

use admin
db.createUser(
	{
		user:'root',
		pwd :'123456',
		customData:{"desc":"This user is for administrators"},
		roles:[
				{
					role:'userAdminAnyDatabase',db:'admin'
				}
		]
	}
)
```