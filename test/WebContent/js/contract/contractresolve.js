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
		queryParams: {
			status: '6'
		},
		columns : [ [
				{field : 'id',title : '主键',hidden : true,checkbox : false},
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
        	if(row.status==6){//可拆分状态
        	    $("#_add").menubutton("enable");
        	}else{
        	    $("#_add").menubutton("disable");
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
			var p={status: '6'};
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
				$('#ff').form('reset');
				$(".sub_resolve").parents("tr").remove();
				$('#contractId').val('0');
				$("#contractCode").text('');
				$("#contractMainFiles").empty();
				$("#contractAttachmentFiles").empty();
			}
		});
	$("#cancel").bind('click',function(){
		$('#dlg').dialog('close');
	});
    //拆分
    $('#_add').bind('click',function(){
    	var row = $('#contract_dg').datagrid('getSelected');
    	if(row&&row.status==6){
    		$('#contractId').val(row.id);
    		$("#contractCode").text(row.code);
    		$('#dlg').dialog('setTitle','合同拆分').dialog('open');
    		getContract(row.id);
    		
    		/*获取合同分解内容*/
	    	$.post(getContractResolveUrl,{contractId: row.id},function(resolves){
	    		//约定付款列表
	    		if(resolves.length>0){
		    		$.each(resolves,function(i,v){
		    			if(i==0){
		    				$("input[data-name='resolveId']").val(v.id);
		    				$("input[data-name='resolveName']").textbox('setValue',v.resolveName);
		    			}else{
				    		var tr = '<tr>'
			    			+'<td>'+(i+1)+'</td>'
							+'<td style="text-align: right;">拆解名称:</td>'
							+'<td><input data-name="resolveId" type="hidden" value="'+v.id+'"/>'
			    			+	 '<input data-name="resolveName" type="text" class="easyui-textbox" data-options="required:true,maxlength:\'20\',prompt:\'请输入拆分名称...\',value:\''+v.resolveName+'\'" style="width: 200px; height: 30px;"/></td>'
							+'<td style="padding-left: 20px;"><a title="删除" href="javascript:void(0);" class="easyui-linkbutton sub_resolve" data-options="plain:true,iconCls:\'icon-remove\'"></a></td>'
							+'</tr>';
							$(tr).appendTo("#table_resolve");
		    			}
		    		});
	    		}
	    		$.parser.parse($(".sub_resolve").parents("tr"));//重新渲染
	    	},'json');
    	}else{
    		$.messager.alert('提示', '请先选择合同');
    	}
    });
    //添加拆解名称
    $('#add_resolve').bind('click',function(){
    	var size=$("#table_resolve").find(".sub_resolve").size();
    	var tr = '<tr>'
    			+'<td>'+(size+2)+'</td>'
    			+'<td style="text-align: right;">拆解名称:</td>'
				+'<td><input data-name="resolveId" type="hidden" value="0"/>'
    			+	 '<input data-name="resolveName" type="text" class="easyui-textbox" data-options="required:true,maxlength:\'20\',prompt:\'请输入拆分名称...\'" style="width: 200px; height: 30px;"/></td>'
				+'<td style="padding-left: 20px;"><a title="删除" href="javascript:void(0);" class="easyui-linkbutton sub_resolve" data-options="plain:true,iconCls:\'icon-remove\'"></a></td>'
				+'</tr>';
		$(tr).appendTo("#table_resolve");
    	$.parser.parse($("#table_resolve").find("tr").last());//重新渲染最后一个tr
    });
    //删除一个拆解名称
    $('#table_resolve').on('click','.sub_resolve',function(){
    	$(this).parents("tr").remove();
    	$("#table_resolve").find(".sub_resolve").each(function(index,v){
    		$(this).parents("tr").find("td").first().text(index+2);
    		}
    	);
    });
    
    //保存按钮
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
     	var contract_id=$('#contractId').val();
    	var arr=[];
    	$("#table_resolve").find("tr").each(function(index,value){
    		var json={};
    		json.id=$(value).find("input[data-name='resolveId']").val();
    		json.resolveName=$(value).find("input[data-name='resolveName']").textbox('getValue');
	    	arr.push(json);
    	});
    	var resolves=JSON.stringify(arr);
    	$.post(saveBatchContractResolveUrl,{contractId:contract_id,resolveList:resolves},function(data){
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
    /*获取合同*/
    function getContract(contractId){
    	$.post(getContractWithFilesByIdUrl,{id: contractId},function(data){
    		$("#contractCode").text(data.code);
    		var contractFileList=data.contractFileList;
    		$.each(contractFileList,function(index,value){
    			if(value.fileType=='main'){
	    			$("#contractMainFiles").append('<a href="javascript:void(0);" class="title downfile" data-id="'+value.uploadFileId+'">'+value.fileName+'</a>');
    			}else{
	    			$("#contractAttachmentFiles").append('<a href="javascript:void(0);" class="title downfile" data-id="'+value.uploadFileId+'">'+value.fileName+'</a>');
    			}
    		});
    	},'json');
    }
    
    //下载文件
    $(document).on("click",".downfile",function(){
		var fileId = $(this).attr("data-id");
		window.open(fileDownloadUrl+"?id="+fileId);
    });
});