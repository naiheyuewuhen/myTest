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

<script type="text/javascript">
	<%-- var loadContractUrl = "<%=request.getContextPath()%>/contract/getAll";//默认无分页，分页需后面拼接page
	var fileUploadUrl = "<%=request.getContextPath()%>/file/upload";
	var getContractUrl = "<%=request.getContextPath()%>/contract/getById"; --%>
	var loadContractUrl = "<%=request.getContextPath()%>/contract/getAll";
	var getContractWithFilesByIdUrl = "<%=request.getContextPath()%>/contract/getWithFilesById";
	var fileDownloadUrl = "<%=request.getContextPath()%>/file/download";
	var loadContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/getAll";//默认无分页，分页需后面拼接page
	var getContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/getById";
	var getGroupByIdUrl = "<%=request.getContextPath()%>/goodsgroup/getById";
	var getGroupUrl = "<%=request.getContextPath()%>/goodsgroup/autocomplete";
	var getGoodsUrl = "<%=request.getContextPath()%>/goods/autocomplete";
	<%-- var getGoodsSupplierByGoodsIdUrl = "<%=request.getContextPath()%>/goodsSupplier/getAllByGoodsId"; --%>
	var saveContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/save";
	var deleteContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/delete";
	
	$(document).ready(function(){
		if(${sessionScope.userName=="system"}){
			$("#contract_main").show();
			$("#contract_attachment").show();
		}else{
			if(${sessionScope.authMap["31113"]==1}){//有查看合同文本权限
				$("#contract_main").show();
			}
			if(${sessionScope.authMap["31114"]==1}){//有查看合同附件权限
				$("#contract_attachment").show();
			}
		}
	});
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/contract/contractresolvedetail.js?v=<%=Math.random()%>"></script>
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
				<div class="statuInquiry_search fr">
	                <input id="search" class="easyui-searchbox" style="width:228px; height:28px; font:12px/30px '';">
	                <div id="mm">
					    <div data-options="name:'contractCode'">合同编号</div>
					    <div data-options="name:'resolveName'">拆解名称</div>
					</div>
                </div>
			</div>
		</div>
		<div data-options="region:'center',border:false,split:true"
			style="width: 100%; padding-left: 5px;">
			<table id="contract_resolve_dg" style="height: 400px">
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 100%; height: 100%;" data-options="closed:true,modal: true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
				<div class="easyui-panel my_button1" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
					<input type="hidden" id="resolveId" value="0" />
					<input type="hidden" id="contractId" value="0" />
					<span style="margin-right: 30px;">合同编号：<em class="title" id="contractCode"></em></span><span id="contract_main" style="display: none;"><span>合同文本：</span><span id="contractMainFiles"></span></span><span id="contract_attachment" style="display: none;"><span>合同附件：</span><span id="contractAttachmentFiles"></span></span>
				</div>
			</div>
			<div class="my_combobox" data-options="region:'center'" style="padding: 10px">
				<div>
					<table style="margin-top: 10px;">
						<tr>
							<td style="text-align: right;">拆解名称:</td>
							<td><input type="text" id="resolveName" name="resolveName" class="easyui-textbox" data-options="required:true,maxlength:'20',editable:false,prompt:'请输入拆分名称...'" style="width: 200px; height: 30px;"></td>
							<td style="text-align: left; color: red;width: 30px;"></td>
							<td style="text-align: right;">选择项目指标:</td>
							<td><input type="text" id="group" class="easyui-combogrid" data-options="iconWidth:28,prompt:'请输入项目指标编码...'" style="width: 200px; height: 30px;"/></td>
							<td style="text-align: right; color: red;width: 30px;">或</td>
							<td style="text-align: right;">选择原材料:</td>
							<td><input type="text" id="goods" class="easyui-combogrid" data-options="iconWidth:28,prompt:'请输入原材料编码...'" style="width: 200px; height: 30px;"/></td>
							<td style="text-align: left; color: red;width: 30px;"></td>
							<!-- <td style="text-align: right;">选择供应商:</td>
							<td><input type="text" id="supplier" class="easyui-combotree" data-options="iconWidth:28,editable:false,prompt:'请选择供应商...'" style="width: 200px; height: 30px;"/></td>
							<td style="text-align: left; color: red;width: 30px;"></td>
							<td><a id="_addgoods" href="javascript:void(0);" class="easyui-linkbutton my_add">添加</a></td>
							<td style="text-align: left; color: red;width: 30px;"></td> -->
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
				<a href="javascript:void(0)" id="save" class="easyui-linkbutton my_submit">完成</a>
				<a href="javascript:void(0)" id="cancel" class="easyui-linkbutton my_clear">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:void(0)" id="transient_save" class="easyui-linkbutton my_submit my_submit2">暂存</a>
			</div>
		</div>
	</div>
</body>
</html>
