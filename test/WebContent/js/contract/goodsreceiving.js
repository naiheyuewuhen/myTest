/**
 * 
 */
$(document).ready(function(){
	/*获取合同下拉列表*/
	$('#contract_select').combobox({
		url:loadContractUrl,
		queryParams: {"statusArr" : "9,10"},//9:供应商确认中；10:供应商确认完成
    	valueField:'id',
    	textField:'code',
    	onLoadSuccess:function(){
    		if($("#contract_select").combobox('getData').length>0){
	        	$("#contract_select").combobox("setValue",$("#contract_select").combobox('getData')[0].id);
	        }
    	},
    	onChange:function(newValue,oldValue){
    		$("#search").searchbox('clear');
    		if(newValue){
    			$('#order').combobox({
					url:getPurchaseOrderUrl,
					queryParams: {"contractId" : newValue},
			    	valueField:'id',
			    	textField:'orderNo',
			    	onSelect: function(rec){    
			            $("#supplier_dg").datagrid({
							url : getSupplierUrl,
							queryParams:{id:rec.supplierId},
							remoteSort : false,
							singleSelect : true,
							rownumbers : true,
							fit:true,
							loadMsg:'正在处理，请稍后...',
							autoRowHeight : false,
							columns : [ [
									{field : 'id',title : '主键',hidden : true},
									{field : 'x',title : '操作',width : '10%',halign:'center',align:'center',formatter: function(value,row,index){
						                	return '<div class="operationItem"><strong class="oprate" v='+index+' title="收货"></strong></div>';
						                }
									},
									{field : 'name',title : '名称',width : '15%',halign:'center'},
									{field : 'phone',title : '电话',width : '15%',halign:'center'},
						            {field : 'address',title : '地址',width : '30%',halign:'center'}, 
						            {field : 'createTime',title : '创建时间',width : '15%',halign:'center'}, 
						            {field : 'remark',title : '备注信息',width : '9%',halign:'center'}
							] ]
						});
			        }
    			});
    			
    		}
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
				{field : 'contractId',title : '合同id',hidden : true},
				{field : 'goodsId',title : '物品id',hidden : true},
				{field : 'supplierId',title : '供应商id',hidden : true},
	            {field : 'contractCode',title : '合同编号',width : '10%',halign:'center'}, 
	            {field : 'supplierName',title : '供应商',width : '10%',halign:'center'}, 
	            {field : 'goodsCode',title : '物品编码',width : '10%',halign:'center'}, 
				{field : 'goodsName',title : '物品名称',width : '10%',halign:'center'}, 
				{field : 'goodsStandard',title : '规格',width : '10%',halign:'center',align:'center'}, 
				{field : 'goodsTotalNum',title : '订购总数量',width : '8%',halign:'center'}, 
				{field : 'goodsSumNum',title : '已接收数量',width : '8%',halign:'center'}, 
	            {field : 'goodsNum',title : '本次收货数量',width : '10%',halign:'center',editor:{
		           	 type:'numberbox',
		             options:{
//		            	 min:0,
		            	 precision:0,
		            	 groupSeparator:','
//		            	 required:true
		             }
	                },formatter:function(value,row,index){
	                	if($.isNumeric(value))
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
		onSelect:function(index, row){
			console.log(JSON.stringify(row));
			if(row.goodsSumNum<row.goodsTotalNum){
				$('#goods_array').datagrid('beginEdit', index);
			}
		},
        onBeginEdit:function(index, row){
        	var editor_num = $("#goods_array").datagrid('getEditor', { index: index, field: 'goodsNum' });
			var $numberbox = $(editor_num.target);
//			$numberbox.numberbox({max:row.goodsTotalNum-row.goodsSumNum});
			$numberbox.numberbox().next('span').find('input').focus();
        },
        onEndEdit:function(index, row, changes){
        	var num = $("#goods_array").datagrid('getEditor', { index: index, field: 'goodsNum' });
			var $numberbox = $(num.target);
			row.goodsNum=$numberbox.numberbox('getValue');
        },
		onAfterEdit:function(index,row){		     
            $('#goods_array').datagrid('refreshRow', index);
        }
	});
	//结束编辑
    $(document).on("blur",".numberbox input",function(){
    	var $_this=$(this);
    	var goodsNum=$(this).val();
		var rowIndex=$(this).parents("tr.datagrid-row").attr("datagrid-row-index");
		var row=$('#goods_array').datagrid('getRows')[rowIndex];
		var maxNum=row.goodsTotalNum-row.goodsSumNum;
		if(goodsNum>maxNum){
			$.messager.alert('提示', '不能大于'+maxNum,'info',function(){
				$_this.focus();
			});
		}else if(goodsNum<0&&goodsNum*(-1)>row.goodsSumNum){//输入负数时，
			$.messager.alert('提示', '输入数据有误','info',function(){
				$_this.focus();
			});
		}else{
			$('#goods_array').datagrid('endEdit', rowIndex);
		}
    });
	
    /*搜索框*/
    $("#search").searchbox({
        searcher : function(value,name){
        	var contractId=$('#contract_select').combobox('getValue');
	        var searchName = $('#search').searchbox('getName');
			var searchValue = $('#search').searchbox('getValue');
			var p={};
			if (searchValue)
			{	
	           p[searchName] = searchValue;      
		 	}
		 	p['contractId']=contractId;
		 	$('#supplier_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    /*搜索框*/
    $("#search2").searchbox({
        searcher : function(value,name){
	        var searchName = $('#search2').searchbox('getName');
			var searchValue = $('#search2').searchbox('getValue');
			var p={};
			if (searchValue)
			{	
	           p[searchName] = searchValue;      
		 	}
//		 	$('#supplier_dg').datagrid('reload',p);
        },
        menu : "#mm2",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
//				$("#resolveId").val('');
//				$("#contractCode").text('');
//				$("#contractFiles").empty();
				$('#search').searchbox('clear');
			}
		});
	$("#cancel").bind('click',function(){
		$('#dlg').dialog('close');
	});
	
	
	//收货
    $(document).on("click",".oprate",function(){
    	$('#dlg').dialog('setTitle','物品签收').dialog('open');
		var rowIndex = $(this).attr("v");
		$('#supplier_dg').datagrid('selectRow', rowIndex);
		var row = $('#supplier_dg').datagrid('getSelected');
		var contractId=$("#contract_select").combobox("getValue");
		var orderId=$("#order").combobox("getValue");
		/*获取合同拆解内容*/
		
		$.post(getReceivingForOrderUrl,{id: orderId},function(data2){
    		/*设置拆解明细*/
    		$("#goods_array").datagrid('loadData',data2.purchaseOrderDetailList);
    	},'json');
		
		
		
    	/*$.post(getReceivingForContractUrl,{contractId:contractId,supplierId: row.id},function(data2){
    		设置拆解明细
    		$("#goods_array").datagrid('loadData',data2);
    	},'json');*/
    });
	
    //保存按钮
    $('#save').bind('click',function(){ 
    	save();
    });
    function save(){
    	var goodsRows=$("#goods_array").datagrid('getRows');
    	var goodsArr=[];
		$.each(goodsRows,function(i,row){
			if(row.goodsNum&&row.goodsNum!=0){
				var goods={};
    			goods.contractId=row.contractId;
    			goods.goodsId=row.goodsId;
    			goods.supplierId=row.supplierId;
    			goods.goodsNum=row.goodsNum;
    			goodsArr.push(goods);
			}
		});
    	if(goodsArr.length>0){
    		$.post(saveUrl,{goodsReceivings:JSON.stringify(goodsArr)},function(data){
		    		$.messager.progress('close');
		    		if(data.success){
		    			$.messager.alert('信息', '保存成功', 'info', function() {
		    				$('#dlg').dialog('close');
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
    		$.messager.alert('提示', '没有录入有效的收货数量');
    	}
    }
});