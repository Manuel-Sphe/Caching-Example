package com.demo.cache;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("products")
public class Controller {

    private final ProductRepo repo;


    public Controller(ProductRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/{id}")
    @Cacheable(value = "product", key = "#id")
    public ResponseEntity<Prouduct> getProductById(@PathVariable long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    @CachePut(cacheNames = "product", key = "#id")
    public ResponseEntity<Prouduct> updateProduct(@PathVariable long id, @RequestBody Prouduct product) {
        return repo.findById(id)
                .map(p -> ResponseEntity.ok(repo.save(
                        p.setName(product.getName())
                        .setCode(product.getCode())
                        .setPrice(product.getPrice())
                        .setQuantity(product.getQuantity()))
                        )
                )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "product", key = "#id", beforeInvocation = true)
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Prouduct> addProduct(@RequestBody Prouduct product) {
        return ResponseEntity.ok(repo.save(product));
    }


}
