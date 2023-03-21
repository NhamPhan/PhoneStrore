package com.phonestore.controller;

import com.phonestore.constant.ProductEndpointConstant;
import com.phonestore.dto.ProductDTO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Product;
import com.phonestore.service.ProductService;
import com.phonestore.service.CategoryService;
import com.phonestore.service.ImageService;
import com.phonestore.util.DataUtil;
import com.phonestore.util.ReflectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(value = {
        ProductEndpointConstant.PRODUCT_SEARCH_ENDPOINT,
        ProductEndpointConstant.PRODUCT_INSERT_ENDPOINT,
        ProductEndpointConstant.PRODUCT_UPDATE_ENDPOINT,
        ProductEndpointConstant.PRODUCT_REMOVE_ENDPOINT,
        ProductEndpointConstant.PRODUCT_DETAIL_ENDPOINT,
        ProductEndpointConstant.PRODUCT_ADMIN_SEARCH_ENDPOINT
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
public class ProductController extends HttpServlet {

    public static final String PATH_UI_USER_PRODUCT = "/view/user/product/index.jsp";
    public static final String PATH_UI_ADMIN_PRODUCT = "/view/admin/product/index.jsp";
    public static final String PATH_UI_FORM_PRODUCT = "/view/admin/product/form.jsp";
    public static final String PATH_UI_PRODUCT_DETAIL = "/view/user/product/detail.jsp";

    private ProductService productService;
    private CategoryService categoryService;
    private ImageService imageService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductService();
        this.categoryService = new CategoryService();
        this.imageService = new ImageService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();

        if (ProductEndpointConstant.PRODUCT_SEARCH_ENDPOINT.equals(endpoint) || ProductEndpointConstant.PRODUCT_ADMIN_SEARCH_ENDPOINT.equals(endpoint)) {
            this.getAll(req, resp);
            return;
        }

        if (ProductEndpointConstant.PRODUCT_INSERT_ENDPOINT.equals(endpoint)) {
            this.openBookForm(req, resp);
            return;
        }

        if (ProductEndpointConstant.PRODUCT_UPDATE_ENDPOINT.equals(endpoint)) {
            this.openBookForm(req, resp);
            return;
        }

        if (ProductEndpointConstant.PRODUCT_REMOVE_ENDPOINT.equals(endpoint)) {
            this.removeBook(req, resp);
        }

        if (ProductEndpointConstant.PRODUCT_DETAIL_ENDPOINT.equals(endpoint)) {
            this.getBookDetail(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();

        if (ProductEndpointConstant.PRODUCT_INSERT_ENDPOINT.equals(endpoint)) {
            this.insertBook(req, resp);
        }

        if (ProductEndpointConstant.PRODUCT_UPDATE_ENDPOINT.equals(endpoint)) {
            this.updateBook(req, resp);
        }
    }

    private void getBookDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("id");
        Product product = this.productService.findById(DataUtil.safeToInt(productId));
        List<Product> productsElse = this.productService.findByIdElse(DataUtil.safeToInt(productId));
        request.setAttribute("product", product);
        request.setAttribute("productElse", productsElse);
        request.getRequestDispatcher(PATH_UI_PRODUCT_DETAIL).forward(request, response);
    }

    private void getAll(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        ProductDTO productDTO = ReflectionUtil.requestParamToObject(req, ProductDTO.class);
        List<Product> resultSearch = this.productService.search(productDTO);
        HttpSession session = req.getSession();
        Object error = session.getAttribute("error");
        if (Objects.nonNull(error)) {
            req.setAttribute("error", error.toString());
            session.removeAttribute("error");
        }
        req.setAttribute("products", resultSearch);
        req.setAttribute("categories", this.categoryService.findAll());
        if (req.getServletPath().equals(ProductEndpointConstant.PRODUCT_ADMIN_SEARCH_ENDPOINT)) {
            req.getRequestDispatcher(PATH_UI_ADMIN_PRODUCT).forward(req, response);
            return;
        }
        req.getRequestDispatcher(PATH_UI_USER_PRODUCT).forward(req, response);
    }

    private void openBookForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object id = req.getParameter("id");
        Product product = Objects.isNull(id) ? new Product() : this.productService.findById(DataUtil.safeToInt(id));
        req.setAttribute("product", product);
        req.setAttribute("categories", this.categoryService.findAll());
        req.getRequestDispatcher(PATH_UI_FORM_PRODUCT).forward(req, resp);
    }

    private void insertBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = ReflectionUtil.requestParamToObject(req, Product.class);
        String imagePath = this.handleImage(req, product);
        if (Objects.nonNull(imagePath)) {
            product.setImage(imagePath);
        }
        ResultDTO<Product> result = this.productService.save(product);
        if (result.isError()) {
            this.handleError(req, resp, result, product);
            return;
        }
        resp.sendRedirect(req.getContextPath() + ProductEndpointConstant.PRODUCT_ADMIN_SEARCH_ENDPOINT);
    }

    private void updateBook(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Product product = ReflectionUtil.requestParamToObject(req, Product.class);
        String imageBook = this.handleImage(req, product);
        if (Objects.nonNull(imageBook)) {
            product.setImage(imageBook);
        }
        ResultDTO<Product> result = this.productService.update(product);
        if (result.isError()) {
            this.handleError(req, resp, result, product);
            return;
        }
        resp.sendRedirect(req.getContextPath() + ProductEndpointConstant.PRODUCT_ADMIN_SEARCH_ENDPOINT);
    }

    private void removeBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object id = req.getParameter("id");
        ResultDTO<Product> resultDelete = this.productService.delete(DataUtil.safeToInt(id));
        if (resultDelete.isError()) {
            req.setAttribute("error", resultDelete.getMessage());
            req.setAttribute("books", this.productService.search(new ProductDTO()));
            req.getRequestDispatcher(PATH_UI_ADMIN_PRODUCT).forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath()+ ProductEndpointConstant.PRODUCT_ADMIN_SEARCH_ENDPOINT);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, ResultDTO<Product> result, Product product) throws ServletException, IOException {
        req.setAttribute("error", result.getMessage());
        req.setAttribute("book", product);
        req.setAttribute("categories", this.categoryService.findAll());
        req.getRequestDispatcher(PATH_UI_FORM_PRODUCT).forward(req, resp);
    }

    private String handleImage(HttpServletRequest req, Product product) throws ServletException, IOException {
        String realPath = req.getServletContext().getRealPath("images/products");

        File realPathAsFile = new File(realPath);
        if (!realPathAsFile.exists()) {
            realPathAsFile.mkdirs();
        }

        Part imageBook = req.getPart("imageBook");
        return this.imageService.store(imageBook, realPath);
    }

    @Override
    public void destroy() {
        this.productService = null;
        this.categoryService = null;
        this.imageService = null;
    }
}