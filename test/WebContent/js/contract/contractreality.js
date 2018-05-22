/**
 * 
 */
$(document).ready(function(){
	/*获取合同下拉列表*/
	$('#contract_select').combobox({
		url:loadContractUrl,
		queryParams: {"statusArr" : "8,9,10"},//8:合同拆解完成；9:供应商确认中；10:供应商确认完成
    	valueField:'id',
    	textField:'code',
    	onLoadSuccess:function(){
    		if($("#contract_select").combobox('getData').length>0){
	        	$("#contract_select").combobox("setValue",$("#contract_select").combobox('getData')[0].id);
	        }
    	},
    	onChange:function(newValue,oldValue){
    		$("#search").searchbox('clear');
    		$("#contract_reality_dg").datagrid({
				url : loadContractResolveUrl+"page",
				queryParams:{contractId:newValue},
				remoteSort : false,
				singleSelect : true,
				checkOnSelect:false,
				selectOnCheck:false,
				rownumbers : true,
				fit:true,
				loadMsg:'正在处理，请稍后...',
				autoRowHeight : false,
				columns : [ [
						{field : 'id',title : '主键',halign:'center',align:'center',checkbox:true},
						{field : 'contractId',title : '合同id',hidden : true},
						{field : 'x',title : '操作',width : '6%',halign:'center',align:'center',formatter: function(value,row,index){
			                	if (row.status==1||row.status==3||row.status==4)//1：有效（拆解完成）；3：供应商确认中；4：供应商确认完成'
			               	 	{
									return '<div class="operationItem"><strong class="oprate" v='+index+' title="编辑"></strong></div>';
			               	 	}
			                	else
			               	 	{			            		 
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
				] ],
				onBeforeCheck:function(index, row){
					if(row.status!=4){
						return false;
					}
				},
				onCheckAll:function(rows){
					$.each(rows,function(i,v){
						if(v.status!=4){//4：供应商确认完成'
							$("#contract_reality_dg").datagrid('uncheckRow', i);        
						}
					});
				}
			});
    	}
	});
	//新增
    $('#_add').bind('click',function(){
    	var rows=$('#contract_reality_dg').datagrid('getChecked');
    	if(rows.length>0){
    		var resolveIds='';
	    	$.each(rows,function(i,v){
	    		if(resolveIds.length>0){
	    			resolveIds+=','+v.id;
	    		}else{
	    			resolveIds=v.id+'';
	    		}
			});
			/*生成采购单*/
	    	$.post(buildorderUrl,{resolveIds: resolveIds,contractId:rows[0].contractId},function(data){
	    		if(data.success){
		    			$.messager.alert('信息', '生成成功', 'info', function() {
		    				$('#dlg').dialog('close');
							$('#contract_reality_dg').datagrid('reload');
		    			});
		    		}else{
		    			$.messager.show({
		                    title : '错误',
		                    msg : data.data
		             	});
		    		}
	    	},'json');
    	}else{
    		$.messager.alert('提示', '您还没有勾选');
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
				/*{field : 'supplierIdsAdvise',title : '推荐供应商ids',hidden : true},*/
				{field : 'supplierIdReality',title : '确定供应商id',hidden : true,formatter:function(value,row,index){
	                	if(row.supplierIdReality/*&&$.inArray(row.supplierIdReality+'', row.supplierIdsAdvise.split(','))>=0*/)
	                	{
	                		return value;
	                	}
	                	else
	                	{
	                		return '';
	                	}
	                }},
				{field : 'x',title : '操作',width : '10%',halign:'center',align:'center',formatter: function(value,row,index){
	                	if (row.editing)
	               	 	{          		 
	               		 	return '<div class="operationItem"><span class="edit_save" title="保存"></span><em class="edit_clean" title="取消"></em></div>';
	               	 	}
	                	else
	               	 	{			            		 
	                		return '<div class="operationItem"><span class="edit_update" title="编辑"></span></div>';
	               	 	}
	                }
				},
	            {field : 'goodsCode',title : '编码',width : '15%',halign:'center'}, 
				{field : 'goodsName',title : '名称',width : '15%',halign:'center'}, 
				{field : 'goodsStandard',title : '规格',width : '10%',halign:'center',align:'center'}, 
				{field : 'goodsNum',title : '数量',width : '8%',halign:'center'}, 
	            /*{field : 'supplierNamesAdvise',title : '推荐供应商',width : '25%',halign:'center',align:'center'},*/
	            {field : 'price',title : '价格',width : '8%',halign:'center',align:'center',editor:{
		           	 type:'numberbox',
		             options:{
		            	 min:0,
		            	 max:9999999,
		            	 precision:2,
		            	 groupSeparator:',',
		            	 required:true
		             }
	                },formatter:function(value,row,index){
	                	if($.isNumeric(value)&&value>=0/*&&row.supplierIdReality&&$.inArray(row.supplierIdReality+'', row.supplierIdsAdvise.split(','))>=0*/)
	                	{
	                		return value;
	                	}
	                	else
	                	{
	                		row.price='';
	                		return '';
	                	}
	                }
	            },
				{field : 'supplierNameReality',title : '确认供应商',width : '15%',halign:'center',align:'center',editor:{
		           	 type:'combobox',
		             options:{
		            	 valueField:'supplierId',
		            	 textField:'supplierName',
		            	 required:true,
		            	 onSelect: function(rec){
		            	 	var rowIndex=$(this).parents("tr.datagrid-row").attr("datagrid-row-index");
		            	 	var editor_price = $("#goods_array").datagrid('getEditor', { index: rowIndex, field: 'price' });
            				var $numberbox = $(editor_price.target);
            				$numberbox.numberbox('setValue', rec.price);
				        }
		             }
	                }
	            }
		] ],
		onBeforeEdit:function(index,row){
            row.editing = true;
            $('#goods_array').datagrid('refreshRow', index);
        },
        onBeginEdit:function(index, row){
        	var editor = $("#goods_array").datagrid('getEditor', { index: index, field: 'supplierNameReality' });
            var $combobox = $(editor.target);
            $.getJSON(getGoodsSupplierByGoodsIdUrl,{goodsId:row.goodsId/*,supplierIds:row.supplierIdsAdvise*/},function(data1){
	            $combobox.combobox({data:data1});
	            if(row.supplierIdReality/*&&$.inArray(row.supplierIdReality+'', row.supplierIdsAdvise.split(','))>=0*/){
		            $combobox.combobox('setValue', row.supplierIdReality);
	            }else{
	            	row.supplierIdReality='';
	            	row.price='';
	            	$combobox.combobox('clear');
	            }
            });
        },
        onEndEdit:function(index, row, changes){
        	var editor_price = $("#goods_array").datagrid('getEditor', { index: index, field: 'price' });
			var $numberbox = $(editor_price.target);
			row.price=$numberbox.numberbox('getValue');
        	
        	var editor = $("#goods_array").datagrid('getEditor', { index: index, field: 'supplierNameReality' });
            var $combobox = $(editor.target);
            row.supplierIdReality=$combobox.combobox('getValue');
            row.supplierNameReality=$combobox.combobox('getText');
            $combobox.combobox('clear');
	    	$combobox.combobox({data:[]});
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
		 	var contractId=$('#contract_select').combobox('getValue');
		 	p['contractId']=contractId;
		 	$('#contract_reality_dg').datagrid('reload',p);
        },
        menu : "#mm",
        prompt : "查询条件"
    });
    //关闭窗口
    $('#dlg').dialog({
			onClose:function(){
				$("#resolveId").val('');
				$("#contractId").val('');
				$("#contractCode").text('');
				$('#resolveName').text('');
				$("#contractMainFiles").empty();
				$("#contractAttachmentFiles").empty();
				$('#search').searchbox('clear');
			}
		});
	$("#cancel").bind('click',function(){
		$('#dlg').dialog('close');
	});
    $(document).on("click",".edit_update",function(){
    	if($(".datagrid-row-editing").size()>0){
    		$.messager.alert('提示', '有其他数据未保存');
    	}else{
    		var rowIndex = $(this).parents("tr.datagrid-row").attr("datagrid-row-index");	
    		$("#goods_array").datagrid('beginEdit',rowIndex);
    	}
    });
    $(document).on("click",".edit_delete",function(){
    	var rowIndex = $(this).parents("tr.datagrid-row").attr("datagrid-row-index");
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
    	var rowIndex = $(this).parents("tr.datagrid-row").attr("datagrid-row-index");
    	if($("#goods_array").datagrid('validateRow',rowIndex)){
    		$("#goods_array").datagrid('endEdit',rowIndex);
    	}else{
    		$.messager.alert('提示', '信息填写错误');
    	}
    });
    $(document).on("click",".edit_clean",function(){
    	var rowIndex = $(this).parents("tr.datagrid-row").attr("datagrid-row-index");	
    	$("#goods_array").datagrid('cancelEdit',rowIndex);
    });
    
    //保存按钮
    $('#save').bind('click',function(){ 
    	save(4);
    });
    //暂存按钮
    $('#transient_save').bind('click',function(){
    	save(3);
    });
    function save(status){
		var resolveId=$("#resolveId").val();
		var contractId=$("#contractId").val();
    	var goodsRows=$("#goods_array").datagrid('getRows');
    	if(resolveId.length==0){
//    		$.messager.alert('提示', '没有选择合同');
    		return;
    	}else if($(".datagrid-row-editing").size()>0){
    		$.messager.alert('提示', '有其他数据还未保存');
    		return;
    	}else{
    		var goodsArr=[];
    		var blog=true;
    		$.each(goodsRows,function(i,v){
    			if(!v.price){
    				$.messager.alert('提示', '第'+(i+1)+'行，价格有误');
    				blog=false;
    				return false;
    			}else if(!v.supplierIdReality){
    				$.messager.alert('提示', '第'+(i+1)+'行，供应商有误');
    				blog=false;
    				return false;
    			}
    			var goods={};
    			goods.id=v.id;
    			goods.goodsId=v.goodsId;
    			goods.supplierIdReality=v.supplierIdReality;
    			goods.supplierNameReality=v.supplierNameReality;
    			goods.price=v.price;
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
		     	param.id = resolveId;
		     	param.contractId = contractId;
	     		param.status=status;
	     		param.goodsList=goodsList;
		     	
		    	$.post(updateForRealityUrl,param,function(data){
		    		$.messager.progress('close');
		    		if(data.success){
		    			$.messager.alert('信息', '保存成功', 'info', function() {
		    				$('#dlg').dialog('close');
							$('#contract_reality_dg').datagrid('reload');
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
    	$('#dlg').dialog('setTitle','供应商确认').dialog('open');
		var rowIndex = $(this).attr("v");
		$('#contract_reality_dg').datagrid('selectRow', rowIndex);
		var row = $('#contract_reality_dg').datagrid('getSelected');
		$('#resolveId').val(row.id);
		$('#contractId').val(row.contractId);
		/*获取合同*/
		getContract(row.contractId);
		/*获取合同拆解内容*/
    	$.post(getContractResolveUrl,{id: row.id},function(data2){
    		$('#resolveName').text(data2.resolveName);
    		/*设置拆解明细*/
    		$("#goods_array").datagrid('loadData',data2.contractGoodsList);
    	},'json');
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
    //删除（只能删除状态为没有完成的记录）
    $(document).on("click",".downfile",function(){
		var fileId = $(this).attr("data-id");
		window.open(fileDownloadUrl+"?id="+fileId);
    });
});