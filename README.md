# kddgw_be
用来做党工委系统的后端系统的代码管理。

---
## 定义

    
- 教职工
    - 在本系统中具有管理权限的用户，一般来说，包括所有正式教职工（core_admin表中的）和部分具有管理权限的学生，即兼职教职工（stu_info表中的）

- 正式教职工
    - 出现在core_admin表中，有工号的，学校正式员工
    
- 兼职教职工
    - 出现在stu_info中的，部分具有管理权限的学生
    
## 权限控制说明
- 由role和permission配合SpringSecurity来完成接口请求权限的控制（RBAC）
- 由DataScope配合DataScope拦截器以及isWithinDataScope方法来完成大部分数据权限的控制
- 接口权限约定
    - 对于只能由学生访问的接口，用`hasRole('STUDENT')` 或`hasAuthority('OWN_INFO_CRUD')`进行控制；
    - 对于一般的学生信息查询，用`hasAuthority('student_select')`进行控制；
    - 对于只能由教职工（含兼职）访问的接口，用`hasAuthority('teacher_own')`进行控制；
    - 对于仅允正式许教职工（不含兼职教职工）访问的接口，用`hasAuthority('teacher_own') and (not hasRole('STUDENT'))`进行控制；
    - 对于仅允许超级管理员访问的接口，用`hasRole('ROOT')`进行控制；
    
## 其他说明
- 关于获取管理姓名的操作
    - 由于当前管理员可能是老师，也可能是学生，因此根据id获取姓名的操作，统一改用`userService.getUserById(id)`来完成；
    
- 关于建表
    - 在与stu_info关联的表中，不要出现department, grade, education_background, major, classes, 以及 state字段；
    
- 关于学生状态控制
    - 在学生状态管理完善之前，相关业务模块不进行学生状态的控制；
    
## 更新日志（仅记录部分线上版本和历史线上版本）

* 2021年5月19日 v10.6.12
    * 【调整】让书记也可以对已审核通过的心理关爱记录进行修改
    
* 2021年5月19日 v10.6.11
    * 【新增】为预约场地新增字段（fieldFor,值只能是teacher或者student）
    * 【排查】心理咨询导出接口问题排查，前端参数传递时间格式与筛选接口的不一致

* 2021年5月17日 v10.6.10
    * 【修复】新增专职辅导员时，默认将is_active设为1
    * 【调整】让学工处权限可以直接对心理定级记录做修改
    * 【调整】关闭老的登录接口

* 2021年4月29日 v10.6.9
    * 【修改】取消增加学生时对辅导员的限制

* 2021年4月29日 v10.6.8
    * 【新增】谈话添加筛选接口
    * 【新增】心理咨询添加删除和更新接口

* 2021年4月16日 v10.6.6
    * 【完善】将userDataScope中的major和classes判断加上
    * 【完善】同步添加了一个班级级联选择接口/api/departmentMajors/getCascaderClasses

* 2021年4月13日 v10.6.6
    * 【优化】为场地预约加上预约成功提示短信

* 2021年4月6日 v10.6.5
    * 【调整】给心理咨询师新开一个不受数据权限限制的“根据学号查询学生信息”的接口（/api/get/psychological_counselor/{stuInfoId}）
    * 【调整】给心理咨询师新开一个不受数据权限限制的“学生信息模糊查询”的接口（/api/psychological_counselor/page）


* 2021年4月6日 v10.6.4
    * 【新增】将用户使用记录写入数据库中
    * 【修复】将心理咨询的数据权限全部放开了
    

* 2021年4月6日 v10.6.3
    * 【新增】ROOT可临时登录任何用户的账号（POST /api/logins/root/user）
    * 【修复】部分老师查看谈话记录报错的问题（由之前有老师离职导致）
    * 【修复】请假信息导出（SQL中字段重复了）
    
 * 2021年3月20日 v10.6.2
     * 添加ROOT管理可租借场地模块
     * 添加角色FIELDMANAGER
     * 添加学生预约场地，管理员审核预约记录

 * 2021年3月15日 v10.6.1
     * 修复审批接口的问题；
     * 给获取审核信息的接口加上了姓名字段；
 * 2021年3月14日 v10.6.0
     * 教职工管理模块增加教学情况和科研情况两个字段，增加审核功能
     * 恢复徐州市内当天返回自动审批功能
    
 * 2021年3月2日 v10.5.5
     * 心理模块修复与优化
 * 2021年3月2日 v10.5.2
    * 【新增】获取教职工简历头像、教职工简历修改接口、教职工获取自己简历信息、ROOT分页获取简历列表接口
    * 【完善】为简历表添加创建时间和更新时间属性
    
 * 2021年2月24日 v10.5.2
    * 【新增】兼职教职工的新增与删除，ROOT管理员具有权限
    
 * 2021年2月23日 v10.5.2
    * 【完善】完善教职工与新增兼职教职工信息展示
    * 【新增】新增教职工数据权限控制（修改userDataScopeList）

 * 2021年2月22日 v10.5.2
     * 【完善】对于报到操作增加了进一步地控制，确保非审核通过的无法报到；
     * 【新增】返校模块提供自定义升序排序方案；
     * 【新增】针对所有用户，返回其基本信息、角色以及数据权限范围的接口；

 * 2021年2月22日 v10.5.1
     * 【新增】返校模块增加按照出发地省份筛选；
    
