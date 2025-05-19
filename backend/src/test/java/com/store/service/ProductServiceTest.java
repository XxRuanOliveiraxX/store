package com.store.service;

import com.store.model.Product;
import com.store.repository.ProductRepository;
import com.store.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Produto Teste");
        product.setPrice(new BigDecimal("99.99"));
        product.setDescription("Descrição do produto teste");
    }

    @Test
    void whenFindByIdWithValidId_thenReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product found = productService.findById(1L);

        assertNotNull(found);
        assertEquals(product.getId(), found.getId());
        assertEquals(product.getName(), found.getName());
        verify(productRepository).findById(1L);
    }

    @Test
    void whenFindByIdWithInvalidId_thenThrowException() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            productService.findById(2L);
        });

        verify(productRepository).findById(2L);
    }

    @Test
    void whenSaveProduct_thenReturnSavedProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product saved = productService.createProduct(product);

        assertNotNull(saved);
        assertEquals(product.getName(), saved.getName());
        verify(productRepository).save(product);
    }
} 