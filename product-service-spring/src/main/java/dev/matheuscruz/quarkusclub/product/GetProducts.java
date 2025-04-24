package dev.matheuscruz.quarkusclub.product;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetProducts {

    private final ProductRepository productRepository;

    public GetProducts(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductOutput> execute() {
        return this.productRepository.findAll().stream().map(product -> new ProductOutput(
                product.getId(), product.getName(), product.getPrice(), product.getStock()
        )).toList();
    }
}
