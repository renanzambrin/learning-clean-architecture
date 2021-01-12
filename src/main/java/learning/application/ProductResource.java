package learning.application;

import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import learning.domain.product.Product;
import learning.domain.product.ProductRepository;

@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductRepository productRepository;

    @Inject
    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GET
    @Path("{product}")
    public Response findProduct(@PathParam("product") String name) {
        Optional<Product> productOpt = productRepository.findProductByName(name);
        if (productOpt.isPresent()) {
            return Response.ok(productOpt.get()).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @POST
    public Response createNewProduct(ProductDTO productDTO) {
        try {
            productRepository.createProduct(productDTO.toProduct());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
