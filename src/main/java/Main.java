import org.shop.exception.ProductNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        UUIDService idService = new UUIDService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        Product product = new Product("11", "T Shirt");
        Product product2 = new Product("21", "Jeans");
        Product product3 = new Product("31", "T Shirt");
        Product product4 = new Product("41", "Jeans");
        productRepo.addProduct(product);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);
        productRepo.addProduct(product4);

//        try {
//            shopService.addOrder(List.of("1", "2"));
//            shopService.addOrder(List.of("3"));
//            shopService.addOrder(List.of("4"));
//        } catch (ProductNotFoundException e) {
//            System.out.println("Product now found");
//        }
//
//        System.out.println("List of orders: " + orderRepo.getOrders());

        Path path = Paths.get("transactions.txt");

        Files.lines(path).forEach(line -> {
            String[] splitArray = line.split(" ");
            if (splitArray[0].equals("addOrder")) {
                List<String> productIds = Arrays.stream(splitArray).skip(2).toList();
                try {
                    Order createdOrder = shopService.addOrder(productIds);
                    createdOrder.withId(splitArray[1]);
                    System.out.println("createdOrder "+createdOrder);
                } catch (ProductNotFoundException e) {
                    System.out.println("product not found");
                }

            }
            if(splitArray[0].equals("setStatus")){
                shopService.updateOrder(splitArray[1], OrderStatus.valueOf(splitArray[2]));
            }
            if(splitArray[0].equals("printOrders")){
                System.out.println(orderRepo.getOrders());
            }
        });


    }
}
