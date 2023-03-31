package com.maxtrain.prsjava.request;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/requests")
public class RequestController {
	
	@Autowired
	private RequestRepository reqRepo;
	
	
	// Get all method
			@GetMapping
			public ResponseEntity<Iterable<Request>> getRequests() {
				Iterable<Request> requests = reqRepo.findAll();
							return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
			}
			// by id
			@GetMapping("{id}")
			public ResponseEntity<Request> getRequest(@PathVariable int id) {
				Optional<Request> request = reqRepo.findById(id);
				if(request.isEmpty()) {
							return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
							return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
			}
			
			
			@GetMapping("reviews")
			public ResponseEntity<Iterable<Request>> getRequestsInReview() {
				var requests = reqRepo.findByStatus("REVIEW");
							return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
			}
			
			@GetMapping("rejected")
			public ResponseEntity<Iterable<Request>> getRequestsInRejected() {
				var requests = reqRepo.findByStatus("REJECTED");
							return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
			}
			
			@GetMapping("approved")
			public ResponseEntity<Iterable<Request>> getRequestsInApproved() {
				var requests = reqRepo.findByStatus("APPROVED");
							return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
			}
		
			// PostMethod
			@PostMapping
			public ResponseEntity<Request> postRequest(@RequestBody Request request) {
					Request newRequest = reqRepo.save(request);
							return new ResponseEntity<Request>(newRequest, HttpStatus.CREATED);
			}
			// PutMethod
			@SuppressWarnings("rawtypes")
			@PutMapping("{id}")
			public ResponseEntity putRequest(@PathVariable int id, @RequestBody Request request) {
					if(request == null || request.getId() == 0) {
							return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
					var active = reqRepo.findById(request.getId());
					if(active.isEmpty()) {
							return new ResponseEntity<>(HttpStatus.NOT_FOUND);
							
					}
					reqRepo.save(request);
							return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			@SuppressWarnings("rawtypes")
			@PutMapping("review/{id}")
			public ResponseEntity reviewRequest(@PathVariable int id, @RequestBody Request request)  {
				var statValue = (request.getTotal() <= 50) ? "APPROVED" : "REVIEW";
				request.setStatus(statValue);
				return putRequest(id, request);
			}
			
			@SuppressWarnings("rawtypes")
			@PutMapping("approve/{id}")
			public ResponseEntity approveRequest(@PathVariable int id, @RequestBody Request request) {
				request.setStatus("APPROVED");
				return putRequest(id, request);
				
			}
			
			@SuppressWarnings("rawtypes")
			@PutMapping("reject/{id}")
			public ResponseEntity rejectRequest(@PathVariable int id, @RequestBody Request request) {
				request.setStatus("REJECTED");
				return putRequest(id, request);
				
			}
			
			
			
			// DeleteMethod
			@SuppressWarnings("rawtypes")
			@DeleteMapping("{id}")
			public ResponseEntity deleteRequest(@PathVariable int id) {
				var active = reqRepo.findById(id);
				if(active.isEmpty()) {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			    }
				reqRepo.delete(active.get());
						return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	        }
	}


