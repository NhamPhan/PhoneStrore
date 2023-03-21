package com.phonestore.controller;

import com.phonestore.constant.AppConstant;
import com.phonestore.constant.BillEndpointConstant;
import com.phonestore.constant.PaymentMethodEnum;
import com.phonestore.dto.BillDTO;
import com.phonestore.dto.CartDTO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Bill;
import com.phonestore.entity.User;
import com.phonestore.service.BillService;
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
        BillEndpointConstant.BILL_CHECKOUT_ENDPOINT,
        BillEndpointConstant.BILL_HISTORY_ENDPOINT
})
public class BillController extends HttpServlet {

    private static final String PATH_UI_CART = "/view/user/history/index.jsp";

    private BillService billService;
    private CartService cartService;
    private DiscountService discountService;

    @Override
    public void init() throws ServletException {
        this.billService = new BillService();
        this.cartService = new CartService();
        this.discountService = new DiscountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();
        User user = (User) req.getSession().getAttribute(AppConstant.SESSION_USER);


        if (BillEndpointConstant.BILL_HISTORY_ENDPOINT.equals(endpoint)) {
            Bill bill = ReflectionUtil.requestParamToObject(req, Bill.class);
            bill.setUsername(user.getUsername());
            List<BillDTO> histories = this.billService.search(bill);
            req.setAttribute("histories", histories);
            req.setAttribute("totalPrice", DataUtil.formatCurrency(histories.stream().map(BillDTO::getTotal).reduce(0.0F, (total, current) -> total + current)));
            req.getRequestDispatcher(PATH_UI_CART).forward(req, resp);
        }

        if (BillEndpointConstant.BILL_CHECKOUT_ENDPOINT.equals(endpoint)) {
            Bill bill = ReflectionUtil.requestParamToObject(req, Bill.class);
            List<CartDTO> carts = this.cartService.findByUsername(user.getUsername());
            ResultDTO resultCheckout = this.billService.checkout(carts, user.getUsername(), req.getParameter("discountCode"), PaymentMethodEnum.NORMAL, bill);
            if (resultCheckout.isError()) {
                this.handleCheckoutError(req, resp, carts, resultCheckout.getMessage());
                return;
            }
            resp.sendRedirect(req.getContextPath()+"/bill/history");
        }
   }

   private void handleCheckoutError(HttpServletRequest req, HttpServletResponse resp, List<CartDTO> carts, String message) throws ServletException, IOException {
       String totalPrice = DataUtil.formatCurrency(carts.stream().map(CartDTO::getSubtotal).reduce(0.0F, (total, current) -> total + current));
       req.setAttribute("error", message);
       req.setAttribute("carts", carts);
       req.setAttribute("totalPrice", totalPrice);
       req.setAttribute("discountCards", this.discountService.findByStatusActive());
       req.setAttribute("totalBook", carts.stream().map(CartDTO::getQuantity).reduce(0, (total, current) -> total + current));
       req.getRequestDispatcher(CartController.PATH_UI_LIST_CART).forward(req, resp);
   }

    @Override
    public void destroy() {
        this.billService = null;
        this.cartService = null;
        this.discountService = null;
    }
}