(function() {
	//默认的Jquery contains方法是区分大小写的,以下方法可以使contains不区分大小写（在匹配时将要匹配的元素内容全部转换成小写再进行匹配）
	jQuery.expr[':'].Contains = function(a, i, m) {
	  return jQuery(a).text().toUpperCase()
	      .indexOf(m[3].toUpperCase()) >= 0;
	};
	// OVERWRITES old selecor
	jQuery.expr[':'].contains = function(a, i, m) {
	  return jQuery(a).text().toUpperCase()
	      .indexOf(m[3].toUpperCase()) >= 0;
	};
	//Update to work for jQuery 1.8
	$.expr[":"].contains = $.expr.createPseudo(function(arg) {
	    return function( elem ) {
	        return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
	    };
	});
	
	function _getNodeArray(data,domIdArr){
				var nodeArr=[];
				var todo = [];
				for(var i=0; i<data.length; i++){
						todo.push(data[i]);
				}
				//深度优先遍历树节点
				while(todo.length){
					var node = todo.shift();
					if($.inArray(node.domId, domIdArr)>=0){
						nodeArr.push(node);
					}
					if (node.children && node.children.length>0){
						todo = todo.concat(node.children);
					}
				}
				return nodeArr;
//			});
		};
	$.extend($.fn.tree.defaults, {
		loadMsg : "Processing, please wait ..."
	});
	$.extend($.fn.tree.methods, {
		// 显示遮罩
		loading : function(jq) {
			return jq.each(function() {
						var opts = $(this).tree("options");
						var wrap = $(this).parent();
						if (opts.loadMsg) {
							$("<div class=\"datagrid-mask\"></div>")
									.css({
												display : "block",
												width: "100%", 
												height: "100%",
												"text-align":"center"
											}).appendTo(wrap);
							$("<div class=\"datagrid-mask-msg\"></div>")
									.html(opts.loadMsg).appendTo(wrap)
									.css({
										display : "block",
										left:"10%"
									});
						}
					});
		},
		//隐藏遮罩 
		loaded : function(jq) {
			return jq.each(function() {
						var wrap = $(this).parent();
						wrap.find("div.datagrid-mask-msg").remove();
						wrap.find("div.datagrid-mask").remove();
					});
		},
		/**
		 * 根据关键字 q 和 checked 属性等条件过滤tree的节点
		 * param：{q:"xxx",checked:true}
		 * 注：q：搜索tree节点的关键字，大小写模糊匹配。
		 *    checked：true表示已选着的节点，false表示未选择的节点，不传即undefined时表示全部节点
		 */
		showChecked:function(jq,param){
			return jq.each(function() {
						q=param.q;
						checked=param.checked;
						var $_this=$(this);
						var wrap = $.data(this, "tree");
						var opts = wrap.options;
						var isAll=true;
						var nodeDomIds=[];
						var parentDoms=[];
						
						if(checked==undefined&&!q){
							$_this.find(".tree-node-hidden").removeClass("tree-node-hidden");
						}else{
							isAll=false;
							$_this.find(".tree-node").addClass("tree-node-hidden");
							var nodeDoms=[];
							var checkboxselect=".tree-checkbox0";
							if(checked){
								var checkboxselect=".tree-checkbox1";
							}
							var checked_dom=$_this.find(checkboxselect).parents(".tree-node");
							$.merge(nodeDoms,checked_dom);
							nodeDoms=$.unique(nodeDoms);
							if(q){
								var q_doms=$_this.find(".tree-title:contains("+q+")").parents(".tree-node");
								nodeDoms=$.grep(q_doms, function(n,i){
									if($.inArray(n, nodeDoms)>=0){
										return n;
									}
								});
							}
							$(nodeDoms).each(function(i,v){
								$(this).removeClass("tree-node-hidden");
								$(this).parents("ul").prev(".tree-node").removeClass("tree-node-hidden");
								nodeDomIds.push($(this).attr("id"));
								var temp=$(this).parents("ul").prev(".tree-node");
								for(i=0;i<temp.length;i++){
									parentDoms.push(temp[i]);
								}
							});
						}
						
//						setTimeout(function(){
							parentDoms=$.unique(parentDoms);
							$(parentDoms).each(function(index,value){
								nodeDomIds.push($(this).attr("id"));
							});
							var data=wrap.data;
							var nodeArr=[];
							var todo = [];
							for(var i=0; i<data.length; i++){
									todo.push(data[i]);
							}
							//深度优先遍历树节点
							while(todo.length){
								var node = todo.shift();
								if(isAll){
									node.hidden = false;
								}else if($.inArray(node.domId, nodeDomIds)>=0){
									node.hidden = false;
								}else{
									node.hidden = true;
								}
								if (node.children && node.children.length>0){
									todo = todo.concat(node.children);
								}
							}
//						},0);
						
					});
		},
		doFilter:function(jq, q) {
			var reg = /("([^\\\"]*(\\.)?)*")|('([^\\\']*(\\.)?)*')|(\/{2,}.*?(\r|\n))|(\/\*(\n|.)*?\*\/)/g;// 正则表达式  
			var filterDefaultStr="function(q,node){returnnode.text.toLowerCase().indexOf(q.toLowerCase())>=0;}";
			return jq.each(function() {
				var hasFilter=false;
				var hasCheckbox=false;
				var nodeDomIds=[];
				var parentDoms=[];
				var filterParentDoms=[];
				var wrap = $.data(this, "tree");
				var opts = wrap.options;
				var str=opts.filter.toString();
				str=str.replace(reg, function(word) { // 去除注释后的文本  
				    return /^\/{2,}/.test(word) || /^\/\*/.test(word) ? "" : word;  
				});
				str=str.replace(/\s*/g,"");//去除空白符（匹配任何空白字符，包括空格、制表符、换页符等等。等价于[ \f\n\r\t\v]。）
				if(str!=filterDefaultStr){
					hasFilter=true;
				}
				if (opts.checkbox) {
					hasCheckbox=true;
				}
				var dom_this=this;
				var $_this=$(this);
				if(q.length==0&&!hasFilter){//没有搜索值，也没有设置filter属性
					$_this.find(".tree-node-hidden").removeClass("tree-node-hidden");
				}else if(q.length>0&&!hasFilter){//有搜索值，但没有设置filter属性
					$_this.find(".tree-node").addClass("tree-node-hidden");
					$_this.find(".tree-title:contains("+q+")").each(function(i,v){
						$(this).parents(".tree-node").removeClass("tree-node-hidden");
						$(this).parents("ul").prev(".tree-node").removeClass("tree-node-hidden");
						if(hasCheckbox){
							nodeDomIds.push($(this).parents(".tree-node").attr("id"));
							var temp=$(this).parents("ul").prev(".tree-node");
							for(i=0;i<temp.length;i++){
								parentDoms.push(temp[i]);
							}
						};
					});
				}else if(hasFilter){//设置了filter属性（无论是否有搜索值，都走filter方法）
					$_this.find(".tree-node").addClass("tree-node-hidden");
				}
				if(hasCheckbox||hasFilter){
//					setTimeout(function(){
						parentDoms=$.unique(parentDoms);
						if(hasCheckbox){
							$(parentDoms).each(function(index,value){
								nodeDomIds.push($(this).attr("id"));
							});
						}
						var data=wrap.data;
						var todo = [];
						for(var i=0; i<data.length; i++){
								todo.push(data[i]);
						}
						//深度优先遍历树节点
						while(todo.length){
							var node = todo.shift();
							if (node.children && node.children.length>0){
								todo = todo.concat(node.children);
							}else{
								if(hasFilter){
									if(opts.filter.call(dom_this, q, node)){
										$("#" + node.domId).removeClass("tree-node-hidden");
										$("#" + node.domId).parents("ul").prev(".tree-node").removeClass("tree-node-hidden");
										if(hasCheckbox){
											var filterParent=$("#" + node.domId).parents("ul").prev(".tree-node");
											for(i=0;i<filterParent.length;i++){
												filterParentDoms.push(filterParent[i]);
											}
										};
										node.hidden = false;
									} else {
										node.hidden = true;
									}
								}else{
									if(nodeDomIds.length==0&&q.length>0){
										node.hidden = true;
									}else if(q.length==0){
										node.hidden = false;
									}else{
										if($.inArray(node.domId+"", nodeDomIds)>=0){
											node.hidden = false;
										}else{
											node.hidden = true;
										}
									}
								}
							}
						}
						if(hasFilter&&filterParentDoms.length>0){
							filterParentDoms=$.unique(filterParentDoms);
							var domIdArr=[];
							$(filterParentDoms).each(function(index,value){
								domIdArr.push($(this).attr("id"));
							});
							var nodes=_getNodeArray(data, domIdArr);
							for(k=0;k<nodes.length;k++){
								console.log(nodes[k].text);
								nodes[k].hidden=false;
							}
						}
//					},0);
				}
			});
		}
	});
})(jQuery); 
/**
 * 重写tree的loadFilter方法,实现异步渲染（加载）所选节点的子节点
 * @param data：子节点内容
 * @param parent：父节点
 * @return {}：显示子节点
 */
