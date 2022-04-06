# myblog
个人博客网站

## 1. 前言

&emsp;&emsp;没错，俺就是博主，目前是上海理工大学研究生二年级学生！自读研以来，自学接触了很多编程和算法的东西，也不知道最后能不能用的上，但是我相信多了解多学习一定没坏处。

&emsp;&emsp;嘿嘿~ 其实最开始着手写这个个人网站项目的时候，本人还是个Java小菜鸡（现在成大菜鸡了），准确地说我是从前端转后端的，以及从Python转Java的。有始有终，这篇博客打算记录下自己这个小破站的成长记录。

## 2. 更新日志

&emsp;&emsp;域名：[www.kwokblog.top](http://www.kwokblog.top/)      技术路线：SpringBoot+Mybatis+semantic UI

| 版本号  |                         更新内容                         |    更新时间    | 是否启用 |
| :-----: | :------------------------------------------------------: | :------------: | :------: |
|  0.0.0  |                   完成前端所有页面内容                   | 2021年12月11日 |    是    |
|  0.0.1  |                  完成后端所有内容并上线                  | 2022年03月22日 |    是    |
|  0.0.2  |                  增加评论功能和归档功能                  | 2022年03月24日 |    是    |
|  0.0.3  | 增加页面背景雪花、树叶飘落，增加看板娘功能，鼠标点击特效 | 2022年03月25日 |    是    |
|  0.0.4  |         增加enter页面，添加动态特效，艺术字logo          | 2022年03月29日 |    是    |
|  0.0.5  |                      更新数据库数据                      |  2022年4月6日  |    是    |
| 待续... |                         待续...                          |    待续...     | 待续...  |

## 3. 前端


- jQuery框架：[jquery@3.2](https://cdn.jsdelivr.net/npm/jquery@3.2/dist/jquery.min.js)
- UI框架：[Semantic UI框架](https://semantic-ui.com/collections/breadcrumb.html)

- 其他工具或插件：[背景下载](https://www.toptal.com/designers/subtlepatterns/)，[动态CSS插件](https://animate.style/)，[目录生成插件](https://tscanlin.github.io/tocbot/)，[二维码生成插件](https://davidshimjs.github.io/qrcodejs/)，[平滑滚动插件](https://github.com/flesler/jquery.scrollTo)，[滚动侦测插件](http://imakewebthings.com/waypoints/)，[编辑Markdown插件](https://pandao.github.io/editor.md/)

## 4. 后端

- 核心框架：Spring Boot
- 持久层框架：MyBatis

- java版本：JDK11
- MySQL版本：8.0.25

- 其他依赖：aop，data-jpa，thymeleaf，jdbc，commonmark

## 5. 功能

#### 入口页面：

- 热门文章： 按照浏览次数来进行倒序排序展示，直接跳转具体blog页面
- 右侧菜单： 跳转首页，分类，后台，艺术签名展示

- 底部链接：关于，归档，作者github

#### 首页：

- 最新推荐： 按照发布时间来进行倒序排序展示
- 导航栏：首页、分类、标签、归档、关于我、后台管理，搜索查找

- 右边栏：分类、标签展示
- 底部栏：联系我、友情链接、站点信息

#### 博客详情页：

- 目录，留言，赞赏，联系

#### 分类：

- 根据类名搜索对应博客展示

#### 标签：

- 根据标签查找含有该标签的博客展示

#### 归档：

- 根据年份归纳博客

#### 关于我：

- 作者信息介绍

#### 后台管理页面：

- 登录功能
- 文章管理：

  - 文章筛选
  - 文章发布
  - 文章编辑
  - 文章删除

- 分类管理：
  - 分类的增删改查

- 标签管理：
  - 标签的增删改查

## 6. 项目线上部署

- 环境：腾讯云服务器，采用jar包部署，需要相关环境配置
- 域名购买，备案，映射

- 博客地址：[http://www.kwokblog.top](http://www.kwokblog.top/)
- 项目地址：[https://github.com/Hokwok/myblog](https://github.com/Hokwok/myblog)

### 参考：

- 李仁密老师：[博客教程](https://www.bilibili.com/video/BV1HE411N76x)
