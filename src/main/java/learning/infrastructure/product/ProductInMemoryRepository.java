package learning.infrastructure.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import learning.domain.product.Product;
import learning.domain.product.ProductRepository;

public class ProductInMemoryRepository implements ProductRepository {

    final List<Product> products;

    public ProductInMemoryRepository() {
        this.products = new ArrayList<>();
    }

    @Override
    public void createProduct(Product product) {
        products.add(product);
    }

    @Override
    public Optional<Product> findProductByName(String name) {
        return products.parallelStream()
                .filter(product -> product.getName().equals(name))
                .limit(1).findFirst();
    }

}
