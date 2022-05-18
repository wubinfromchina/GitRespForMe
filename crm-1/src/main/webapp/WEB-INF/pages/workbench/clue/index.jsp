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
		$(".nextTime").datetimepicker({
			language:'zh-CN', //语言
			format:'yyyy-mm-dd',//日期的格式
			minView:'month', //可以选择的最小视图
			initialDate:new Date(),//初始化显示的日期
			autoclose:true,//设置选择完日期或者时间之后，日否自动关闭日历
			todayBtn:true,//设置是否显示"今天"按钮,默认是false
			clearBtn:true//设置是否显示"清空"按钮，默认是false
		})
		//页面加载完毕展示数据
		clueByCondition(1,10);
		//查询按钮加单击事件
		$("#selectBtn").click(function () {
			clueByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
		})

		$("#clueTBody").on("click","input[type='checkbox']",function () {
			//判断所有checkbox是否全选或全不选中
			if($("#clueTBody input[type='checkbox']").size()==$("#clueTBody input[type='checkbox']:checked").size()){
				$("#checkAll").prop("checked",true);
			}else {
				$("#checkAll").prop("checked",false);
			}
		});
		//点击创建按钮弹出模态窗口
		$("#createOneModal").click(function () {
			//重置表单
			$("#createClueForm").get(0).reset();

			//弹出模态窗口
			$("#createClueModal").modal("show");
		});

		//给保存按钮绑定单击事件
		$("#saveOneClue").click(function (){
			//收集参数
			var fullname = $.trim($("#create-fullname").val());
			var appellation = $("#create-appellation").val();
			var owner = $("#create-owner").val();
			var company = $.trim($("#create-company").val());
			var job = $.trim($("#create-job").val());
			var email = $.trim($("#create-email").val());
			var phone = $.trim($("#create-phone").val());
			var website = $.trim($("#create-website").val());
			var mphone = $.trim($("#create-mphone").val());
			var state = $("#create-state").val();
			var source = $("#create-source").val();
			var description = $("#create-description").val();
			var contactSummary = $.trim($("#create-contactSummary").val());
			var nextContactTime = $.trim($("#create-nextContactTime").val());
			var address = $.trim($("#create-address").val());

			//表单验证
			if (company==""||fullname==""){
				alert("带*为必填项");
				return;
			}
			var retExp=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			if (!retExp.test(email)){
				alert("请输入合法邮箱");
				return;;
			}
			retExp=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
			if (!retExp.test(mphone)){
				alert("请输入准确的手机号");
				return;
			}
			retExp=/\d{3}-\d{8}|\d{4}-\d{7}/;
			if (!retExp.test(phone)){
				alert("座机号格式不正确，请输入例如以下格式的座机号:0511-4405222、021-87888822");
				return;
			}
			//发送异步请求
			$.ajax({
				url:'workbench/clue/saveClue.do',
				data:{
					fullname:fullname,
					appellation:appellation,
					owner:owner,
					company:company,
					job:job,
					email:email,
					phone:phone,
					website:website,
					mphone:mphone,
					state:state,
					source:source,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime,
					address:address
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code=='1'){
						$("#createClueModal").modal("hide");
						alert("创建成功");
						//回调函数刷新页面
						clueByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
					}else {
						alert(data.msg);
					}
				}
			});
		});
		//给删除键绑定单击事件
		$("#deleteBtn").click(function () {
			//收集选中的线索的id
			var checkedIds = $("#clueTBody input[type='checkbox']:checked");
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
					url:'workbench/clue/deleteByIds.do',
					data:ids,
					type:'post',
					dataType:'json',
					success:function (data) {
						if (data.code=="1"){
							clueByCondition(1,$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
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
			var checkedBoxById = $("#clueTBody input[type='checkbox']:checked");
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
				url:'workbench/clue/selectById.do',
				data:{
					id:id
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					//把市场活动信息展示在模态窗口上
					$("#edit-id").val(data.id);
					$("#edit-clueOwner").val(data.owner);
					$("#edit-company").val(data.company);
					$("#edit-appellation").val(data.appellation);
					$("#edit-fullname").val(data.fullname);
					$("#edit-job").val(data.job);
					$("#edit-email").val(data.email);
					$("#edit-phone").val(data.phone);
					$("#edit-website").val(data.website);
					$("#edit-mphone").val(data.mphone);
					$("#edit-state").val(data.state);
					$("#edit-source").val(data.source);
					$("#edit-description").val(data.description);
					$("#edit-contactSummary").val(data.contactSummary);
					$("#edit-nextContactTime").val(data.nextContactTime);
					$("#edit-address").val(data.address);

					$("#editClueModal").modal("show");
				}
			})
		});
		//给更新按钮绑定单击事件
		$("#updateClueBtn").click(function () {
			var id = $("#edit-id").val();
			var Owner = $("#edit-clueOwner").val();
			var company = $("#edit-company").val();
			var appellation = $("#edit-appellation").val();
			var fullname = $("#edit-fullname").val();
			var job = $("#edit-job").val();
			var email = $("#edit-email").val();
			var phone = $("#edit-phone").val();
			var website = $("#edit-website").val();
			var mphone = $("#edit-mphone").val();
			var state = $("#edit-state").val();
			var source = $("#edit-source").val();
			var description = $("#edit-description").val();
			var contactSummary = $("#edit-contactSummary").val();
			var nextContactTime = $("#edit-nextContactTime").val();
			var address = $("#edit-address").val();
			//表单验证
			if (company==""||fullname==""){
				alert("带*为必填项");
				return;
			}
			var retExp=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
			if (!retExp.test(email)){
				alert("请输入合法邮箱");
				return;;
			}
			retExp=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
			if (!retExp.test(mphone)){
				alert("请输入准确的手机号");
				return;
			}
			retExp=/\d{3}-\d{8}|\d{4}-\d{7}/;
			if (!retExp.test(phone)){
				alert("座机号格式不正确，请输入例如以下格式的座机号:0511-4405222、021-87888822");
				return;
			}
			$.ajax({
				url:'workbench/clue/updateByCon.do',
				data:{
					id:id,
					Owner:Owner,
					company:company,
					appellation:appellation,
					fullname:fullname,
					job:job,
					email:email,
					phone:phone,
					website:website,
					mphone:mphone,
					state:state,
					source:source,
					description:description,
					contactSummary:contactSummary,
					nextContactTime:nextContactTime,
					address:address
				},
				type:'post',
				dataType:'json',
				success:function (data) {
					if (data.code=='1'){
						clueByCondition($("#demo_pag1").bs_pagination('getOption','currentPage'),$("#demo_pag1").bs_pagination('getOption','rowsPerPage'));
						$("#editClueModal").modal("hide");
					}else {
						alert(data.msg);
					}
				}
			});
		});
		//给全选按钮绑定单击事件
		$("#checkAll").click(function () {
			$("#clueTBody input[type='checkbox']").prop("checked",this.checked);
		});
	});
	//封装查询所有列表函数
	//封装函数clueByCondition
	function clueByCondition(pageNo,pageSize) {
		var fullname = $.trim($("#name").val());
		var company = $.trim($("#company").val());
		var phone = $.trim($("#phone").val());
		var source = $("#selectSourceId").val();
		var owner = $("#selectOwnerId").val();
		var mphone = $.trim($("#mphone").val());
		var state = $("#selectClueStateId").val();

		$.ajax({
			url:'workbench/clue/getAllClueByCon.do',
			data:{
				fullname:fullname,
				company:company,
				phone:phone,
				source:source,
				owner:owner,
				mphone:mphone,
				state:state,
				pageNo:pageNo,
				pageSize:pageSize
			},
			type:'post',
			dataType:'json',
			success:function (data) {
				//总条数显示到页面
				//$("#rowsB").text(data.totalRows);
				//线索列表
				var htmlVar = "";
				$.each(data.clueList,function (index,e) {
					htmlVar+="<tr>";
					htmlVar+="	<td><input type=\"checkbox\" value='"+e.id+"'/></td>";
					htmlVar+="	<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/clue/detail.do?id="+e.id+"';\">"+e.fullname+e.appellation+"</a></td>";
					htmlVar+="	<td>"+e.company+"</td>";
					htmlVar+="	<td>"+e.phone+"</td>";
					htmlVar+="	<td>"+e.mphone+"</td>";
					htmlVar+="	<td>"+e.source+"</td>";
					htmlVar+="	<td>"+e.owner+"</td>";
					htmlVar+="	<td>"+e.state+"</td>";
					htmlVar+="</tr>";
				});
				$("#clueTBody").html(htmlVar);
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
						clueByCondition(pageObj.currentPage,pageObj.rowsPerPage);
					}
				});
			}
		});
	};
