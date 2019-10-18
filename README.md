# 阿里springcloud及部分组件

#### 项目简介
1.主要技术栈：springcloud Alibaba+nacos+dubbo+gateway+ribbon。
2.注册中心是用nacos，下载地址：https://github.com/alibaba/nacos/releases
3.nacos动态刷新配置用了两种方式：
    [api-service]用的是nacos-config-spring-boot-starter的实现，可以动态指定nacos配置界面中的data-id(可以随意取data-id的名
        字，只要启动类中的@NacosPropertySource中的dataId保持一致即可)。应用启动时候不会暴露是否加载了nacos中的配置，且nacos
        中的配置更新的时候，当前应用中不会打印日志。
    [api-service2]用的是spring-cloud-starter-alibaba-nacos-config，定义的data-id必须更应用名保持相同(必须指定扩展名，默认
        是properties，如果要修改的话需要在配置文件中指定扩展名)，且定义的data-id的扩展名必须与其配置格式保持一致(例如创建
        的data-id是user-api-service.yaml，那么其详情中的配置格式必须选择yaml)。应用启动时日志中会暴露是否加载了nacos中的
        配置，且nacos中的配置更新的时候，当前应用中会打印刷新日志。
    这两种方式引入的包是不同的，且不能同时引入，目前看是会冲突的。个人推荐使用第二种。
4.gateway根据nacos动态刷新路由已完成，代码中已实现。
    ● 配置demo：
    data-id:api-gateway-service.json
    group:DEFAULT_GROUP    (默认的)
    配置内容：[{
       	"id": "router1",
       	"uri": "http://127.0.0.1:8081",
       	"order": 0,
       	"filters": [],
       	"predicates": [{
       		"args": {
       			"pattern": "/**"
       		},
       		"name": "Path"
       	}]
       }]
    ● 该配置的意思是访问http://localhost:8084/所有请求都会被转发到127.0.0.1:8081上(api-service)上，亲测可用，示例地址：
    http://localhost:8084/login/login?userName=test&password=1234，而且该配置不会影响gateway中已经配置在bootstrap中(还是
    会负载均衡到8081和8082上)。
    ● 目前没有实现配置demo中的负载均衡。
    
#### 项目结构
aliCloudTest  
├── api-gateway      -- 网关  
├── api-service      -- 对外开放api接口  
├── api-service2     -- 对外开放api接口(模拟集群环境)  
├── user-center      -- 用户详细信息服务  
├── user-login       -- 用户登录服务  
└── user-login2      -- 用户登录服务(模拟集群环境)  

#### 安装

1.  nacos安装需要依赖jdk环境，且java环境变量必须配置%JAVA_HOME%变量，负责nacos无法启动(如果配置了仍无法启动，重启再试)。
2.  如果启动过程报错，清理下maven包环境。
3.  数据库推荐是用mysq5.7以上版本。

#### SQL导入语句

1.  用户详细信息表结构及mock数据：  
    SET NAMES utf8mb4;
    SET FOREIGN_KEY_CHECKS = 0;
    
    -- ----------------------------
    -- Table structure for user_info
    -- ----------------------------
    DROP TABLE IF EXISTS `user_info`;
    CREATE TABLE `user_info`  (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
      `true_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '真实姓名',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Records of user_info
    -- ----------------------------
    INSERT INTO `user_info` VALUES (1, 1, '甘道夫');
    
    SET FOREIGN_KEY_CHECKS = 1;

2.  用户登录用户名记密码表结构及mock数据：   
    SET NAMES utf8mb4;
    SET FOREIGN_KEY_CHECKS = 0;
    
    -- ----------------------------
    -- Table structure for user_login
    -- ----------------------------
    DROP TABLE IF EXISTS `user_login`;
    CREATE TABLE `user_login`  (
      `id` bigint(20) NOT NULL AUTO_INCREMENT,
      `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录用户名',
      `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录密码(暂不做MD5加密)',
      PRIMARY KEY (`id`) USING BTREE
    ) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
    
    -- ----------------------------
    -- Records of user_login
    -- ----------------------------
    INSERT INTO `user_login` VALUES (1, 'test', '1234');
    
    SET FOREIGN_KEY_CHECKS = 1;
