package com.maxtrain.prsjava.vendor;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface VendorRepository extends CrudRepository <Vendor, Integer> {

				Optional<Vendor> getVendorByCode(String code);
}
