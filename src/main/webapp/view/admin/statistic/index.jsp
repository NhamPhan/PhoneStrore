<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        Thống kê hóa đơn | Phone store
    </title>
    <jsp:include page="../../common/styles.jsp"/>
</head>
<body>
<jsp:include page="../common/navbar.jsp"/>
<div class="cart-main-area mt-4">
    <div class="container">
        <form class="d-flex justify-content-between">
            <h3 class="cart-page-title">Thống kế hóa đơn</h3>
            <input class="form-control col-md-3" type="date" name="createdDate" onchange="form.submit()" />
        </form>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="table-content table-responsive cart-table-content">
                    <table class="w-100">
                        <thead>
                        <tr>
                            <th>Người Mua</th>
                            <th>Ngày Mua</th>
                            <th>Mã Giảm Giá</th>
                            <th>Tổng tiền</th>
                            <th>Hình thức thanh toán</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${histories}">
                                <tr>
                                    <td class="product-name">${item.username}</td>
                                    <td class="product-price-cart">
                                        <span class="amount">${item.formattedDate}</span>
                                    </td>
                                    <td class="product-quantity">
                                        <span>${item.discountCode}</span>
                                    </td>
                                    <td class="product-subtotal">${item.totalVnd}</td>
                                    <td>${item.paymentMethod}</td>
                                    <td class="product-remove">
                                        <button
                                                class="btn btn-primary"
                                                style="color: white"
                                                data-toggle="modal"
                                                data-target="#history${item.id}">
                                            Xem chi tiết
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="5" style="font-size: 20px; font-weight: bold">
                                    Tổng tiền: ${totalPrice}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <c:forEach var="item" items="${histories}">
        <div class="modal fade" id="history${item.id}" tabindex="-1" role="dialog" >
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">x</span></button>
                    </div>
                    <div class="modal-body">
                        <div class="container">
                            <div class="d-flex justify-content-between">
                                <h3 class="cart-page-title">Đơn hàng chi tiết</h3>
                                <h3 class="cart-page-title">
                                    ${item.totalVnd}
                                    <c:if test="${item.discountCode ne null}">
                                        - ${item.discountCode}
                                    </c:if>
                                </h3>
                            </div>
                            <div class="row">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                                    <div class="table-content table-responsive cart-table-content">
                                        <table>
                                            <thead>
                                            <tr>
                                                <th>Hình Ảnh</th>
                                                <th>Tên Sách</th>
                                                <th>Giá Tiền</th>
                                                <th>Số Lượng</th>
                                                <th>Tổng Tiền</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="detail" items="${item.billDetailDTOS}">
                                                <tr>
                                                    <td class="product-thumbnail">
                                                        <a href="${pageContext.request.contextPath}/product/detail?id=${detail.product.id}">
                                                            <img style="width: 98px; height: 98px" src="${pageContext.request.contextPath}${detail.product.image}" alt="${detail.product.name}" />
                                                        </a>
                                                    </td>
                                                    <td class="product-name">${detail.product.name}</td>
                                                    <td class="product-price-cart">
                                                        <span class="amount">${detail.priceVnd}</span>
                                                    </td>
                                                    <td class="product-quantity">${detail.quantity}</td>
                                                    <td class="product-subtotal">${detail.subtotalVnd}</td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
<script src="${pageContext.request.contextPath}/view/js/vendor/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/view/js/vendor/modernizr-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/meanmenu.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/owl-carousel.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/jquery.nice-select.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/countdown.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/elevateZoom.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/slick.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/scrollup.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/range-script.js"></script>
<script src="${pageContext.request.contextPath}/view/js/plugins/fontawesome.min.js"></script>
<script src="${pageContext.request.contextPath}/view/js/main.js"></script>
</html>
