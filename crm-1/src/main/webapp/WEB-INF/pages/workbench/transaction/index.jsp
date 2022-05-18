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
		//页面加载调用函数渲染页面
		tranByCondition(1,10);
		//查询按钮加单击事件
		$("#selectBtn").click(function () {
			tranByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
		});



		//给全选按钮绑定单击事件
		$("#checkAll").click(function () {
			$("#tBody input[type='checkbox']").prop("checked",this.checked);
		});
		$("#tBody").on("click","input[type='checkbox']",function () {
			//判断所有checkbox是否全选或全不选中
			if($("#tBody input[type='checkbox']").size()==$("#tBody input[type='checkbox']:checked").size()){
				$("#checkAll").prop("checked",true);
			}else {
				$("#checkAll").prop("checked",false);
			}
		});
		//给修改按钮绑定单击事件
		$("#editTranBtn").click(function () {
			//收集被选中的id参数
			var checkedBoxById = $("#tBody input[type='checkbox']:checked");

			if (checkedBoxById.size()==0){
				alert("至少选中一条数据");
				return;
			}
			if (checkedBoxById.size()>1){
				alert("只能选择一条数据修改");
				return;
			}
			if(checkedBoxById.attr("name")=='成交'){
				alert("已成交的交易无法修改");
				return;
			}
			var id = checkedBoxById.val();
			window.location.href="workbench/transaction/toEdit.do?id="+id;
		});
	});
	//封装函数
	function tranByCondition(pageNo,pageSize) {
		var owner = $.trim($("#ownerByGet").val());
		var name = $.trim($("#nameByGet").val());
		var customerId = $.trim($("#customerId").val());
		var stage = $("#stage").val();
		var type = $("#type").val();
		var source = $("#source").val();
		var contactsId = $.trim($("#contactsId").val());
		//var pageNo = 1;
		//var pageSize = 10;
		$.ajax({
			url:'workbench/tran/getAllTranByCon.do',
			data:{
				owner:owner,
				name:name,
				customerId:customerId,
				stage:stage,
				type:type,
				source:source,
				contactsId:contactsId,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type:'post',
			dataType:'json',
			success:function (data) {
				//总条数显示到页面
				//$("#rowsB").text(data.totalRows);
				//交易列表
				var htmlVar = "";
				$.each(data.tranList,function (index,e) {
					htmlVar += "<tr>";
					htmlVar += "	<td><input type=\"checkbox\" name='"+e.stage+"' value='"+e.id+"'/></td>";
					htmlVar += "	<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/transaction/doDetail.do?id="+e.id+"';\">"+e.name+"</a></td>";
					htmlVar += "	<td>"+e.contactsId+"</td>";
					htmlVar += "	<td>"+e.stage+"</td>";
					htmlVar += "	<td>"+e.type+"</td>";
					htmlVar += "	<td>"+e.owner+"</td>";
					htmlVar += "	<td>"+e.source+"</td>";
					htmlVar += "	<td>"+e.contactsId+"</td>";
					htmlVar += "</tr>";
				});
				$("#tBody").html(htmlVar);
				//取消全选按钮
				$("#checkAll").prop("checked",false);
				//总页数
				var totalPages = 1;
				if (data.totalRows%pageSize==0){
					totalPages=data.totalRows/pageSize;
				}else {
					totalPages=parseInt(data.totalRows/pageSize) + 1;
				}
				//调用pagination函数，显示翻页信息
				$("#demo_pag1").bs_pagination({
					currentPage:pageNo,//当前页号,相当于pageNo

					rowsPerPage:pageSize,//每页显示条数,相当于pageSize
					totalRows:data.totalRows,//总条数
					totalPages: totalPages,  //总页数,必填参数.

					visiblePageLinks:5,//最多可以显示的卡片数

					showGoToPage:true,//是否显示"跳转到"部分,默认true--显示
					showRowsPerPage:true,//是否显示"每页显示条数"部分。默认true--显示
					showRowsInfo:true,//是否显示记录的信息，默认true--显示

					//用户每次切换页号，都自动触发本函数;
					//每次返回切换页号之后的pageNo和pageSize
					onChangePage: function(event,pageObj) { // returns page_num and rows_per_page after a link has clicked
						//js代码
						tranByCondition(pageObj.currentPage,pageObj.rowsPerPage);
					}
				});
			}
		});
	}
</script>
</head>
<body>

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="ownerByGet">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="nameByGet">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" type="text" id="customerId">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="stage">
					  	<option></option>
						  <c:forEach items="${stageList}" var="stage">
							  <option value="${stage.id}">${stage.value}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="type">
					  	<option></option>
						  <c:forEach items="${typeList}" var="type">
							  <option value="${type.id}">${type.value}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="source">
						  <option></option>
						  <c:forEach items="${sourceList}" var="source">
							  <option value="${source.id}">${source.value}</option>
						  </c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" type="text" id="contactsId">
				    </div>
				  </div>
				  
				  <button type="submit" class="btn btn-default" id="selectBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='workbench/transaction/toSave.do';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editTranBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tBody">
					<%--
						<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点-交易01</a></td>
							<td>动力节点</td>
							<td>谈判/复审</td>
							<td>新业务</td>
							<td>zhangsan</td>
							<td>广告</td>
							<td>李四</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点-交易01</a></td>
                            <td>动力节点</td>
                            <td>谈判/复审</td>
                            <td>新业务</td>
                            <td>zhangsan</td>
                            <td>广告</td>
                            <td>李四</td>
                        </tr>
					--%>
					</tbody>
				</table>
				<div id="demo_pag1"></div>
			</div>
			
			<%--<div style="height: 50px; position: relative;top: 20px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>--%>
			
		</div>
		
	</div>
</body>
</html>