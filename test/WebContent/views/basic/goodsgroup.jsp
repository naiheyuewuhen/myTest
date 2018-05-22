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
	var loadGoodsGroupUrl = "<%=request.getContextPath()%>/goodsgroup/getAll";//默认无分页，分页需后面拼接page
	var getGoodsGroupByIdUrl = "<%=request.getContextPath()%>/goodsgroup/getById";
	var getGoodsUrl = "<%=request.getContextPath()%>/goods/autocomplete";
	var saveGoodsGroupUrl = "<%=request.getContextPath()%>/goodsgroup/save";
	var deleteGoodsGroupUrl = "<%=request.getContextPath()%>/goodsgroup/delete";
<%-- 	var loadContractUrl = "<%=request.getContextPath()%>/contract/getAll";
	var getContractWithFilesByIdUrl = "<%=request.getContextPath()%>/contract/getWithFilesById";
	var fileDownloadUrl = "<%=request.getContextPath()%>/file/download";
	var loadContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/getAll";//默认无分页，分页需后面拼接page
	var getContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/getById";
	var getGoodsUrl = "<%=request.getContextPath()%>/goods/autocomplete";
	var getGoodsSupplierByGoodsIdUrl = "<%=request.getContextPath()%>/goodsSupplier/getAllByGoodsId";
	var saveContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/save";
	var deleteContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/delete"; --%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/basic/goodsgroup.js?v=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/util.js?v=<%=Math.random()%>"></script>
<style type="text/css">
.title{
color: #008AD4;
margin: 0 10px 0 10px;
}
.my_submitClear .my_submit2 {
background: #3fafff;
}
.my_submitClear .my_submit2:hover {
    background: #338ae3;
}
</style>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="min-width: 900px;">
		<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
			<div class="easyui-panel my_button1 my_combobox" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
				<!-- 选择合同：<input type="text" id="contract_select" class="easyui-combobox" data-options="iconWidth:28,editable:false" style="width: 200px; height: 30px;"/>&nbsp; -->
				<a id="_add" href="javascript:void(0);" class="easyui-menubutton my_add" data-options="iconCls:'icon-help'">新增</a>
				<div class="statuInquiry_search fr">
	                <input id="search" class="easyui-searchbox" style="width:228px; height:28px; font:12px/30px '';">
	                <div id="mm">
	                	<div data-options="name:'code'">编码</div>
					    <div data-options="name:'name'">名称</div>
					</div>
                </div>
			</div>
		</div>
		<div data-options="region:'center',border:false,split:true"
			style="width: 100%; padding-left: 5px;">
			<table id="goods_group_dg" style="height: 400px">
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 100%; height: 100%;" data-options="closed:true,modal: true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 0px;">
				<div class="my_combobox easyui-panel my_button1" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
					<input type="hidden" id="groupId" value="0" />
					<table>
						<tr>
							<td style="text-align: right;">指标编码:</td>
							<td><input type="text" id="code" name="code" class="easyui-textbox" data-options="required:true,maxlength:'20',prompt:'请输入项目指标编码...'" style="width: 200px; height: 30px;"></td>
							<td style="text-align: left; color: red;width: 30px;">*</td>
							<td style="text-align: right;">指标名称:</td>
							<td><input type="text" id="name" name="name" class="easyui-textbox" data-options="required:true,maxlength:'20',prompt:'请输入项目指标名称...'" style="width: 200px; height: 30px;"></td>
							<td style="text-align: left; color: red;width: 30px;">*</td>
							<td style="text-align: right;">备注:</td>
							<td><input type="text" id="remark" name="remark" class="easyui-textbox" data-options="maxlength:'20',prompt:'可输入备注信息...'" style="width: 200px; height: 30px;"></td>
							<td style="text-align: left; color: red;width: 30px;"></td>
						</tr>
					</table>
				</div>
			</div>
			<div class="my_combobox" data-options="region:'center'" style="padding: 10px">
				<div>
					<table>
						<tr>
							<td style="text-align: right;">选择原材料:</td>
							<td><input type="text" id="goods" class="easyui-combogrid" data-options="iconWidth:28,prompt:'请输入原材料编码...'" style="width: 200px; height: 30px;"/></td>
							<td style="text-align: left; color: red;width: 30px;"></td>
						</tr>
					</table>
				</div>
				<div style="margin-top:10px; border-top: 1px dashed rgb(204, 204, 204);">
					<div style="min-height:300px;padding-top: 1px;margin-top: 10px;">
		            	<div class="easyui-layout" data-options="fit:true">	
							<div data-options="region:'west',collapsible:false" style="width:100%;border-right:1px solid #ddd;">
								<table id="goods_array"> 	
			            		</table>
							</div>
						</div>	
		            </div>	
				</div>
			</div>
			<div class="my_submitClear" data-options="region:'south',border:false" style="margin: 5px; height: 34px;">
				<a href="javascript:void(0)" id="save" class="easyui-linkbutton my_submit">保存</a>
				<a href="javascript:void(0)" id="cancel" class="easyui-linkbutton my_clear">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</div>
	</div>
</body>
</html>
