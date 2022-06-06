package gap.utils;

import gap.constants.Category;
import gap.constants.Color;
import gap.constants.Gender;
import gap.constants.Size;
import gap.model.Order;
import gap.service.Gap;

import java.util.*;
import java.util.logging.Logger;

public class ConsoleEntry {
    private Logger logger = Logger.getLogger(ConsoleEntry.class.getName());
    private Scanner scanner = new Scanner(System.in);

    public Category getCategory(boolean check, String message) {
        String categoryOptions = Arrays.stream(Category.values()).map(category -> category + "(" + category.getOption() + ")").reduce((categories, category) -> categories + " " + category).get();
        if (check) {
            logger.info(message + "\n" + categoryOptions);
        }
        scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            return Arrays.stream(Category.values()).filter(category -> category.getOption() == option).findFirst().orElseGet(() -> {
                logger.info("Please enter valid category\n" + categoryOptions);
                return getCategory(false, message);
            });
        } catch (InputMismatchException e) {
            logger.info("Please enter valid category\n" + categoryOptions);
            return getCategory(false, message);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return getCategory(false, message);
        }
    }

    public Size getSize(boolean check, String message) {
        String categoryOptions = Arrays.stream(Size.values()).map(size -> size + "(" + size.getOption() + ")").reduce((sizes, size) -> sizes + " " + size).get();
        if (check) {
            logger.info(message + "\n" + categoryOptions);
        }
        scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            return Arrays.stream(Size.values()).filter(size -> size.getOption() == option).findFirst().orElseGet(() -> {
                logger.info("Please enter valid category\n" + categoryOptions);
                return getSize(false, message);
            });
        } catch (InputMismatchException e) {
            logger.info("Please enter valid category\n" + categoryOptions);
            return getSize(false, message);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return getSize(false, message);
        }
    }

    public Gender getGender(boolean check, String message) {
        String genderOptions = Arrays.stream(Gender.values()).map(category -> category + "(" + category.getOption() + ")").reduce((categories, category) -> categories + " " + category).get();
        if (check) {
            logger.info(message + "\n" + genderOptions);
        }
        scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            return Arrays.stream(Gender.values()).filter(gender -> gender.getOption() == option).findFirst().orElseGet(() -> {
                logger.info("Please enter valid gender\n" + genderOptions);
                return getGender(false, message);
            });
        } catch (InputMismatchException e) {
            logger.info("Please enter valid gender\n" + genderOptions);
            return getGender(false, message);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return getGender(false, message);
        }
    }


    public Color getColor(boolean check, String message) {
        String colorOptions = Arrays.stream(Color.values()).map(category -> category + "(" + category.getOption() + ")").reduce((categories, category) -> categories + " " + category).get();
        if (check) {
            logger.info(message + "\n" + colorOptions);
        }
        try {
            int option = scanner.nextInt();
            return Arrays.stream(Color.values()).filter(color -> color.getOption() == option).findFirst().orElseGet(() -> {
                logger.info("Please enter valid color\n" + colorOptions);
                return getColor(false, message);
            });
        } catch (InputMismatchException e) {
            logger.info("Please enter valid color\n" + colorOptions);
            return getColor(false, message);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return getColor(false, message);
        }
    }

    public double getPrice(boolean flag) {
        if (flag) {
            logger.info("Enter price for the product");
        }
        scanner = new Scanner(System.in);
        try {
            return scanner.nextDouble();
        } catch (InputMismatchException e) {
            logger.warning("Please enter valid price");
            return getPrice(false);
        }
    }

    public int getCount() {
        int count;
        scanner = new Scanner(System.in);
        try {
            count = scanner.nextInt();
            if (count < 0) {
                throw new RuntimeException();
            }
            return count;
        } catch (Exception e) {
            logger.warning("Please enter valid number");
            return getCount();
        }
    }

    public LinkedHashMap<Size, Integer> getProductSizes() {
        LinkedHashMap<Size, Integer> productSizes = new LinkedHashMap<>();
        logger.info("Please enter the available stock for particular sizes \n Number of products with size S");
        productSizes.put(Size.S, getCount());
        logger.info("Number of products with size M");
        productSizes.put(Size.M, getCount());
        logger.info("Number of products with size L");
        productSizes.put(Size.L, getCount());
        logger.info("Number of products with size XL");
        productSizes.put(Size.XL, getCount());
        logger.info("Number of products with size XXL");
        productSizes.put(Size.XXL, getCount());
        return productSizes;
    }

    public Map<Size, Integer> getProductSizes(int s, int m, int l, int xl, int xxl) {
        LinkedHashMap<Size, Integer> productSizes = new LinkedHashMap<>();
        productSizes.put(Size.S, s);
        productSizes.put(Size.M, m);
        productSizes.put(Size.L, l);
        productSizes.put(Size.XL, xl);
        productSizes.put(Size.XXL, xxl);
        return productSizes;
    }

    public String productIdGenerator() {
        return Arrays.stream(UUID.randomUUID().toString().split("-")).skip(1).findFirst().get() + "#" + Gap.class.getSimpleName();
    }

    public String orderIdGenerator() {
        return Arrays.stream(UUID.randomUUID().toString().split("-")).findFirst().get() + "#" + Order.class.getSimpleName();
    }
}
