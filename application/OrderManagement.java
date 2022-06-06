package gap.application;

import gap.service.Gap;
import gap.service.GapService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Logger;

public class OrderManagement {
    private Scanner scanner;
    private final Logger logger;

    public OrderManagement() {
//        System.setProperty("java.util.logging.SimpleFormatter.format",
//                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s %n");
        this.scanner = new Scanner(System.in);
        this.logger = Logger.getLogger(OrderManagement.class.getName());
    }

    public void menu() {
        logger.info("Welcome to GAP Business Management");
        Gap gap = new GapService(scanner, logger);
        String menu = "\nMenu \n 1. add product to your stock \n 2. get products available for customers \n 3. get order details \n 4. exit";
        boolean flag = true;
        scanner = new Scanner(System.in);
        logger.info(menu);
        int menuOption = 0;
        while (flag) {
            try {
                menuOption = scanner.nextInt();
            } catch (Exception e) {
                menu();
            }
            switch (menuOption) {
                case 1:
                    try {
                        gap.addProduct();
                    } catch (Exception e) {
                        e.printStackTrace();
                        menu();
                    }
                    logger.info("Please enter 5 for menu\n To exit please enter 4\n To Add more enter 1");
                    break;
                case 2:
                    gap.searchProduct();
                    logger.info("Please enter 5 for menu\n To exit please enter 4");
                    break;
                case 4:
                    flag = false;
                    break;
                case 5:
                    logger.info(menu);
                    break;
                default:
                    logger.warning("Please enter valid menu option");
                    logger.info(menu);

            }

        }
    }

    public static void main(String[] args) throws SQLException {
        OrderManagement orderManagement = new OrderManagement();
        orderManagement.menu();
//        Statement statement = Database.getConnection().createStatement();
//        ResultSet resultSet = statement.executeQuery("select * from gap.products where gender='Men' group by gender");
//        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//        while (resultSet.next()) {
//            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
//                System.out.println(resultSet.getString(i) + "\t");
//            }
//            System.out.println();
//        }
        GapService gap = new GapService(new Scanner(System.in), Logger.getLogger(Gap.class.getName()));
//        LinkedHashMap<Size, Integer> sizeIntegerLinkedHashMap = new LinkedHashMap<>();
//        sizeIntegerLinkedHashMap.put(Size.S,0);
//        sizeIntegerLinkedHashMap.put(Size.M, 0);
//        sizeIntegerLinkedHashMap.put(Size.L, 0);
//        sizeIntegerLinkedHashMap.put(Size.XL, 0);
//        sizeIntegerLinkedHashMap.put(Size.XXL, 0);
//        Product shirt1 = new Product("abcd", "men regular fit shirt", Category.HOODIES, Color.BLUE, 500.00, Gender.MEN, sizeIntegerLinkedHashMap);
//        System.out.println(gap.addProductToCart(shirt1));
        //        gap.searchProduct();
//        gap.getCategoriesByGender(Gender.FEMALE);
//        gap.getProductsByGenderAndCategory(Gender.WOMEN, Category.SHIRTS);
//        gap.getColorsByGenderAndCategory(Gender.MEN, Category.DRESS);

//        System.out.println(UUID.randomUUID());
    }
}
