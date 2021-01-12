package learning.infrastructure.product;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import learning.domain.product.Price;
import learning.domain.product.Product;
import learning.domain.product.ProductRepository;
import org.bson.Document;

public class ProductMongoDBRepository implements ProductRepository {

    static final String PROPERTY_NAME = "name";
    static final String PROPERTY_PRICES = "prices";
    static final String PROPERTY_PRICE_VALUE = "value";
    static final String PROPERTY_PRICE_FROM = "validFrom";
    static final String PROPERTY_PRICE_TO = "validTo";

    private final MongoClient mongoClient;

    @Inject
    public ProductMongoDBRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void createProduct(Product product) {
        List<Document> prices = product.getPrices().stream()
                .map(price -> new Document()
                        .append(PROPERTY_PRICE_VALUE, price.getValue().toString())
                        .append(PROPERTY_PRICE_FROM, price.getValidFrom().toString())
                        .append(PROPERTY_PRICE_TO, price.getValidTo() != null ? price.getValidTo().toString() : null))
                .collect(Collectors.toList());
        Document document = new Document()
                .append(PROPERTY_NAME, product.getName())
                .append(PROPERTY_PRICES, prices);
        getCollection().insertOne(document);
    }

    @Override
    public Optional<Product> findProductByName(String name) {
        BasicDBObject query = new BasicDBObject();
        query.put(PROPERTY_NAME, name);
        //@formatter:off
        MongoCursor<Document> cursor = getCollection()
                .find(query)
                .limit(1)
                .iterator();
        //@formatter:on
        return cursor.hasNext() ? Optional.of(from(cursor.next())) : Optional.empty();
    }

    static Product from(Document document) {
        List<Price> prices = getPriceList(document);
        return Product.builder()
                .withName(document.getString(PROPERTY_NAME))
                .withPrices(prices)
                .build();
    }

    static List<Price> getPriceList(Document document) {
        List<Document> documents = document.getList(PROPERTY_PRICES, Document.class);
        return documents.stream().map(doc -> Price.builder()
                .withValue(new BigDecimal(doc.getString(PROPERTY_PRICE_VALUE)))
                .withValidFrom(LocalDateTime.parse(doc.getString(PROPERTY_PRICE_FROM)))
                .withValidTo(doc.getString(PROPERTY_PRICE_TO) != null ?
                        LocalDateTime.parse(doc.getString(PROPERTY_PRICE_TO)) : null)
                .build())
                .collect(Collectors.toList());
    }

    MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("learning-clean-architecture")
                .getCollection("products");
    }

}
