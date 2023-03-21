<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-expand-lg navbar-dark bg-dark" style="position: sticky; top: 0; z-index: 100">
    <nav class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/home">Trang chủ</a>

        <ul class="navbar-nav mr-auto">

            <li class="nav-item ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/category' ? 'active' : ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/category">Quản lý loại sản phẩm</a>
            </li>

            <li class="nav-item ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/product' ? 'active' : ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/product">Quản lý sản phẩm</a>
            </li>

            <li class="nav-item ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/discount' ? 'active' : ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/discount">Quản lý phiếu giảm giá</a>
            </li>

            <li class="nav-item ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/user' ? 'active' : ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/user">Quản lý người dùng</a>
            </li>

            <li class="nav-item ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/statistic' ? 'active' : ''}">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/statistic">Thống kê</a>
            </li>
        </ul>

        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
    </nav>
</div>