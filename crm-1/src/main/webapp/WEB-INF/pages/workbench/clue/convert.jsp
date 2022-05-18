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

<script type="text/javascript">
	$(function(){
		//日期插件
		$(".myDt").datetimepicker({
			language:'zh-CN', //语言
			format:'yyyy-mm-dd ',//日期的格式
			minView:'month', //可以选择的最小视图
			initialDate:new Date(),//初始化显示的日期
			autoclose:true,//设置选择完日期或者时间之后，日否自动关闭日历
			todayBtn:true,//设置是否显示"今天"按钮,默认是false
			clearBtn:true//设置是否显示"清空"按钮，默认是false
		})

		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
		//给市场活动搜索按钮绑定单击事件
		$("#searchActBtn").click(function () {
			//清空模态窗口信息
			$("#actTBody").html("");
			$("#searchActTest").val("");
			//弹出关联市场活动模态窗口
			$("#searchActivityModal").modal("show");
		});
		//给市场活动搜索文本框绑定键盘弹起事件
		$("#searchActTest").keyup(function () {
			//收参数
			var activityName = this.value;
			var clueId = "${clue.id}";
			$.ajax({
				url:'workbench/clue/selectActByNameClueIdForConvert.do',
				data:{
					activityName:activityName,
					clueId:clueId
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
			$("#searchActivityModal").modal("hide");
		});
		//给转换按钮绑定单击事件
		$("#saveConvertBtn").click(function () {
			//收集参数
			var clueId = "${clue.id}";
			var money = $.trim($("#amountOfMoney").val());
			var name = $.trim($("#tradeName").val());
			var expectedDate = $("#expectedDate").val();
			var stage = $("#stage").val();
			var activityId = $("#activityId").val();
			var isCreateTran = $("#isCreateTransaction").prop("checked");
			//表单验证
			var regExp = /^(([1-9]\d*)|0)$/;
			if (!regExp.test(money) && isCreateTran==true){
				alert("成本只能是非负整数");
				return;
			}
			$.ajax({
				url: 'workbench/clue/saveConvertClue.do',
				data: {
					clueId:clueId,
					money:money,
					name:name,
					expectedDate:expectedDate,
					stage:stage,
					activityId:activityId,
					isCreateTran:isCreateTran
				},
				type: 'post',
				dataType: 'json',
				success:function (data) {
					if (data.code=='1'){
						window.location.href="workbench/clue/toIndex.do";
					}else {
						alert(data.msg)
					}
				}
			});
		});

	});
</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" id="searchActTest" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
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

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${clue.fullname}${clue.appellation}-${clue.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${clue.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${clue.fullname}${clue.appellation}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" value="${clue.company}-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedDate">预计成交日期</label>
		    <input type="text" class="form-control myDt" id="expectedDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
				<option></option>
				<c:forEach items="${stageList}" var="s">
					<option value="${s.id}">${s.value}</option>
				</c:forEach>
		    	<%--<option>资质审查</option>
		    	<option>需求分析</option>
		    	<option>价值建议</option>
		    	<option>确定决策者</option>
		    	<option>提案/报价</option>
		    	<option>谈判/复审</option>
		    	<option>成交</option>
		    	<option>丢失的线索</option>
		    	<option>因竞争丢失关闭</option>--%>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activityName">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" id="searchActBtn" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
			<input type="hidden" id="activityId">
		    <input type="text" class="form-control" id="activityName" placeholder="点击上面搜索" readonly>
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${clue.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" id="saveConvertBtn" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>