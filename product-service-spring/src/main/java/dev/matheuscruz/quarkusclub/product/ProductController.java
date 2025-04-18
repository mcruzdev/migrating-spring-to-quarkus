package dev.matheuscruz.quarkusclub.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final CreateProduct createProduct;
    private final ProductRepository productRepository;

    public ProductController(CreateProduct createProduct, ProductRepository productRepository) {
        this.createProduct = createProduct;
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody CreateProductRequest request) {
        CreateProduct.Output output = this.createProduct
                .execute(new CreateProduct.Input(request.name(), request.price(), request.stock()));
        return ResponseEntity.created(URI.create("/api/v1/products/%s".formatted(output.id()))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProductResponse> getProductById(@PathVariable("id") String id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity
                .ok(new GetProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock()));
    }

    public record CreateProductRequest(String name, BigDecimal price, Integer stock) {
    }

    public record GetProductResponse(String id, String name, BigDecimal price, Integer stock) {
    }
}
