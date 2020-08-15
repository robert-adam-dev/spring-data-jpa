package br.com.robert.store.repository;

import br.com.robert.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    // Querys personalizadas utilizando SQL NATIVO
    @Query(value = "SELECT MAX(PRICE) FROM PRODUCT", nativeQuery = true)
    BigDecimal findMostExpensiveProduct();

    @Query(value = "SELECT MIN(PRICE) FROM PRODUCT", nativeQuery = true)
    BigDecimal findMostCheapestProduct();

    @Query(value = "SELECT AVG(PRICE) FROM PRODUCT", nativeQuery = true)
    BigDecimal findAveragePrice();

    // Querys personalizadas utilizando JPQL
    @Query("select p from product p where upper(p.name) like concat('%', upper(?1),'%')")
    List<Product> searchByName(String name);

    @Query("select p from product p where upper(p.name) like concat('%', upper(?1),'%')" +
            "order by p.price desc")
    List<Product> findProductOrderByHighestPrice(String name);

    @Query("select p from product p where upper(p.name) like concat('%', upper(?1),'%')" +
            "order by p.price asc")
    List<Product> findProductOrderByCheapestPrice(String name);
}