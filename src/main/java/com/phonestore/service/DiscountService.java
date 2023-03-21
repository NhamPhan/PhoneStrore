package com.phonestore.service;

import com.phonestore.constant.AppConstant;
import com.phonestore.dao.DiscountDAO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Discount;
import com.phonestore.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountService.class);

    private DiscountDAO discountDAO;

    public DiscountService() {
        this.discountDAO = new DiscountDAO();
    }

    public List<Discount> findByStatusActive() {
        return this.discountDAO.findStatusActive();
    }

    public List<Discount> findAll() {
        return this.discountDAO.findAll();
    }

    public Discount findByCode(String code) {
        return this.discountDAO.findByCode(code);
    }

    public ResultDTO<Discount> insert(Discount discount) {
        try {
            logger.info("create discount card");

            Discount foundDiscount = this.discountDAO.findByCode(discount.getCode());
            if (Objects.nonNull(foundDiscount)) {
                return new ResultDTO<>(null, true, "Mã giảm giá đã tồn tại");
            }
            String messageValidate = Validator.validate(discount);
            if (Objects.nonNull(messageValidate)) {
                return new ResultDTO<>(null, true, messageValidate);
            }
            this.discountDAO.insert(discount);
            return new ResultDTO<>(discount, false, "Tạo mới phiếu giảm giá thành công");
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Thêm mới phiếu giảm giá lỗi");
        }
    }

    public ResultDTO updateStatus(String discountCode) {
        try {
            Discount discount = this.findByCode(discountCode);
            discount.setStatus(AppConstant.STATUS_DISCOUNT_INACTIVE);
            this.discountDAO.update(discount);
            return new ResultDTO(null, false, "Cập nhật trạng thái thành công");
        } catch(Exception ex) {
            return new ResultDTO<>(null, true, "Cập nhật trạng thái thất bại");
        }
    }
}