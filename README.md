# JSPatchServer4J

> `Java` 轻量级 [JSPatch](https://github.com/bang590/JSPatch) 的后台服务：

- 支持内容加密
- 轻量级（war包110KB、无需数据库）
- 建议配合linux服务器加git使用
- 配合git的情况下，测试环境和正式环境分别部署，支持2个环境的内容按git分支隔离

## 部署
- 下载
    + 可以直接从bin目录中下载编译好的war包
    + 也可以下载完整版源码自己编译
- 部署
    + 如果自己有git私服并且打算与git配合使用，需要将JSPatchServer4J部署在一个git私服能访问到的服务器上（后面需要配置git的钩子）。
    + 最好自己有git私服（后面会配置钩子，每次push自动更新服务）
    + `Jdk1.7` + `Tomcat 7` 没问题，其他版本jdk和WebServer尚未经过测试
    + 修改 WEB-INF/classes 下的 config.properties 
        * 配置js文件的根路径
    + 修改 WEB-INF/classes 下的 keys.properties
        * 针对不同项目配置不同的密钥，密钥长度必须24位（下文中《js文件路径规则》提到的项目名）
- 配置（自己有git私服）
    + 在git私服（我用的是 [gitolite](http://gitolite.com/gitolite/index.html)）上创建一个专门用于存放js文件的版本库。
    + 将上面 config.properties 配置的目录 关联到此私服（使其直接git pull的就能获取到最新的js内容 **关键**）
    + 添加钩子
        * 修改 hooks/post-update 文件（没有的话新建一个）内容如下：
```bash
branch=$(git rev-parse --symbolic --abbrev-ref $1)
if [ $branch == "dev" ]; then
  curl http://JSPatchServer4J测试环境路径/update.do > /dev/null 2>&1
elif [ $branch == "master" ]; then
  curl http://JSPatchServer4J正式环境路径/update.do > /dev/null 2>&1   
else
  echo "no branch on server need update"
fi
```

- 配置（自己没有git私服）
    + 自己上传js文件到上面 config.properties 配置的目录中。

## js文件路径规则（建议）
- 项目A（英文名）
    + 1.0.0.js
    + 1.0.1.js
    + 1.0.2.js
- 项目B（英文名）
    + 2.0.0.js
    + 2.0.1.js
    + 2.0.2.js


> [配套Objective-C版源码传送门](https://github.com/shaozepeng/JSPatch-IOS) （包含解密规则）

