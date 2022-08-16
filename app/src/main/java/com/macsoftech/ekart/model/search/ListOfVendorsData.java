package com.macsoftech.ekart.model.search;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ListOfVendorsData implements Parcelable {
    private String createdAt;

    private String quantity;

    private List<String> productImage;

    private String productId;

    private String size;
    private String location;
    private String length;

    private String __v;

    private String vendorId;
    private String userId;

    private String _id;

    private String vendorName;
    private String entityName;
    private String mobileNum;

    private String productName;

    private String updatedAt;

    protected ListOfVendorsData(Parcel in) {
        createdAt = in.readString();
        quantity = in.readString();
        productImage = in.createStringArrayList();
        productId = in.readString();
        size = in.readString();
        location = in.readString();
        length = in.readString();
        __v = in.readString();
        vendorId = in.readString();
        userId = in.readString();
        _id = in.readString();
        vendorName = in.readString();
        entityName = in.readString();
        mobileNum = in.readString();
        productName = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeString(quantity);
        dest.writeStringList(productImage);
        dest.writeString(productId);
        dest.writeString(size);
        dest.writeString(location);
        dest.writeString(length);
        dest.writeString(__v);
        dest.writeString(vendorId);
        dest.writeString(userId);
        dest.writeString(_id);
        dest.writeString(vendorName);
        dest.writeString(entityName);
        dest.writeString(mobileNum);
        dest.writeString(productName);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListOfVendorsData> CREATOR = new Creator<ListOfVendorsData>() {
        @Override
        public ListOfVendorsData createFromParcel(Parcel in) {
            return new ListOfVendorsData(in);
        }

        @Override
        public ListOfVendorsData[] newArray(int size) {
            return new ListOfVendorsData[size];
        }
    };

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public List<String> getProductImage() {
        if (productImage == null) {
            return new ArrayList<>();
        }
        return productImage;
    }

    public void setProductImage(List<String> productImage) {
        this.productImage = productImage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ClassPojo [createdAt = " + createdAt + ", quantity = " + quantity + ", productImage = " + productImage + ", productId = " + productId + ", size = " + size + ", __v = " + __v + ", vendorId = " + vendorId + ", _id = " + _id + ", vendorName = " + vendorName + ", productName = " + productName + ", updatedAt = " + updatedAt + "]";
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }
}