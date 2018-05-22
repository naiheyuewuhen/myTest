$(document).ready(function(){
	var goodsArr=[];
	var checkedArr=[];
	$('#supplier_dg').datagrid({
    	url : loadSupplierUrl+"page",
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
				{field : 'name',title : '供应商',width : '15%',halign:'center',sortable:true},
				{field : 'phone',title : '电话',width : '15%',halign:'center',align:'center'},
				{field : 'address',title : '地址',width : '15%',halign:'center'},
				{field : 'createTime',title : '创建时间',width : '15%',halign:'center',align:'center'},
				{field : 'sortNum',title : '序号',width : '10%',halign:'center',align:'center'},
				{field : 'remark',title : '备注信息',width : '15%',halign:'center',align:'center'}
		] ],
        onBeforeLoad:function(param){
        	
        },
        onLoadSuccess:function(data){
        	
        }
    });
    $.getJSON(getGoodsUrl,function(data){
    	goodsArr=data;
//    	$("#goods_tree").treegrid({data:data});
    });
    $("#goods_tree").treegrid({
//    	url:getGoodsUrl,
//    	loadMsg:'数据加载中...',
    	nowrap:true,
    	showHeader:true,
    	checkbox:true,
//    	rownumbers:true,
    	idField:'id',
    	treeField:'code',
    	columns:[[
    		{field:'goodsSupplierId',hidden:true},
    		{field:'price',hidden:true},
	        {field:'code',title:'编码',width:'30%',halign:'center',align:'left'},    
	        {field:'name',title:'名称',width:'25%',halign:'center',align:'center'},    
	        {field:'standard',title:'规格',width:'25%',halign:'center',align:'center'},    
	        {field:'remark',title:'备注',width:'20%',halign:'center',align:'center'}    
	    ]],
	    onLoadSuccess:function(row, data){
	    	$.each(checkedArr,function(i,v){
	    		$('#goods_tree').treegrid('update',{
					id: v.goodsId,
					row: {
						goodsSupplierId:v.id,
						price:v.price
					}
				});
				if(v.status==1){
		    		$("#goods_tree").treegrid('checkNode',v.goodsId);
				}
	    	});
	    	$(".load_tree").remove();
	    }
//	    onCheckNode:function(row,checked){
//	    	console.log("前："+JSON.stringify(checkedArr));
//	    	if(checked){
//	    		var goodsSupplier={};
//	    		goodsSupplier.goodsId=row.id;
//	    		if(row.goodsSupplierId){
//	    			goodsSupplier.id=row.goodsSupplierId;
//	    		}else{
//	    			goodsSupplier.id=0;
//	    		}
//	    		if(row.price){
//		    		goodsSupplier.price=row.price;
//	    		}else{
//	    			goodsSupplier.price=0;
//	    		}
//	    		checkedArr.push(goodsSupplier);
//	    		console.log("是："+JSON.stringify(checkedArr));
//	    	}else{
//	    		checkedArr=$.map(checkedArr, function(goodsSupplier){
//				  return goodsSupplier.goodsId == row.id ? null : goodsSupplier;
//				});
//				console.log("否："+JSON.stringify(checkedArr));
//	    	}
//	    }
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
		 	$('#supplier_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
				$('#ff').form('reset');
				$("#supplierId").val("0");
				checkedArr=[];
				$("#goods_tree").treegrid('clearChecked');
				$("#goods_tree").treegrid({data:[]});
			}
		});
	$("#cancel").bind('click',function(){
		$('#dlg').dialog('close');
	});
    //新增
    $('#_add').bind('click',function(){
    	$('#dlg').dialog('setTitle','新增').dialog('open');
		$("<div class=\"datagrid-mask load_tree\"></div>").css({ display: "block", width: "100%", height: "100%","text-align":"center" }).appendTo("#loadgoods");
    	$("<div class=\"datagrid-mask-msg load_tree\"></div>").html("数据加载中，请稍待...").appendTo("#loadgoods").css({ display: "block","left":"15%" });
	
    	setTimeout(function(){
	    	if(goodsArr.length>0){
	    		var goodsData=[];
	    		goodsData=$.extend(true,goodsData,goodsArr);//深度复制一份goodsArr数据到goodsData
		    	$("#goods_tree").treegrid({data:goodsData});
	    	}else{
	    		$.getJSON(getGoodsUrl,function(data){
	    			$.extend(true,goodsArr,data);//深度复制一份data数据到goodsArr
			    	$("#goods_tree").treegrid({data:data});
			    });
	    	}
   		},0);
    });
    //编辑
    $(document).on("click",".update",function(){
    	$('#dlg').dialog('setTitle','修改').dialog('open');
    	$("<div class=\"datagrid-mask load_tree\"></div>").css({ display: "block", width: "100%", height: "100%","text-align":"center" }).appendTo("#loadgoods");
    	$("<div class=\"datagrid-mask-msg load_tree\"></div>").html("数据加载中，请稍待...").appendTo("#loadgoods").css({ display: "block","left":"15%" });
	
    	var p = {};
		var value = $(this).attr("v");
		$('#supplier_dg').datagrid('selectRow', value);
		var row = $('#supplier_dg').datagrid('getSelected');
		        	
		/*获取合同内容*/
    	$.post(getSupplierUrl,{id: row.id},function(supplier){
    		checkedArr=supplier.goodsSupplierInfoList;
    		setTimeout(function(){
		    	if(goodsArr.length>0){
		    		var goodsData=[];
		    		goodsData=$.extend(true,goodsData,goodsArr);//深度复制一份goodsArr数据到goodsData
			    	$("#goods_tree").treegrid({data:goodsData});
		    	}else{
		    		$.getJSON(getGoodsUrl,function(data){
		    			$.extend(true,goodsArr,data);//深度复制一份data数据到goodsArr
				    	$("#goods_tree").treegrid({data:data});
				    });
		    	}
	   		},0);
    		
    		$('#ff').form('load',supplier);
    	},'json');
		
    	
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
    	var goodsChecked=$("#goods_tree").treegrid('getCheckedNodes');
    	var goodsSupplierList=[];
    	$.each(goodsChecked,function(i,v){
    		var goodsSupplier={};
    		goodsSupplier.goodsId=v.id;
    		if(v.goodsSupplierId){
    			goodsSupplier.id=v.goodsSupplierId;
    		}else{
    			goodsSupplier.id=0;
    		}
    		if(v.price){
	    		goodsSupplier.price=v.price;
    		}else{
    			goodsSupplier.price=0;
    		}
    		goodsSupplierList.push(goodsSupplier);
    	});
    	param += "&goodsSupplierList="+JSON.stringify(goodsSupplierList);
    	$.post(saveSupplierUrl,param,function(data){
    		$.messager.progress('close');
    		if(data.success){
    			$.messager.alert('信息', '保存成功', 'info', function() {
    				$('#dlg').dialog('close');
					$('#supplier_dg').datagrid('reload');
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