# Git多账号配置

## 配置多账号
### 1. 清除全局配置
首先，清空全局配置对用户信息，然后可以通过git config --global --list查看全局配置信息，是否清空成功。
```shell
git config --global --unset user.name
git config --global --unset user.email
```
### 2. 生成密钥对
默认情况下，密钥对保存路径为~/.ssh，根据自己的情况，判断选择是否全部清空密钥对（id_rsa、id_rsa.pub 这些公钥和密钥文件），我这里是懒得更改旧的GItHub配置（用的是默认命名的文件），所以没删除，生成公司GitHub使用的密钥对：
```shell
ssh-keygen -t rsa -C "jack.wen@xxx-email.com"
```
如果熟悉生成密钥对的知道执行该命令后需要进行3次或4次确认：
- 重点这一步：确认秘钥的保存路径，这里我们不采用默认命名id_rsa，增加一些后缀以便区分，例如改成id_rsa_gitlab：
```shell
Enter file in which to save the key (/Users/wenwl/.ssh/id_rsa):/Users/wenwl/.ssh/id_rsa_gitlab
```
- 如果上一步保存路径下已经有秘钥文件，则需要确认是否覆盖（如果之前的秘钥不再需要则直接回车覆盖，如需要则手动拷贝到其他目录后再覆盖）

- 创建密码（如果不需要密码则直接回车）

- 确认密码
  如果你有多个仓库，重复上面的步骤即可，重点就是更改秘钥名称！，例如github的可以改成id_rsa_github。
### 3. 配置公钥到SSH Keys 中
这里默认认为你懂得如何配置，只需要上面生成的公配置到对应的仓库中，例如id_rsa_gitlab.pub配置到GitLab，id_rsa_github.pub配置到GitHub。

我这里只重新生成了新公司GitLab要用的密钥对，所以只配置了GitHub 的 SSH Keys。

### 4. 配置本地私钥
配置本地私钥命令参考如下：
```shell
ssh-add ~/.ssh/id_rsa_gitlab
```
ssh-add ~/.ssh/id_rsa_github 如果生成了github的私钥，你再添加即可

可以使用ssh-add -l查看配置的私钥信息。

上面的命令只是私钥添加到本地，我们还需要做一个配置，告诉Git不同的仓库具体使用的是哪个密钥和用户信息：

编辑~/.ssh 目录下的config文件，如果没有，请创建：
```shell
vi ~/.ssh/config
# p配置参考如下
Host github
HostName github.com
User wenwl
IdentityFile ~/.ssh/id_rsa

Host xxx-gitlab # 仓库别名，随意取, 同时可以替代 HostName 来使用的别名
HostName git.xxx.com # 仓库的域名或者IP
User jack.wen # 用户名
IdentityFile ~/.ssh/id_rsa_gitlab # 私钥的绝对路径
# ......
```

上面注释说到，Host是可以替代 HostName 来使用的别名，例如：
```
# github有个仓库的克隆地址为：
git@github.com:wwllong/blog.git
# 如我上面的config配置，可以等价于
git@github:wwllong/blog.git
```

可以通过ssh -T检测配置的 Host 是否是连通：
```
ssh -T git@github.com
Hi wwllong! You've successfully authenticated, but GitHub does not provide shell access.
ssh -T git@gitlab.xxx.com
Welcome to GitLab, @jack.wen
```
### 5. 仓库配置
完成上面的配置后，我们还需要给本地仓库配置用户名和邮箱，否则可能无法提交本地修改或者提交的用户名变成了系统主机名。

这是因为Git的配置分为三级：System（系统）、 Global （全局）、Local（仓库级别），优先级是 Local > Global > System。由于一开始清除了全局的用户名，又没有给本地仓库配置用户信息，所以会出现这种情况。

配置也很简单，进入到对应的仓库后目录后，执行以下命令即可，例如，我入职新公司拉下的项目配置的命令
```shell
git config --local user.name "jack.wen"
git config --local user.email "jack.wen@xxx-email.com"
```
可以执行git config --local --list查看本地仓库配置的信息。

> https://www.wenwl.site/pages/dfb4d0/#%E9%85%8D%E7%BD%AE%E5%A4%9A%E8%B4%A6%E5%8F%B7