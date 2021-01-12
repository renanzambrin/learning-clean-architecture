package learning.application;

import java.math.BigDecimal;
import java.util.Optional;
import javax.ws.rs.core.Response;
import learning.domain.product.Product;
import learning.domain.product.ProductRepository;
import learning.resolver.ProductDTOResolver;
import learning.resolver.ProductDTOResolver.FakeProductDTO;
import learning.resolver.ProductResolver;
import learning.resolver.ProductResolver.FakeProduct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith({ProductResolver.class, ProductDTOResolver.class})
class ProductResourceTest {

    ProductRepository productRepository;
    ProductResource productResource;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productResource = new ProductResource(productRepository);
    }

    @Test
    void givenProductDTO_WhenCreateProductIsCalled_ThenProductRepositoryIsCalled(@FakeProductDTO ProductDTO dto) {
        productResource.createNewProduct(dto);
        Mockito.verify(productRepository).createProduct(any());
    }

    @Test
    void givenProductDTOWithoutName_WhenCreateProductIsCalled_ThenResponseBadRequest() {
        ProductDTO dto = new ProductDTO(null, BigDecimal.TEN);
        Response response = productResource.createNewProduct(dto);
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Mockito.verifyNoInteractions(productRepository);
    }

    @Test
    void givenProductDTOWithoutPrice_WhenCreateProductIsCalled_ThenResponseBadRequest() {
        ProductDTO dto = new ProductDTO("A Fake Product", null);
        Response response = productResource.createNewProduct(dto);
        Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Mockito.verifyNoInteractions(productRepository);
    }

    @Test
    void givenProduct_WhenFindProductByNameIsCalled_ThenResponseOk(@FakeProduct Product product) {
        Mockito.when(productRepository.findProductByName(product.getName())).thenReturn(Optional.of(product));
        Response response = productResource.findProduct(product.getName());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Mockito.verify(productRepository).findProductByName(product.getName());
    }

    @Test
    void givenNoProduct_WhenFindProductByNameIsCalled_ThenResponseNoContent() {
        Mockito.when(productRepository.findProductByName(any())).thenReturn(Optional.empty());
        Response response = productResource.findProduct("NoProduct");
        Assertions.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        Mockito.verify(productRepository).findProductByName(any());
    }


}
