package learning.application;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import learning.domain.product.Product;
import learning.domain.product.ProductRepository;
import learning.profile.InMemoryProfile;
import learning.resolver.ProductDTOResolver;
import learning.resolver.ProductDTOResolver.FakeProductDTO;
import learning.resolver.ProductResolver;
import learning.resolver.ProductResolver.FakeProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@QuarkusTest
@TestProfile(InMemoryProfile.class)
@ExtendWith({ProductDTOResolver.class, ProductResolver.class})
class ProductResourceIT {

    private static final String PRODUCTS_URL = "products";

    private final ProductRepository productRepository;

    @Inject
    ProductResourceIT(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    void givenProductDTO_WhenPOSTToProducts_ThenCreated(@FakeProductDTO ProductDTO productDTO) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .basePath(PRODUCTS_URL)
                .body(productDTO)
                .when()
                .post()
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    void givenProductDTOWithoutName_WhenPOSTToProducts_ThenBadRequest() {
        ProductDTO productDTO = new ProductDTO(null, BigDecimal.TEN);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .basePath(PRODUCTS_URL)
                .body(productDTO)
                .when()
                .post()
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    void givenProductDTOWithoutPrice_WhenPOSTToProducts_ThenBadRequest() {
        ProductDTO productDTO = new ProductDTO("A Fake Product", null);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .basePath(PRODUCTS_URL)
                .body(productDTO)
                .when()
                .post()
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    void givenProduct_WhenGETProducts_ThenOk(@FakeProduct Product product) {
        productRepository.createProduct(product);
        RestAssured.given()
                .contentType(ContentType.JSON)
                .basePath(PRODUCTS_URL)
                .pathParam("product", product.getName())
                .when()
                .get("{product}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void givenNoProduct_WhenGETProducts_ThenNoContent() {
        RestAssured.given()
                .basePath(PRODUCTS_URL)
                .pathParam("product", "NoProduct")
                .when()
                .get("{product}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

}