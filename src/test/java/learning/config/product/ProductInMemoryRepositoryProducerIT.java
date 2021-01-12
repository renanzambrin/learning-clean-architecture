package learning.config.product;

import io.quarkus.runtime.configuration.ProfileManager;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import javax.inject.Inject;
import learning.domain.product.ProductRepository;
import learning.infrastructure.product.ProductInMemoryRepository;
import learning.profile.InMemoryProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(InMemoryProfile.class)
class ProductInMemoryRepositoryProducerIT {

    private final ProductRepository productRepository;

    @Inject
    ProductInMemoryRepositoryProducerIT(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    void givenInMemoryProfileIsSelected_WhenApplicationStarts_ThenInMemoryRepositoryShouldBeAvailable() {
        Assertions.assertEquals(ProductRepositoryProducer.PROFILE_IN_MEMORY, ProfileManager.getActiveProfile());
        Assertions.assertEquals(ProductInMemoryRepository.class, productRepository.getClass());
    }

}