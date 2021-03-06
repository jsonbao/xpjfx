<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收入记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cw/fIncomeRecord/">收入记录列表</a></li>
	<!--	<shiro:hasPermission name="cw:fIncomeRecord:edit"><li><a href="${ctx}/cw/fIncomeRecord/form">收入记录添加</a></li></shiro:hasPermission> -->
	</ul>
	<form:form id="searchForm" modelAttribute="fIncomeRecord" action="${ctx}/cw/fIncomeRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>订单编号：</label>
				<form:input path="ddbh" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>来往账号：</label>
				<form:input path="traverAccount" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>收入账号：</label>
				<form:input path="incomeAccount" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单编号</th>
				<th>来往帐号</th>
				<th>收入帐号</th>
				<th>支付时间</th>
				<th>收入金额</th>
				<th>备注</th>
				<shiro:hasPermission name="cw:fIncomeRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fIncomeRecord">
			<tr>
				<td>
						${fIncomeRecord.ddbh}
				</td>
				<td>
						${fIncomeRecord.traverAccount}
				</td>
				<td>
						${fIncomeRecord.incomeAccount}
				</td>
				<td>
					<fmt:formatDate value="${fIncomeRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
						${fIncomeRecord.incomeMoney}
				</td>
				<td>
						${fIncomeRecord.remarks}
				</td>
				<shiro:hasPermission name="cw:fIncomeRecord:edit"><td>
    				<a href="${ctx}/cw/fIncomeRecord/form?id=${fIncomeRecord.id}">修改</a>
					<a href="${ctx}/cw/fIncomeRecord/delete?id=${fIncomeRecord.id}" onclick="return confirmx('确认要删除该收入记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
		<tr>
			<td colspan="4"><b>合计</b></td>
			<td colspan="3"><b>${Sum}￥</b></td>
		</tr>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>