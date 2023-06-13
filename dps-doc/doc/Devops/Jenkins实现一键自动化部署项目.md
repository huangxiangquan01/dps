# 一、安装Jenkins

### 1.安装Jenkins
```shell
docker run --name jenkins -u root --rm -d -p 8070:8080 -p 50000:50000 \
-v /Users/huangxq/docker/jenkins/jenkins_home:/var/jenkins_home \
-v /var/run/docker.sock:/var/run/docker.sock jenkins/jenkins

# 使用socat镜像开启服务
docker run -d --name socat --restart always -p 2375:2375 -v /var/run/docker.sock:/var/run/docker.sock alpine/socat tcp-listen:2375,fork,reuseaddr unix-connect:/var/run/docker.sock

# 配置
vi ~/.bash_profile
export DOCKER_HOST=tcp://localhost:2375

# 重启生效
source .bash_profile
```
### 2.初始化Jenkins
#### 2.1 解锁Jenkins
- 进入Jenkins容器：docker exec -it {Jenkins容器名} bash
- 例如 docker exec -it jenkins bash
- /var/jenkins_home/secrets/initialAdminPassword
- 复制密码到输入框里面
#### 2.2 安装插件
选择第一个：安装推荐的插件
#### 2.3 创建管理员用户

# 二、系统配置
### 1. 安装需要插件
进入【首页】–【系统管理】–【插件管理】–【可选插件】

搜索以下需要安装的插件，点击安装即可。

- 安装Maven Integration
- 安装Publish Over SSH(如果不需要远程推送，不用安装)
- 如果使用Gitee 码云，安装插件Gitee（Git自带不用安装）
### 2. 配置Maven
进入【首页】–【系统管理】–【全局配置】，拉到最下面maven–maven安装

# 三、创建任务
### 1. 新建任务
点击【新建任务】，输入任务名称，点击构建一个自由风格的软件项目
### 2. 源码管理
点击【源码管理】–【Git】，输入仓库地址，添加凭证，选择好凭证即可。
### 3.构建触发器
点击【构建触发器】–【构建】–【增加构建步骤】–【调用顶层Maven目标】–【填写配置】–【保存】

此处命令只是install，看是否能生成jar包
```shell
clean install -Dmaven.test.skip=true
```
### 4. 保存
点击【保存】按钮即可
# 四、测试
> 该功能测试是否能正常打包
### 1. 构建

点击构建按钮

### 2.查看日志
点击正在构建的任务，或者点击任务名称，进入详情页面，查看控制台输出，看是否能成功打成jar包。

该处日志第一次可能下载依赖jar包失败，再次点击构建即可成功。

### 3. 查看项目位置
1. cd /var/jenkins_home/workspace
2. ll 即可查看是否存在
# 五、运行项目
因为我们项目和jenkins在同一台服务器，所以我们用shell脚本运行项目，原理既是通过dockerfile 打包镜像，然后docker运行即可
### 1. Dockerfile
```dockerfile

```
### 2. 修改jenkins任务配置
```shell
cd /var/jenkins_home/workspace/zx-order-api
docker stop zx-order || true
docker rm zx-order || true
docker rmi zx-order || true
docker build -t zx-order .
docker run -d -p 8888:8888 --name zx-order zx-order:latest
```
### 3. 保存
点击保存即可
### 4. 构建

查看jenkins控制台输出，输出如下，证明成功！
### 5. 验证
docker ps 查看是否有自己的容器
docker logs 自己的容器名 查看日志是否正确
