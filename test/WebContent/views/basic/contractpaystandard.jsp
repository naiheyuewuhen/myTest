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
<link href="<%=request.getContextPath()%>/css/base/my_easyui.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/main/main_reset.css" rel="stylesheet" />

<script>
	var loadContractPayStandardUrl = "<%=request.getContextPath()%>/contractPayStandard/getAll";//默认无分页，分页需后面拼接page
	var saveContractPayStandardUrl = "<%=request.getContextPath()%>/contractPayStandard/save";
	<%-- var getContractPayStandardUrl = "<%=request.getContextPath()%>/contractPayStandard/getById"; --%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/basic/contractpaystandard.js?v=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/util.js?v=<%=Math.random()%>"></script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="min-width: 900px;">
		<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
			<div class="easyui-panel my_button1" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
				<a id="_add" href="javascript:void(0);" class="easyui-menubutton my_add" data-options="iconCls:'icon-help'">新增</a>
				<div class="statuInquiry_search fr" style="display: none;">
	               <input id="search" class="easyui-searchbox" style="width:228px; height:28px; font:12px/30px '';">
	               <div id="mm">
					    <div data-options="name:'startDate'">起始日期</div>
					    <div data-options="name:'endDate'">截止日期</div>
					  </div>
               </div>
			</div>
		</div>
		<div data-options="region:'center',border:false,split:true"
			style="width: 100%; padding-left: 5px;">
			<table id="pay_dg" style="height: 400px">
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 100%; height: 100%;" data-options="closed:true,modal: true">
		<div class="easyui-layout" data-options="fit:true">
			<div class="my_combobox" data-options="region:'center'" style="padding: 10px">
				<form id="ff" action="" class="easyui-form">
					<input id="payId" type="hidden" name="id" value="0"/>
					<div>
						<table style="margin-top: 10px;">
							<tr>
								<td style="text-align: right;">起始日期:</td>
								<td><input type="text" name="startDate" class="easyui-datebox" data-options="required:true,editable:false,prompt:'起始日期...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">截止日期:</td>
								<td><input type="text" name="endDate" class="easyui-datebox" data-options="required:true,editable:false,prompt:'截止日期...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">第1笔款:</td>
								<td><input class="payNo" type="text" name="payNo1" class="easyui-numberbox" style="width: 100px; height: 30px;"/>%</td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">第2笔款:</td>
								<td><input class="payNo" type="text" name="payNo2" class="easyui-numberbox" style="width: 100px; height: 30px;"/>%</td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">第3笔款:</td>
								<td><input class="payNo" type="text" name="payNo3" class="easyui-numberbox" style="width: 100px; height: 30px;"/>%</td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">第4笔款:</td>
								<td><input class="payNo" type="text" name="payNo4" class="easyui-numberbox" style="width: 100px; height: 30px;"/>%</td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">第5笔款:</td>
								<td><input class="payNo" type="text" name="payNo5" class="easyui-numberbox" style="width: 100px; height: 30px;"/>%</td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
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