import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.shop.exception.ProductNotFoundException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopService {
    @NonNull
    private final ProductRepo productRepo;
    @NonNull
    private final OrderRepo orderRepo;

    public ShopService() {
        productRepo = new ProductRepo();
        orderRepo = new OrderMapRepo();
    }

    public Order addOrder(List<String> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new ProductNotFoundException("product not found");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> orderStatus(OrderStatus orderStatus) {
        return orderRepo.getOrders().stream().filter(order -> order.orderStatus().equals(orderStatus)).toList();
    }

    public Order updateOrder(String orderId, OrderStatus newOrderStatus) {
        Order foundOrder = orderRepo.getOrderById(orderId);
        if (foundOrder == null) {
            return null;
        }
        return foundOrder.withOrderStatus(newOrderStatus);
    }
}
