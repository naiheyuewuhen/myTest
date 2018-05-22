$(document).ready(function(){
	$('#user_dg').datagrid({
    	url : loadUserUrl+"page",
    	queryParams:{status:1},
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
				{field : 'roleId',title : '角色id',hidden : true,checkbox : false},
				{field : 'password',title : '密码',hidden : true,checkbox : false},
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
				{field : 'username',title : '登录用户名',width : '10%',halign:'center',align:'center'},
				{field : 'name',title : '姓名',width : '10%',halign:'center',align:'center'},
				{field : 'sex',title : '性别',width : '5%',halign:'center',align:'center',formatter : function(value, row, index) {
	                if(value == 1)
	                {
	                    return '男';
	                }
	                else
	                {
	                    return '女';
	            	}
				}},
				{field : 'phone',title : '手机号',width : '10%',halign:'center',align:'center'},
				{field : 'roleName',title : '角色',width : '15%',halign:'center',align:'center'},
				{field : 'updateUser',title : '最后修改人',width : '10%',halign:'center',align:'center'},
				{field : 'updateTime',title : '最后修改时间',width : '10%',halign:'center',align:'center'},
				{field : 'remark',title : '备注信息',width : '15%',halign:'center',align:'center'}
		] ],
        onBeforeLoad:function(param){
        	
        },
        onLoadSuccess:function(data){
        	
        }
    });
    /*获取角色下拉列表*/
	$('#roleId').combobox({
		url:loadRoleUrl,
		queryParams: {"status" : 1},
    	valueField:'id',
    	textField:'name'
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
		 	p['status']=1;
		 	$('#user_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
				$('#ff').form('reset');
				$("#id").val("0");
				$('#roleId').combobox('clear');
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
//    	var p = {};
		var value = $(this).attr("v");
		$('#user_dg').datagrid('selectRow', value);
		var row = $('#user_dg').datagrid('getSelected');
    	$('#ff').form('load',row);
		/*
		获取合同内容
    	$.post(getGoodsUrl,{id: row.id},function(goodsInfo){
    		$('#ff').form('load',goodsInfo);
    	},'json');
		*/
    	
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
    	$.post(saveUserUrl,param,function(data){
    		$.messager.progress('close');
    		if(data.success){
    			$.messager.alert('信息', '保存成功', 'info', function() {
    				$('#dlg').dialog('close');
					$('#user_dg').datagrid('reload');
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