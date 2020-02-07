# TermuxWithGradle
由于AIDE中的gradle功能不完整，且bug连连，最致命的就是当涉及到分包操作时，会导致导致崩溃。

因而采用在Termux中使用gradle官方版本进行构建是一条曲线救国的路线。

**注意：由于此文章仅侧重于搭建构建APK的环境，所以需要具备一定的Linux基础**
## 安装Arch
我这里用的是[TermuxArch](https://github.com/SDRausty/TermuxArch)，其它途径安装的应该是一样的

`wget https://github.com/SDRausty/TermuxArch/raw/master/setupTermuxArch.bash && bash setupTermuxArch.bash`

*为何选用Arch，首先是因为它可以访问到  /mnt/sdcard/，这样就解决了AIDE无法访问到/data/data/com.termux/的问题，这也意味着可以用AIDE开发，Termux直接到项目路径进行编译，更为方便*

## 安装Gradle
首先安装[sdkman](https://sdkman.io/install)这玩意是真的好用，使用sdkman安装gradle6.1.1

`sdkman install gradle 6.1.1`

**sdkman的详细使用方法参见[官网](https://sdkman.io/)，当然，如果你不想用sdkman，也可以直接从Gradle官网下载安装，切记不要使用sdkman安装jdk，装不上的，jdk只有手动安装**
## 安装JDK
本仓库里有一分jdk8_arm65版本的，直接用就行
* 下载并解压文件
* 配置环境变量(/etc/profile)
```
export JAVA_HOME=/root/jdk1.8.0_221 //此处为jdk路径
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib/tools.jar:${JRE_HOME}/lib/dt.jar
export PATH=${JAVA_HOME}/bin:${JAVA_HOME}/jre/bin:$PATH
```
***记得source /etc/profile***

## 安装Android SDK
* 创建一个android_sdk目录并进入到它
 
`mkdir android_sdk && cd android sdk`

* 安装[Android SDK Tools](https://developer.android.com/studio/)

```
wget https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip && unzip sdk-tools-linux-4333796.zip 
```
(去[官网](https://developer.android.com/studio/)复制下来链接，上述示例可能不是最新版)

* 配置环境变量，在/etc/profile添加如下内容

```
export ANDROID_HOME=/root/android_sdk //此处写android_sdk的路径
export PATH=$ANDROID_HOME/tools:$PATH
export PATH=$ANDROID_HOME/tools/bin:$PATH
export PATH=$ANDROID_HOME/platform-tools:$PATH
```
***记得source一下***

* 使用[sdkmanager](https://developer.android.com/studio/command-line/sdkmanager)安装build-tools，platform-tools和platforms
```
sdkmanager "platform-tools" "build-tools;29.0.3" "platforms;android-29"
```
***sdkmanager的详细使用方法参见[官网](https://developer.android.com/studio/command-line/sdkmanager)***

### 