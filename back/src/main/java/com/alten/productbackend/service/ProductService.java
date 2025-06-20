
package com.alten.productbackend.service;

import com.alten.productbackend.model.Product;
import com.alten.productbackend.repository.ProductRepository;
import com.alten.productbackend.util.BeanUtilsPatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return productRepository.save(product);
    }
/*
    public Product replaceProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setCode(productDetails.getCode());
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setImage(productDetails.getImage());
        product.setCategory(productDetails.getCategory());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setInternalReference(productDetails.getInternalReference());
        product.setShellId(productDetails.getShellId());
        product.setInventoryStatus(productDetails.getInventoryStatus());
        product.setRating(productDetails.getRating());
        product.setUpdatedAt(Instant.now());
        return productRepository.save(product);
    }

    */
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // C'EST ICI QUE BeanUtilsPatch DOIT ÊTRE UTILISÉ
        BeanUtilsPatch.copyNonNullProperties(productDetails, existingProduct);

        existingProduct.setUpdatedAt(Instant.now());
        return productRepository.save(existingProduct);
    }
    
    public Product replaceProduct(Long id, Product productDetails) {
        // Pour PUT, nous remplaçons la ressource. Le ID doit correspondre.
        // Si le produit n'existe pas, certains choisissent de le créer (upsert).
        // Ici, nous allons le mettre à jour s'il existe, sinon lancer une exception.
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found for replacement");
        }
        productDetails.setId(id); // Assurez-vous que l'ID de l'objet est bien celui du chemin
        productDetails.setUpdatedAt(Instant.now());
        // Conservez createdAt si vous le souhaitez, ou mettez-le à jour si c'est une recréation logique
        // Pour un remplacement complet, il est courant de ne pas toucher à createdAt
        return productRepository.save(productDetails);
    }
    
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}


