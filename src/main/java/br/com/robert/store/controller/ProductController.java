package br.com.robert.store.controller;

import br.com.robert.store.dto.ProductDto;
import br.com.robert.store.model.Product;
import br.com.robert.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Busca utilizando um ID específico. É necessário passar o ID pela URI e passar para o método utilizando
    // o @PathVariable
    @GetMapping("/{id}")
    public ResponseEntity<Product> findClientById(@PathVariable long id){
        return productRepository.findById(id)
                .map(client -> ResponseEntity.ok().body(client))
                .orElse(ResponseEntity.notFound().build());
    }

    // Se receber um nome como parâmetro, ele busca um produto específico
    // Caso contrário, ele retorna uma lista com todos os clientes.

/*    @GetMapping
    public Object productList(@RequestParam(required = false) String name){

        if (name == null){
            return productRepository.findAll();
        } else {
            return productRepository.searchByName(name);
        }
    }*/

    @GetMapping
    public Page<Product> productslist(@PageableDefault(sort = "price", direction = Sort.Direction.ASC, page = 0, size = 3) Pageable pageable){
        return productRepository.findAll(pageable);
    }

    // Insere um novo produto
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDto productDto){
        Product product = productRepository.save(productDto.convert());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // Atualiza o produto
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id,
                                               @RequestBody ProductDto productDto){

        return productRepository.findById(id)
                .map(record -> {
                    record.setName(productDto.getName());
                    record.setPrice(productDto.getPrice());
                    Product product = productRepository.save(productDto.convert());
                    return ResponseEntity.ok().body(product);
                }).orElse(ResponseEntity.notFound().build());
    }

    // Deleta um objeto utilizando o ID,
    // semelhante a consulta, passamos o ID para o método utilizando o @PathVariable
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long id){

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
