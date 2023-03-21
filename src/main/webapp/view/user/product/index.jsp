<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Trang chủ | Phone store</title>
    <jsp:include page="../../common/styles.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/user/product/css/product.css">
</head>
<body>
<jsp:include page="../common/navbar.jsp"/>

<div class="slider-area">
    <div class="slider-active-3 owl-carousel slider-hm8 owl-dot-style">

        <div class="slider-height-6 d-flex align-items-start justify-content-start bg-img" style="background: url(${pageContext.request.contextPath}/images/slider-image/banner-iphone-hands_3_by-tranmautritam.jpg);">
            <div class="container">
                <div class="slider-content-1 slider-animated-1 text-left">
                    <span class="animated" style="color: white">IPHONE RẺ VÔ ĐỐI</span>
                    <h1 class="animated" style="color: white">
                        Chính sách, xách tay - máy nguyên seal
                    </h1>
                </div>
            </div>
        </div>

        <div class="slider-height-6 d-flex align-items-start justify-content-start bg-img" style="background-image: url(${pageContext.request.contextPath}/images/slider-image/maxresdefault.jpg);">
            <div class="container">
                <div class="slider-content-1 slider-animated-1 text-left">
                    <span class="animated"></span>
                    <h1 class="animated">
                    </h1>
                    <p class="animated"></p>
                </div>
            </div>
        </div>
    </div>
</div>

<section class="static-area mt-60px">
    <div class="container">
        <div class="static-area-wrap">
            <div class="row">

                <div class="col-lg-3 col-xs-12 col-md-6 col-sm-6">
                    <div class="single-static pb-res-md-0 pb-res-sm-0 pb-res-xs-0">
                        <img src="${pageContext.request.contextPath}/images/icons/static-icons-1.png" alt="" class="img-responsive" />
                        <div class="single-static-meta">
                            <h4>Miễn phí vận chuyển</h4>
                            <p>Đối với tất cả các đơn đặt hàng trên 100.000VNĐ</p>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-xs-12 col-md-6 col-sm-6">
                    <div class="single-static pb-res-md-0 pb-res-sm-0 pb-res-xs-0 pt-res-xs-20">
                        <img src="${pageContext.request.contextPath}/images/icons/static-icons-2.png" alt="" class="img-responsive" />
                        <div class="single-static-meta">
                            <h4>Trả hàng miễn phí</h4>
                            <p>Trả hàng miễn phí trong vòng 9 ngày</p>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-xs-12 col-md-6 col-sm-6">
                    <div class="single-static pt-res-md-30 pb-res-sm-30 pb-res-xs-0 pt-res-xs-20">
                        <img src="${pageContext.request.contextPath}/images/icons/static-icons-3.png" alt="" class="img-responsive" />
                        <div class="single-static-meta">
                            <h4>Thanh toán an toàn 100%</h4>
                            <p>Thanh toán của bạn được an toàn với chúng tôi.</p>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-xs-12 col-md-6 col-sm-6">
                    <div class="single-static pt-res-md-30 pb-res-sm-30 pt-res-xs-20">
                        <img src="${pageContext.request.contextPath}/images/icons/static-icons-4.png" alt="" class="img-responsive" />
                        <div class="single-static-meta">
                            <h4>Hỗ trợ 24/7</h4>
                            <p>Liên hệ với chúng tôi 24 giờ một ngày</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<div>
    <div class="container py-2 d-flex px-0" style="background: white">
        <div class="col-md-3 p-0" style="position: sticky;top: 50px;align-self: flex-start;">
            <form class="d-flex flex-wrap justify-content-center p-3">
                <div class="form-group col-md-12 p-0">
                    <label>Tên sản phẩm: </label>
                    <input placeholder="Nhập tên sản phẩm" name="name" value="${param.name}" class="form-control" />
                </div>
                <div class="form-group col-md-12 p-0 my-select-group">
                    <label>Loại sản phẩm: </label>
                    <select class="form-control" name="idCategory" style="width: 100%;">
                        <c:forEach varStatus="statusLoop" var="category" items="${categories}">
                            <option value="${category.id}" ${category.id == param.idCategory || statusLoop.index == 0 ? 'selected' : ''}>${category.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group col-md-12 p-0">
                    <label>Từ giá: </label>
                    <input type="number" placeholder="Nhập giá" name="fromPrice" value="${param.fromPrice}" class="form-control" />
                </div>
                <div class="form-group col-md-12 p-0">
                    <label>Đến giá: </label>
                    <input type="number" placeholder="Nhập giá" name="toPrice" value="${param.toPrice}" class="form-control" />
                </div>
                <div class="d-flex justify-content-center col-md-12 p-0">
                    <button class="btn btn-primary">Tìm kiếm</button>
                </div>
            </form>
        </div>
        <div class="col-md-9">
            <div class="font-weight-bold">SẢN PHẨM</div>
            <div class="d-flex flex-wrap">
                <c:forEach varStatus="statusLoop" var="product" items="${products}">
                    <a class="col-md-3 product py-3" href="${pageContext.request.contextPath}/product/detail?id=${product.id}" target="_blank">
                        <div class="d-flex flex-column">
                            <div style="position: relative">
                                <c:if test="${product.quantity == 0}">
                                    <div class="product-label" style="--primary: #f35959">Hết hàng</div>
                                </c:if>
                                <c:if test="${product.quantity > 0}">
                                    <div class="product-label" style="--primary: #08b308">Còn hàng</div>
                                </c:if>
                                <img class="product-image" src="${pageContext.request.contextPath}${product.image}"/>
                            </div>
                            <span class="product-name" title="${product.name}">${product.name} </span>
                            <div class="d-flex align-items-center">
                                <div>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                    <i class="fas fa-star"></i>
                                </div>
                                <span class="product__quantity">&ensp;|&ensp;Số lượng: ${product.quantity}</span>
                            </div>
                            <span class="product-price">${product.priceVnd}</span>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    const error = '${error}';
    if (error) {
        toastr.error(error);
    }
</script>
</html>
