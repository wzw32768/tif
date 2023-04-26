## tiff提取图片工具类
提取tiff图片中的第一张图片，解决在多线程环境下写入冲突问题
### Maven
在Release中下载jar

通过命令
`mvn install:install-file -Dfile=jar位置`
例如：`mvn install:install-file -Dfile=D:\tif-util-0.0.1.jar`

安装依赖到你的本地仓库

在项目的pom.xml的dependencies中加入以下内容:
```xml
<dependency>
    <groupId>com.wzw</groupId>
    <artifactId>tif-util</artifactId>
    <version>0.0.1</version>
</dependency>
```
