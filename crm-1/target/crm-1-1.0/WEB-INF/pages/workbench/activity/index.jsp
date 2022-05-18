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
		//给创建按钮加单击事件
		$("#createActBtn").click(function () {
			//初始化工作
			$("#actForm").get(0).reset();//重置表单
			//弹出模态窗口
			$("#createActivityModal").modal("show")
		})

		//给保存按钮加单击事件
		$("#saveActBtn").click(function () {
			//收集参数
			var owner = $("#create-marketActivityOwner").val();
			var name = $.trim($("#create-marketActivityName").val());
			var startDate = $("#create-startDate").val();
			var endDate = $("#create-endDate").val();
			var cost = $.trim($("#create-cost").val());
			var description = $("#create-description").val();
			var regExp = /^(([1-9]\d*)|0)$/;
			//表单验证
			if (owner == ""){
				alert("所有者不能为空");
				return;
			}
			if (name == ""){
				alert("名称不能为空");;
				return;
			}
			if (startDate!="" && endDate!=""){
				if (endDate<=startDate){
					alert("结束日期至少要大于起始日期");
					return;
				}
			}
			if (!regExp.test(cost)){
				alert("成本只能是非负整数");
				return;
			}
			$.ajax({
				url:'workbench/activity/insertOne.do',
				data:{
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code == "1"){
						//关闭窗口
						$("#createActivityModal").modal("hide");
						//刷新市场活动页面（未完成）
						activityByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
					}else {
						alert(data.msg);
					}
				}
			})
		})
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
		//查询按钮加单击事件
		$("#selectBtn").click(function () {
			activityByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
		})
		//页面加载执行查询函数

		activityByCondition(1,10);

		//给全选按钮绑定单击事件
		$("#checkAll").click(function () {
			$("#actTBody input[type='checkbox']").prop("checked",this.checked);

		})
		/*$("#actTBody input[type='checkbox']").click(function () {
			//判断所有checkbox是否全选或全不选中
			if($("#actTBody input[type='checkbox']").size()==$("#actTBody input[type='checkbox']:checked").size()){
				$("#checkAll").prop("checked",true);
			}else {
				$("#checkAll").prop("checked",false);
			}
		})*/
		$("#actTBody").on("click","input[type='checkbox']",function () {
			//判断所有checkbox是否全选或全不选中
			if($("#actTBody input[type='checkbox']").size()==$("#actTBody input[type='checkbox']:checked").size()){
				$("#checkAll").prop("checked",true);
			}else {
				$("#checkAll").prop("checked",false);
			}
		});
		//给删除键绑定单击事件
		$("#deleteBtn").click(function () {
			//收集选中的市场活动的id
			var checkedIds = $("#actTBody input[type='checkbox']:checked");
			if (checkedIds.size()==0){
				alert("至少选择一条数据");
				return;
			}
			if(window.confirm("确定删除该条记录么")){
				var ids = "";
				$.each(checkedIds,function (index, element) {
					ids += "id=" + element.value + "&";
				})
				ids = ids.substr(0,ids.length-1);
				//发送请求
				$.ajax({
					url:'workbench/activity/deleteByIds.do',
					data:ids,
					type:'post',
					dataType:'json',
					success:function (data) {
						if (data.code=="1"){
							activityByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
						}else {
							alert(data.msg)
						}
					}

				});
			}
		});
		//给修改按钮绑定单击事件
		$("#editActBtn").click(function () {
			//收集被选中的id参数
			var checkedBoxById = $("#actTBody input[type='checkbox']:checked");
			if (checkedBoxById.size()==0){
				alert("至少选中一条数据");
				return;
			}
			if (checkedBoxById.size()>1){
				alert("只能选择一条数据修改");
				return;
			}
			var id = checkedBoxById.val();

			$.ajax({
				url:'workbench/activity/selectById.do',
				data:{
					id:id
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					//把市场活动信息展示在模态窗口上
					$("#edit-id").val(data.id);
					$("#edit-marketActivityOwner").val(data.owner);
					$("#edit-marketActivityName").val(data.name);
					$("#edit-startTime").val(data.startDate);
					$("#edit-endTime").val(data.endDate);
					$("#edit-cost").val(data.cost);
					$("#edit-describe").val(data.description);
					$("#editActivityModal").modal("show");
				}
			})
		});

		//给更新按钮绑定单击事件
		$("#saveBtn").click(function () {
			//收集参数
			var id = $("#edit-id").val();
			var owner = $("#edit-marketActivityOwner").val();
			var name = $.trim($("#edit-marketActivityName").val());
			var startDate = $("#edit-startTime").val();
			var endDate = $("#edit-endTime").val();
			var cost = $.trim($("#edit-cost").val());
			var description = $("#edit-describe").val();
			var regExp = /^(([1-9]\d*)|0)$/;
			//表单验证
			if (owner == ""){
				alert("所有者不能为空");
				return;
			}
			if (name == ""){
				alert("名称不能为空");;
				return;
			}
			if (startDate!="" && endDate!=""){
				if (endDate<=startDate){
					alert("结束日期至少要大于起始日期");
					return;
				}
			}
			if (!regExp.test(cost)){
				alert("成本只能是非负整数");
				return;
			}
			$.ajax({
				url:'workbench/activity/updateActivityByAllCon.do',
				data:{
					id:id,
					owner:owner,
					name:name,
					startDate:startDate,
					endDate:endDate,
					cost:cost,
					description:description
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code == "1"){
						$("#editActivityModal").modal("hide");
						activityByCondition($("#demo_pag1").bs_pagination('getOption','currentPage'),$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
					}else {
						alert(data.msg);
					}
				}
			})

		});
		//批量下载
		$("#exportActivityAllBtn").click(function () {
			if (window.confirm("确定导出所有数据么")) {
				window.location.href = "workbench/activity/allActivityDownload.do";
			}
		})
		//选择下载
		$("#exportActivityXzBtn").click(function () {
			//收集选中的数据中的id
			var checkedIds = $("#actTBody input[type='checkbox']:checked");
			if (checkedIds.size()==0){
				alert("至少选中一条数据");
				return;
			}
			var ids = "";
			$.each(checkedIds,function (index,element) {
				ids += "id=" + element.value + "&";
			})
			ids.substr(0,ids.length-1);
			window.location.href = "workbench/activity/getActByCon.do" + "?" +ids;
		});

		//给文件导入按钮绑定单击事件
		$("#importActivityBtn").click(function () {
			var fileName = $("#activityFile").val();
			var substr = fileName.substr(fileName.lastIndexOf(".") + 1);
			substr = substr.toLowerCase();
			if (substr!="xls"){
				alert("请选择以xls结尾的文件");
				return;
			}
			var file = $("#activityFile")[0].files[0];
			if (file.size > 1024*1024*5){
				alert("文件过于庞大，请重新上传");
				return;
			}
			var formData = new FormData();
			formData.append("myFile",file);
			$.ajax({
				url:'workbench/activity/fileUpload.do',
				data:formData,
				processData:false,//默认统一转为字符串，设置为关闭该功能
				contentType:false,//设置是否把所有参数按urlencoded编码，默认为true
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code=="1"){
						alert("成功导入" + data.retData + "条纪记录");
						//关闭模态窗口
						$("#importActivityModal").modal("hide");
						activityByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
					}else {
						alert(data.msg);
						$("#importActivityModal").modal("show");
					}
				}
			})
		});

	});

	//封装函数
	function activityByCondition(pageNo,pageSize) {
		var owner = $.trim($("#ownerByGet").val());
		var name = $.trim($("#nameByGet").val());
		var startDate = $("#startTimeByGet").val();
		var endDate = $("#endTimeByGet").val();
		//var pageNo = 1;
		//var pageSize = 10;
		$.ajax({
			url:'workbench/activity/getAllByCon.do',
			data:{
				owner:owner,
				name:name,
				startDate:startDate,
				endDate:endDate,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type:'post',
			dataType:'json',
			success:function (data) {
				//总条数显示到页面
				//$("#rowsB").text(data.totalRows);
				//市场活动列表
				var htmlVar = "";
				$.each(data.activityList,function (index,element) {
					htmlVar += "<tr class=\"active\">";
					htmlVar += "<td><input type=\"checkbox\" value=\""+element.id+"\"/></td>"
					htmlVar += "<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/getDetailActivity.do?id="+element.id+"';\">"+element.name+"</a></td>";
					htmlVar += "<td>"+element.owner+"</td>";
					htmlVar += "<td>"+element.startDate+"</td>";
					htmlVar += "<td>"+element.endDate+"</td>";
					htmlVar += "<td>"+element.cost+"</td>";
					htmlVar += "<td>"+element.description+"</td>";
					htmlVar += "</tr>";
				});
				$("#actTBody").html(htmlVar);
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
						activityByCondition(pageObj.currentPage,pageObj.rowsPerPage);
					}
				});
			}
		});
	}
	
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="actForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${list}" var="u">
									  <option value="${u.id}">${u.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;color: #2a6496">
								<input type="text" class="form-control myDt" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;color: #2a6496">
								<input type="text" class="form-control myDt" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveActBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${list}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;color: #2a6496">
								<input type="text" class="form-control myDt" id="edit-startTime" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;color: #2a6496">
								<input type="text" class="form-control myDt" id="edit-endTime" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>请在所有者字段和创建者字段中填写贵公司在职员工的姓名。</li>
                            <li>录入系统的日期值以您在Excel文件中的数据为准。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式，例如2022-04-26。</li>
                            <li>时间值以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式，例如2022-04-26 22:31:00。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="nameByGet">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="ownerByGet">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="startTimeByGet" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="endTimeByGet">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="selectBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createActBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editActBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
							<td>成本(￥)</td>
							<td>说明</td>
						</tr>
					</thead>
					<tbody id="actTBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
				<div id="demo_pag1"></div>
			</div>


			
			<%--<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="rowsB">50</b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" id="pageSizeForOne" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" id="pageSizeForTwo">20</a></li>
							<li><a href="#" id="pageSizeForThree">30</a></li>
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