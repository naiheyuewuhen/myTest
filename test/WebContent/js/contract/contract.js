/**
 * 
 */
$(document).ready(function(){
	$('#contract_dg').datagrid({
    	url : loadContractUrl+"page",
		method : 'POST',
		remoteSort : false,
		singleSelect : true,
		rownumbers : true,
		nowrap : true,
		pagination : true,//底部显示分页工具栏
		fit:true,
		loadMsg:'正在处理，请稍后...',
		autoRowHeight : false,
		columns : [ [
				{field : 'id',title : '主键',hidden : true,checkbox : false},
				{field : 'x',title : '操作',width : '10%',halign:'center',align:'center',formatter : function(value, row, index) {
	                if(row.status == 0 || row.status == 2)
	                {
	                    return '<div class="operationItem"><span class="update" v="' + index + '" title="编辑"></span><i></i>'+'<span class="_ck more1" v="' + index + '" title="查看" ></span>'+'</div>';
	                }
	                else
	                {
	                    return '<div class="operationItem"><span class="update" style="display: none;" v="' + index + '" title="编辑"></span><i></i>'+'<span class="_ck more1" v="' + index + '" title="查看" ></span>'+'</div>';
	            	}	
				}},
				{field : 'code',title : '合同编号',width : '10%',halign:'center'}, 
				{field : 'contractType',title : '标准合同',width : '7%',halign:'center',align:'center',formatter: function(value,row,index){
					if(row.contractType==1){
						return '是';
					}else{
		            	return '否';
		            }
				}}, 
				{field : 'contractDate',title : '签约日期',width : '8%',halign:'center',align:'center',sortable:true}, 
				{field : 'firstParty',title : '甲方（单位）',width : '15%',halign:'center'}, 
				{field : 'firstLinkman',title : '甲方联系人',width : '8%',halign:'center',align:'center'}, 
				{field : 'firstPhone',title : '甲方联系电话',width : '10%',halign:'center',align:'center'}, 
				{field : 'secondParty',title : '乙方（单位）',width : '15%',halign:'center'}, 
				{field : 'secondLinkman',title : '乙方联系人',width : '8%',halign:'center',align:'center'}, 
				{field : 'secondPhone',title : '乙方联系电话',width : '10%',halign:'center',align:'center'},
				{field : 'fatherId',title : '合同类型',width : '10%',halign:'center',align:'center',formatter: function(value,row,index){
					if(row.fatherId>0){
						return '补充合同';
					}else{
		            	return '';
		            }
				}}, 
				{field : 'createUser',title : '创建人',width : '10%',halign:'center',align:'center'}, 
				{field : 'createTime',title : '创建时间',width : '15%',halign:'center',align:'center'}, 
				{field : 'auditor',title : '审批人',width : '10%',halign:'center',align:'center'}, 
				{field : 'auditingTime',title : '审批时间',width : '15%',halign:'center',align:'center'},
				{field : 'auditingRemark',title : '审批意见',width : '10%',halign:'center',align:'center'},
				{field : 'status',title : '合同状态',width : '10%',halign:'center',align:'center',formatter : function(value, row, index) {
					switch(value){
						case 0:return '待审批';
						case 1:return '已审核通过';
						case 2:return '审核未通过';
						case 3:return '完成';
						case 4:return '合同已取消';
						case 5:return '一级审核通过';
						case 6:return '可拆解状态';
						case 7:return '拆解进行中';
						case 8:return '合同拆解完成';
						case 9:return '供应商确认中';
						case 10:return '供应商确认完成';
						default:return '';
					}
				}}
		] ],
		onSelect:function(index,row){
			var v=$("#auditing").attr("data-v");
        	if(row.status==0||row.status==2||row.status==5||(row.status==1&&v==2)){//0-未审核；1-审核完成；2-审核未通过（可以重新审核）；5-一级审核完成；
        		$("#auditing_id").val(row.id);
        		$("#contract_type").val(row.contractType);
        		$("#contract_auditing").text(row.code);
        	    $("#auditing").menubutton("enable");
        	}else{
        		$("#auditing_id").val('');
        		$("#contract_type").val('');
        		$("#contract_auditing").text('');
        	    $("#auditing").menubutton("disable");
        	}
        },
        onBeforeLoad:function(param){
        	
        },
        onLoadSuccess:function(data){
        	
        }
    });
    
    /*搜索框*/
    $("#search").searchbox({
        searcher : function(value,name){
	        var searchName = $('#search').searchbox('getName');
			var searchValue = $('#search').searchbox('getValue');
			var p={};
			if (searchValue)
			{	
	           p[searchName] = searchValue;      
		 	}
		 	$('#contract_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
//				$('#ff').form('clear');//不能清空file表单
				$('#ff').form('reset');
//				$('#upfile').val('');
				$("#upload").textbox({required:true});
				$("#upload2").textbox({required:true});
				$("#contractId").val("0");
				$(".sub_agreed_payment").parents("tr").remove();
				$("#file_arr").empty();
				$("#file_arr2").empty();
				$(".reality_add").remove();
				$("#reality_payment_div").hide();
				$("#save").show();
			}
		});
	$("#cancel").bind('click',function(){
		$('#dlg').dialog('close');
	});
    //新增
    $('#_add').bind('click',function(){
    	$('#dlg').dialog('setTitle','新增合同').dialog('open');
    	/*获取合同下拉列表*/
    	$.post(loadContractUrl,{father_id: 0},function(jsonstr){
    		jsonstr.unshift({
    			'id': '0',
    			'code': '主合同'
    			});//向json数组开头添加自定义数据
    		$('#contractFather').combobox({
    			data:jsonstr,
		    	valueField:'id',
		    	textField:'code'
	    	});
	    	$('#contractFather').combobox('setValue', '0');
    	},'json');
    });
    //查看
    $(document).on("click","._ck",function(){
    	$("#reality_payment_div").show();
    	$("#save").hide();
    	$(this).prev().prev().click();
    });
    //编辑
    $(document).on("click",".update",function(){
    	$('#dlg').dialog('setTitle','修改合同').dialog('open');
    	var p = {};
		var value = $(this).attr("v");
		$('#contract_dg').datagrid('selectRow', value);
		var row = $('#contract_dg').datagrid('getSelected');
		        	
		/*获取合同内容*/
    	$.post(getContractUrl,{id: row.id},function(contractInfo){
//    		$('#ff').form('load',contractInfo);
    		var paymentAgreedList=contractInfo.paymentAgreedList;
    		var paymentRealityList=contractInfo.paymentRealityList;
    		var contractFileList=contractInfo.contractFileList;
    		//约定付款列表
    		if(paymentAgreedList.length>0){
	    		$.each(paymentAgreedList,function(i,v){
	    			if(i==0){
	    				$("input[data-name='agreed_id']").val(v.id);
	    				$("input[data-name='agreed_amount']").numberbox('setValue',v.amount);
	    				$("input[data-name='agreed_paymentMode']").textbox('setValue',v.paymentMode);
	    				$("input[data-name='agreed_paymentDate']").datebox('setValue',v.paymentDate);
	    				
//	    				$("#agreed_id_1").val(v.id);
//	    				$("#agreed_amount_1").numberbox('setValue',v.amount);
//	    				$("#agreed_paymentMode_1").textbox('setValue',v.paymentMode);
//	    				$("#agreed_paymentDate_1").datebox('setValue',v.paymentDate);
	    			}else{
			    		var tr = '<tr>'
		    			+'<td>'+(i+1)+'</td>'
		    			+'<td><input data-name="agreed_id" type="hidden" value="'+v.id+'"/>'
		    			+	 '<input data-name="agreed_amount" type="text" class="easyui-numberbox" data-options="required:true,min:0.01,precision:2,width:150,height:30,maxlength:15,prompt:\'长度在1-15之间\',value:'+v.amount+'" /></td>'
						+'<td><input data-name="agreed_paymentMode" type="text" class="easyui-textbox" data-options="required:true,maxlength:20,width:150,height:30,prompt:\'付款方式...\',value:\''+v.paymentMode+'\'" /></td>'
						+'<td><input data-name="agreed_paymentDate" type="text" class="easyui-datebox" data-options="required:true,editable:false,width:150,height:30,prompt:\'付款时间...\',value:\''+v.paymentDate+'\'"/></td>'
						+'<td style="padding-left: 20px;"><a title="删除" href="javascript:void(0);" class="easyui-linkbutton sub_agreed_payment" data-options="plain:true,iconCls:\'icon-remove\'"></a></td>'
						+'</tr>';
						$(tr).appendTo("#agreed_payment");
	    			}
	    		});
    		}
//    		$.parser.parse("#payment");//重新渲染（重新渲染datebox空间，会导致$("#agreed_paymentDate_1").datebox('setValue',v.paymentDate)赋值出错）
    		$.parser.parse($(".sub_agreed_payment").parents("tr"));//重新渲染
    		//收款列表
    		if(paymentRealityList.length>0){
	    		$.each(paymentRealityList,function(i,v){
		    		var tr = '<tr class="reality_add">'
							+'<td>'+(i+1)+'</td>'
							+'<td><input data-name="reality_amount" type="text" class="easyui-numberbox" data-options="required:true,editable:false,min:0.01,precision:2,width:150,height:30,maxlength:15,prompt:\'长度在1-15之间\',value:'+v.amount+'" /></td>'
							+'<td><input data-name="reality_paymentMode" type="text" class="easyui-textbox" data-options="required:true,editable:false,maxlength:20,width:150,height:30,prompt:\'收款方式...\',value:\''+v.paymentMode+'\'" /></td>'
							+'<td><input data-name="reality_paymentDate" type="text" class="easyui-datebox" data-options="required:true,editable:false,width:150,height:30,prompt:\'收款时间...\',value:\''+v.paymentDate+'\'"/></td>'
							+'</tr>';
					$(tr).insertBefore("#reality_tr");
					
	    		});
	    		$.parser.parse(".reality_add");//重新渲染最后一个tr
    		}
    		if(contractFileList.length>0){
    			var main_flag=false;
    			var attachment_flag=false;
    			$.each(contractFileList,function(i,v){
	    			var li='<li data-id="'+v.id+'" data-file_id="'+v.uploadFileId+'" data-file_name="'+v.fileName+'" data-file_url="'+v.fileUrl+'" data-create_time="'+v.createTime+'"><span>'+v.fileName+'</span><span style="float: right;"><span class="up_file_time">'+v.createTime+'</span><a class="del" href="javascript:void(0);" title="删除">X</a></span></li>';
							if(v.fileType=="attachment"){
								attachment_flag=true;
								$(li).appendTo("#file_arr2");
							}else{
								main_flag=true;
								$(li).appendTo("#file_arr");
							}
	    		});
	    		if(main_flag){
		    		$("#upload").textbox({required:false});
	    		}
	    		if(attachment_flag){
		    		$("#upload2").textbox({required:false});
	    		}
    		}
    		/*获取合同下拉列表*/
	    	$.post(loadContractUrl,{father_id: 0},function(jsonstr){
	    		$.each(jsonstr,function(i,v){
	    			if(v.id==row.id){
	    				jsonstr.splice(i,1);//移除自身
	    				return false;
	    			}
	    		});
	    		jsonstr.unshift({
	    			'id': '0',
	    			'code': '主合同'
	    			});//向json数组开头添加自定义数据
	    		$('#contractFather').combobox({
	    			data:jsonstr,
			    	valueField:'id',
			    	textField:'code'
		    	});
		    	$('#ff').form('load',contractInfo);
	    	},'json');
    	},'json');
		
    	
    });
    //添加付款方式
    $('#add_agreed_payment').bind('click',function(){
    	var size=$("#agreed_payment").find(".sub_agreed_payment").size();
    	var tr = '<tr>'
    			+'<td>'+(size+2)+'</td>'
				+'<td><input data-name="agreed_id" type="hidden" value="0"/>'
    			+	 '<input data-name="agreed_amount" type="text" class="easyui-numberbox" data-options="required:true,min:0.01,precision:2,width:150,height:30,maxlength:15,prompt:\'长度在1-15之间\'" /></td>'
				+'<td><input data-name="agreed_paymentMode" type="text" class="easyui-textbox" data-options="required:true,maxlength:20,width:150,height:30,prompt:\'付款方式...\'" /></td>'
				+'<td><input data-name="agreed_paymentDate" type="text" class="easyui-datebox" data-options="required:true,editable:false,width:150,height:30,prompt:\'付款时间...\'"/></td>'
				+'<td style="padding-left: 20px;"><a title="删除" href="javascript:void(0);" class="easyui-linkbutton sub_agreed_payment" data-options="plain:true,iconCls:\'icon-remove\'"></a></td>'
				+'</tr>';
		$(tr).appendTo("#agreed_payment");
    	$.parser.parse($("#agreed_payment").find("tr").last());//重新渲染最后一个tr
    });
    //删除一个付款方式
    $('#agreed_payment').on('click','.sub_agreed_payment',function(){
    	$(this).parents("tr").remove();
    	$("#agreed_payment").find(".sub_agreed_payment").each(function(index,v){
    		$(this).parents("tr").find("td").first().text(index+2);
    		}
    	);
    });
    //删除上传的合同文本文件
    $('#file_arr').on('click','.del',function(){
    	$(this).parents("li").remove();
    	if($("#file_arr li").size()==0){
			 $("#upload").textbox({required:true});
		}
    });
    //删除上传的合同附件文件
    $('#file_arr2').on('click','.del',function(){
    	$(this).parents("li").remove();
    	if($("#file_arr2 li").size()==0){
			 $("#upload2").textbox({required:true});
		}
    });
    //上传合同文件
    $("#upload").textbox({
    	onClickButton:function(){
    		$("#upfile").click();
    	}
    });
    //上传合同附件
    $("#upload2").textbox({
    	onClickButton:function(){
    		$("#upfile2").click();
    	}
    });
    //上传合同文本
    $("#upfile").change(function(){
    	fileupload("");
    });
    //上传合同附件
    $("#upfile2").change(function(){
    	fileupload("2");
    });
    function fileupload(flag){
    	var files=document.getElementById("upfile"+flag).files;
    	if(files.length>0){
	    	var formData=new FormData();
	    	for(i=0;i<files.length;i++){
    			formData.append("file",files[i]);
	    	}
//    		console.log(JSON.stringify(formData));
		    $.ajax({
		        url: fileUploadUrl,
		        type: 'POST',
		        cache: false,
		        data: formData,
		        processData: false,
		        contentType: false,
		        dataType:'json',
		        success: function(data){
		        	if(data.success){
		        		var upFiles=data.data;
		        		$.each(upFiles,function(i,v){
		        			var li='<li data-id="0" data-file_id="'+v.id+'" data-file_name="'+v.fileName+'" data-file_url="'+v.fileUrl+'" data-create_time="'+v.createTime+'"><span>'+v.fileName+'</span><span style="float: right;"><span class="up_file_time">'+v.createTime+'</span><a class="del" href="javascript:void(0);" title="删除">X</a></span></li>';
							$(li).appendTo("#file_arr"+flag);
		        		});
		        		if($("#file_arr"+flag+" li").size()>0){
		        			 $("#upload"+flag).textbox({required:false});
		        		}
		        	}
		        },
		        error:function(){
		        	
		        }
		    });  
    	}
    }
    
    //保存
    $('#save').bind('click',function(){
    	if(!$('#ff').form('validate'))
    	{
    		return;
    	}
    	$.messager.progress({ 
     	    title: '请等待', 
     	    msg: '数据处理中...', 
     	    text: ' ' 
     	});
    	var param = $("#ff").serialize();
    	var arr=[];
    	$("#agreed_payment").find("tr:not(.head)").each(function(index,value){
    		var json={};
    		json.id=$(value).find("input[data-name='agreed_id']").val();
    		json.amount=$(value).find("input[data-name='agreed_amount']").numberbox('getValue');
    		json.paymentMode=$(value).find("input[data-name='agreed_paymentMode']").textbox('getValue');
    		json.paymentDate=$(value).find("input[data-name='agreed_paymentDate']").datebox('getValue');
	    	arr.push(json);
    		
    		
//    		var json={};
//	    	$(value).find("input[data-name]").each(function(i,v){
//	    		json[$(v).attr("data-name")]=$(v).attr("value");
//    		});
//    		if(!$.isEmptyObject(json)){
//	    		arr.push(json);
//    		}
    	});
    	var upfileArr=[];
    	$("#file_arr").find("li").each(function(index,value){
    		var upfile={};
    		upfile.id=$(this).attr("data-id");
    		upfile.uploadFileId=$(this).attr("data-file_id");
    		upfile.fileName=$(this).attr("data-file_name");
    		upfile.fileUrl=$(this).attr("data-file_url");
    		upfile.createTime=$(this).attr("data-create_time");
    		upfile.fileType="main";
    		upfileArr.push(upfile);
    	});
    	$("#file_arr2").find("li").each(function(index,value){
    		var upfile={};
    		upfile.id=$(this).attr("data-id");
    		upfile.uploadFileId=$(this).attr("data-file_id");
    		upfile.fileName=$(this).attr("data-file_name");
    		upfile.fileUrl=$(this).attr("data-file_url");
    		upfile.createTime=$(this).attr("data-create_time");
    		upfile.fileType="attachment";
    		upfileArr.push(upfile);
    	});
    	var payments=JSON.stringify(arr);
    	var upfiles=JSON.stringify(upfileArr);
    	$.post(saveContractUrl,param+"&payments="+payments+"&upfiles="+upfiles,function(data){
    		$.messager.progress('close');
    		if(data.success){
    			$.messager.alert('信息', '保存成功', 'info', function() {
    				$('#dlg').dialog('close');
					$('#contract_dg').datagrid('reload');
    			});
    		}else{
    			$.messager.show({
                    title : '错误',
                    msg : data.data
             	});
    		}
    		
    	},'json').error(function(e){
		            $.messager.progress('close');
		            $.messager.show({
		                title : '错误',
		                msg : '服务器异常'
		            });
    		});

    });
       //收款
    $('#add_reality_payment').bind('click',function(){
    	var am=$("#reality_tr").find("input[data-name='reality_amount']").numberbox('getValue');
    	var pm=$("#reality_tr").find("input[data-name='reality_paymentMode']").textbox('getValue');
    	var pd=$("#reality_tr").find("input[data-name='reality_paymentDate']").datebox('getValue');
    	if(am.length==0||am==0||pm.length==0||pd.length==0){
    		$.messager.alert('信息', '请将信息填写完整', 'info', function() {});
    	}else{
	    	$.messager.progress({ 
	     	    title: '请等待', 
	     	    msg: '数据处理中...', 
	     	    text: ' ' 
	     	});
			var payment={};
			payment.contractId=$("#contractId").val();
			payment.amount = am;
			payment.paymentMode = pm;
			payment.paymentDate = pd;
	    	$.post(savePaymentUrl,payment,function(data){
	    		$.messager.progress('close');
	    		if(data.success){
	    			$.messager.alert('信息', '保存成功', 'info', function() {
	//    				$('#dlg').dialog('close');
	    				var size=$(".reality_add").size();
						var tr = '<tr class="reality_add">'
								+'<td>'+(size+1)+'</td>'
								+'<td><input data-name="reality_amount" type="text" class="easyui-numberbox" data-options="required:true,editable:false,min:0.01,precision:2,width:150,height:30,maxlength:15,prompt:\'长度在1-15之间\',value:'+payment.amount+'" /></td>'
								+'<td><input data-name="reality_paymentMode" type="text" class="easyui-textbox" data-options="required:true,editable:false,maxlength:20,width:150,height:30,prompt:\'收款方式...\',value:\''+payment.paymentMode+'\'" /></td>'
								+'<td><input data-name="reality_paymentDate" type="text" class="easyui-datebox" data-options="required:true,editable:false,width:150,height:30,prompt:\'收款时间...\',value:\''+payment.paymentDate+'\'"/></td>'
								+'</tr>';
						$(tr).insertBefore("#reality_tr");
						$.parser.parse($("#reality_tr").prev());//重新渲染最后一个tr
						$("#reality_tr").find("input[data-name='reality_amount']").numberbox('clear');
						$("#reality_tr").find("input[data-name='reality_paymentMode']").textbox('clear');
						$("#reality_tr").find("input[data-name='reality_paymentDate']").datebox('clear');
						$('#contract_dg').datagrid('reload');
	    			});
	    		}else{
	    			$.messager.show({
	                    title : '错误',
	                    msg : data.data
	             	});
	    		}
	    	},'json').error(function(e){
			            $.messager.progress('close');
			            $.messager.show({
			                title : '错误',
			                msg : '服务器异常'
			            });
	    		});
    	
    	}
    });
    
    //审批按钮
    $("#auditing").click(function(){
    	var id=$("#auditing_id").val();
    	if(id.length>0){
	    	var auditing_remark=$("#auditing_remark").val('');
		    $('#msg_confirm').dialog('open');
    	}
    });
    
    //审批同意
    $("#msg_sure").click(function(){
    	var v=$("#auditing").attr("data-v");
    	if(v==2){//有二级审批权限
	    	auditingChange(1);//审批通过
    	}else {
    		var contract_type=$("#contract_type").val();
    		if(contract_type==1){//标准合同
    			auditingChange(1);//审批通过
    		}else{
	    		auditingChange(5);//一级审批通过（非标准合同，需要两级审批）
    		}
    	}
    });
    
    //审批不同意
    $("#msg_no_sure").click(function(){
    	auditingChange(2);
    });
    //审批操作
    function auditingChange(status){
    	var id=$("#auditing_id").val();
    	var auditing_remark=$("#auditing_remark").val();
    	$.messager.progress({ 
		   	    title: '请等待', 
		   	    msg: '数据处理中...', 
		   	    text: ' ' 
		   	  });
		   	  
		$.post(auditingContractUrl,{id:id,status:status,auditingRemark:auditing_remark},function(data){
		   	  	$.messager.progress('close');
    			if(data.success)
    			{
    				$.messager.alert('信息','保存成功'); 
    				$('#msg_confirm').dialog('close');
    				$("#auditing_id").val('');
	        		$("#contract_auditing").text('');
	        	    $("#auditing").menubutton("disable");
    				$('#contract_dg').datagrid('reload');
    			}else{
	    			$.messager.show({
	                    title : '错误',
	                    msg : data.msg
	                });
	    		}
    		},'json').error(function(e){
    			$.messager.progress('close');
    			$.messager.alert('信息', '处理数据时连接发生错误！', 'info', function(){});
    			$('#msg_confirm').dialog('close');
    		});
    }
    
    $("#msg_cancel").click(function(){
    	$('#msg_confirm').dialog('close');
    });
    
});