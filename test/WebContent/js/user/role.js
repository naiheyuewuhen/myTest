$(document).ready(function(){
	var modules=[];
	$.post(getModulesUrl,function(data){
		$.extend(true, modules, data);
	},'json');
	$('#role_dg').datagrid({
    	url : loadRoleUrl+"page",
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
				{field : 'x',title : '操作',width : '15%',halign:'center',align:'center',formatter : function(value, row, index) {
	                if(row.status == 1)
	                {
	                    return '<div class="operationItem"><span class="update" v="' + index + '" title="编辑"></span><i></i>'/*+'<span class="_ck more1" v="' + index + '" title="查看" ></span>'*/+'</div>';
	                }
	                else
	                {
	                    return '<div class="operationItem"></em><i></i>'/*+'<span class="_ck more1" v="' + index + '" title="查看" ></span>'*/+'</div>';
	            	}
				}},
				{field : 'name',title : '名称',width : '15%',halign:'center',align:'center'},
				{field : 'createUser',title : '创建人',width : '10%',halign:'center',align:'center'},
				{field : 'createTime',title : '创建时间',width : '15%',halign:'center',align:'center'},
				{field : 'updateUser',title : '最后修改人',width : '10%',halign:'center',align:'center'},
				{field : 'updateTime',title : '最后修改时间',width : '15%',halign:'center',align:'center'},
				{field : 'remark',title : '备注信息',width : '15%',halign:'center',align:'center'}
		] ],
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
		 	$('#role_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
				$('#ff').form('reset');
				$("#id").val("0");
			}
		});
	$("#cancel").bind('click',function(){
		$('#dlg').dialog('close');
	});
    //新增
    $('#_add').bind('click',function(){
    	$('#dlg').dialog('setTitle','新增').dialog('open');
    });
    //编辑
    $(document).on("click",".update",function(){
    	$('#dlg').dialog('setTitle','修改').dialog('open');
		var value = $(this).attr("v");
		$('#role_dg').datagrid('selectRow', value);
		var row = $('#role_dg').datagrid('getSelected');
    	$('#ff').form('load',row);
    });
    //权限分配
    $("#modulePermission").on("click",function(){
		var row = $('#role_dg').treegrid('getSelected');
		if(row)
		{
			$("#roleDlg").dialog({
				title : "模块权限设置",
				width : "80%",
				height : "100%",
				closed: false,
				cache: false,
				modal: true,
				toolbar:[{
					text:'展开树',
					iconCls:'tree-folder-open',
					handler:function(){
						$("#roleTree").tree("expandAll");
						}
					},
					{
					text:'关闭树',
					iconCls:'tree-folder',
					handler:function(){
						$("#roleTree").tree("collapseAll");
						}
					},
					{
					text:'刷新',
					iconCls:'icon-reload',
					handler:function(){
						$.post(getAuthorityUrl,{roleId: row.id},function(data1){
				    		$.post(getModulesUrl,function(data2){
									$.extend(true, modules, data2);
									checkedDataByIdInArray(data2,data1);
					    			$("#roleTree").tree({data: data2});
								},'json');
				    		},'json');
						}
					},
					{
					text:'<span id="qx">全选</span>',
					iconCls:'icon-ok',
					handler:function(){
						var roots = $("#roleTree").tree('getRoots');//返回tree的所有根节点数组
						var qx = $("#qx").html();
						if ("全选"==qx) {
							 $("#qx").html("取消全选");
							for ( var i = 0; i < roots.length; i++) {
								$("#roleTree").tree('check', roots[i].target);//将得到的节点选中
							}
						} else {
							 $("#qx").html("全选");
							for ( var i = 0; i < roots.length; i++) {
								$("#roleTree").tree('uncheck', roots[i].target);
							}
						}
					}
				}],
				buttons : [{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						 $.messager.progress({ 
						   	    title: '请等待', 
						   	    msg: '数据处理中...', 
						   	    text: ' ' 
						   	  });
						var add = new Array();
						var target = $("#roleTree").tree("getChecked", ['checked','indeterminate']);
						for(var j = 0; j < target.length; j++)
						{					
								add.push(target[j].id);
						}
						$.post(saveAuthorityUrl,{roleId : row.id,moduleIds : add},function(d){
							if (d.success) 
							{
								 $.messager.progress('close');
								$.messager.alert('信息', '设置成功！', 'info', function(){
									$('#roleDlg').dialog('close');
								});
							} 
							else 
							{
								 $.messager.progress('close');
								$.messager.show({
									title : '错误',
									msg : d.msg
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
				},{
					text:'取消',
					iconCls:'icon-remove',
					handler:function(){
						$("#roleDlg").dialog("close");
					}
				}]
			});
	    	$.post(getAuthorityUrl,{roleId: row.id},function(data1){
	    		if(modules.length==0){
	    			$.post(getModulesUrl,function(data2){
						$.extend(true, modules, data2);
						checkedDataByIdInArray(data2,data1);
		    			$("#roleTree").tree({data: data2});
					},'json');
	    		}else{
	    			var data_modules = [];
            		$.extend(true, data_modules, modules);
	    			checkedDataByIdInArray(data_modules,data1);
	    			$("#roleTree").tree({data: data_modules});
	    		}
	    	},'json').error(function(e){
		            $.messager.progress('close');
		            $.messager.show({
		                title : '错误',
		                msg : '服务器异常'
		            });
		        });
		}
		else
		{
			$.messager.alert('信息', '请先选择一个角色', 'info', function(){});
		}
	});
	//权限模块
	$("#roleTree").tree({
//		url:getModulesUrl,
		checkbox : true,
		lines : true,
		animate : true,
		onLoadSuccess : function(node, data){
		}
	});
    
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
    	$.post(saveRoleUrl,param,function(data){
    		$.messager.progress('close');
    		if(data.success){
    			$.messager.alert('信息', '保存成功', 'info', function() {
    				$('#dlg').dialog('close');
					$('#role_dg').datagrid('reload');
    			});
    		}else{
    			$.messager.show({
                    title : '错误',
                    msg : '保存失败，请稍后重试！'
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
    
});