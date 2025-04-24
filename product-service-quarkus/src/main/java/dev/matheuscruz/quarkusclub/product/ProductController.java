package dev.matheuscruz.quarkusclub.product;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.net.URI;

@Path("/api/v1/products")
public class ProductController {

    final CreateProduct createProduct;
    final GetProducts getProducts;

    public ProductController(final CreateProduct createProduct, final GetProducts getProducts) {
        this.createProduct = createProduct;
        this.getProducts = getProducts;
    }

    // TODO: add validation with bean validation
    @POST
    public Response addProduct(final CreateProductRequest request) {
        CreateProduct.Output output = this.createProduct
                .execute(new CreateProduct.Input(request.name(), request.price(), request.stock()));
        return Response.created(URI.create("/api/v1/products/%s".formatted(output.id()))).build();
        // TODO: implement /api/v1/products/{productId}
    }

    @GET
    public Response getAllProducts() {
        // TODO: implement pagination
        return Response.ok(this.getProducts.execute()).build();
    }

    public record CreateProductRequest(String name, BigDecimal price, Integer stock) {
    }
}
