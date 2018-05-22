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
	var loadContractUrl = "<%=request.getContextPath()%>/contract/getAll";
	var getReceivingForContractUrl = "<%=request.getContextPath()%>/goodsreceiving/getAllForContract";
	var getReceivingForOrderUrl = "<%=request.getContextPath()%>/purchaseOrder/getById";
	var saveUrl = "<%=request.getContextPath()%>/goodsreceiving/save";
	var getSupplierByContractIdUrl = "<%=request.getContextPath()%>/supplier/getAllByContractId";
	var getSupplierUrl = "<%=request.getContextPath()%>/supplier/getAll";
	var getPurchaseOrderUrl = "<%=request.getContextPath()%>/purchaseOrder/getAll";
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/contract/goodsreceiving.js?v=<%=Math.random()%>"></script>
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
		<div data-options="region:'north',border:false" style="width: 60%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
			<div class="easyui-panel my_button1 my_combobox" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
					选择合同：<input type="text" id="contract_select" class="easyui-combobox" data-options="iconWidth:28,editable:false" style="width: 200px; height: 30px;"/>&nbsp;&nbsp;&nbsp;&nbsp;
					选择采购单：<input type="text" id="order" class="easyui-combobox" data-options="iconWidth:28,editable:false,prompt:'请选择采购单...'" style="width: 200px; height: 30px;"/>
				<div class="statuInquiry_search fr">
	                <input id="search" class="easyui-searchbox" style="width:228px; height:28px; font:12px/30px '';">
	                <div id="mm">
					    <div data-options="name:'supplierName'">供应商名称</div>
					</div>
	            </div>
			</div>
		</div>
		<div data-options="region:'center',border:false,split:true"
			style="width: 100%; padding-left: 5px;">
			<table id="supplier_dg" style="height: 400px">
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 100%; height: 100%;" data-options="closed:true,modal: true">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
				<div class="easyui-panel my_button1 my_combobox" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
					<div class="statuInquiry_search fr">
		                <input id="search2" class="easyui-searchbox" style="width:228px; height:28px; font:12px/30px '';">
		                <div id="mm2">
						    <div data-options="name:'goodsCode'">物品编号</div>
						    <div data-options="name:'goodsName'">物品名称</div>
						</div>
	                </div>
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
				<a href="javascript:void(0)" id="cancel" class="easyui-linkbutton my_clear">返回</a>
			</div>
		</div>
	</div>
</body>
</html>
