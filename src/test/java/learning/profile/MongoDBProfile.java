package learning.profile;

import io.quarkus.test.junit.QuarkusTestProfile;
import learning.config.product.ProductRepositoryProducer;

public class MongoDBProfile implements QuarkusTestProfile {

    @Override
    public String getConfigProfile() {
        return ProductRepositoryProducer.PROFILE_MONGODB;
    }

}
