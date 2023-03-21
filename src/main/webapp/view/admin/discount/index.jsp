<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Quản lý phiếu giảm giá | Phone store</title>
    <jsp:include page="../../common/styles.jsp"/>
</head>
<body>
<jsp:include page="../common/navbar.jsp"/>

<div style="background: #eef0f8" class="h-100">

    <div class="container p-0 h-100"style="background: white">
        <div class="d-flex justify-content-between align-items-center p-2" style="border-bottom: 1px solid #ebedf3">
            <div class="font-weight-bold">KẾT QUẢ TÌM KIẾM</div>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/discount/create">Tạo phiếu giảm giá</a>
        </div>
        <div class="p-2">
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Mã giảm giá</th>
                    <th scope="col">Giảm giá (%)</th>
                    <th scope="col">Ngày hết hạn</th>
                    <th scope="col">Trạng thái</th>
                    <th scope="col" colspan="2"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach varStatus="statusLoop" var="discount" items="${discounts}">
                    <tr>
                        <td>${statusLoop.index + 1}</td>
                        <td>${discount.code}</td>
                        <td>${discount.percent}</td>
                        <td>
                            <fmt:formatDate value="${discount.expireDate}" pattern="dd-MM-yyyy" />
                        </td>
                        <td>${discount.status == 0 ? 'Kích hoạt' : 'Ngừng kích hoạt'}</td>
                        <td>
                            <c:if test="${discount.status == 0}">
                                <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin/discount/cancel?discountCode=${discount.code}">
                                    Hủy
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
<script>
    const error = `${error}`;
    if (error) {
        toastr.error(error);
    }
</script>
</html>