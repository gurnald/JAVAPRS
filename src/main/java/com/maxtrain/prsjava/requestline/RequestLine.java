package com.maxtrain.prsjava.requestline;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maxtrain.prsjava.product.Product;
import com.maxtrain.prsjava.request.Request;

import jakarta.persistence.*;


@Entity
@Table(name="Requestlines")

public class RequestLine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int quantity = 1;
	
	@JsonBackReference
	@ManyToOne(optional=false)
	@JoinColumn(name="requestId", columnDefinition="int")
	private Request request;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="productId", columnDefinition="int")
	private Product product;
	
	public RequestLine() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	
	
}
