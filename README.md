# kddgw_be
用来做党工委系统的后端系统的代码管理。

---

2020年12月2日，提交了2020年11月20日从高远处接手的原始代码。
## 更新日志（仅记录线上版本和历史线上版本）

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
