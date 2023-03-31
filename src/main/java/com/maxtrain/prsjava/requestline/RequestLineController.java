package com.maxtrain.prsjava.requestline;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.prsjava.product.Product;
import com.maxtrain.prsjava.product.ProductRepository;
import com.maxtrain.prsjava.request.Request;
import com.maxtrain.prsjava.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("api/requestlines")


public class RequestLineController {
	@Autowired
	private RequestRepository reqRepo;
	@Autowired
	private RequestLineRepository reqlRepo;
	@Autowired 
	ProductRepository prodRepo;
	
	private boolean recalculateOrderTotal (int requestId) {
		Optional<Request> aRequest = reqRepo.findById(requestId);

		if(aRequest.isEmpty()) {
			return false;
		}
		 
		Request request = aRequest.get();
		Iterable<RequestLine> requestlines = reqlRepo.findByRequestId(requestId);
		double total = 0;
		for(RequestLine rl : requestlines) {
			if(rl.getProduct().getName() == null) {
				Product product = prodRepo.findById(rl.getProduct().getId()).get();
				rl.setProduct(product);;
			}
			total += rl.getQuantity() * rl.getProduct().getPrice();
		}
		request.setTotal(total);
		reqRepo.save(request);
		
		return true;
	}	
	
	@GetMapping
	public ResponseEntity<Iterable<RequestLine>> getRequestlines() { 
		Iterable<RequestLine> requestlines = reqlRepo.findAll();
		return new ResponseEntity<Iterable<RequestLine>>(requestlines, HttpStatus.OK); 
		
	} 
	
	@GetMapping("{id}")
	public ResponseEntity<RequestLine> getRequestline(@PathVariable int id) {
		Optional<RequestLine> requestline = reqlRepo.findById(id);
		if(requestline.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RequestLine>(requestline.get(), HttpStatus.OK);
	}

	
	@PostMapping
	public ResponseEntity<RequestLine> postRequestline (@RequestBody RequestLine requestline) {
		RequestLine newRequestline = reqlRepo.save(requestline);
		Optional<Request> request = reqRepo.findById(requestline.getRequest().getId());
		if(!request.isEmpty() ) {
			boolean success = recalculateOrderTotal(request.get().getId());
			if(!success) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<RequestLine>(newRequestline, HttpStatus.CREATED);
		
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putRequestline (@PathVariable int id, @RequestBody RequestLine requestline) {
		if(requestline.getId() != id) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
		reqlRepo.save(requestline);
		Optional<Request> request = reqRepo.findById(requestline.getRequest().getId());
		if(!request.isEmpty() ) {
			boolean success = recalculateOrderTotal(request.get().getId());
			if(!success) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}


	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteRequestline(@PathVariable int id) {
		Optional<RequestLine> requestline = reqlRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		reqlRepo.delete(requestline.get());
		
		Optional<Request> request = reqRepo.findById(requestline.get().getRequest().getId());
		if(!request.isEmpty() ) {
			boolean success = recalculateOrderTotal(request.get().getId());
			if(!success) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}

