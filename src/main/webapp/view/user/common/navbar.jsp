<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="navbar navbar-expand-lg navbar-dark bg-dark" style="position: sticky; top: 0; z-index: 100">
    <nav class="container">
        <a class="navbar-brand"  href="${pageContext.request.contextPath}/home">Trang chủ</a>

        <ul class="navbar-nav mr-auto">

            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
            </li>

            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/bill/history">Lịch sử mua hàng</a>
            </li>
        </ul>

        <c:if test="${sessionScope.USER_LOGGED_IN != null}">
            <a class="cursor-pointer nav-link" data-toggle="modal" data-target="#profile" style="color: white;">Xin chào, ${sessionScope.USER_LOGGED_IN.username}</a>
            <a class="cursor-pointer nav-link" style="color: white;" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>

            <div id="profile" class="modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <div class="d-flex justify-content-between w-100">
                                <h4 class="modal-title">Thông tin người dùng</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </div>
                        <form action="<c:url value="/user/update" />" method="post">
                            <div class="modal-body">
                                <div>
                                    <label for="username">Tên đăng nhập</label>
                                    <input id="username" class="form-control" name="username" value="${sessionScope.USER_LOGGED_IN.username}" readonly/>
                                </div>

                                <div>
                                    <label for="fullname">Tên đầy đủ</label>
                                    <input id="fullname" class="form-control" name="fullName" value="${sessionScope.USER_LOGGED_IN.fullName}" />
                                </div>

                                <div>
                                    <label for="phone">Số điện thoại</label>
                                    <input id="phone" class="form-control" name="phone" value="${sessionScope.USER_LOGGED_IN.phone}" />
                                </div>

                                <div>
                                    <label for="address">Địa chỉ</label>
                                    <input id="address" class="form-control" name="address" value="${sessionScope.USER_LOGGED_IN.address}" />
                                </div>

                                <div>
                                    <label for="password">Mật khẩu</label>
                                    <input type="password" id="password" class="form-control" name="password" value="${sessionScope.USER_LOGGED_IN.password}" />
                                </div>
                            </div>
                            <div class="modal-footer">
                                <div class="d-flex justify-content-center w-100">
                                    <button type="submit" class="btn btn-primary mr-2" style="padding: 0.375rem 0.75rem; border-radius: 4px">Lưu lại</button>
                                    <button type="button" class="btn btn-secondary"data-dismiss="modal">Đóng</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${sessionScope.USER_LOGGED_IN == null}">
            <div class="d-flex">
                <a class="cursor-pointer nav-link" style="color: white;" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                <a class="cursor-pointer nav-link" style="color: white;" href="${pageContext.request.contextPath}/register">Đăng ký</a>
            </div>
        </c:if>
    </nav>
</div>