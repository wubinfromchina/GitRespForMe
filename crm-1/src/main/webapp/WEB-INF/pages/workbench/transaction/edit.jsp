<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
String basePath = request.getScheme() +"://"+ request.getServerName() +":"+ request.getServerPort() + request.getContextPath() + "/";
%>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

	<!--JQUERY-->
	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<!--BOOTSTRAP框架-->
	<link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<!--BOOTSTRAP_DATETIMEPICKER插件-->
	<link rel="stylesheet" type="text/css" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<!--  PAGINATION 插件 -->
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
	<%--typeahead插件--%>
	<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
	<script type="text/javascript">
		$(function () {
			//日期插件
			$(".myDt").datetimepicker({
				language:'zh-CN', //语言
				format:'yyyy-mm-dd ',//日期的格式
				minView:'month', //可以选择的最小视图
				initialDate:new Date(),//初始化显示的日期
				autoclose:true,//设置选择完日期或者时间之后，日否自动关闭日历
				todayBtn:true,//设置是否显示"今天"按钮,默认是false
				clearBtn:true//设置是否显示"清空"按钮，默认是false
			});
			//当容器加载完成之后，对容器调用工具函数
			$("#create-customerName").typeahead({
				source:function (jquery,process) {//每次键盘弹起，都自动触发本函数；我们可以向后台送请求，查询客户表中所有的名称，把客户名称以[]字符串形式返回前台，赋值给source
					//process：是个函数，能够将['xxx','xxxxx','xxxxxx',.....]字符串赋值给source，从而完成自动补全
					//jquery：在容器中输入的关键字
					//var customerName=$("#customerName").val();
					//发送查询请求
					$.ajax({
						url:'workbench/transaction/queryCustomerNameByName.do',
						data:{
							customerName:jquery
						},
						type:'post',
						dataType:'json',
						success:function (data) {//['xxx','xxxxx','xxxxxx',.....]
							process(data);
						}
					});
				}
			});;

			//给阶段下拉列表绑定change事件
			$("#create-stage").change(function () {
				//收集参数
				var stageValue = $(this).find("option:selected").text();
				if (stageValue == "" || stageValue == null){
					$("#create-possibility").val("");
					return;
				}
				$.ajax({
					url:'workbench/transaction/getPossibilityByStage.do',
					data:{
						stageValue:stageValue
					},
					type:'post',
					dataType:'json',
					success:function (data) {
						//返回的可能性设置到可能性文本框内
						$("#create-possibility").val(data);
					}
				});
			});

			//给市场活动搜索按钮绑定单击事件
			$("#searchActBtn").click(function () {
				//清空模态窗口信息
				$("#actTBody").html("");
				$("#searchActTest").val("");
				//弹出关联市场活动模态窗口
				$("#findMarketActivity").modal("show");
			});
			//给市场活动搜索文本框绑定键盘弹起事件
			$("#searchActTest").keyup(function () {
				//收参数
				var activityName = this.value;
				$.ajax({
					url:'workbench/transaction/searchAct.do',
					data:{
						activityName:activityName,
					},
					type:'post',
					dataType:'json',
					success:function (data) {
						var htmlStr = "";
						$.each(data,function (index, e) {
							htmlStr += "<tr>";
							htmlStr += "<td><input type=\"radio\" value=\""+e.id+"\" activityName=\""+e.name+"\" name=\"activity\"/></td>";
							htmlStr += "<td>"+e.name+"</td>";
							htmlStr += "<td>"+e.startDate+"</td>";
							htmlStr += "<td>"+e.endDate+"</td>";
							htmlStr += "<td>"+e.owner+"</td>";
							htmlStr += "</tr>";
						});
						$("#actTBody").html(htmlStr);
					}
				});
			});
			//给所有市场活动单选按钮添加单击事件
			$("#actTBody").on("click","input[type='radio']",function () {
				//收集参数
				var id = this.value;
				var activityName = $(this).attr("activityName");
				$("#activityId").val(id);
				$("#activityName").val(activityName);
				$("#findMarketActivity").modal("hide");
			});

			//给联系人搜索按钮绑定单击事件
			$("#searchContactsBtn").click(function () {
				//清空模态窗口信息
				$("#conTBody").html("");
				$("#searchConText").val("");
				//弹出联系人模态窗口
				$("#findContacts").modal("show");
			});
			//给联系人搜索文本框绑定键盘弹起事件
			$("#searchConText").keyup(function () {
				//收参数
				var contactsName = this.value;
				$.ajax({
					url:'workbench/transaction/searchContacts.do',
					data:{
						contactsName:contactsName,
					},
					type:'post',
					dataType:'json',
					success:function (data) {
						var htmlStr = "";
						$.each(data,function (index, e) {
							htmlStr += "<tr>";
							htmlStr += "	<td><input type=\"radio\" contactsName='"+e.fullname+"' value='"+e.id+"' name=\"activity\"/></td>";
							htmlStr += "	<td>"+e.fullname+"</td>";
							htmlStr += "	<td>"+e.email+"</td>";
							htmlStr += "	<td>"+e.mphone+"</td>";
							htmlStr += "</tr>";
						});
						$("#conTBody").html(htmlStr);
					}
				});
			});
			//给所有联系人单选按钮添加单击事件
			$("#conTBody").on("click","input[type='radio']",function () {
				//收集参数
				var id = this.value;
				var contactsName = $(this).attr("contactsName");
				$("#contactsId").val(id);
				$("#contactsName").val(contactsName);
				$("#findContacts").modal("hide");
			});


			//给保存按钮绑定单击事件
			$("#saveBtn").click(function () {
				//获取参数
				var id = '${tran.id}';
				var createTime = '${tran.createTime}';
				var owner = $("#create-owner").val();
				var money = $.trim($("#create-money").val());
				var name = $.trim($("#create-name").val());
				var expectedDate = $("#create-expectedDate").val();
				var customerName = $.trim($("#create-customerName").val());
				var stage = $("#create-stage").val();
				var type = $("#create-type").val();
				var source = $("#create-source").val();
				var activityId = $("#activityId").val();
				var contactsId = $("#contactsId").val();
				var description = $.trim($("#create-description").val());
				var contactSummary = $.trim($("#create-contactSummary").val());
				var nextContactTime = $("#create-nextContactTime").val();
				//表单验证
				if (name=='' || expectedDate=='' || customerName==''){
					alert("带*的为必填字段");
					return;
				}

				//发送异步请求
				$.ajax({
					url:'workbench/transaction/saveEditTran.do',
					data:{
						id:id,
						createTime:createTime,
						owner:owner,
						money:money,
						name:name,
						expectedDate:expectedDate,
						customerName:customerName,
						stage:stage,
						type:type,
						source:source,
						activityId:activityId,
						contactsId:contactsId,
						description:description,
						contactSummary:contactSummary,
						nextContactTime:nextContactTime
					},
					type:'post',
					dataType:'json',
					success:function (data) {
						if (data.code=='1'){
							//跳转到交易页面
							window.location.href='workbench/transaction/index.do';
						}else {
							alert(data.msg);
						}
					}
				});
			});

			//给取消按钮绑定单击事件
			$("#cancelBtn").click(function (){
				if (window.confirm("确定取消修改么？")){
					window.location.href='workbench/transaction/index.do';
				}
			});
		});
	</script>
