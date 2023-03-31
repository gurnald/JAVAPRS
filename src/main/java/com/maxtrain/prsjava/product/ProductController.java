package com.maxtrain.prsjava.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("api/products")
public class ProductController {
	
	@Autowired
	private ProductRepository prodRepo;
	
	
	
		// Get all method
		@GetMapping
		public ResponseEntity<Iterable<Product>> getProducts() {
			Iterable<Product> products = prodRepo.findAll();
						return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
		}
		
		// by id
		@GetMapping("{id}")
		public ResponseEntity<Product> getProduct(@PathVariable int id) {
			Optional<Product> product = prodRepo.findById(id);
			if(product.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
						return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
		}
		
		// PostMethod
		@PostMapping
		public ResponseEntity<Product> postProduct(@RequestBody Product product) {
				Product newProduct = prodRepo.save(product);
						return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
		}
		// PutMethod
		@SuppressWarnings("rawtypes")
		@PutMapping("{id}")
		public ResponseEntity putProduct(@PathVariable int id, @RequestBody Product product) {
				if(product == null || product.getId() == 0) {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				var active = prodRepo.findById(product.getId());
				if(active.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
						
				}
				prodRepo.save(product);
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		// DeleteMethod
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity deleteProduct(@PathVariable int id) {
			var active = prodRepo.findById(id);
			if(active.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
			prodRepo.delete(active.get());
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
		}
		
		}
