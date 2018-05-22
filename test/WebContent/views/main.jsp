<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<title>工业智能数字平台（测试）</title>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/base/favicon.ico" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base/easyui/themes/metro-gray/easyui.css" type="text/css" /> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/base/easyui/themes/icon.css" type="text/css" /> 
<link href="<%=request.getContextPath()%>/css/main/main_interface.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/main/main_reset.css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/main/feature.presenter.1.5.css" rel="stylesheet" />
<script type="text/javascript" src="<%= request.getContextPath()%>/js/base/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/base/jquery.easyui.min.js"></script> 
<script type="text/javascript" src="<%= request.getContextPath()%>/js/base/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/main.js?v=<%=Math.random()%>"></script>
<script>
var $thisismainpage = true;
var ContextPath = "<%=request.getContextPath()%>";
var getModuleUrl = "<%=request.getContextPath()%>/module/getAllByFatherId";
var changeThemes= "<%=request.getContextPath()%>/framework/systemUserRest/changeThemes";
var logoutUrl= "<%=request.getContextPath()%>/framework/systemUserRest/logout";
var updatePassUrl= "<%=request.getContextPath()%>/framework/employeeContraller/updatePassWord";
//alert(ContextPath);
var n = 0; //这个n 记录标题条的下标
var employee_id = "<%=session.getAttribute("e_id")%>" ;
var user_name = "<%=session.getAttribute("employeeName")%>";

