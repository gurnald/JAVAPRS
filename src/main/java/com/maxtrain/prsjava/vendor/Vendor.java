package com.maxtrain.prsjava.vendor;

import jakarta.persistence.*;


@Entity
@Table(name="Vendors", uniqueConstraints=@UniqueConstraint(name="UIDX_code", columnNames="code"))
public class Vendor {
	
			@Id
			@GeneratedValue(strategy=GenerationType.IDENTITY)
			private int id;
			@Column(length=30, nullable=false)
			private String code;
			@Column(length=30, nullable=false)
			private String name;
			@Column(length=30, nullable=false)
			private String address;
			@Column(length=30, nullable=false)
			private String city;
			@Column(length=2, nullable=false)
			private String state;
			@Column(length=5, nullable=false)
			private String zip;
			@Column(length=12, nullable=false)
			private String phone;
			@Column(length=255, nullable=false)
			private String email;
			
			public Vendor() {}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getState() {
				return state;
			}

			public void setState(String state) {
				this.state = state;
			}

			public String getZip() {
				return zip;
			}

			public void setZip(String zip) {
				this.zip = zip;
			}

			public String getPhone() {
				return phone;
			}

			public void setPhone(String phone) {
				this.phone = phone;
			}

			public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}
			
			

}
