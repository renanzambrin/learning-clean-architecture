package learning.config.product;

import io.quarkus.runtime.configuration.ProfileManager;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import javax.inject.Inject;
import learning.domain.product.ProductRepository;
import learning.infrastructure.product.ProductMongoDBRepository;
import learning.profile.MongoDBProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(MongoDBProfile.class)
class ProductMongoDBRepositoryProducerIT {

    private final ProductRepository productRepository;

    @Inject
    ProductMongoDBRepositoryProducerIT(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    void givenMongoProfileIsSelected_WhenApplicationStarts_ThenMongoRepositoryShouldBeAvailable() {
        Assertions.assertEquals(ProductRepositoryProducer.PROFILE_MONGODB, ProfileManager.getActiveProfile());
        Assertions.assertEquals(ProductMongoDBRepository.class, productRepository.getClass());
    }

}