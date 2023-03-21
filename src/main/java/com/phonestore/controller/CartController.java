package com.phonestore.controller;

import com.phonestore.constant.AppConstant;
import com.phonestore.constant.CartEndpointConstant;
import com.phonestore.dto.CartDTO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Product;
import com.phonestore.entity.Cart;
import com.phonestore.entity.User;
import com.phonestore.service.ProductService;
import com.phonestore.service.CartService;
import com.phonestore.service.DiscountService;
import com.phonestore.util.DataUtil;
import com.phonestore.util.ReflectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = {
        CartEndpointConstant.CART_INSERT_ENDPOINT,
        CartEndpointConstant.CART_UPDATE_ENDPOINT,
        CartEndpointConstant.CART_REMOVE_ENDPOINT,
        CartEndpointConstant.CART_GET_ALL_ENDPOINT
})
public class CartController extends HttpServlet {

    public final static String PATH_UI_LIST_CART = "/view/user/cart/index.jsp";

    private CartService cartService;
    private ProductService productService;
    private DiscountService discountService;

    @Override
    public void init() throws ServletException {
        this.cartService = new CartService();
        this.productService = new ProductService();
        this.discountService = new DiscountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();
        User user = (User) req.getSession().getAttribute(AppConstant.SESSION_USER);

        if (CartEndpointConstant.CART_GET_ALL_ENDPOINT.equals(endpoint)) {
            this.getInfoCart(req, resp, user);

        }

        if (CartEndpointConstant.CART_REMOVE_ENDPOINT.equals(endpoint)) {
            String cartId = req.getParameter("id");
            ResultDTO result = this.cartService.remove(DataUtil.safeToInt(cartId));
            if (result.isError()) {
                req.setAttribute("error", result.getMessage());
                req.setAttribute("carts", this.cartService.findByUsername(user.getUsername()));
                req.getRequestDispatcher(PATH_UI_LIST_CART).forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath()+CartEndpointConstant.CART_GET_ALL_ENDPOINT);
        }

        if (CartEndpointConstant.CART_UPDATE_ENDPOINT.equals(endpoint)) {
            Cart cart = ReflectionUtil.requestParamToObject(req, Cart.class);
            ResultDTO<Cart> result = this.cartService.updateById(cart);
            if (result.isError()) {
                req.setAttribute("error", result.getMessage());
                this.getInfoCart(req, resp, user);
                return;
            }
            resp.sendRedirect(req.getContextPath() + CartEndpointConstant.CART_GET_ALL_ENDPOINT);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();
        User user = (User) req.getSession().getAttribute(AppConstant.SESSION_USER);

        if (CartEndpointConstant.CART_INSERT_ENDPOINT.equals(endpoint)) {
            Product product = ReflectionUtil.requestParamToObject(req, Product.class);
            ResultDTO<Cart> result = this.cartService.insert(product, user.getUsername());
            if (result.isError()) {
                Product foundProduct = this.productService.findById(product.getId());
                req.setAttribute("error", result.getMessage());
                req.setAttribute("book", foundProduct);
                req.getRequestDispatcher(ProductController.PATH_UI_PRODUCT_DETAIL).forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath()+CartEndpointConstant.CART_GET_ALL_ENDPOINT);

        }
    }

    private void getInfoCart(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        List<CartDTO> carts = this.cartService.findByUsername(user.getUsername());
        String totalPrice = DataUtil.formatCurrency(carts.stream().map(CartDTO::getSubtotal).reduce(0.0F, (total, current) -> total + current));
        req.setAttribute("carts", carts);
        req.setAttribute("totalPrice", totalPrice);
        req.setAttribute("discountCards", this.discountService.findByStatusActive());
        req.setAttribute("totalBook", carts.stream().map(CartDTO::getQuantity).reduce(0, (total, current) -> total + current));
        req.getRequestDispatcher(PATH_UI_LIST_CART).forward(req, resp);
    }

    @Override
    public void destroy() {
        this.cartService = null;
        this.productService = null;
        this.discountService = null;
    }
}