* 2021年2月21日 v10.5.0
    * 【新增】教职工数据权限管理；
    * 【新增】正式教职工简历管理；
    

    
 * 2021年2月20日 v10.4.3
     * 【完善】对于在融合门户能正常登录，在本系统无账号的用户加了单独的提示；
     * 【新增】返校信息查询接口上新增了筛选功能；
     
 * 2021年2月20日 v10.4.2
     * 【调整】在学生状态管理完善之前，相关业务模块改为不进行学生状态的控制
    
 * 2021年2月15日 v10.4.0
     * 改用统一身份认证登录
     * 全面重构数据权限控制模式
     * 修复若干已知的bug
 * 2021年1月29日 v10.3.0
     * 【新增】就业模块
     * 【修改】去除了请假自动审批
 * 2021年1月15日 v10.2.4
     * 【新增】离校接口的筛选和导出功能
     * 【新增】请假接口的筛选功能
     * 【修改】请假接口的筛选从必须有起止时间改成两个只有一个也可。
     * 【修改】给离校接口新增的审核人字段
 * 2021年1月10日 v10.2.2
     * 【新增】2020-2021年秋季学期离校模块
 * 2021年1月3日 v10.1.5
     * 咨询记录模块
         * 【新增】通过学生学号或时间段获取所有咨询记录和测试记录详情的接口（获取的是所有的咨询记录）（可根据起止时间或学号筛选）
         * 【修改】获取某位同学的咨询记录和心理测评记录的接口将参数减少为只剩学号。（获取的是某位同学所有的咨询记录和测评记录）
     * 关爱对象信息模块
         * 【新增权限管理】定级记录查看接口和有效定级记录筛选接口的权限管理更新为：学工处可看所有，其余老师可看其所在学院的工作年级的。（与学生信息筛选模块同）
         * 【新增】有效定级记录筛选接口新增按照学号筛选。
         * 【完善】定级记录查看接口多返回了审核人和记录人的姓名两项。（PsychologicalLevelCheckSelectVO）
         * 【修复】修复了提交定级信息时系统无法正确处理时间格式的问题。（PsychologicalLevelInsertVO）
         * 【修复】辅导员在更新被拒绝的心理定级申请后，该定级记录的状态由不变改为变为未审核。（直接改的SQL）

 * 2020年12月23日 v10.1.3
     *  【功能修复】增加了jib插件中构建镜像的配置中关于"-Duser.timezone=GMT+08"这个jvmFlas的配置，修复了时区错乱的问题。
 
 * 2020年12月16日 v10.1.2
     *  【功能修复】增加定级信息接口的判空处理
     *  【功能完善】丰富了直观获取某同学的心理定级信息和干预情况的接口。

 * 2020年12月15日 v10.1.1
     *  【功能新增】新增心理咨询记录、心理测评记录、心理定级管理等接口

 * 2020年11月20日 v10.0.0
     *  从黑天鹅工作室处接手系统，与12月2日提交了该代码。


## Docker使用
- 容器镜像服务（Registry）
    - registry.cn-hangzhou.aliyuncs.com/xzszkj
    
- docker 登录
    - sudo docker login --username=quietttt registry.cn-hangzhou.aliyuncs.com
    
- 从Registry中拉取镜像
    - sudo docker pull registry.cn-hangzhou.aliyuncs.com/xzszkj/kddgw:[镜像版本号]
    
- 将镜像推送到Registry
    - 一般方式
        - $ sudo docker login --username=quietttt registry.cn-hangzhou.aliyuncs.com
        - $ sudo docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/xzszkj/kddgw:[镜像版本号]
        - $ sudo docker push registry.cn-hangzhou.aliyuncs.com/xzszkj/kddgw:[镜像版本号]
    - jib插件方式（本项目使用的方式）
        - 在pom.xml里配置好jib插件处的账号和密码以及版本号等，用mvn clean package后，在用mvn jib:build即可完成镜像的构建和上传
        - 注意，若此处的版本号和云端的相同，则云端的会被覆盖掉，因此测试服务一定记得在版本号后加个`.test`
        - 另外，上传代码时，不要将密码给上传上去了，可将其替换为xxxxxx
    
- 部署
    - 在目标服务器上先用docker login登录到公司的容器镜像服务（登陆过一次就不用再登录了）
    - 用docker ps 检查一下有没有还在运行的老服务，若有，先用docker stop关掉；
    - 再用下边的docker run命令可自动完成拉取和运行（后续可能会改成更方便的docker-compose方式），记得改要运行的镜像的版本号和容器名。
        - sudo docker run -d -p 9090:9090 --name committee_10_6_1 --network=newCommittee --volume=/home/newcommittee/java/log:/log  --volume=/home/eval/photos:/photos registry.cn-hangzhou.aliyuncs.com/xzszkj/kddgw:10.6.1