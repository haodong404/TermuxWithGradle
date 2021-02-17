# TermuxWithGradle
​		由于AIDE中的gradle功能不完整，且bug连连，最致命的就是当涉及到分包操作时，会导致导致崩溃。因而采用在Termux中使用gradle官方版本进行构建是一条曲线救国的路线。

​		**注意：由于此文章仅侧重于搭建构建APK的环境，所以需要具备一定的Linux基础；由于许多文件都下载的官方版本，所以需要科学上网。**

### 一、安装Arch

​	我这里用的是[TermuxArch](https://github.com/SDRausty/TermuxArch)，其它途径安装的应该是一样的。`wget https://raw.githubusercontent.com/SDRausty/TermuxArch/master/setupTermuxArch.bash && bash 	setupTermuxArch.bash`

*    为何选用Arch，首先是因为它可以访问到  /mnt/sdcard/，这样就解决了AIDE无法访问到/data/data/com.termux/的问题，这也意味着可以用AIDE开发，Termux直接到项目路径进行编译，更为方便
 * 其实debian/ubuntu，centos也能够安装，但都有一定的缺陷:
    1. debian系发行版:  无法访问到/mnt/sdcard/;需自行编译安装glibc
    2. centos: 没有glibc，只能自行编译安装
    3. 上述几种版本安装与arch稍有差别，在结尾处会有介绍

### 二、开始配置构建环境
1. #### **安装Gradle**

   首先安装[sdkman](https://sdkman.io/install)，这玩意儿是真的好用，使用sdkman安装gradle6.1.1。

   `sdk install gradle 6.1.1`

   **sdkman的详细使用方法参见[官网](https://sdkman.io/)，当然，如果你不想用sdkman，也可以直接从Gradle官网下载安装，切记不要使用sdkman安装jdk，装不上的，jdk只能手动安装**。

2. #### **安装JDK**

   ​	本仓库里有一份jdk8_arm版本的，可直接使用。

   * **下载并解压文件**

     `wget https://github.com/zacharyching/TermuxWithGradle/raw/master/jdk8_arm64/jdk-8u221-linux-arm64-vfp-hflt.tar.gz && tar xzvf jdk-8u221-linux-arm64-vfp-hflt.tar.gz`

   * **配置环境变量(/etc/profile)**

     ```shell
     export JAVA_HOME=/root/jdk1.8.0_221 #此处为jdk路径
     export JRE_HOME=${JAVA_HOME}/jre
     export CLASSPATH=.:${JAVA_HOME}/lib/tools.jar:${JRE_HOME}/lib/dt.jar
     export PATH=${JAVA_HOME}/bin:${JAVA_HOME}/jre/bin:$PATH
     ```

     **记得** `source /etc/profile`

3. #### **安装Android SDK**

   - **创建一个android_sdk目录并进入到它**

     `mkdir android_sdk && cd android_sdk`

   - **安装[Android SDK Tools](https://developer.android.com/studio/)**

     `wget https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip && unzip sdk-tools-linux-4333796.zip` 

     （最好自行去[官网](https://developer.android.com/studio/)复制下来链接，上述示例可能不是最新版，但最新版SDK的使用方法可能有所改变。）

   - **使用[sdkmanager](https://developer.android.com/studio/command-line/sdkmanager)安装build-tools，platform-tools和platforms**

     `sdkmanager "platform-tools" "build-tools;29.0.3" "platforms;android-29"`

     **记得** `source` **一下**

4. #### **适配Termux**

   - 我这里编写了个shell脚本，更方便配置，目前仅支持arch系统，arm64架构。

     `wget https://raw.githubusercontent.com/zacharyching/TermuxWithGradle/master/adaptTermuxARM64.sh && chmod 777 adaptTermuxARM64.sh && ./adaptTermuxARM64.sh`\

   - 自行将本仓库中对应架构的aapt,aapt2覆盖到android_sdk/build-tools/***version***/下（注意对aapt,aapt2授予执行权限!!!很重要!!!!）(当然，如果你cpu的架构与原来的文件相符就不用替换)

5. #### **安装glibc**

   ​	`pacman -S glibc`

### 三、注意事项
- `classpath 'com.android.tools.build:gradle:3.0.0'`中的版本号为Android Gradle插件的版本，而非Gradle的版本，[官方](https://developer.android.com/studio/releases/gradle-plugin?hl=zh-cn)有一个两者的对应关系。两者版本一定要对应上，否则会报错。
- 目前`com.android.tools.build:gradle`的版本最好低于`3.1.0`，因为在`3.1.0`之上启用aapt2打包资源之后会出问题。
- 每一个版本的build-tools中的aapt都需替换为对应cpu架构的版本。

### 四、部分Linux发行版安装gradle的区别
- #### Debian系

  不用执行`adaptTermuxARM64.sh`脚本，取而代之的是
  `apt-get install aapt`

  不执行`pacman -S glibc`，需自行编译安装glibc：方法自行Google或百度

- #### CentOS

  不执行`pacman -S glibc`，需自行编译安装glibc：方法自行Google或百度

  **手动编译安装glibc大约要用3小时**

## 五、测试
​	仓库中有个exampleApp可供测试，我已配置完整
