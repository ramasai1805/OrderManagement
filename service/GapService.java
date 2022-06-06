package gap.service;

import gap.constants.Category;
import gap.constants.Color;
import gap.constants.Gender;
import gap.constants.Size;
import gap.model.Product;
import gap.utils.ConsoleEntry;
import gap.utils.Database;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class GapService extends ConsoleEntry implements Gap, ProductFilter {
    private Scanner scanner;
    private Connection connection;
    private final Logger logger;
    public List<Product> products;

    public GapService(Scanner scanner, Logger logger) {
        this.products = new ArrayList<>();
        this.scanner = scanner;
        this.logger = logger;
    }


    public boolean addProduct() {
        boolean productAdded = false;
        String id = productIdGenerator();
        scanner = new Scanner(System.in);
        logger.info("Enter Product details \n Enter Product Name");
        String name = scanner.next();
        name += scanner.nextLine();
        Category category = getCategory(true, "Enter Product Category");
        Color color = getColor(true, "Select color of the product");
        double price = getPrice(true);
        Gender gender = getGender(true, "Product is ideal for");
        Map<Size, Integer> size = getProductSizes();
        connection = Database.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into products values(?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, category.toString());
            preparedStatement.setString(4, color.toString());
            preparedStatement.setDouble(5, price);
            preparedStatement.setString(6, gender.getGender());
            preparedStatement.setInt(7, size.get(Size.S));
            preparedStatement.setInt(8, size.get(Size.M));
            preparedStatement.setInt(9, size.get(Size.L));
            preparedStatement.setInt(10, size.get(Size.XL));
            preparedStatement.setInt(11, size.get(Size.XXL));
            productAdded = preparedStatement.executeUpdate() > 0;
            if (productAdded) {
                logger.info("Your product with name : " + name + " is added successfully");
            } else {
                logger.info("Your product is not added successfully");
                productAdded = addProduct();
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            logger.warning(e.getMessage());
            logger.info("Your product is not added successfully");
            productAdded = addProduct();
        }
        return productAdded;
    }


    @Override
    public void searchProduct() {
        connection = Database.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
        List<Product> cart = new ArrayList<>();
        boolean orderBoolean = true;
        while (orderBoolean) {
            logger.info("********************GAP********************* " + "\n \t (1) Men  \t \t \t \t (2) Women");
            Gender gender = null;

            gender = getGender(false, "Please select...");

            boolean genderBoolean = true;
            while (genderBoolean) {
                Map<Integer, Category> categoryMenu = getCategoriesByGender(gender);
                logger.info("Select the category\nTo change the gender please enter 0");

                int categoryMenuNumber = scanner.nextInt();
                Category category = categoryMenu.get(categoryMenuNumber);
                if (category==null) {
                    break;
                }
                boolean categoryBoolean = true;
                while (categoryBoolean) {
                    Map<Integer, Product> productsByGenderAndCategory = getProductsByGenderAndCategory(gender, category);
                    logger.info("Select the product\n To change the category please enter 0");
                    int productMenuNumber = scanner.nextInt();
                    Product product = productsByGenderAndCategory.get(productMenuNumber);
                    if (product == null) {
                        break;
                    }
                    if (!addProductToCart(cart, product)) {
                        logger.info("Sorry to say this. Currently this product was out of stock.");
                        continue;
                    } else {
//                        select size_s from gap.products where id='c4c9#Gap';
//                        update gap.products set size_s=6 where id ='c4c9#Gap';
//                        PreparedStatement preparedStatement = connection.prepareStatement();
                        logger.info("Your product : " + product.getName() + " is added to your cart.");
                    }
                    logger.info("Please enter 1 - to continue the shopping \nPress any key to place the order");
                    int order = scanner.nextInt();
                    switch (order) {
                        case 1:
                            categoryBoolean = false;
                            genderBoolean = false;
                            break;
                        default:
                            categoryBoolean = false;
                            genderBoolean = false;
                            orderBoolean = false;
                    }
                }
            }
        }
    }


    @Override
    public boolean addProductToCart(Collection<Product> cart, Product product) {
        if (product.outOfStock()) {
            return false;
        }
        connection = Database.getConnection();
//        connection.prepareStatement()
        return cart.add(product);
    }

    @Override
    public Map<Integer, Category> getCategoriesByGender(Gender gender) {
        Map<Integer, Category> categoryMap = new LinkedHashMap<>();
        AtomicInteger number = new AtomicInteger(0);
        Connection connection = Database.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select category from gap.products where gender=? group by category");
            preparedStatement.setString(1, gender.getGender());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categoryMap.put(number.incrementAndGet(), Category.valueOf(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            logger.info("got error while fetching the gender. please try again");
            return getCategoriesByGender(gender);
        }
        if (categoryMap.isEmpty()) {
            logger.info("Categories not found in " + gender);
        } else {
            String menu = categoryMap.entrySet().stream().map(entry -> entry.getKey() + ". " + entry.getValue()).reduce("", (categories, category) -> categories + "\n" + category);
            logger.info(menu);
        }
        return categoryMap;
    }

    @Override
    public Map<Integer, Color> getColorsByGenderAndCategory(Gender gender, Category category) {
        Map<Integer, Color> colorMap = new LinkedHashMap<>();
        AtomicInteger number = new AtomicInteger(0);
        Connection connection = Database.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select color from gap.products where gender=? and category=? group by color");
            preparedStatement.setString(1, gender.getGender());
            preparedStatement.setString(2, category.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                colorMap.put(number.incrementAndGet(), Color.valueOf(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            logger.info("got error while fetching the gender. please try again");
            return getColorsByGenderAndCategory(gender, category);
        }
        if (colorMap.isEmpty()) {
            logger.info("Colors not found in " + category + " for " + gender);
        } else {
            String menu = colorMap.entrySet().stream().map(entry -> entry.getKey() + ". " + entry.getValue()).reduce("", (colors, color) -> colors + "\n" + color);
            logger.info(menu);
        }
        return colorMap;
    }


    @Override
    public Map<Integer, Product> getProductsByGenderAndCategory(Gender gender, Category category) {
        Map<Integer, Product> productMap = new LinkedHashMap<>();
        AtomicInteger number = new AtomicInteger(0);
        Connection connection = Database.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from gap.products where gender=? and category =?");
            preparedStatement.setString(1, gender.getGender());
            preparedStatement.setString(2, category.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getString(1), resultSet.getString(2), Category.valueOf(resultSet.getString(3)), Color.valueOf(resultSet.getString(4)), resultSet.getDouble(5), Gender.valueOf(resultSet.getString(6).toUpperCase()), getProductSizes(resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10), resultSet.getInt(11)));
                productMap.put(number.incrementAndGet(), product);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            logger.info("got error while fetching the gender. please try again");
            return getProductsByGenderAndCategory(gender, category);
        }
        if (productMap.isEmpty()) {
            logger.info("Products not found in " + category);
        } else {
            String menu = productMap.entrySet().stream().map(entry -> entry.getKey() + ". " + entry.getValue().productShowcaseInMenu()).reduce("", (products, product) -> products + "\n" + product);
            logger.info(menu);
        }
        return productMap;
    }

    @Override
    public Map<Integer, Product> getProductsByGenderAndColor(Collection<Product> productList, Color color) {

        return null;
    }
}
