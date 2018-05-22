/**
 * 
 */
$(document).ready(function(){
	$("#goods_group_dg").datagrid({
		url : loadGoodsGroupUrl+"page",
		remoteSort : false,
		singleSelect : true,
		rownumbers : true,
		fit:true,
		loadMsg:'正在处理，请稍后...',
		autoRowHeight : false,
		columns : [ [
				{field : 'id',title : '主键',hidden : true},
				{field : 'x',title : '操作',width : '20%',halign:'center',align:'center',formatter: function(value,row,index){
	                	return '<div class="operationItem"><span class="update" v='+index+' title="编辑"></span><em class="delete" v='+index+' title="删除"></em></div>';
	                }
				},
				{field : 'code',title : '项目指标编码',width : '20%',halign:'center'}, 
				{field : 'name',title : '项目指标名称',width : '20%',halign:'center'}, 
				{field : 'remark',title : '备注信息',width : '35%',halign:'center'}
		] ]
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
			$('#goods').combogrid('reset');
			$('#goods').combogrid({data:[]});
        }
	});
	//项目指标明细
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
			{field : 'x',title : '操作',width : '15%',halign:'center',align:'center',formatter: function(value,row,index){
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
            {field : 'goodsCode',title : '编码',width : '20%',halign:'center'}, 
			{field : 'goodsName',title : '名称',width : '20%',halign:'center'}, 
			{field : 'goodsStandard',title : '规格',width : '20%',halign:'center',align:'center'}, 
			{field : 'goodsNum',title : '数量',width : '15%',halign:'center',editor:{
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
		 	$('#goods_group_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
				$('#groupId').val('0');
				$("#code").textbox('setValue','');
				$("#name").textbox('setValue','');
				$("#remark").textbox('setValue','');
				$('#goods').combogrid('reset');
				$('#goods').combogrid({data:[]});
				$("#goods_array").datagrid({data:[]});
			}
		});
	$("#cancel").bind('click',function(){
		$('#dlg').dialog('close');
	});
    //新增
    $('#_add').bind('click',function(){
	    $('#dlg').dialog('setTitle','新增项目指标').dialog('open');
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
    function save(status){
		var groupId=$("#groupId").val();
    	var code=$.trim($("#code").textbox('getValue'));
    	var name=$.trim($("#name").textbox('getValue'));
    	var remark=$.trim($("#remark").textbox('getValue'));
    	var goodsRows=$("#goods_array").datagrid('getRows');
    	if(code.length==0){
    		$.messager.alert('提示', '请输入指标编码');
    		return;
    	}else if(name.length==0){
    		$.messager.alert('提示', '请输入指标名称');
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
    				$.messager.alert('提示', '请填写有效的数量');
    				blog=false;
    				return false;
    			}
    			var goods={};
    			goods.id=(v.id)?parseInt(v.id):0;
	    		goods.groupId=groupId.length>0?parseInt(groupId):0;
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
		     	param.id = groupId>0?groupId:0;
	     		param.code=code;
	     		param.name=name;
	     		param.remark=remark;
	     		param.status=1;
	     		param.goodsList=goodsList;
		     	
		    	$.post(saveGoodsGroupUrl,param,function(data){
		    		$.messager.progress('close');
		    		if(data.success){
		    			$.messager.alert('信息', '保存成功', 'info', function() {
		    				$('#dlg').dialog('close');
							$('#goods_group_dg').datagrid('reload');
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
    $(document).on("click",".update",function(){
    	$('#dlg').dialog('setTitle','编辑项目指标').dialog('open');
		var rowIndex = $(this).attr("v");
		$('#goods_group_dg').datagrid('selectRow', rowIndex);
		var row = $('#goods_group_dg').datagrid('getSelected');
		$('#groupId').val(row.id);
		/*获取项目指标内容*/
    	$.post(getGoodsGroupByIdUrl,{id: row.id},function(data2){
    		$('#code').textbox('setValue',data2.code);
    		$('#name').textbox('setValue',data2.name);
    		$('#remark').textbox('setValue',data2.remark);
    		/*设置项目指标明细*/
    		$("#goods_array").datagrid('loadData',data2.goodsGroupDetailList);
    	},'json');
    });
    //删除（只能删除状态为没有完成的记录）
    $(document).on("click",".delete",function(){
		var rowIndex = $(this).attr("v");
		$('#goods_group_dg').datagrid('selectRow', rowIndex);
		var row = $('#goods_group_dg').datagrid('getSelected');
		$.messager.confirm('确认对话框', '确定要删除【'+row.code+'】吗？', function(r){
			if (r){
			    // 进行删除
				$.getJSON(deleteGoodsGroupUrl,{id:row.id},function(data3){
					if(data3.success){
						$('#goods_group_dg').datagrid('reload');
					}else{
						$.messager.show({
			                title : '错误',
			                msg : '服务器异常'
			            });
					}
				});
				}
		});
    });
});