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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/easyui-lang-zh_CN.js"></script>
<%-- <link href="<%=request.getContextPath()%>/css/main/main_interface.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/main/feature.presenter.1.5.css" rel="stylesheet" /> --%>
<link href="<%=request.getContextPath()%>/css/base/my_easyui.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/main/main_reset.css" rel="stylesheet" />

<script>
	var loadRoleUrl = "<%=request.getContextPath()%>/role/getAll";//默认无分页，分页需后面拼接page
	var saveRoleUrl = "<%=request.getContextPath()%>/role/save";
	var getRoleUrl = "<%=request.getContextPath()%>/role/getById";
	var getModulesUrl="<%=request.getContextPath()%>/module/getTreeAll";//权限模块
	var getAuthorityUrl="<%=request.getContextPath()%>/authority/getModulesByRoleId";//已有权限
	var saveAuthorityUrl="<%=request.getContextPath()%>/authority/save";//已有权限
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/user/role.js?v=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/util.js?v=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/treeutil.js?v=<%=Math.random()%>"></script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="min-width: 900px;">
		<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
			<div class="easyui-panel my_button1" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
				<a id="_add" href="javascript:void(0);" class="easyui-menubutton my_add" data-options="iconCls:'icon-help'">新增</a>
				<a href="javascript:void(0)"  id="modulePermission" class="easyui-menubutton my_fash"  style="width:90px;"><span>模块权限</span></a>
				<div class="statuInquiry_search fr">
	               <input id="search" class="easyui-searchbox" style="width:228px; height:28px; font:12px/30px '';">
	               <div id="mm">
					    <div data-options="name:'name'">名称</div>
					  </div>
               </div>
			</div>
		</div>
		<div data-options="region:'center',border:false,split:true"
			style="width: 100%; padding-left: 5px;">
			<table id="role_dg" style="height: 400px">
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
								<td style="text-align: right;">名称:</td>
								<td><input type="text" name="name" class="easyui-textbox" data-options="required:true,maxlength:'50',prompt:'请输入名称...'" style="width: 200px; height: 30px;"/></td>
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
	<div id="roleDlg">
		<ul id="roleTree">
		</ul>      
	</div>
</body>
</html>