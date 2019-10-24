# 阿里springcloud及部分组件
 
#### 项目简介
1.主要技术栈：   springcloud Alibaba+nacos+dubbo+gateway+ribbon+sentinel。  
2.注册中心是用nacos，下载地址：https://github.com/alibaba/nacos/releases  
3.nacos动态刷新配置用了两种方式：  
    **[api-service]** 用的是nacos-config-spring-boot-starter的实现，可以动态指定nacos配置界面中的data-id(可以随意取data-id的名
        字，只要启动类中的@NacosPropertySource中的dataId保持一致即可)。应用启动时候不会暴露是否加载了nacos中的配置，且nacos
        中的配置更新的时候，当前应用中不会打印日志。  
    **[api-service2]** 用的是spring-cloud-starter-alibaba-nacos-config，定义的data-id必须更应用名保持相同(必须指定扩展名，默认
        是properties，如果要修改的话需要在配置文件中指定扩展名)，且定义的data-id的扩展名必须与其配置格式保持一致(例如创建
        的data-id是user-api-service.yaml，那么其详情中的配置格式必须选择yaml)。应用启动时日志中会暴露是否加载了nacos中的
        配置，且nacos中的配置更新的时候，当前应用中会打印刷新日志。  
    这两种方式引入的包是不同的，且不能同时引入，目前看是会冲突的。个人推荐使用第二种。  
4.已集成sentinel，目前可以实现限流功能，代码亲测可用，sentinel版本1.6.3，该版本只提供了jar刚方式，故需要配置jdk环境，如果
    配置好了可以直接使用java -jar启动，默认端口8080(也可以pull release版本的代码自定义端口启动)。
    下载地址：https://github.com/alibaba/Sentinel/releases
4.gateway根据nacos动态刷新路由已完成，代码中已实现。  
* 配置demo(nacos控制台的配置内容)：  
    data-id:api-gateway-service.json  
    group:DEFAULT_GROUP    (默认的)  
    配置内容：  
    `[{
     	"id": "router1",
     	"uri": "lb://user-api-service",
     	"order": 0,
     	"filters": [{
              "args": {
                 "parts": "1"
             },
             "name": "StripPrefix"
         }],
     	"predicates": [{
     		"args": {
     			"pattern": "/test/**"
     		},
     		"name": "Path"
     	}]
     }]`
     uri使用了负载均衡，但是对应的定义"user-api-service"需要提前现在配置文件中声明。
* 该配置的意思是访问http://localhost:8084/所有请求都会被转发到127.0.0.1:8081上(api-service)上，亲测可用，示例地址：
    http://localhost:8084/login/login?userName=test&password=1234，而且该配置不会影响gateway中已经配置在bootstrap中(还是
    会负载均衡到8081和8082上)。  
* 目前没有实现配置demo中的负载均衡。  
    
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

#### 疑问点
1.负载均衡的心跳列表，默认是30s心跳一次，且每次心跳后会在下一秒再心跳一次，为了防止网络不好的情况下导致的丢包；可以设置心  
跳间隔时间(配置详见gateway模块的bootstrap.yml中的配置及注释)。

#### 踩坑记录
1.由于消费者与生产者之间dubbo方式调用默认的是随机Random访问，想要改成按照生产者的相应时间动态分发请求LeastActive。  
    * dubbo负载均衡策略
        - random loadBalance：
            - 随机，按权重设置随机概率。  
            - 在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。  
        - roundRobin loadBalance：  
            - 轮询，按公约后的权重设置轮询比率。  
            - 存在慢的提供者类即请求问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。    
        - leastactive loadbalance：  
            - 这个就是自动感知一下，如果某个机器性能越差，那么接收的请求越少，越不活跃，此时就会给不活跃的性能差的机器更少的请求。  
        - consistanthash loadbalance:
            - 一致性 Hash 算法，相同参数的请求一定分发到一个 provider 上去，provider 挂掉的时候，会基于虚拟节点均匀分配剩余的流量，抖动不会太大。如果你需要的不是随机负载均衡，是要一类请求都到一个节点，那就走这个一致性 Hash 策略。
    * 使用leastactive loadbalance需要在流量大的情况下才能看见差异。