</head>
<body>

<!-- 查找市场活动模态窗口 -->
<div class="modal fade" id="findMarketActivity" role="dialog">
	<div class="modal-dialog" role="document" style="width: 80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">查找市场活动</h4>
			</div>
			<div class="modal-body">
				<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
					<form class="form-inline" role="form">
						<div class="form-group has-feedback">
							<input type="text" class="form-control" id="searchActTest" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
							<span class="glyphicon glyphicon-search form-control-feedback"></span>
						</div>
					</form>
				</div>
				<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
					<thead>
					<tr style="color: #B3B3B3;">
						<td></td>
						<td>名称</td>
						<td>开始日期</td>
						<td>结束日期</td>
						<td>所有者</td>
					</tr>
					</thead>
					<tbody id="actTBody">
					<%--<tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>发传单</td>
                        <td>2020-10-10</td>
                        <td>2020-10-20</td>
                        <td>zhangsan</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>发传单</td>
                        <td>2020-10-10</td>
                        <td>2020-10-20</td>
                        <td>zhangsan</td>
                    </tr>--%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<!-- 查找联系人模态窗口 -->
<div class="modal fade" id="findContacts" role="dialog">
	<div class="modal-dialog" role="document" style="width: 80%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">查找联系人</h4>
			</div>
			<div class="modal-body">
				<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
					<form class="form-inline" role="form">
						<div class="form-group has-feedback">
							<input type="text" id="searchConText" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
							<span class="glyphicon glyphicon-search form-control-feedback"></span>
						</div>
					</form>
				</div>
				<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
					<thead>
					<tr style="color: #B3B3B3;">
						<td></td>
						<td>名称</td>
						<td>邮箱</td>
						<td>手机</td>
					</tr>
					</thead>
					<tbody id="conTBody">
					<%--<tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>李四</td>
                        <td>lisi@bjpowernode.com</td>
                        <td>12345678901</td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="activity"/></td>
                        <td>李四</td>
                        <td>lisi@bjpowernode.com</td>
                        <td>12345678901</td>
                    </tr>--%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>修改交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
			<button type="button" class="btn btn-default" id="cancelBtn">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-owner">
					<c:forEach items="${userList}" var="u">
						<option value="${u.id}">${u.name}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-money" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-money" value="${tran.money}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-name" value="${tran.name}">
			</div>
			<label for="create-expectedDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control myDt" id="create-expectedDate" value="${tran.expectedDate}">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-customerName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-customerName" value="${tran.customerId}" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-stage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-stage">
			  	<option></option>
				  <c:forEach items="${stageList}" var="stage">
					  <option value="${stage.id}">${stage.value}</option>
				  </c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-type" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-type">
				  <option></option>
					<c:forEach items="${typeList}" var="type">
						<option value="${type.id}">${type.value}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" value="${possibility}" id="create-possibility" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-source" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-source">
				  <option></option>
					<c:forEach items="${sourceList}" var="source">
						<option value="${source.id}">${source.value}</option>
					</c:forEach>
				</select>
			</div>
			<label for="activityName" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="activityId">
				<input type="text" class="form-control" id="activityName">
			</div>
		</div>
		
		<div class="form-group">
			<label for="contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="contactsId">
				<input type="text" class="form-control" id="contactsName">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-description" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3"  id="create-description">${tran.description}</textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3"  id="create-contactSummary">${tran.contactSummary}</textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control myDt" value="${tran.nextContactTime}" id="create-nextContactTime">
			</div>
		</div>
		
	</form>
</body>
</html>