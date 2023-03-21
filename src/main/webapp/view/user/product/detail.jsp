<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        <c:if test="${product ne null}">
            ${product.name} | Phone store
        </c:if>

        <c:if test="${product eq null}">
            Không tìm thấy sản phẩm | Phone store
        </c:if>
    </title>
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <jsp:include page="../../common/styles.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/user/product/css/product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/user/product/css/detail.css">
</head>
<body style="background: #eef0f8">
<jsp:include page="../common/navbar.jsp"/>
<c:if test="${product == null}">
    <div class="d-flex justify-content-center">
        <span class="h4 error">Không tìm thấy sản phẩm!!!</span>
    </div>
</c:if>
<c:if test="${product != null}">
    <div class="container bg-white detail d-flex">
        <div class="product-image col-md-4 d-flex flex-column">
            <div style="position: relative">
                <c:if test="${product.quantity == 0}">
                    <div class="product-label" style="--primary: #f35959">Hết hàng</div>
                </c:if>
                <c:if test="${product.quantity > 0}">
                    <div class="product-label" style="--primary: #08b308">Còn hàng</div>
                </c:if>
            </div>
            <img class="h-100" style="width: 50%; align-self: center;" src="${pageContext.request.contextPath}${product.image}" alt="${product.name}" }/>
            <div class="d-flex align-items-center mt-4" style="gap: 10px">
                <div class="shareable mr-2">Chia sẻ:</div>
                <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/social-facebook.svg" />
                <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/social-messenger.svg" />
                <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/social-pinterest.svg" />
                <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/social-twitter.svg" />
                <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/social-copy.svg" />
            </div>
        </div>
        <div class="product-info col-md-8 d-flex flex-column">
            <span class="detail-product-name font-weight-bold">${product.name}</span>
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
            <div class="container-price">
                <span class="detail-product-price">${product.priceVnd}</span>
            </div>
            <form class="mt-2" action="${pageContext.request.contextPath}/cart/create" method="post">
                <input name="id" value="${product.id}" hidden />
                <div>
                    <span class="label__quantity">Số lượng</span>
                    <div class="d-flex">
                        <button type="button" class="button btn-subtract">
                            <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/icons-remove.svg" />
                        </button>
                        <input value="${param.quantity == null ? 1 : param.quantity}" min="1" max="${product.quantity}" name="quantity" class="input" onkeydown="return false"/>
                        <button type="button" class="button btn-plus">
                            <img src="https://frontend.tikicdn.com/_desktop-next/static/img/pdp_revamp_v2/icons-add.svg" />
                        </button>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary mt-2 col-md-4 btn-buy">
                    Chọn mua
                </button>
            </form>
        </div>
    </div>

    <div class="container detail pt-0">
        <div class="detail-info-label">Thông Tin Chi Tiết</div>
        <div class="ml-3">
            <table>
                <tbody>
                <tr>
                    <td>Mô tả</td>
                    <td>${product.description}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="container detail pt-0">
        <div class="similar-product">Sản phẩm khác</div>
        <div class="similar-product-container">
            <c:forEach varStatus="statusLoop" var="product" items="${productElse}">
                <a class="col-md-2 product py-3" href="${pageContext.request.contextPath}/product/detail?id=${product.id}" target="_blank">
                    <div class="d-flex flex-column">
                        <img class="product-image" src="${pageContext.request.contextPath}${product.image}"/>
                        <span class="product-name" title="${ product.name }">${product.name} </span>
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
</c:if>
</body>
<script src="https://kit.fontawesome.com/68830dcf1e.js" crossorigin="anonymous"></script>
<script charset="UTF-8" src="${pageContext.request.contextPath}/view/user/product/js/detail.js"></script>
<script>
    const error = '${error}';
    if (error) {
        toastr.error(error);
    }
</script>
</html>
