<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Phiếu giảm giá | Phone store</title>
  <jsp:include page="../../common/styles.jsp"/>
</head>
<body style="background: #eef0f8">
<jsp:include page="../common/navbar.jsp"/>

<div class="mt-5">
  <div class="container" style="background: white">
    <form method="post" class="d-flex flex-wrap py-4">
      <input value="${discount.id}" hidden name="id" />

      <div class="form-group col-md-6">
        <label for="code" class="required">Mã giảm giá: </label>
        <input type="text" class="form-control" id="code" placeholder="Nhập mã giảm giá" name="code" value="${discount.code}">
      </div>

      <div class="form-group col-md-6">
        <label for="percent" class="required">Giảm giá (%):</label>
        <input type="number" class="form-control" id="percent" placeholder="Nhập tỷ lệ giảm giá" name="percent" value="${discount.percent}" min="1" max="100">
      </div>


      <div class="form-group col-md-6">
        <label for="expireDate" class="required">Ngày hết hạn</label>
        <input type="date" class="form-control" id="expireDate" name="expireDate" value="${discount.expireDate}">
      </div>

      <div class="form-group col-md-6">
        <label for="status" class="required">Trạng thái</label>
        <select class="form-control" id="status" name="status">
          <option value="0" ${param.status == 0 ? 'selected' : ''}>Kích hoạt</option>
          <option value="1" ${param.status == 1 ? 'selected' : ''}>Ngừng kích hoạt</option>
        </select>
      </div>

      <div class="d-flex justify-content-center">
        <button class="btn btn-primary">Lưu lại</button>
      </div>
    </form>
  </div>
</div>
</body>
<script>
  const e = '${error}';
  if (e) {
    toastr.error(e);
  }
</script>
</html>