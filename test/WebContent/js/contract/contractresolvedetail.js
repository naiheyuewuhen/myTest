/**
 * 
 */
$(document).ready(function(){
	$("#contract_resolve_dg").datagrid({
		url : loadContractResolveUrl+"page",
		remoteSort : false,
		singleSelect : true,
		rownumbers : true,
		fit:true,
		loadMsg:'正在处理，请稍后...',
		autoRowHeight : false,
		columns : [ [
			{field : 'id',title : '主键',hidden : true},
			{field : 'contract_id',title : '合同id',hidden : true},
			{field : 'x',title : '操作',width : '10%',halign:'center',align:'center',formatter: function(value,row,index){
					if(row.status==1||row.status==2){//1-拆解完成;2-拆解未完成（暂存）
//	                	return '<div class="operationItem"><span class="oprate update" v='+index+' title="编辑"></span><em class="delete" v='+index+' title="删除"></em></div>';
                		return '<div class="operationItem"><strong class="oprate" v='+index+' title="技术拆解"></strong></div>';
            		}else{
            			return '';
            		}
                }
			},
			{field : 'contractCode',title : '合同编号',width : '15%',halign:'center'}, 
			{field : 'resolveName',title : '拆解名称',width : '15%',halign:'center'},
            {field : 'createUser',title : '创建人',width : '9%',halign:'center'}, 
            {field : 'createTime',title : '创建时间',width : '15%',halign:'center'}, 
            {field : 'updateUser',title : '最后修改人',width : '9%',halign:'center'}, 
            {field : 'updateTime',title : '最后修改时间',width : '15%',halign:'center'}, 
            {field : 'status',title : '状态',width : '10%',halign:'center',formatter: function(value,row,index){
            		if(value==1){
            			return '拆解完成';
            		}else if(value==2){
            			return '拆解未完成';
            		}else if(value==3){
            			return '供应商确认中';
            		}else if(value==4){
            			return '供应商确认完成';
            		}else if(value==5){
            			return '已生成采购单';
            		}else{
            			return '';
            		}
            	}
            }
		] ]
	});
	/*搜索项目指标*/
	$('#group').combogrid({
		panelWidth:380,
	    delay: 500,
	    mode: 'remote',
	    rownumbers:true,
	    url: getGroupUrl,
	    idField: 'id',
	    textField: 'code',
	    columns: [[
	        {field:'code',title:'编码',width:120,sortable:true},    
	        {field:'name',title:'名称',width:120,sortable:true}
	    ]],
	    onSelect:function(index,row){
	    	$.getJSON(getGroupByIdUrl,{id:row.id},function(data){
	    		var goodsGroupDetail=data.goodsGroupDetailList;
	    		$.each(goodsGroupDetail,function(i,goods){
            		var detail = {goodsId:goods.goodsId,goodsCode:goods.goodsCode,goodsName:goods.goodsName,goodsStandard:goods.goodsStandard,goodsNum:goods.goodsNum};
					$('#goods_array').datagrid('insertRow',{
						index: 0,	// 索引从0开始
						row: detail
					});
            	});
				$('#group').combogrid('clear');
				$('#group').combogrid({data:[]});
            });
        }
	});
	/*搜索物品*/
	$('#goods').combogrid({
		panelWidth:380,
	    delay: 500,
	    mode: 'remote',
	    rownumbers:true,
	    url: getGoodsUrl,
	    idField: 'id',
	    textField: 'code',
	    columns: [[
	        {field:'code',title:'编码',width:120,sortable:true},    
	        {field:'name',title:'名称',width:120,sortable:true},   
	        {field:'standard',title:'规格',width:100,sortable:true}    
	    ]],
	    onSelect:function(index,row){
	    	var detail = {goodsId:row.id,goodsCode:row.code,goodsName:row.name,goodsStandard:row.standard,goodsNum:1};
			$('#goods_array').datagrid('insertRow',{
				index: 0,	// 索引从0开始
				row: detail
			});
			$('#goods').combogrid('clear');
			$('#goods').combogrid({data:[]});
        }
	});
	//拆解明细
	$("#goods_array").datagrid({
		remoteSort : false,
		singleSelect : true,
		rownumbers : true,
		fit:true,
		loadMsg:'正在处理，请稍后...',
		autoRowHeight : false,
		columns : [ [
				{field : 'id',title : '主键',hidden : true},
				{field : 'goodsId',title : '物品id',hidden : true},
				{field : 'x',title : '操作',width : '10%',halign:'center',align:'center',formatter: function(value,row,index){
	                	if (row.editing)
	               	 	{          		 
	               		 	return '<div class="operationItem"><span class="edit_save" title="保存"></span><em class="edit_clean" title="取消"></em></div>';
	               	 	}
	                	else
	               	 	{			            		 
	                		return '<div class="operationItem"><span class="edit_update" title="编辑"></span><em class="edit_delete" title="删除"></em></div>';
	               	 	}
	                }
				},
	            {field : 'goodsCode',title : '编码',width : '15%',halign:'center'}, 
				{field : 'goodsName',title : '名称',width : '15%',halign:'center'}, 
				{field : 'goodsStandard',title : '规格',width : '15%',halign:'center',align:'center'}, 
				{field : 'goodsNum',title : '数量',width : '10%',halign:'center',editor:{
		           	 type:'numberbox',
		             options:{
		            	 min:0,
		            	 max:9999999,
		            	 precision:0,
		            	 groupSeparator:',',
		            	 required:true
		             }
	                },formatter:function(value,row,index){
	                	if($.isNumeric(value)&&value>0)
	                	{
	                		return value;
	                	}
	                	else
	                	{
	                		return '';
	                	}
	                }
	            }
		] ],
		onBeforeEdit:function(index,row){
            row.editing = true;
            $('#goods_array').datagrid('refreshRow', index);
        },
		onAfterEdit:function(index,row){		     
            row.editing = false;
            $('#goods_array').datagrid('refreshRow', index);
        },
        onCancelEdit:function(index,row){
            row.editing = false;
            $('#goods_array').datagrid('refreshRow', index);
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
		 	$('#contract_resolve_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
				$('#resolveId').val('0');
				$('#contractId').val('0');
				$("#contractCode").text('');
				$("#contractMainFiles").empty();
				$("#contractAttachmentFiles").empty();
				$("#resolveName").textbox('setValue','');
			}
		});
	$("#cancel").bind('click',function(){
		$('#dlg').dialog('close');
	});
    $(document).on("click",".edit_update",function(){
    	if($(".datagrid-row-editing").size()>0){
    		$.messager.alert('提示', '有其他数据未保存');
    	}else{
    		var rowIndex = $(this).parents("tr").attr("datagrid-row-index");	
    		$("#goods_array").datagrid('beginEdit',rowIndex);
    	}
    });
    $(document).on("click",".edit_delete",function(){
    	var rowIndex = $(this).parents("tr").attr("datagrid-row-index");
    	$.messager.confirm('确认对话框', '确定要删除该条数据吗？', function(r){
    		if (r){
    			$("#goods_array").datagrid('deleteRow',rowIndex);
    			//下面这两行，为了解决删除，行号以及index不自动更新的bug
    			var data=$("#goods_array").datagrid('getData');
    			$("#goods_array").datagrid('loadData',data);
    		}
    	});
    });
    $(document).on("click",".edit_save",function(){
    	var rowIndex = $(this).parents("tr").attr("datagrid-row-index");
    	if($("#goods_array").datagrid('validateRow',rowIndex)){
    		$("#goods_array").datagrid('endEdit',rowIndex);
    	}else{
    		$.messager.alert('提示', '信息填写错误');
    	}
    });
    $(document).on("click",".edit_clean",function(){
    	var rowIndex = $(this).parents("tr").attr("datagrid-row-index");	
    	$("#goods_array").datagrid('cancelEdit',rowIndex);
    });
    
    //保存按钮
    $('#save').bind('click',function(){
    	save(1);
    });
    //暂存按钮
    $('#transient_save').bind('click',function(){
    	save(2);
    });
    function save(status){
		var resolveId=$("#resolveId").val();
    	var contractId=$("#contractId").val();
    	var resolveName=$.trim($("#resolveName").textbox('getValue'));
    	var goodsRows=$("#goods_array").datagrid('getRows');
    	if(contractId.length==0){
    		$.messager.alert('提示', '没有选择合同');
    		return;
    	}else if(resolveName.length==0){
    		$.messager.alert('提示', '请填写拆解名称');
    		return;
    	}else if(goodsRows.length==0){
    		$.messager.alert('提示', '请设置原材料');
    		return;
    	}else if($(".datagrid-row-editing").size()>0){
    		$.messager.alert('提示', '有其他数据还未保存');
    		return;
    	}else{
    		var goodsArr=[];
    		var blog=true;
    		$.each(goodsRows,function(i,v){
    			if(v.goodsNum<=0){
    				$.messager.alert('提示', '第'+(i+1)+'行，填写的数量有误');
    				blog=false;
    				return false;
    			}
    			var goods={};
    			goods.id=(v.id)?parseInt(v.id):0;
	    		goods.resolveId=resolveId.length>0?parseInt(resolveId):0;
    			goods.contractId=parseInt(contractId);
    			goods.goodsId=v.goodsId;
    			goods.goodsNum=v.goodsNum;
    			goodsArr.push(goods);
    		});
    		if(blog){
    			$.messager.progress({ 
		     	    title: '请等待', 
		     	    msg: '数据处理中...', 
		     	    text: ' ' 
		     	});
		     	var goodsList=JSON.stringify(goodsArr);
		     	var param={};
		     	param.id = resolveId>0?resolveId:0;
	     		param.contractId=contractId;
	     		param.resolveName=resolveName;
	     		param.status=status;
	     		param.goodsList=goodsList;
		     	
		    	$.post(saveContractResolveUrl,param,function(data){
		    		$.messager.progress('close');
		    		if(data.success){
		    			$.messager.alert('信息', '保存成功', 'info', function() {
		    				$('#dlg').dialog('close');
							$('#contract_resolve_dg').datagrid('reload');
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
			     	
    		}else{
    			return;
    		}
    	}
    }
    //编辑
    $(document).on("click",".oprate",function(){
    	$('#dlg').dialog('setTitle','技术拆解').dialog('open');
		var rowIndex = $(this).attr("v");
		$('#contract_resolve_dg').datagrid('selectRow', rowIndex);
		var row = $('#contract_resolve_dg').datagrid('getSelected');
		$('#resolveId').val(row.id);
		$('#contractId').val(row.contractId);
		/*获取合同*/
		getContract(row.contractId);
		/*获取合同拆解内容*/
    	$.post(getContractResolveUrl,{id: row.id},function(data2){
    		$('#resolveName').textbox('setValue',data2.resolveName);
    		/*设置拆解明细*/
    		$("#goods_array").datagrid('loadData',data2.contractGoodsList);
    	},'json');
    });
    //删除（只能删除状态为没有完成的记录）
    $(document).on("click",".delete",function(){
		var rowIndex = $(this).attr("v");
		$('#contract_resolve_dg').datagrid('selectRow', rowIndex);
		var row = $('#contract_resolve_dg').datagrid('getSelected');
		if(row.status==2){
			$.messager.confirm('确认对话框', '确定要删除【'+row.resolveName+'】吗？', function(r){
				if (r){
				    // 进行删除
					$.getJSON(deleteContractResolveUrl,{id:row.id},function(data3){
						if(data3.success){
							$('#contract_resolve_dg').datagrid('reload');
						}else{
							$.messager.show({
				                title : '错误',
				                msg : '服务器异常'
				            });
						}
					});
					}
			});
		}else{
			//已经完成的不能删除
			$.messager.alert('提示', '已经完成的不能删除');
		}
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