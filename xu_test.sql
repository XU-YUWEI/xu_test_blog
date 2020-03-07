/*
MySQL Data Transfer
Source Host: localhost
Source Database: xu_test
Target Host: localhost
Target Database: xu_test
Date: 2020/3/7 17:37:23
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for tb_admin_user
-- ----------------------------
CREATE TABLE `tb_admin_user` (
  `admin_id` tinyint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(600) DEFAULT NULL,
  `login_password` varchar(150) DEFAULT NULL,
  `admin_nick_name` varchar(150) DEFAULT NULL,
  `locked` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
CREATE TABLE `tb_blog` (
  `blog_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客表主键id',
  `blog_title` varchar(200) NOT NULL COMMENT '博客标题',
  `blog_sub_url` varchar(200) NOT NULL COMMENT '博客自定义路径url',
  `blog_cover_image` varchar(200) NOT NULL COMMENT '博客封面图',
  `blog_content` mediumtext NOT NULL COMMENT '博客内容',
  `blog_category_id` int(11) NOT NULL COMMENT '博客分类id',
  `blog_category_name` varchar(50) NOT NULL COMMENT '博客分类(冗余字段)',
  `blog_tags` varchar(200) NOT NULL COMMENT '博客标签',
  `blog_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-草稿 1-发布',
  `blog_views` bigint(20) NOT NULL DEFAULT '0' COMMENT '阅读量',
  `enable_comment` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-允许评论 1-不允许评论',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_blog_comment
-- ----------------------------
CREATE TABLE `tb_blog_comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联的blog主键',
  `commentator` varchar(50) NOT NULL DEFAULT '' COMMENT '评论者名称',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '评论人的邮箱',
  `website_url` varchar(50) NOT NULL DEFAULT '' COMMENT '网址',
  `comment_body` varchar(200) NOT NULL DEFAULT '' COMMENT '评论内容',
  `comment_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论提交时间',
  `commentator_ip` varchar(20) NOT NULL DEFAULT '' COMMENT '评论时的ip地址',
  `reply_body` varchar(200) NOT NULL DEFAULT '' COMMENT '回复内容',
  `reply_create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '回复时间',
  `comment_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否审核通过 0-未审核 1-审核通过',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_blog_tag
-- ----------------------------
CREATE TABLE `tb_blog_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tag_name` varchar(100) NOT NULL COMMENT '标签名称',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0=否 1=是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_blog_tag_relation
-- ----------------------------
CREATE TABLE `tb_blog_tag_relation` (
  `relation_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关系表id',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_link
-- ----------------------------
CREATE TABLE `tb_link` (
  `link_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `link_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '友链类别 0-友链 1-推荐 2-个人网站',
  `link_name` varchar(50) NOT NULL COMMENT '网站名称',
  `link_url` varchar(100) NOT NULL COMMENT '网站链接',
  `link_description` varchar(100) NOT NULL COMMENT '网站描述',
  `link_rank` int(11) NOT NULL DEFAULT '0' COMMENT '用于列表排序',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`link_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_news
-- ----------------------------
CREATE TABLE `tb_news` (
  `news_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '新闻主键id',
  `news_title` varchar(200) NOT NULL DEFAULT '' COMMENT '标题',
  `news_category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '新闻类型',
  `news_cover_image` varchar(200) NOT NULL DEFAULT '' COMMENT '新闻封面图片',
  `news_content` text NOT NULL COMMENT '内容',
  `news_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '发布状态 0-发布 1-草稿',
  `news_views` bigint(20) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已删除 0-未删除 1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`news_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_news_category
-- ----------------------------
CREATE TABLE `tb_news_category` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类别主键id',
  `category_name` varchar(200) NOT NULL DEFAULT '' COMMENT '类别名称',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已删除 0-未删除 1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_news_comment
-- ----------------------------
CREATE TABLE `tb_news_comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论主键id',
  `news_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联咨询主键id',
  `commentator` varchar(200) NOT NULL DEFAULT '' COMMENT '评论人',
  `comment_body` varchar(300) NOT NULL DEFAULT '' COMMENT '评论内容',
  `comment_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '评论状态 0-未审核 1-审核通过',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已删除 0-未删除 1-已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES ('3', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'xu', '0');
