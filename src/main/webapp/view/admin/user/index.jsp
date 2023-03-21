<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản lý người dùng | Phone store</title>
    <jsp:include page="../../common/styles.jsp"/>
    <style>
        .user-active {
            color: #00a3ae;
            background: #f4feff;
        }

        .user-inactive {
            color: #df642a;
            background: #ffede4;
        }
    </style>
</head>
<body>
<jsp:include page="../common/navbar.jsp"/>

<div style="background: #eef0f8">

    <div class="container p-0" style="background: white">
        <div class="font-weight-bold p-2" style="border-bottom: 1px solid #ebedf3">THÔNG TIN TÌM KIẾM</div>
        <div>
            <form class="d-flex flex-wrap justify-content-center p-3" action="<c:url value="/admin/user"/>">
                <div class="form-group col-md-5">
                    <label>Tên đăng nhập: </label>
                    <input placeholder="Nhập tên đăng nhập" name="username" value="${param.username}" class="form-control" />
                </div>
                <div class="form-group col-md-5">
                    <label>Họ và tên: </label>
                    <input placeholder="Nhập họ và tên" name="fullName" value="${param.fullName}" class="form-control" />
                </div>
                <div class="form-group col-md-5">
                    <label>Địa chỉ: </label>
                    <input placeholder="Nhập địa chỉ" name="address" value="${param.address}" class="form-control" />
                </div>
                <div class="form-group col-md-5">
                    <label>Trạng thái: </label>
                    <select class="form-control" name="status">
                        <option value="-1">Chọn trạng thái</option>
                        <option value="1" ${param.status == 1 || param.status == '' ? 'selected' : ''}>Active</option>
                        <option value="0" ${param.status == 0 ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>

                <div class="d-flex justify-content-center col-md-12">
                    <button class="btn btn-primary">Tìm kiếm</button>
                </div>
            </form>
        </div>
    </div>
    <div class="container p-0"style="background: white">
        <div class="d-flex justify-content-between align-items-center p-2" style="border-bottom: 1px solid #ebedf3">
            <div class="font-weight-bold">KẾT QUẢ TÌM KIẾM</div>
        </div>
        <div class="p-2">
            <table class="table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Tên đăng nhập</th>
                    <th scope="col">Họ và tên</th>
                    <th scope="col">Địa chỉ</th>
                    <th scope="col">Số điện thoại</th>
                    <th scope="col">Trạng thái</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach varStatus="statusLoop" var="user" items="${users}">
                    <tr>
                        <td style="vertical-align: middle; text-align: center">${statusLoop.index + 1}</td>
                        <td style="vertical-align: middle">
                            ${user.username}
                        </td>
                        <td style="vertical-align: middle; max-width: 500px;" class="text-dot">${user.fullName}</td>
                        <td style="vertical-align: middle">${user.address}</td>
                        <td style="vertical-align: middle">${user.phone}</td>
                        <td style="vertical-align: middle">
                            <c:if test="${user.status == 1}">
                                <span class="user-active">Hoạt động</span>
                            </c:if>

                            <c:if test="${user.status == 0}">
                                <span class="user-inactive">Không hoạt động</span>
                            </c:if>
                        </td>
                        <td style="vertical-align: middle">
                            <c:if test="${user.status == 1}">
                                <form method="post" action="<c:url value="/admin/user/lock"/>">
                                    <input name="username" value="${user.username}" hidden/>
                                    <button class="btn btn-danger">Khóa</button>
                                </form>
                            </c:if>
                            <c:if test="${user.status == 0}">
                                <form method="post" action="<c:url value="/admin/user/unlock"/>">
                                    <input name="username" value="${user.username}" hidden/>
                                    <button class="btn btn-success">Mở khóa</button>
                                </form>
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
    function confirmBeforeRemove(bookId) {
        const i = window.confirm("Bạn có chắc chắn muốn xóa không?")
        if (i) {
            window.location.href = '${pageContext.request.contextPath}/admin/product/remove?id='+bookId;
        }
    }
</script>
</html>