</script>
</head>
<body>

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form id="createClueForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								  <c:forEach items="${userList}" var="user">
									  <option value="${user.id}">${user.name}</option>
								  </c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-appellation">
								  <option></option>
									<c:forEach items="${appellationList}" var="appellation">
										<option value="${appellation.id}">${appellation.value}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-fullname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone">
							</div>
							<label for="create-state" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-state">
								  <option></option>
									<c:forEach items="${clueStateList}" var="clueState">
										<option value="${clueState.id}">${clueState.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source">
								  <option></option>
									<c:forEach items="${sourceList}" var="source">
										<option value="${source.id}">${source.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						

						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control nextTime" id="create-nextContactTime">
								</div>
								<label for="create-nextContactTime" class="col-sm-2 control-label"></label>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveOneClue">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
					
						<div class="form-group">
							<label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-clueOwner">
								  <c:forEach items="${userList}" var="user">
									  <option value="${user.id}">${user.name}</option>
								  </c:forEach>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" value="">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-appellation" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-appellation">
								  <option></option>
									<c:forEach items="${appellationList}" var="appellation">
										<option value="${appellation.id}">${appellation.value}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-fullname" value="李四">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job" value="CTO">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" value="010-84846003">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website" value="http://www.baidu.com">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" value="12345678901">
							</div>
							<label for="edit-state" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-state">
									<c:forEach items="${clueStateList}" var="clueState">
										<option value="${clueState.id}">${clueState.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source">
								  <option></option>
									<c:forEach items="${sourceList}" var="source">
										<option value="${source.id}">${source.value}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description">这是一条线索的描述信息</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary">这个线索即将被转换</textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control nextTime" id="edit-nextContactTime" value="2017-05-01">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address">北京大兴区大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateClueBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">姓名</div>
				      <input class="form-control" type="text" id="name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司</div>
				      <input class="form-control" type="text" id="company">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="phone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select class="form-control" id="selectSourceId">
					  	  <option></option>
						  <c:forEach items="${sourceList}" var="source">
							  <option value="${source.id}">${source.value}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
						<select class="form-control" id="selectOwnerId">
							<option></option>
							<c:forEach items="${userList}" var="user">
								<option value="${user.id}">${user.name}</option>
							</c:forEach>
						</select>
				    </div>
				  </div>
				  
				  
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input class="form-control" type="text" id="mphone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select class="form-control" id="selectClueStateId">
					  	<option></option>
						  <c:forEach items="${clueStateList}" var="clueState">
							  <option value="${clueState.id}">${clueState.value}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" id="selectBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createOneModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editActBtn" ><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll" /></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="clueTBody">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>
							<td>Google</td>
							<td>010-84846003</td>
							<td>12345678901</td>
							<td>广告</td>
							<td>zhangsan</td>
							<td>已联系</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">李四先生</a></td>
                            <td>Firefox</td>
                            <td>010-84846003</td>
                            <td>12345678901</td>
                            <td>广告</td>
                            <td>zhangsan</td>
                            <td>已联系</td>
                        </tr>--%>
					</tbody>
				</table>
				<div id="demo_pag1"></div>
			</div>
			
			<%--<div style="height: 50px; position: relative;top: 60px;">
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