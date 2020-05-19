package com.babak.utils.constants;

public class SqlQuery {

    public static final String FILTER = "SELECT * FROM(SELECT * FROM(SELECT p.id AS product_id,"
            + "p.product_name, p.product_description, c.category_name, p.price,"
            + "manufacturer_id, p.link from products AS p "
            + "JOIN categories as c ON p.category_id=c.id) AS newTable "
            + "JOIN manufacturers AS m ON manufacturer_id=m.id) "
            + "AS newTable_manufacturer WHERE";
    public static final String PRICE = "( price BETWEEN ? AND ?)";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String LIMIT = " LIMIT ?, ?";
    public static final String COUNT_BY_FILTER_HEADER = "SELECT COUNT(*) AS fCount FROM (";
    public static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products LIMIT ?, ?";
    public static final String COUNT_BY_FILTER_FOOTER = ") as hgvhkygtf";
    public static final String FIELD_CATEGORY_NAME = "category_name=";
    public static final String NUMBER_ROWS = "SELECT COUNT(*) FROM products";
    public static final String NEW_PATH = "../new_image_products/";
    public static final String GET_ALL_CATEGORIES = "SELECT * FROM categories";
    public static final String GET_ALL_MANUFACTURERS = "SELECT * FROM manufacturers";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";
    public static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM users WHERE login=? AND user_password=?";
    public static final String COUNT_ALL = "count(*)";
    public static final String MANUFACTURE_NAME_IN = "manufacturer_name IN (";
    public static final String PRODUCT_BY_ID = "select p.product_name, p.price, p.product_description, p.link from products as p where p.id=?";
    public static final String INSERT_USER = "INSERT INTO users(user_name, surname, email, login, user_password, distribution) "
            + "VALUE (?, ?, ?, ?, ?, ?)";
    public static final String INSERT_ORDER = "INSERT INTO orders (status_order, detail_status, id_users, created) VALUE (?, ?,?, ?)";
    public static final String INSERT_ORDER_ITEM = "INSERT INTO order_items (id_orders, id_products, count) VALUE (?, ?,?)";
}
