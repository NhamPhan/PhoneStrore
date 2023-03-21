<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập</title>
    <jsp:include page="./common/styles.jsp"/>
</head>
<body>
<div class="d-flex justify-content-center">
    <form method="post" class="col-md-5">
        <div class="form-group">
            <label for="username">Tài khoản</label>
            <input type="text" class="form-control" id="username" name="username" placeholder="Nhập tài khoản">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" placeholder="Nhập mật khẩu" name="password">
        </div>
        <button type="submit" class="btn btn-primary">Đăng nhập</button>
        <a href="${pageContext.request.contextPath}/register">Bạn chưa có tài khoản?</a>
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