</script>
<style>
.posbtn{
	border-radius: 7px!important;
	box-shadow: 0px 2px 1px #9e9e9e!important;
    background: -moz-linear-gradient(top, #ffffff 0%, #dcdada 100%)!important;
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #ffffff), color-stop(100%, #eae7e7))!important;
    background: -webkit-linear-gradient(top, #ffffff 0%, #dcdada 100%)!important;
    background: -o-linear-gradient(top, #ffffff 0%, #dcdada 100%)!important;
    background: -ms-linear-gradient(top, #ffffff 0%, #dcdada 100%)!important;
    background: linear-gradient(to bottom, #ffffff 0%, #dcdada 100%)!important;
    border: 0px!important;
    padding: inherit!important;
    -moz-user-select: none;
    -ms-user-select: none;
    -webkit-user-select: none;
    user-select: none;
}
.posbtn:active{
	background: #f1f0f0!important;
}
.phide{
	display: none!important;
}
</style>
<script>
	$(function() {
		
		$(window).resize(function(){
			$("#layout").layout('resize');
		});

		$("#contain").height($(window).height() - $("#contain").offset().top);
		
		//$("#mainbody").css("height",($(window).height() - $("#header").height() - $("#footer").height() + 15));

		$('#tabsbody').tabs({
			tools : "#tab-tools",
			onUpdate:function(title,index){
				if(index)
				{
					var tabpanel=$(this).tabs("getTab",index);
					var iframe= tabpanel.find("iframe")[0];
		            $(iframe).load(function(){
		            	$("#"+iframe.id+"p").hide();
		            });
		           
				}
			}
		});
		
		var _scroll;
		var _i = 0;
		$("#leftmenuup").bind("click",function(){
			
			if(_scroll)
			{
				clearInterval(_scroll);
				_i = 0; 
			}
			
			_scroll = setInterval(function(){
				
				_i++;
				$("#contain").scrollTop($("#contain").scrollTop() - 2);
				
				if(_i > 50)
				{
					clearInterval(_scroll);
					_i = 0;
					
				}
				
			},10);
		});
		
		$("#leftmenudown").bind("click",function(){
			
			if(_scroll)
			{
				clearInterval(_scroll);
				_i = 0; 
			}
			
			_scroll = setInterval(function(){
				
				_i++;
				$("#contain").scrollTop($("#contain").scrollTop() + 2);
				
				if(_i > 50)
				{
					clearInterval(_scroll);
					_i = 0;
				}
				
			},10);
		});
		
		//关闭当前页
		$("#closecurrent").bind("click",function(){
			var tab = $("#tabsbody").tabs("getSelected");
			var index = $("#tabsbody").tabs("getTabIndex", tab);
			if(index > 0)
			{
				$("#tabsbody").tabs("close", index);
			}
		});
		
		//关闭当前页以外
		$("#closeother").bind("click",function(){
			var tab = $("#tabsbody").tabs("getSelected");
			var index = $("#tabsbody").tabs("getTabIndex", tab);
			var tabs = $("#tabsbody").tabs("tabs");

			if(tabs)
			{
				for(var i = tabs.length - 1; i > 0; i--)
				{
					var t = $("#tabsbody").tabs("getTabIndex", tabs[i]);
					if(t > 0 && t != index)
					{
						$("#tabsbody").tabs("close", t);
					}
				}
				$("#tabsbody").tabs("select", 1);
			}
		});
		
		//关闭全部
		$("#closeall").bind("click",function(){
			var tabs = $("#tabsbody").tabs("tabs");
			if(tabs)
			{
				for(var i = tabs.length - 1; i > 0; i--)
				{
					var t = $("#tabsbody").tabs("getTabIndex", tabs[i]);
					if(t > 0)
					{
						$("#tabsbody").tabs("close", t);
					}
				}
			}
		});

		//最大化
		$("#fullscreen").bind("click",function(){
			
			$("#contain").hide("fast");
			$("#header").hide("fast",function(){
				$("#tabsbody").css("margin-left","0px");
				
				$("#fullscreen").hide();
				$("#recovery").show();
				
				$("#tabsbody").tabs("resize");
				$("#layout").layout();
				
				$("iframe").height($("#mainbody").height() - 40);
			});
		});
		
		//恢复
		$("#recovery").bind("click",function(){
			
			$("#header").show("normal");
			$("#contain").show("normal");
			$("#tabsbody").css("margin-left","120px");
			
			$("#recovery").hide();
			$("#fullscreen").show();
			
			$("#tabsbody").tabs("resize");
			$("#layout").layout();
			
			$("iframe").height($("#mainbody").height() - 40);
		});
		
		$("#refresh").bind("click",function(){
			var tab = $('#tabsbody').tabs('getSelected');
			var iframe = $(tab).find("iframe")[0];
			if(iframe)
			{
				$("#"+iframe.id+"p").show();
				var isrc = iframe.src;
				var srci = isrc.indexOf("?");
				if(srci>0)
				{
					isrc = isrc.substring(0,srci);
				}

				iframe.src = isrc;
			}
		});
		
		$("#themesId").combobox("select", "<%=session.getAttribute("themes")%>");
		

		var height = $("#mainbody").height() - 40;
		var iframeSrc = 'home.jsp';
		
		$("#tabsbody").tabs("add",{
		    title:"首页",
		    selected:true,
		    closable : false,
		    content : '<iframe id="iframehome" width="100%" height="' + height + '" src="'+iframeSrc+'" style="overflow:visible;" scrolling="yes" class="mainContent" frameborder="0"></iframe>'
		});
	});
</script>
</head>
<body>
	<!--内容 三 四级导航-->
	
	<div id="contain" class="third_nav" style="width: 120px;position: absolute;top: 105px;left: 0px;z-index: 999;overflow: hidden;"> 
		<a id="leftmenuup" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-up',width:120" style="position: fixed;top: 105px;z-index: 1000"></a>  
		<div id="third_nav">
			<ul class="active" id="third_nav_a">
			</ul>
		</div>
		<a id="leftmenudown" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-down',width:120" style="position: fixed;bottom: 0px;z-index: 1000"></a>
	</div>
	
	<div id="fourth_nav" style="display: none;">
	</div>

	<div id="tab-tools">
		<a id="refresh" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-refresh',plain:true" title="刷新当前页"></a> 
		<a id="fullscreen" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-fullscreen',plain:true" title="全屏"></a>
		<a id="recovery" href="javascript:void(0);" class="easyui-linkbutton" style="display: none;" data-options="iconCls:'icon-recovery',plain:true" title="恢复"></a>
		<a href="javascript:void(0);" class="easyui-menubutton" data-options="iconCls:'icon-dropdown',plain:true,menu:'#mm',hasDownArrow:false"></a>
	</div>
		
	<div id="mm" style="width:100px;height:80px;">
	    <div id="closecurrent">关闭当前页</div>
	    <div id="closeother">关闭当前页以外</div>
	    <!-- <div class="menu-sep"></div> -->
	    <div id="closeall">关闭所有</div>
	</div>


	<div id="layout" class="easyui-layout" data-options="fit:true">
		
		<!--头部--->
		<div id="header" class="header" data-options="region:'north',border:false">
			<div class="fl logo" id="header_href">
				<img src="<%=request.getContextPath()%>/images/main/logo.png" alt="#">
			</div>
			<div class="header_cont">
				<div class="user_info">
					<span>当前登录用户：</span>
					<a href="javascript:void(0)" id="mb" class="easyui-menubutton" data-options="menu:'#mmp'"><%= (int)session.getAttribute("systemUser")==1?"超级管理员":session.getAttribute("userName") %></a>
					 <div id="mmp" class="easyui-menu" data-options="onClick:menuHandler"  style="width:150px;">
					    <div id="updatepass" data-options="iconCls:'icon-edit'" style="display:none;" >修改密码</div>
					    <div class="menu-sep" style="display:none;"></div>
					     <div id="logout" data-options="iconCls:'icon-signout'">退出</div>
					</div>
					<div id="dlg_pass" class="easyui-dialog" title="修改密码" data-options="closed:true,width: 400,height:250,modal: true" >
						<div   class="" style="margin:20px;">
  							<div style="">原密码:&nbsp;&nbsp;&nbsp;<input type="password" id="epass" required="required" placeholder="请输入原密码" style="width:200px; height:30px;border:1px solid #dedede;"/></div>
							
						</div>
						<div  class="" style="margin:20px;">
  							<div style="">新密码:&nbsp;&nbsp;&nbsp;<input type="password" id="npass" required="required"  placeholder="请输入新密码" style="width:200px; height:30px;border:1px solid #dedede;"/></div> 
							
						</div>
						<div  class="" style="margin:20px;">
  							<div style=""> 确  认 :&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" id="znpass" required="required"  placeholder="请输入新密码" style="width:200px; height:30px;border:1px solid #dedede;"/></div>
							
						</div>
						
						<div  class="" style="margin:20px;">
  							
							<div class="my_submitClear"style="margin-right: 25px;height: 34px;float:right;">
								<a href="javascript:void(0)" id="xgmm" class="easyui-linkbutton my_submit" style="width:90px;height:30px;">修改密码</a> 
							</div>
						</div>
					</div>
					<div id="dlg_relogin" class="easyui-dialog" title="登录超时" data-options="width: 330,height:250,modal: true,closable:false,closed:true" >
						<div style="height: 30px;background-color: #DFE0E0;color:#666666;line-height: 30px;" align="center">当前登录已超时,请重新登录后再次执行操作</div>
						<div   class="" style="margin:20px;">
  							<div style="">用户名:&nbsp;<input type="text" id="username" required="required" style="width:200px; height:30px;border:1px solid #dedede;" readonly="readonly"  /></div>
							
						</div>
						<div   class="" style="margin:20px;">
  							<div style="">&nbsp;密&nbsp;码:&nbsp;&nbsp;<input type="password" id="repass" required="required" placeholder="请输入密码" style="width:200px; height:30px;border:1px solid #dedede;"/></div>
						</div>
						
						<div  class="" style="margin:20px;">
  							
							<div class="my_submitClear"style="height: 34px;" align="center" >
								<a href="javascript:void(0)" id="relogin" class="easyui-linkbutton my_submit" style="width:90px;height:30px;">登录</a> 
							</div>
						</div>
						
					</div>
					<!-- </span>
					<a href="#" class="logout"><span></span><em style="font-weight:bold;" onclick="logout()">退出</em></a> -->
					
				</div>
				
				<!-- 一级导航 -->
				<ul id="first_nav">
	
				</ul>
				<!-- 二级导航 -->
				<div id="second_nav">
				</div>
			</div>
		</div>
		
		
		<!--数据详细内容--->
		<div id="mainbody" data-options="region:'center',border:false">
			
			<div id="tabsbody"  class="easyui-tabs" style="margin-left: 120px;background-color: #D4D4D4;" >
				<!-- 
				<div id="_home" title="首页" style="padding:20px;">
			    </div>
			     -->
			</div>
		</div>
		
		<!--版权--->
		
		
	</div>
		<!-- <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1261775888'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1261775888' type='text/javascript'%3E%3C/script%3E"));</script> -->
</body>
</html>
