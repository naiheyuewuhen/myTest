$(document).ready(function(){
	$('#pay_dg').datagrid({
    	url : loadContractPayStandardUrl+"page",
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
	                    return '<div class="operationItem"><span class="update" v="' + index + '" title="编辑"></span><i></i>'/*+'<span class="_ck more1" v="' + index + '" title="查看" ></span>'*/+'</div>';
				}},
				{field : 'startDate',title : '起始日期',width : '15%',halign:'center',align:'center'},
				{field : 'endDate',title : '截止日期',width : '15%',halign:'center',align:'center'},
				{field : 'payNo1',title : '第1笔款（%）',width : '10%',halign:'center',align:'center'},
				{field : 'payNo2',title : '第2笔款（%）',width : '10%',halign:'center',align:'center'},
				{field : 'payNo3',title : '第3笔款（%）',width : '10%',halign:'center',align:'center'},
				{field : 'payNo4',title : '第4笔款（%）',width : '10%',halign:'center',align:'center'},
				{field : 'payNo5',title : '第5笔款（%）',width : '10%',halign:'center',align:'center'}
		] ],
        onBeforeLoad:function(param){
        	
        },
        onLoadSuccess:function(data){
        	
        }
    });
    $('.payNo').numberbox({
    	required:true,
    	value:0,
    	min:0,
    	max:100,
    	prompt:'百分比...'
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
		 	$('#pay_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
				$('#ff').form('reset');
				$("#payId").val("0");
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
    	var p = {};
		var value = $(this).attr("v");
		$('#pay_dg').datagrid('selectRow', value);
		var row = $('#pay_dg').datagrid('getSelected');
		
		$('#ff').form('load',row);
		        	
    	/*$.post(getGoodsUrl,{id: row.id},function(goodsInfo){
    		$('#ff').form('load',goodsInfo);
    	},'json');*/
		
    	
    });
    
    //保存
    $('#save').bind('click',function(){
    	if(!$('#ff').form('validate'))
    	{
    		return;
    	}
    	var s=0;
    	$('.payNo').each(function(){
    		var v=$(this).numberbox('getValue');
    		s +=parseInt(v);
		});
		if(s!=100){
			$.messager.alert('提示', '合计不是100%');
			return;
		}
    	
    	$.messager.progress({ 
     	    title: '请等待', 
     	    msg: '数据处理中...', 
     	    text: ' ' 
     	});
    	var param = $("#ff").serialize();
    	$.post(saveContractPayStandardUrl,param,function(data){
    		$.messager.progress('close');
    		if(data.success){
    			$.messager.alert('信息', '保存成功', 'info', function() {
    				$('#dlg').dialog('close');
					$('#pay_dg').datagrid('reload');
    			});
    		}else{
    			$.messager.show({
                    title : '错误',
                    msg : data.msg
             	});
    		}
    	},'json');
    });
    
});