function myLoadFilter(data, parent){
	var state = $.data(this, 'tree');
	function setData(){
		var serno = 1;
		var todo = [];
        for(var i=0; i<data.length; i++){
			todo.push(data[i]);
		}
		//深度优先遍历树节点
		while(todo.length){
			var node = todo.shift();
			if (node.id == undefined){
				node.id = '_node_' + (serno++);
			}
			if (node.children && node.children.length>0){
				node.state = 'closed';
				//实现延迟加载的关键
				//将children先缓存再置空
				//第一次渲染时就渲染不到children节点了
				node.children1 = node.children;
				node.children = undefined;
				todo = todo.concat(node.children1);
			}
		}
		state.tdata = data;
	}
	function finddata(id){
		var data = state.tdata;
		var cc = [data];
		while(cc.length){
			var c = cc.shift();
			for(var i=0; i < c.length; i++) {
				var node = c[i];
				if (node.id == id){
//					node.children=node.children1;
					return node;
				} else if (node.children1){
					cc.push(node.children1);
				}
			}
		}
		return null;
	}
	setData();
	var t = $(this);
	var opts = t.tree('options');
	//在展开节点前将children重置回清空前的属性
	opts.onBeforeExpand = function(node){
		var n = finddata(node.id);
		//已经展开，返回
		if (n.children && n.children.length>0){return}
		if (n.children1){
			var filter = opts.loadFilter;
			opts.loadFilter = function(data){return data;};
			t.tree('append',{
				parent:node.target,
				data:n.children1
			});
			opts.loadFilter = filter;
			n.children = n.children1;
		}
	};
	return data;
}
/**
 * tree节点的指定属性node_property值等于给定参数compare_value值的将被选中（若有数组参数checkedArr，则会记录下勾选节点的id）
 * @param data：原数据
 * @param node_property：匹配的节点数据属性字段
 * @param compare_value：要匹配的值
 * @param checkedArr：记录勾选节点id（是由节点id组成的数组）
 * @return data：返回带有节点勾选状态的数据
 */
