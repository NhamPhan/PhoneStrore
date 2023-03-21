package com.phonestore.controller;

import com.phonestore.constant.StatisticEndpointConstant;
import com.phonestore.dto.BillDTO;
import com.phonestore.entity.Bill;
import com.phonestore.service.BillService;
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
        StatisticEndpointConstant.ADMIN_STATISTIC_ENDPOINT
})
public class AdminStatisticController extends HttpServlet {

    public static final String UI_STATISTIC_PATH = "/view/admin/statistic/index.jsp";

    private BillService billService;

    @Override
    public void init() throws ServletException {
        this.billService = new BillService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Bill bill = ReflectionUtil.requestParamToObject(req, Bill.class);
        List<BillDTO> histories = this.billService.search(bill);
        req.setAttribute("histories", histories);
        req.setAttribute("totalPrice", DataUtil.formatCurrency(histories.stream().map(BillDTO::getTotal).reduce(0.0F, (total, current) -> total + current)));
        req.getRequestDispatcher(UI_STATISTIC_PATH).forward(req, resp);
    }

    @Override
    public void destroy() {
        this.billService = null;
    }
}