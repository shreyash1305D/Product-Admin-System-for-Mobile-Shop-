package com.adminsystem.MobileShop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int prodid;
	 	@Column(name = "model_name", nullable = false)
	    private String modelName;
	    private String brand;
	    private float price;
	    private int stockQuantity;
	    private String features;
	    private String warrantyPeriod;
		public int getProdid() {
			return prodid;
		}
		public void setProdid(int prodid) {
			this.prodid = prodid;
		}
		public String getModelName() {
			return modelName;
		}
		public void setModelName(String modelName) {
			this.modelName = modelName;
		}
		public String getBrand() {
			return brand;
		}
		public void setBrand(String brand) {
			this.brand = brand;
		}
		public float getPrice() {
			return price;
		}
		public void setPrice(float price) {
			this.price = price;
		}
		public int getStockQuantity() {
			return stockQuantity;
		}
		public void setStockQuantity(int stockQuantity) {
			this.stockQuantity = stockQuantity;
		}
		public String getFeatures() {
			return features;
		}
		public void setFeatures(String features) {
			this.features = features;
		}
		public String getWarrantyPeriod() {
			return warrantyPeriod;
		}
		public void setWarrantyPeriod(String warrantyPeriod) {
			this.warrantyPeriod = warrantyPeriod;
		}
	    
	    

}
