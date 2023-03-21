<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng ký</title>
    <jsp:include page="./common/styles.jsp"/>
</head>
<body>
<div class="d-flex justify-content-center">
    <form method="post" class="col-md-5">
        <div class="form-group">
            <label for="username">Tài khoản</label>
            <input value="${user.username}" type="text" class="form-control" id="username" name="username" placeholder="Nhập tài khoản">
        </div>

        <div class="form-group">
            <label for="fullname">Họ và tên</label>
            <input value="${user.fullName}" type="text" class="form-control" id="fullname" name="fullName" placeholder="Nhập họ và tên">
        </div>

        <div class="form-group">
            <label for="phone">Số điện thoại</label>
            <input value="${user.phone}" type="text" class="form-control" id="phone" name="phone" placeholder="Nhập số điện thoại">
        </div>

        <div class="form-group">
            <label for="address">Địa chỉ</label>
            <input value="${user.address}" type="text" class="form-control" id="address" name="address" placeholder="Nhập địa chỉ">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input value="${user.password}" type="password" class="form-control" id="password" placeholder="Nhập mật khẩu" name="password">
        </div>
        <button type="submit" class="btn btn-primary">Đăng ký</button>
        <a href="${pageContext.request.contextPath}/login">Bạn đã có tài khoản?</a>
    </form>
</div>
</body>
<script>
    const error = '${error}';
    if (error) {
        toastr.error(error);
    }
</script>
</html>