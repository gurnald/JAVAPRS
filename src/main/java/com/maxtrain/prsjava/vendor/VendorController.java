package com.maxtrain.prsjava.vendor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/vendors")

public class VendorController {

	@Autowired
	private VendorRepository venRepo;

	// Get all method
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors() {
		Iterable<Vendor> vendors = venRepo.findAll();
					return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);
	}
	// by id
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendor(@PathVariable int id) {
		Optional<Vendor> vendor = venRepo.findById(id);
		if(vendor.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
					return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);				
	}
	
	@GetMapping("code/{code}")
	public ResponseEntity<Vendor> getVendorByCode(@PathVariable String code) {
		Optional<Vendor> vendor = venRepo.getVendorByCode(code);
		if(vendor.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	
	
	
	
	
	// PostMethod
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor) {
			Vendor newVendor = venRepo.save(vendor);
					return new ResponseEntity<Vendor>(newVendor, HttpStatus.CREATED);
	}
	// PutMethod
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putUser(@PathVariable int id, @RequestBody Vendor vendor) {
			if(vendor == null || vendor.getId() == 0) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			var active = venRepo.findById(vendor.getId());
			if(active.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					
			}
			venRepo.save(vendor);
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	// DeleteMethod
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		var active = venRepo.findById(id);
		if(active.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
		venRepo.delete(active.get());
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
    }
}
