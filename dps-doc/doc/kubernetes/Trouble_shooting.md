## Trouble *shooting*

####  1 kubeadm命令

1.kubeadm init 启动引导一个 Kubernetes 主节点

2.kubeadm join 启动引导一个 Kubernetes 工作节点并且将其加入到集群

3.kubeadm upgrade 更新 Kubernetes 集群到新版本 

4.kubeadm config 如果你使用 kubeadm v1.7.x 或者更低版本，你需要对你的集群做一些配置以便使用 kubeadmupgrade 命令

5.kubeadm token 使用 kubeadm join 来管理令牌 kubeadm reset 还原之前使用 kubeadm init 或者 kubeadm join 对节点所作改变 

6.kubeadm version 打印出 kubeadm 版本

7.kubeadm alpha 预览一组可用的新功能以便从社区搜集反馈


#### 2. Coredns

在每个节点创建文件/run/flannel/subnet.env写入以下内容。注意每个节点都要加哦，不是主节点

```
FLANNEL_NETWORK=10.244.0.0/16 # 对应kubeadm --pod-network-cidr
FLANNEL_SUBNET=10.244.0.1/24
FLANNEL_MTU=1450
FLANNEL_IPMASQ=true
```

#### 3. Join证书过期如何办？

```
kubeadm token create --print-join-command

然后把master节点的~/.kube/config文件拷贝到当前NODE

node role change
kubectl label nodes k8s-master node-role.kubernetes.io/node=
```

#### 4.nfs
```
https://blog.csdn.net/qq_40722827/article/details/127948651

https://blog.csdn.net/weixin_43313333/article/details/128666197

kubectl patch users admin -p '{"spec":{"password":""}}' --type='merge' && kubectl annotate users admin iam.kubesphere.io/password-encrypted-
```

