// 导航栏配置文件
var outlookbar=new outlook();
var t;
t=outlookbar.addtitle('个人中心','个人中心,管理首页',1);
outlookbar.additem('查看个人资料',t,'jsp/student/showPersonalStudent.jsp');
outlookbar.additem('修改个人资料',t,'jsp/student/editPersonalStudent.jsp');
outlookbar.additem('更改登录密码',t,'jsp/user/changePwd.jsp');

/*
 * 业务管理
 */
t=outlookbar.addtitle('消息管理','业务管理',1);
outlookbar.additem('系统消息列表',t,'#');
outlookbar.additem('接收消息列表',t,'#');
outlookbar.additem('发送消息列表',t,'#');
outlookbar.additem('发送组别管理',t,'#');
outlookbar.additem('发送规则管理',t,'#');

t=outlookbar.addtitle('请假管理','业务管理',1);
outlookbar.additem('请假记录列表',t,'jsp/leave/listSenderLeave.jsp');
outlookbar.additem('接收记录列表',t,'jsp/leave/listReceiverLeave.jsp');

t=outlookbar.addtitle('选课管理','业务管理',1);
outlookbar.additem('课程信息管理',t,'#');
outlookbar.additem('班级课程计划',t,'#');
outlookbar.additem('教师授课管理',t,'#');
outlookbar.additem('学生选课管理',t,'#');

/*
 * 业务资源
 */
t=outlookbar.addtitle('人员管理','业务资源',1);
outlookbar.additem('学生信息管理',t,'jsp/student/listStudent.jsp');
outlookbar.additem('教师信息管理',t,'jsp/teacher/teacherManager.jsp');

t=outlookbar.addtitle('资源管理','业务资源',1);
outlookbar.additem('部门信息管理',t,'jsp/dept/deptManager.jsp');
outlookbar.additem('教室信息管理',t,'#');

/*
 * 系统管理
 */
t=outlookbar.addtitle('权限管理','系统管理',1);
outlookbar.additem('系统用户管理',t,'jsp/user/listUser.jsp');
outlookbar.additem('系统角色管理',t,'jsp/role/listRole.jsp');
outlookbar.additem('系统资源管理',t,'jsp/resource/resourceManager.jsp');

t=outlookbar.addtitle('数据管理','系统管理',1);
outlookbar.additem('业务字典管理',t,'jsp/dict/dictManager.jsp');

t=outlookbar.addtitle('系统安全','系统管理',1);
outlookbar.additem('IP规则管理',t,'jsp/ipfilter/ipFilterManager.jsp');
outlookbar.additem('系统工作日志',t,'jsp/security/listOperation.jsp');
outlookbar.additem('入侵防护日志',t,'jsp/security/listAggressionLog.jsp');

t=outlookbar.addtitle('备份恢复','系统管理',1);
outlookbar.additem('数据库备份',t,'jsp/backupandrecovery/backupDatabase.jsp');
outlookbar.additem('数据库恢复',t,'jsp/backupandrecovery/loadDatabase.jsp');
outlookbar.additem('工程文件备份',t,'jsp/backupandrecovery/backupWorkspace.jsp');
outlookbar.additem('工程文件恢复',t,'jsp/backupandrecovery/recoveryWorkspace.jsp');

t=outlookbar.addtitle('退出系统','管理首页',1);
outlookbar.additem('点击退出登录',t,'logout.jsp');