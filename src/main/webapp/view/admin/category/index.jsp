<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý loại sản phẩm | Phone store</title>
    <jsp:include page="../../common/styles.jsp"/>
</head>
<body>
<jsp:include page="../common/navbar.jsp"/>

<div style="background: #eef0f8">
    <div class="container p-0"style="background: white">
        <div class="d-flex justify-content-between align-items-center p-2" style="border-bottom: 1px solid #ebedf3">
            <div class="font-weight-bold"></div>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/create">Tạo mới loại sản phẩm</a>
        </div>
        <div class="p-2">
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Tên</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach varStatus="statusLoop" var="category" items="${categories}">
                    <tr>
                        <td style="vertical-align: middle; text-align: center">${statusLoop.index + 1}</td>
                        <td style="vertical-align: middle; max-width: 500px;" class="text-dot" title="${category.name}">${category.name}</td>
                        <td style="vertical-align: middle">
                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/update?id=${category.id}">Cập nhật</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