function checkedDataByPropertyEqValue(data,node_property,compare_value,checkedArr){
	var todo = [];
	for(var i=0; i<data.length; i++){
			todo.push(data[i]);
	}
	//深度优先遍历树节点
	while(todo.length){
		var node = todo.shift();
		if (node.children && node.children.length>0){
//			node.state='closed';
			todo = todo.concat(node.children);
		}else if(node[node_property]==compare_value){
			node.checked=true;
			if(checkedArr){
				checkedArr.push(node.id);
			}
		}else{
			node.checked=false;
		}
	}
	return data;
}
/**
 * tree节点的id在数组idArr中的将被选中（若有数组参数checkedArr，则会记录下勾选节点的id）
 * @param data：原数据
 * @param idArr：tree节点id在idArr数组中的将被选中
 * @param checkedArr：记录勾选节点id（是由节点id组成的数组）
 * @return {} 返回带有节点勾选状态的数据
 */
function checkedDataByIdInArray(data,idArr,checkedArr){
	var idArr=idArr.toString().split(",");   
	var todo = [];
	for(var i=0; i<data.length; i++){
			todo.push(data[i]);
	}
	//深度优先遍历树节点
	while(todo.length){
		var node = todo.shift();
		if (node.children && node.children.length>0){
//			node.state='closed';
			todo = todo.concat(node.children);
		}else if($.inArray(node.id+"", idArr)>=0){
			node.checked=true;
			if(checkedArr){
				checkedArr.push(node.id);
			}
		}else{
			node.checked=false;
		}
	}
	return data;
}
/**
 * 不展开形式显示tree数据
 * @param {} data：原数据
 * @return {}
 */
function dataWithClosed(data){
	var todo = [];
	for(var i=0; i<data.length; i++){
			todo.push(data[i]);
	}
	//深度优先遍历树节点
	while(todo.length){
		var node = todo.shift();
		if (node.children && node.children.length>0){
			node.state='closed';
			todo = todo.concat(node.children);
		}
	}
	return data;
}


function listToArray(list, pram_key) {
    var resultList = [];
    if (!list) {
        return resultList;
    }
    for (var i = 0; i < list.length; i++) {
        resultList.push(list[i][pram_key]);
    }
    return resultList;
}
