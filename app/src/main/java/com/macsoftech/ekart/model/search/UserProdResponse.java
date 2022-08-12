package com.macsoftech.ekart.model.search;

import java.util.List;

public class UserProdResponse {
    private String createdAt;

    private List<String> productImage;

    private String productCode;

    private String productId;

    private String __v;

    private String productStatus;

    private List<String> location;

    private String _id;

    private String productName;

    private List<String> productAlias;

    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<String> productImage) {
        this.productImage = productImage;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(List<String> productAlias) {
        this.productAlias = productAlias;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ClassPojo [createdAt = " + createdAt + ", productImage = " + productImage + ", productCode = " + productCode + ", productId = " + productId + ", __v = " + __v + ", productStatus = " + productStatus + ", location = " + location + ", _id = " + _id + ", productName = " + productName + ", productAlias = " + productAlias + ", updatedAt = " + updatedAt + "]";
    }
}