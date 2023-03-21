<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Loại sản phẩm | Phone store</title>
    <jsp:include page="../../common/styles.jsp"/>
</head>
<body style="background: #eef0f8">
<jsp:include page="../common/navbar.jsp"/>
<c:if test="${category eq null}">
    <span style="color: red; font-size: 20px">Không tìm thấy category!!!</span>
</c:if>

<c:if test="${category ne null}">

    <div class="mt-5">
        <div class="container" style="background: white">
            <form method="post" class="d-flex flex-wrap py-4">
                <input value="${category.id}" hidden name="id" />

                <div class="form-group col-md-6">
                    <label for="name" class="required">Tên loại sản phẩm</label>
                    <input type="text" class="form-control" id="name" placeholder="Nhập tên của loại sản phẩm" name="name" value="${category.name}">
                </div>

                <div class="col-md-12">
                    <button class="btn btn-primary">Lưu lại</button>
                    <a class="btn btn-danger ml-3" href="${pageContext.request.contextPath}/admin/category">Hủy bỏ</a>
                </div>
            </form>
        </div>
    </div>
</c:if>
</body>
<script>
    const e = '${error}';
    console.log(e);
    if (e) {
        toastr.error(e);
    }
</script>
</html>
