package com.phonestore.service;

import com.phonestore.constant.AppConstant;
import com.phonestore.constant.UserStatusEnum;
import com.phonestore.dao.UserDAO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.User;
import com.phonestore.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public ResultDTO<User> login(User user) {
        try {
            logger.info("log in with username: {}", user.getUsername());
            User foundUser = this.userDAO.findByUsername(user.getUsername());
            if (Objects.isNull(foundUser)) {
                logger.error("username is incorrect");
                return new ResultDTO<>(null, true, "Tài khoản không chính xác");
            }

            if (!foundUser.getPassword().equals(user.getPassword())) {
                logger.error("password is incorrect");
                return new ResultDTO<>(null, true, "Mật khẩu không chính xác");
            }

            if (UserStatusEnum.INACTIVE.getValue().equals(foundUser.getStatus())) {
                return new ResultDTO<>(null, true, "Tài khoản đã bị khóa!");
            }

            return new ResultDTO<>(foundUser, false, "");
        } catch(Exception ex) {
            return new ResultDTO<>(null, true, "Đăng nhập thất bại");
        }
    }

    public ResultDTO<User> register(User user) {
        try {
            String messageValidate = Validator.validate(user);
            if (Objects.nonNull(messageValidate)) {
                return new ResultDTO<>(null, true, messageValidate);
            }
            user.setRole(AppConstant.ROLE_USER);
            user.setStatus(UserStatusEnum.ACTIVE.getValue());
            int id = this.userDAO.insert(user);
            user.setId(id);
            return new ResultDTO<>(user, false, "Đăng ký thành công");
        } catch(Exception ex) {
            return new ResultDTO<>(null, true, "Đăng ký thất bại");
        }
    }

    public User getCurrentUser(HttpSession httpSession) {
        Object user = httpSession.getAttribute(AppConstant.SESSION_USER);
        if (Objects.isNull(user)) {
            return null;
        }

        if (!(user instanceof User)) return null;
        return (User) user;
    }

    public ResultDTO<User> update(User user) {
        try {
            String validate = Validator.validate(user);
            if (Objects.nonNull(validate)) {
                return new ResultDTO<>(null, true, validate);
            }
            this.userDAO.update(user);
            user = this.userDAO.findByUsername(user.getUsername());
            return new ResultDTO<>(user, false, "");
        } catch(Exception ex) {
            return new ResultDTO<>(null, true, "Có lỗi xảy ra");
        }
    }

    public List<User> searchUser(User user) {
        return this.userDAO.searchUser(user);
    }

    public ResultDTO lockUser(String username) {
        try {
            this.userDAO.updateStatusUser(username, UserStatusEnum.INACTIVE);
            return new ResultDTO(null, false, "");
        } catch(Exception ex) {
            return new ResultDTO(null, true, "Khóa người dùng thất bại");
        }
    }

    public ResultDTO unlockUser(String username) {
        try {

            this.userDAO.updateStatusUser(username, UserStatusEnum.ACTIVE);
            return new ResultDTO(null, false, "");
        } catch(Exception ex) {
            return new ResultDTO(null, true, "Mở khóa người dùng thất bại");
        }
    }
}