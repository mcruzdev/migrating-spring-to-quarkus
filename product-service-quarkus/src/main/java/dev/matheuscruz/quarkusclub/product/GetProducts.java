package dev.matheuscruz.quarkusclub.product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class GetProducts {

    final ProductRepository productRepository;

    @Inject
    public GetProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductOutput> execute() {
        return this.productRepository.findAll().stream().map(product -> new ProductOutput(product.getId(),
                product.getName(), product.getPrice(), product.getStock())).toList();
    }
}
