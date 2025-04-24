package dev.matheuscruz.quarkusclub.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final CreateProduct createProduct;
    private final GetProducts getProducts;

    public ProductController(final CreateProduct createProduct, final GetProducts getProducts) {
        this.createProduct = createProduct;
        this.getProducts = getProducts;
    }

    // TODO: add validation with bean validation
    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody CreateProductRequest request) {
        CreateProduct.Output output = this.createProduct
                .execute(new CreateProduct.Input(request.name(), request.price(), request.stock()));
        return ResponseEntity.created(URI.create("/api/v1/products/%s".formatted(output.id()))).build();
        // TODO: implement /api/v1/products/{productId}
    }

    @GetMapping
    public ResponseEntity<List<ProductOutput>> getAllProducts() {
        // TODO: implement pagination
        return ResponseEntity
                .ok(this.getProducts.execute());
    }
    public record CreateProductRequest(String name, BigDecimal price, Integer stock) {
    }
}
