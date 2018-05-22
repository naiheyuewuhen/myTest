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
	var loadContractUrl = "<%=request.getContextPath()%>/contract/getAll";//默认无分页，分页需后面拼接page
	var fileUploadUrl = "<%=request.getContextPath()%>/file/upload";
	var saveContractUrl = "<%=request.getContextPath()%>/contract/save";
	var auditingContractUrl = "<%=request.getContextPath()%>/contract/auditing";
	var getContractUrl = "<%=request.getContextPath()%>/contract/getById";
	var savePaymentUrl = "<%=request.getContextPath()%>/payment/save";
	
	$(document).ready(function(){
		if(${sessionScope.userName=="system"}||${sessionScope.authMap["31112"]==1}){//有二级审批权限
			$("#auditing").show();
		}else if(${sessionScope.authMap["31111"]==1}){//有一级审批权限
			$("#auditing").attr("data-v","1");
			$("#auditing").show();
		}
	});
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/contract/contract.js?v=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/util.js?v=<%=Math.random()%>"></script>
<style type="text/css">
li{
width: 45%;
color: #008AD4;
height:20px;
}
.up_file_time{
padding-right: 20px;
}
.del{
color: #C7CCD1;
}
/* .selected_chanels{
	background-color:#0e88c7;
	color:#fff
}
.selected_chanels:hover{ 
	background-color:#0a628f;
}
.unselected_chanels{
	background-color:#e6e6e6;
	color:#00000
}
.unselected_chanels:hover{
	background-color:#c2c2c2;
	color:#00000
}
.add_chanels1{}
.tdclass{}
.ymbz{
line-height:25px;
vertical-align:middle;
font-size: 12px;
color: #999; 
} */
</style>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="min-width: 900px;">
		<div data-options="region:'north',border:false" style="width: 100%; height: 60px; border-bottom: 1px dashed #ccc; margin-bottom: 15px;">
			<div class="easyui-panel my_button1" data-options="fit:true,border:false" style="padding: 15px 5px 0px 5px;">
				<a id="_add" href="javascript:void(0);" class="easyui-menubutton my_add" data-options="iconCls:'icon-help'">新增</a>
				<a href="javascript:void(0);" id="auditing" data-v="2" style="display:none;" class="easyui-menubutton my_fash" data-options="disabled:true">审核</a>
				<div class="statuInquiry_search fr">
	               <input id="search" class="easyui-searchbox" style="width:228px; height:28px; font:12px/30px '';">
	               <div id="mm">
					    <div data-options="name:'code'">编号</div>
					    <div data-options="name:'firstParty'">甲方</div>
					    <div data-options="name:'second_party'">乙方</div>
					  </div>
               </div>
			</div>
		</div>
		<div data-options="region:'center',border:false,split:true"
			style="width: 100%; padding-left: 5px;">
			<table id="contract_dg" style="height: 400px">
			</table>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 100%; height: 100%;" data-options="closed:true,modal: true">
		<div class="easyui-layout" data-options="fit:true">
			<div class="my_combobox" data-options="region:'center'" style="padding: 10px">
				<form id="ff" action="" class="easyui-form">
					<input id="contractId" type="hidden" name="id" value="0"/>
					<div>
						<table style="margin-top: 10px;">
							<tr>
								<td style="text-align: right;">合同编号:</td>
								<td><input type="text" name="code" class="easyui-textbox" data-options="required:true,maxlength:'20',prompt:'请输入合同编号...'" style="width: 200px; height: 30px;"></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">签约日期:</td>
								<td><input type="text" name="contractDate" class="easyui-datebox" data-options="required:true,editable:false,prompt:'请选择签约日期...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">合同金额:</td>
								<td><input type="text" name="totalAmount" class="easyui-numberbox" data-options="required:true,min:0.01,precision:2,iconWidth:28,prompt:'请输入合同金额...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">依附合同:</td>
								<td><input type="text" id="contractFather" name="fatherId" class="easyui-combobox" data-options="required:true,iconWidth:28,editable:false" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">甲方:</td>
								<td><input type="text" name="firstParty" class="easyui-textbox" data-options="required:true,maxlength:'50',prompt:'请输入单位名称...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">地址:</td>
								<td><input type="text" name="firstAddress" class="easyui-textbox" data-options="required:true,maxlength:'50',prompt:'请输入甲方地址...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">联系人:</td>
								<td><input type="text" name="firstLinkman" class="easyui-textbox" data-options="required:true,maxlength:'20',prompt:'请输入甲方联系人...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">电话:</td>
								<td><input type="text" name="firstPhone" class="easyui-textbox" data-options="required:true,maxlength:'25',prompt:'请输入甲方联系电话...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">乙方:</td>
								<td><input type="text" name="secondParty" class="easyui-textbox" data-options="required:true,maxlength:'50',prompt:'请输入单位名称...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">地址:</td>
								<td><input type="text" name="secondAddress" class="easyui-textbox" data-options="required:true,maxlength:'50',prompt:'请输入乙方地址...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">联系人:</td>
								<td><input type="text" name="secondLinkman" class="easyui-textbox" data-options="required:true,maxlength:'20',prompt:'请输入乙方联系人...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
								<td style="text-align: right;">电话:</td>
								<td><input type="text" name="secondPhone" class="easyui-textbox" data-options="required:true,maxlength:'25',prompt:'请输入乙方联系电话...'" style="width: 200px; height: 30px;"/></td>
								<td style="text-align: left; color: red;width: 30px;">*</td>
							</tr>
							<tr>
								<td style="text-align: right;">备注:</td>
								<td colspan="4"><input type="text" name="remark" class="easyui-textbox" data-options="maxlength:'200',prompt:'可输入备注信息...'" style="width: 496px; height: 30px;"/></td>
							</tr>
						</table>
					</div>
					<div style="margin-top:10px; border-top: 1px dashed rgb(204, 204, 204);">
						<div style="width: 49.8%;float:left;">
							<table style="margin-top: 10px;" id="agreed_payment">
								<tr class="head">
									<th align="center">序号</th><th align="center">付款金额（元）</th><th align="center">付款方式</th><th align="center">付款时间</th>
								</tr>
								<tr>
									<td>1</td>
									<td><input data-name="agreed_id" type="hidden" value="0"/>
										<input data-name="agreed_amount" type="text" class="easyui-numberbox" data-options="required:true,min:0.01,precision:2,width:150,height:30,maxlength:15,prompt:'长度在1-15之间'" /></td>
									<td><input data-name="agreed_paymentMode" type="text" class="easyui-textbox" data-options="required:true,maxlength:20,width:150,height:30,prompt:'付款方式...'" /></td>
									<td><input data-name="agreed_paymentDate" type="text" class="easyui-datebox" data-options="required:true,editable:false,width:150,height:30,prompt:'付款时间...'"/></td>
									<td style="padding-left: 20px;"><a id="add_agreed_payment" title="添加" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'"></a></td>
								</tr>
							</table>
						</div>
						<div id="reality_payment_div" style="width: 50%;float: right; border-left: 1px dashed rgb(204, 204, 204); display: none;">
							<table style="margin-top: 10px;" id="reality_payment">
								<tr class="head">
									<th align="center">序号</th><th align="center">收款金额（元）</th><th align="center">收款方式</th><th align="center">收款时间</th>
								</tr>
								<tr id="reality_tr">
									<td>&nbsp;</td>
									<td><input data-name="reality_amount" type="text" class="easyui-numberbox" data-options="min:0.01,precision:2,width:150,height:30,maxlength:15,prompt:'长度在1-15之间'" /></td>
									<td><input data-name="reality_paymentMode" type="text" class="easyui-textbox" data-options="maxlength:20,width:150,height:30,prompt:'收款方式...'" /></td>
									<td><input data-name="reality_paymentDate" type="text" class="easyui-datebox" data-options="editable:false,width:150,height:30,prompt:'收款时间...'"/></td>
									<td style="padding-left: 20px;"><a id="add_reality_payment" title="保存" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"></a></td>
								</tr>
							</table>
						</div>
						<div style="float:left;width: 99%;padding: 10px;margin-top:10px; border-top: 1px dashed rgb(204, 204, 204);">
							<ul id="file_arr">
							</ul>
							<label>合同文本：</label><input id="upload" class="easyui-textbox" data-options="required:true,prompt:'请选择合同文本...',editable:false,buttonText:'上传文本',width:300,height:30" />
							<input id="upfile" type="file" multiple="multiple" style="display: none;"/>
							<ul id="file_arr2">
							</ul>
							<label>合同附件：</label><input id="upload2" class="easyui-textbox" data-options="required:true,prompt:'请选择合同附件...',editable:false,buttonText:'上传附件',width:300,height:30" />
							<input id="upfile2" type="file" multiple="multiple" style="display: none;"/>
						</div>
					</div>
				</form>
			</div>
			<div class="my_submitClear" data-options="region:'south',border:false" style="margin: 5px; height: 34px;">
				<a href="javascript:void(0)" id="save" class="easyui-linkbutton my_submit">完成</a>
				<a href="javascript:void(0)" id="cancel" class="easyui-linkbutton my_clear">返回</a>
			</div>
		</div>
	</div>
	<div id="msg_confirm"  title="审批提示" class="easyui-dialog" style="width: 550px; height: 260px" data-options="closed:true,modal: true" >
	<div class="easyui-layout" data-options="fit:true">	
		<div  data-options="region:'center'" align="left">
		<input type="hidden" id="auditing_id" />
		<input type="hidden" id="contract_type" />
		<div style='padding:3px;color:red;'>&nbsp;&nbsp;&nbsp;&nbsp;合同编号【<span id="contract_auditing"></span>】，请您审批</div>
			<div id = "msg_msg" style='padding:10px 80px;width: 350px;'>
			审批意见：<br>
			<textarea rows="4" cols="40" id="auditing_remark" maxlength="100" style="width:350px; height:100px; border:solid 1px #abafb8; "></textarea>
			</div>
    	</div>
		<div class="my_submitClear" data-options="region:'south',border:false"	style="margin: 5px;height: 34px;" align="center">
			  <a href="javascript:void(0)" id="msg_sure" class="easyui-linkbutton wait_run my_submit">同意</a> 
			  <a href="javascript:void(0)" id="msg_no_sure" class="easyui-linkbutton my_submit">不同意</a> 

			  <a href="javascript:void(0)" id="msg_cancel" class="easyui-linkbutton my_clear">取消</a>
		  </div>
	</div>
</div>	
</body>
</html>
