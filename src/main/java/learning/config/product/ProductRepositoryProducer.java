package learning.config.product;

import com.mongodb.client.MongoClient;
import io.quarkus.arc.DefaultBean;
import io.quarkus.runtime.configuration.ProfileManager;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import learning.domain.product.ProductRepository;
import learning.infrastructure.product.ProductInMemoryRepository;
import learning.infrastructure.product.ProductMongoDBRepository;

@Dependent
public class ProductRepositoryProducer {

    public static final String PROFILE_MONGODB = "mongodb";
    public static final String PROFILE_IN_MEMORY = "in-memory";

    @Produces
    @DefaultBean
    public ProductRepository productRepository(MongoClient mongoClient) {
        if (ProfileManager.getActiveProfile().equals(PROFILE_MONGODB)) {
            return new ProductMongoDBRepository(mongoClient);
        }
        return new ProductInMemoryRepository();
    }

}
