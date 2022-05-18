<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
	String basePath = request.getScheme() +"://"+ request.getServerName() +":"+ request.getServerPort() + request.getContextPath() + "/";
%>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function () {
		//回车登录函数
		$(window).keydown(function (e) {
			//如果是回车键，则发送请求
			if(e.keyCode == 13){
				//模拟单击登录
				$("#loginBtn").click();
			}
		})
		//登录按钮的单击事件
		$("#loginBtn").click(function () {
			//收集参数
			var loginAct = $.trim($("#loginId").val());
			var loginPwd = $.trim($("#loginPwd").val());
			var isRemPwd = $("#isRemPwd").prop("checked");
			//表单验证
			if (loginAct == "" || loginPwd == ""){
				alert("用户名和密码不能为空");
				return;
			} else {
				$.ajax({
					url:'settings/qx/user/login.do',
					data:{
						loginAct:loginAct,
						loginPwd:loginPwd,
						isRemPwd:isRemPwd
					},
					type:'post',
					dataType:'json',
					success:function (data) {
						if (data.code == "1") {
							window.location.href="workbench/toWorkbenchIndex.do";
						}else {
							$("#msg").html(data.msg);
						}
					},
					beforeSend:function () {//ajax发送请求前，自动执行本函数返回boolean类型,该函数返回值决定是否向后台发送请求
						$("#msg").text("正在验证....");
					}
				})
			}
		})
	})
</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 14px;">&copy;2022&nbsp;吴斌制作</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" id="loginId" type="text" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" id="loginPwd" type="password" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<input type="checkbox" id="isRemPwd"> 记住密码
						</label>
						&nbsp;&nbsp;
						<span id="msg" style="color: #8b0000"></span>
					</div>
					<button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>