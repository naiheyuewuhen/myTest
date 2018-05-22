INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('1', '系统管理', '', '1', '0', 'url', 'system', '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('11', '系统设置', '', '2', '1', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('12', '人员管理', '', '2', '1', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('121', '权限管理', '', '3', '12', 'url', 'jurisdiction', '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('1211', '角色管理', '/views/user/role.jsp', '4', '121', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('1212', '用户管理', '/views/user/user.jsp', '4', '121', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('2', '源数据管理', '', '1', '0', 'url', 'data', '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('21', '运营资料设置', '', '2', '2', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('211', '基础数据管理', '', '3', '21', 'url', 'basicsetup', '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('2111', '材料管理', '/views/basic/goods.jsp', '4', '211', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('2112', '供应商管理', '/views/basic/supplier.jsp', '4', '211', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('3', '合同管理', '', '1', '0', 'url', 'store', '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('31', '合同管理', '', '2', '3', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('311', '合同管理', '', '3', '31', 'url', 'memberfile', '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('3111', '合同管理', '/views/contract/contract.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('3112', '合同分解', '/views/contract/contractresolve.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('3113', '技术拆解', '/views/contract/contractresolvedetail.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('3114', '采购管理', '/views/contract/contractreality.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('3115', '工厂收货', '/views/contract/goodsreceiving.jsp', '4', '311', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('31111', '合同一级审批', 'contract/auditing', '5', '3111', 'button', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('31112', '合同二级审批', 'contract/auditing2', '5', '3111', 'button', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('31113', '查看合同文本', 'contract/main', '5', '3111', 'button', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('31114', '查看合同附件', 'contract/attachment', '5', '3111', 'button', null, '0', '1');

INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('111', '合同规范', '', '3', '11', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('1111', '标准合同付款规范', '/views/basic/contractpaystandard.jsp', '4', '111', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('2113', '项目指标管理', '/views/basic/goodsgroup.jsp', '4', '211', 'url', null, '0', '1');

--待扩展功能菜单
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('122', '待扩展功能', '', '3', '12', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('123', '待扩展功能', '', '3', '12', 'url', null, '0', '1');
INSERT INTO `module`(id,name,url,level,father_id,type,use_img,sort_num,status) VALUES ('13', '待扩展功能', '', '2', '1', 'url', null, '0', '1');




员工信息修改时，登录密码 需要处理