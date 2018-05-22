<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <title>工业智能数字平台（测试）</title>
    <link rel="shortcut icon" href="<%= request.getContextPath()%>/img/base/favicon.ico">
    <%--  <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/index/index_home.css"> --%>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/login.css">	
	<script src="<%= request.getContextPath()%>/js/base/jquery-1.9.1.min.js"></script>
	<script src="<%= request.getContextPath()%>/js/login.js"></script>
	<script src="<%= request.getContextPath()%>/js/base/jquery.storageapi.min.js"></script>
	<script src="<%= request.getContextPath()%>/js/base/md5.js"></script>
	<%-- <script src="<%= request.getContextPath()%>/js/base/bootstrap/js/bootstrap.min.js"></script>
	
	<script src="<%= request.getContextPath()%>/js/login/token.js?v=<%=Math.random()%>"></script>
	
	<script src="<%= request.getContextPath()%>/js/login/yunLogin.js?v=<%=Math.random()%>"></script>
	
	 --%>

	<script type="text/javascript">
	var contextPath = "<%= request.getContextPath()%>";
	var loginUrl = contextPath+"/login/userLogin";
	</script>

	</head>
	<body style="overflow-x: hidden;" onload="resize(this)">
	<section>
			<div class="head">
				<div class="top">
					<div class="top_l">
						<i></i>
						<span>工业智能数字平台（测试）</span>
					</div>
					<div class="top_r">
						<i></i>
						<span>服务热线：400-888-8888</span>
					</div>
				</div>
			</div>
			<div class="blue">
				<div class="main">
					<div class="main_l">
						<img src="<%= request.getContextPath()%>/images/banner.png" alt="" />
					</div>
					<div class="main_r">
						<ul class="use_tab">
							<li class="merchant_tab this">用户登录</li>
						</ul>
						<div class="content">
							<form id="myform" method="post" class="login-form">
				   				<input type="hidden" id="themes" name="themes" value=""/>
								<div class="put merchant">
									<p class="tag" id="tip"><!-- <s></s> --></p>
									<div><input type="text" class="use_name" name="loginUserName" id="loginUserName" required="required"  tabindex="1" placeholder="用户名"/><i></i></div>
									<div>
										<input type="password" class="password"  id="password" required="required" tabindex="1"  placeholder="密码"/><i></i>
										<input id="passwordHidden" type="hidden" name="password" />
									</div>
									<div class="remember"><em id="remember"></em>&nbsp;记住密码</div>
									<div class="login_btn" onclick="mysubmit();" id="login">登&nbsp;录</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				</div>
				<footer>
					<div class="foot">
						<div class="foot_l">
							<ul>
								<li>关于</li>
								<li>联系我们</li>
								<li>下载中心</li>
								<li>帮助</li>
							</ul>
							<p><span>京ICP备88888888号</span><span>京公网安备888888888888号</span></p>
						</div>
						<%-- <ul class="foot_r" style="display: none;">
							<li>
								<div><img src="<%= request.getContextPath()%>/images/login/code2.png" alt="" /></div>
								<span>e&nbsp;合同</span>							
							</li>
							<li>
								<div><img src="<%= request.getContextPath()%>/images/login/code2.png" alt="" /></div>
								<span>e&nbsp;采购</span>							
							</li>
							<li>
								<div><img src="<%= request.getContextPath()%>/images/login/code2.png" alt="" /></div>
								<span>e&nbsp;运营</span>							
							</li>
						</ul> --%>
					</div>
				</footer>
		</section>
	</body>
</html>