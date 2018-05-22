<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/base/favicon.ico" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base/easyui/themes/metro-gray/easyui.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base/easyui/themes/icon.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/1.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/easyui-lang-zh_CN.js"></script>
<%-- <link href="<%=request.getContextPath()%>/css/main/main_interface.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/main/feature.presenter.1.5.css" rel="stylesheet" /> --%>
<link href="<%=request.getContextPath()%>/css/base/my_easyui.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/main/main_reset.css" rel="stylesheet" />

<script>
	var loadUserUrl = "<%=request.getContextPath()%>/user/getAll";//默认无分页，分页需后面拼接page
	var saveUserUrl = "<%=request.getContextPath()%>/user/save";
	var getUserUrl = "<%=request.getContextPath()%>/user/getById";
	var loadRoleUrl = "<%=request.getContextPath()%>/role/getAll";
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/user/user.js?v=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/util.js?v=<%=Math.random()%>"></script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="min-width: 900px;">
		<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
			<div class="easyui-panel my_button1" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
				<a id="_add" href="javascript:void(0);" class="easyui-menubutton my_add" data-options="iconCls:'icon-help'">新增</a>
				<div class="statuInquiry_search fr">
	               <input id="search" class="easyui-searchbox" style="width:228px; height:28px; font:12px/30px '';">
	               <div id="mm">
					    <div data-options="name:'name'">姓名</div>
					    <div data-options="name:'phone'">手机号</div>
				   </div>
               </div>
			</div>
		</div>
		<div data-options="region:'center',border:false,split:true"
			style="width: 100%; padding-left: 5px;">
			<table id="user_dg" style="height: 400px">
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 100%; height: 100%;" data-options="closed:true,modal: true">
		<div class="easyui-layout" data-options="fit:true">
			<div class="my_combobox" data-options="region:'center'" style="padding: 10px">
				<form id="ff" action="" class="easyui-form">
					<input id="id" type="hidden" name="id" value="0"/>
					<div>
						<table style="margin-top: 10px;">
							<tr>
								<td style="text-align: right;">登录用户名:</td>
								<td><input type="text" name="username" class="easyui-textbox" data-options="required:true,maxlength:'50',prompt:'请输入登录用户名...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">登录密码:</td>
								<td><input type="text" name="password" class="easyui-passwordbox" data-options="required:true,maxlength:'50',showEye:false,prompt:'请输入登录密码...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">姓名:</td>
								<td><input type="text" name="name" class="easyui-textbox" data-options="required:true,maxlength:'50',prompt:'请输入姓名...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">性别:</td>
								<td><input type="radio" name="sex" value="0" checked="checked"/>女
						            <input type="radio" name="sex" value="1" />男</td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">手机号:</td>
								<td><input type="text" name="phone" class="easyui-textbox" data-options="required:true,maxlength:'11',prompt:'请输入手机号...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">角色:</td>
								<td><input type="text" id="roleId" name="roleId" class="easyui-combobox" data-options="required:true,iconWidth:28,editable:false" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">备注:</td>
								<td><input type="text" name="remark" class="easyui-textbox" data-options="maxlength:'100',prompt:'可输入备注信息...'" style="width: 200px; height: 30px;"/></td>
								<td></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			<div class="my_submitClear" data-options="region:'south',border:false" style="margin: 5px; height: 34px;">
				<a href="javascript:void(0)" id="save" class="easyui-linkbutton my_submit">保存</a>
				<a href="javascript:void(0)" id="cancel" class="easyui-linkbutton my_clear">返回</a>
			</div>
		</div>
	</div>
</body>
</html>