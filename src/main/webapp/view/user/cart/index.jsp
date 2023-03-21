<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Giỏ hàng | Phone store</title>
    <jsp:include page="../../common/styles.jsp"/>
</head>
<body>
<jsp:include page="../common/navbar.jsp"/>
<div class="cart-main-area mtb-60px">
    <div class="container">
        <h3 class="cart-page-title">Giỏ hàng của bạn</h3>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="table-content table-responsive cart-table-content">
                    <table>
                        <thead>
                        <tr>
                            <th>Hình Ảnh</th>
                            <th>Tên Sản Phẩm</th>
                            <th>Giá Tiền</th>
                            <th>Số Lượng</th>
                            <th>Tổng Tiền</th>
                            <th>Tháo Tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${carts}">
                            <tr>
                                <td class="product-thumbnail">
                                    <a href="${pageContext.request.contextPath}/product/detail?id=${item.product.id}">
                                        <img style="width: 98px; height: 98px"
                                             src="${pageContext.request.contextPath}${item.product.image}"
                                             alt="${item.product.name}"/>
                                    </a>
                                </td>
                                <td class="product-name">${item.product.name}</td>
                                <td class="product-price-cart">
                                    <span class="amount">${item.priceVnd}</span>
                                </td>
                                <td class="product-quantity">
                                    <div class="cart-plus-minus">
                                        <a class="dec qtybutton"
                                           href="${pageContext.request.contextPath}/cart/update?id=${item.id}&quantity=${item.quantity - 1}">-</a>
                                        <input class="cart-plus-minus-box" type="text" name="qtybutton"
                                               value="${item.quantity}"/>
                                        <a class="inc qtybutton increase-button" style="z-index: 1000"
                                           href="${pageContext.request.contextPath}/cart/update?id=${item.id}&quantity=${item.quantity + 1}">+</a>
                                    </div>
                                </td>
                                <td class="product-subtotal">${item.subtotalVnd}</td>
                                <td class="product-remove">
                                    <button class="btn btn-danger" style="color: white"
                                            onclick="confirmBeforeDelete('${item.id}')">Xóa
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="cart-shiping-update-wrapper">
                            <div class="cart-shiping-update">
                                <a href="${pageContext.request.contextPath}/home">Tiếp tục mua sắm</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">

                    <div class="col-lg-8 col-md-12">
                        <div class="grand-totall">
                            <div class="title-wrap">
                                <h4 class="cart-bottom-title section-bg-gary-cart">Phiếu giảm giá</h4>
                            </div>
                            <div class="d-flex my-3" style="gap: 10px; overflow-x: auto">
                                <button type="button" class="col-md-3 bg-white"
                                        style="border: 2px solid #4fb68b; border-radius: 20px"
                                        onclick="clearDiscount()">
                                    <div>KHÔNG CHỌN GIẢM GIÁ</div>
                                </button>
                                <c:forEach var="card" items="${discountCards}">
                                    <button type="button" class="col-md-3 bg-white"
                                            style="border: 2px solid #4fb68b; border-radius: 20px"
                                            onclick="printDiscountCode('${card.code}', ${card.percent})">
                                        <div>Mã giảm giá: <span class="text-dot"
                                                                title="${card.code}">${card.code}</span></div>
                                        <div>Giảm giá: ${card.percent}%</div>
                                    </button>
                                </c:forEach>
                            </div>
                            <input placeholder="Chọn mã giảm giá" class="form-control" name="discountCode" readonly/>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-12">
                        <div class="grand-totall">
                            <div class="title-wrap">
                                <h4 class="cart-bottom-title section-bg-gary-cart">Thông tin thành tiền</h4>
                            </div>
                            <h5>Tổng số lượng sản phẩm: ${totalBook}</h5>
                            <h4 class="grand-totall-title">Tổng tiền: <span id="totalPrice">${totalPrice}</span></h4>
                            <div class="d-flex justify-content-between">
                                <a class="col-md-12" data-toggle="modal" data-target="#payment"
                                        style="border: none; width: 200px;">Thanh toán
                                </a>
                                <div id="payment" class="modal fade" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h3>Thông tin thanh toán</h3>
                                            </div>
                                            <form action="${pageContext.request.contextPath}/bill/checkout"
                                                  class="p-0">
                                                <input name="discountCode" hidden/>
                                                <div class="modal-body">
                                                    <div>
                                                        <label>Địa chỉ nhận hàng</label>
                                                        <input name="address" class="form-control"
                                                               value="${sessionScope.USER_LOGGED_IN.address}" required/>
                                                    </div>

                                                    <div>Số điện thoại</div>
                                                    <input name="phone" class="form-control"
                                                           value="${sessionScope.USER_LOGGED_IN.phone}" required/>
                                                </div>

                                                <div class="modal-footer justify-content-center">
                                                    <button class="btn btn-primary mr-2"
                                                            style="padding: 0.375rem 0.75rem; border-radius: 4px; height: 38px"
                                                            type="submit">Thanh toán
                                                    </button>
                                                    <button class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/view/js/plugins/fontawesome.min.js"></script>
<script>

    const error = '${error}';
    if (error) {
        toastr.error(error);
    }

    const discountCodeDOMs = document.querySelectorAll('input[name=discountCode]')
    const totalPriceDOM = document.querySelector('#totalPrice');
    const totalPrice = '${totalPrice}';

    function printDiscountCode(discountCode, percent) {
        discountCodeDOMs.forEach(discountCodeDOM => discountCodeDOM.value = discountCode)
        calculateTotalPrice(percent)
    }

    function calculateTotalPrice(percent) {
        const totalPriceNum = parseInt(totalPrice.substring(0, totalPrice.length - 1).replaceAll('.', ''))
        const discount = totalPriceNum * percent / 100;
        const newTotalPriceText = (totalPriceNum - discount).toLocaleString('it-IT', {
            style: 'currency',
            currency: 'VND'
        });
        totalPriceDOM.textContent = newTotalPriceText.replaceAll(',', '.').split('VND')[0].trim() + ' đ';
    }

    function clearDiscount() {
        discountCodeDOM.value = null;
        totalPriceDOM.textContent = totalPrice;
    }

    function confirmBeforeDelete(cartId) {
        const i = window.confirm("Bạn có chắc chắn muốn xóa không?")
        if (i) {
            window.location.href = '${pageContext.request.contextPath}/cart/remove?id=' + cartId;
        }
    }
</script>
</html>
