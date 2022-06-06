package gap.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Order {
    private String orderId;
    private LocalDate orderDate;
    private LocalTime localTime;
    private String customerName;
    private String phoneNumber;
    List<Product> productsList;


}
