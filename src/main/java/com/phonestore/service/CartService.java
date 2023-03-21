package com.phonestore.service;

import com.phonestore.dao.ProductDAO;
import com.phonestore.dao.CartDAO;
import com.phonestore.dto.CartDTO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Product;
import com.phonestore.entity.Cart;
import com.phonestore.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private CartDAO cartDAO;
    private ProductDAO productDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
        this.productDAO = new ProductDAO();
    }

    public List<CartDTO> findByUsername(String username) {
        return this.cartDAO.findByUsername(username).stream()
                .map(cart -> {
                    CartDTO cartDTO = new CartDTO(cart);
                    cartDTO.setProduct(this.productDAO.findById(cart.getProductId()));
                    return cartDTO;
                }).collect(Collectors.toList());
    }

    public List<CartDTO> findByUsernameAndIds(String username, List<String> ids) {
        try {
            return this.cartDAO.findByUsernameAndIds(username, ids).stream()
                    .map(cart -> {
                        CartDTO cartDTO = new CartDTO(cart);
                        cartDTO.setProduct(this.productDAO.findById(cart.getProductId()));
                        return cartDTO;
                    }).collect(Collectors.toList());
        } catch(Exception ex) {
            return new ArrayList<>();
        }
    }

    public ResultDTO<Cart> insert(Product product, String username) {
        try {
            logger.info("add book to cart: {}", product);

            Cart cart = this.cartDAO.findByUsernameAndBookId(username, product.getId());
            if (Objects.nonNull(cart)) {
                cart.setQuantity(cart.getQuantity() + product.getQuantity());
                return this.updateById(cart);
            }

            cart = new Cart();
            cart.setProductId(product.getId());
            cart.setUsername(username);
            cart.setQuantity(product.getQuantity());

            String messsageValidate = Validator.validate(cart);
            if (Objects.nonNull(messsageValidate)) {
                return new ResultDTO<>(null, true, messsageValidate);
            }

            Product foundProduct = this.productDAO.findById(product.getId());
            if (Objects.isNull(foundProduct)) {
                logger.error("not found bool: {}", product);
                return new ResultDTO<>(null, true, "Không tìm thất sách: "+ product.getId());
            }

            if (foundProduct.getQuantity() < product.getQuantity()) {
                logger.error("not enough quantity for book");
                return new ResultDTO<>(null, true, "Không đủ số lượng sản phẩm");
            }

            int id = this.cartDAO.insert(cart);
            cart.setId(id);
            logger.info("add book to cart successfully");
            return new ResultDTO<Cart>(cart, false, "Thêm sách vào giỏ hàng thành công");
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResultDTO<Cart>(null, true, "Thêm sách vào giỏ hàng thất bại");
        }
    }

    public ResultDTO<Cart> updateById(Cart cart) {
        try {
            logger.info("update quantity for cart: {}", cart);

            if (cart.getQuantity() == 0) {
                return new ResultDTO<>(null, true, "Số lượng không thể nhỏ hơn 1");
            }

            Cart foundCart = this.cartDAO.findById(cart.getId());
            if (Objects.isNull(foundCart)) {
                logger.error("not found cart: {}", cart);
                return new ResultDTO<>(null, true, "Không tìm thấy cart: "+cart.getId());
            }

            Product product = this.productDAO.findById(foundCart.getProductId());
            if (Objects.isNull(product)) {
                logger.error("not found book: {}", cart.getProductId());
                return new ResultDTO<>(null, true, "Không tìm thấy sản phẩm: " + foundCart.getProductId());
            }

            if (product.getQuantity() < cart.getQuantity()) {
                logger.error("not enough quantity for cart");
                return new ResultDTO<>(null, true, "Không đủ số lượng sách");
            }

            this.cartDAO.update(cart);
            logger.info("update quantity for cart successfully");
            return new ResultDTO<>(cart, false, "Cập nhật giỏ hàng thành công");
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Cập nhật giỏ hàng thất bại");
        }
    }

    public ResultDTO remove(Integer cartId) {
        try {
            logger.info("remove cart: {}", cartId);
            Cart cart = this.cartDAO.findById(cartId);
            if (Objects.isNull(cart)) {
                return new ResultDTO(null, true, "Không tìm thấy giỏ hàng: " + cartId);
            }

            this.cartDAO.deleteById(cartId);
            logger.info("remove cart successfully");
            return new ResultDTO(cart, false, "Xóa giỏ hàng thành công");
        } catch(Exception ex) {
            return new ResultDTO(null, true, "Xóa giỏ hàng thất bại");
        }

    }
}