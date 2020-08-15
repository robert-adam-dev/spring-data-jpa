package br.com.robert.store.dto;

import br.com.robert.store.model.Client;
import br.com.robert.store.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class ProductDto {

    private String name;
    private BigDecimal price;

    public ProductDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public Product convert(){
        return new Product(name, price);
    }
}
