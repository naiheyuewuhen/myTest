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

<script type="text/javascript">
	var loadContractUrl = "<%=request.getContextPath()%>/contract/getAll";
	var getContractWithFilesByIdUrl = "<%=request.getContextPath()%>/contract/getWithFilesById";
	var fileDownloadUrl = "<%=request.getContextPath()%>/file/download";
	var loadContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/getAll";//默认无分页，分页需后面拼接page
	var getContractResolveUrl = "<%=request.getContextPath()%>/contractresolve/getById";
	var getGoodsSupplierByGoodsIdUrl = "<%=request.getContextPath()%>/goodsSupplier/getAllByGoodsId";
	var updateForRealityUrl = "<%=request.getContextPath()%>/contractresolve/updateForReality";
	var buildorderUrl = "<%=request.getContextPath()%>/contractresolve/buildorder";
	
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/contract/contractreality.js?v=<%=Math.random()%>"></script>
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
.my_button1 .my_add {
    width: 100px;
}
</style>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="min-width: 900px;">
		<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
			<div class="easyui-panel my_button1 my_combobox" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
				选择合同：<input type="text" id="contract_select" class="easyui-combobox" data-options="iconWidth:28,editable:false" style="width: 200px; height: 30px;"/>&nbsp;
				<a id="_add" href="javascript:void(0);" class="easyui-menubutton my_add" data-options="iconCls:'icon-help'" data-options="disabled:true">生成采购单</a>
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
			<table id="contract_reality_dg" style="height: 400px">
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 100%; height: 100%;" data-options="closed:true,modal: true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
				<div class="easyui-panel my_button1" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
					<input type="hidden" id="contractId" value="" />
					<input type="hidden" id="resolveId" value="" />
					拆解名称：<span id="resolveName"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span style="margin-right: 30px;">合同编号：<em class="title" id="contractCode"></em></span><span id="contract_main" style="display: none;"><span>合同文本：</span><span id="contractMainFiles"></span></span><span id="contract_attachment" style="display: none;"><span>合同附件：</span><span id="contractAttachmentFiles"></span></span>
				</div>
			</div>
			<div class="my_combobox" data-options="region:'center'" style="padding: 10px">
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
