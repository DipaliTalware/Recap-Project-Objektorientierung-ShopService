import org.shop.exception.ProductNotFoundException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        UUIDService idService = new UUIDService();
        ShopService shopService = new ShopService(productRepo, orderRepo, idService);

        Product product = new Product("1", "T Shirt");
        Product product2 = new Product("2", "Jeans");
        Product product3 = new Product("3", "T Shirt");
        Product product4 = new Product("4", "Jeans");
        productRepo.addProduct(product);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);
        productRepo.addProduct(product4);

        try {
            shopService.addOrder(List.of("1","2"));
            shopService.addOrder(List.of("3"));
            shopService.addOrder(List.of("4"));
        } catch (ProductNotFoundException e) {
            System.out.println("Product now found");
        }

        System.out.println("List of orders: " + orderRepo.getOrders());
    }
}
