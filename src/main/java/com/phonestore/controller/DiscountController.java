package com.phonestore.controller;

import com.phonestore.constant.DiscountEndpointConstant;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Discount;
import com.phonestore.service.DiscountService;
import com.phonestore.util.ReflectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {
        DiscountEndpointConstant.DISCOUNT_GET_ALL_ENDPOINT,
        DiscountEndpointConstant.DISCOUNT_INSERT_ENDPOINT,
        DiscountEndpointConstant.DISCOUNT_CANCEL_ENDPOINT
})
public class DiscountController extends HttpServlet {

    private static final String PATH_UI_DISCOUNT_FORM = "/view/admin/discount/form.jsp";
    private static final String PATH_UI_ALL_DISCOUNT = "/view/admin/discount/index.jsp";

    private DiscountService discountService;

    @Override
    public void init() throws ServletException {
        this.discountService = new DiscountService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();

        if (DiscountEndpointConstant.DISCOUNT_INSERT_ENDPOINT.equals(endpoint)) {
            req.setAttribute("discount", new Discount());
            req.getRequestDispatcher(PATH_UI_DISCOUNT_FORM).forward(req, resp);
            return;
        }

        if (DiscountEndpointConstant.DISCOUNT_CANCEL_ENDPOINT.equals(endpoint)) {
            String discountCode = req.getParameter("discountCode");
            ResultDTO resultDTO = this.discountService.updateStatus(discountCode);
            if (!resultDTO.isError()) {
                resp.sendRedirect(req.getContextPath()+DiscountEndpointConstant.DISCOUNT_GET_ALL_ENDPOINT);
                return;
            }
            req.setAttribute("error", resultDTO.getMessage());
        }

        req.setAttribute("discounts", this.discountService.findAll());
        req.getRequestDispatcher(PATH_UI_ALL_DISCOUNT).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();
        Discount discount = ReflectionUtil.requestParamToObject(req, Discount.class);

        if (DiscountEndpointConstant.DISCOUNT_INSERT_ENDPOINT.equals(endpoint)) {
            ResultDTO<Discount> result = this.discountService.insert(discount);
            if (result.isError()) {
                req.setAttribute("error", result.getMessage());
                req.setAttribute("discount", discount);
                req.getRequestDispatcher(PATH_UI_DISCOUNT_FORM).forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath()+DiscountEndpointConstant.DISCOUNT_GET_ALL_ENDPOINT);
        }
    }

    @Override
    public void destroy() {
        this.discountService = null;
    }
}