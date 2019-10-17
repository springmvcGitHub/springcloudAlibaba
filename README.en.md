# 阿里springcloud及部分组件

#### 项目简介
1.主要技术栈：springcloud Alibaba+nacos+dubbo+gateway+ribbon。
2.注册中心是用nacos，下载地址：https://github.com/alibaba/nacos/releases

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
