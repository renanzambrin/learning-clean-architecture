package learning.profile;

import io.quarkus.test.junit.QuarkusTestProfile;
import learning.config.product.ProductRepositoryProducer;

public class InMemoryProfile implements QuarkusTestProfile {

    @Override
    public String getConfigProfile() {
        return ProductRepositoryProducer.PROFILE_IN_MEMORY;
    }

}
