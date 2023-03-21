package com.phonestore.service;

import com.phonestore.constant.AppConstant;
import com.phonestore.constant.PaymentMethodEnum;
import com.phonestore.dao.BillDAO;
import com.phonestore.dao.BillDetailDAO;
import com.phonestore.dto.BillDTO;
import com.phonestore.dto.BillDetailDTO;
import com.phonestore.dto.CartDTO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Bill;
import com.phonestore.entity.BillDetail;
import com.phonestore.entity.Product;
import com.phonestore.entity.Discount;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BillService {

    private BillDetailDAO billDetailDAO;
    private BillDAO billDAO;
    private DiscountService discountService;
    private ProductService productService;
    private CartService cartService;

    public BillService() {
        this.billDAO = new BillDAO();
        this.billDetailDAO = new BillDetailDAO();
        this.discountService = new DiscountService();
        this.productService = new ProductService();
        this.cartService = new CartService();
    }

    public void update(Bill bill) {
        this.billDAO.update(bill);
    }

    public List<BillDTO> search(Bill bill) {
        return this.billDAO.search(bill).stream()
                .map(b -> {
                    List<BillDetailDTO> billDetailDTOS = this.billDetailDAO.findByBillId(b.getId()).stream()
                            .map(billDetail -> {
                                Product product = this.productService.findById(billDetail.getProductId());
                                return new BillDetailDTO(billDetail, product);
                            }).collect(Collectors.toList());
                    return new BillDTO(b, billDetailDTOS);
                }).collect(Collectors.toList());
    }

    public ResultDTO checkout(List<CartDTO> carts, String username, String discountCode, PaymentMethodEnum paymentMethodEnum, Bill billDto) {
        try {
            Discount discount = null;
            boolean presentDiscountCode = Objects.nonNull(discountCode) && !discountCode.isEmpty();

            if (Objects.isNull(carts) || carts.isEmpty()) {
                return new ResultDTO(null, true, "Chưa có sản phẩm trong giỏ hàng!");
            }

            if (presentDiscountCode) {
                discount = this.discountService.findByCode(discountCode);
                if (Objects.isNull(discount)) {
                    return new ResultDTO(null, true, "Mã giảm giá không tồn tại");
                }

                if (AppConstant.STATUS_DISCOUNT_INACTIVE.equals(discount.getStatus())) {
                    return new ResultDTO(null, true, "Mã giảm giá đã được sử dụnng");
                }

                if (discount.getExpireDate().getTime() < new java.util.Date().getTime()) {
                    return new ResultDTO(null, true, "Mã giảm giá đã hết hạn");
                }
            }

            Float totalPrice = carts.stream()
                    .map(cartDTO -> cartDTO.getQuantity() * cartDTO.getProduct().getPrice())
                    .reduce(0.0F, (total, current) -> total + current);

            if (presentDiscountCode) {
                totalPrice = totalPrice - (totalPrice * discount.getPercent() / 100);
            }

            Bill bill = new Bill();
            bill.setCreatedDate(new Date(System.currentTimeMillis()));
            bill.setUsername(username);
            bill.setTotal(totalPrice);
            bill.setPaymentMethod(paymentMethodEnum.method());
            bill.setDiscountCode(presentDiscountCode ? discountCode : null);
            bill.setAddress(billDto.getAddress());
            bill.setPhone(billDto.getPhone());
            bill.setPaymentMethod(billDto.getPaymentMethod());
            int id = this.billDAO.insert(bill);
            bill.setId(id);

            if (presentDiscountCode) {
                this.discountService.updateStatus(discountCode);
            }

            for (CartDTO cartDTO : carts) {
                BillDetail billDetail = new BillDetail();
                billDetail.setBillId(bill.getId());
                billDetail.setPrice(cartDTO.getProduct().getPrice());
                billDetail.setProductId(cartDTO.getProduct().getId());
                billDetail.setSubtotal(cartDTO.getQuantity() * cartDTO.getProduct().getPrice());
                billDetail.setQuantity(cartDTO.getQuantity());
                this.billDetailDAO.insert(billDetail);

                ResultDTO resultRemoveCart = this.cartService.remove(cartDTO.getId());
                if (resultRemoveCart.isError()) {
                    return resultRemoveCart;
                }

                Product product = this.productService.findById(cartDTO.getProduct().getId());
                product.setQuantity(product.getQuantity() - cartDTO.getQuantity());
                ResultDTO<Product> update = this.productService.update(product);
                if (update.isError()) {
                    return update;
                }
            }

            return new ResultDTO(bill, false, "Thanh toán thành công");
        } catch(Exception ex) {
            return new ResultDTO(null, true, "Thanh toán thất bại");
        }
    }
}