package com.macsoftech.ekart.model.sizes;

import java.util.List;

public class SizeModelData {
    private List<SizeModel> sizesList;

    private String sizesCount;

    public List<SizeModel> getSizesList() {
        return sizesList;
    }

    public void setSizesList(List<SizeModel> sizesList) {
        this.sizesList = sizesList;
    }

    public String getSizesCount() {
        return sizesCount;
    }

    public void setSizesCount(String sizesCount) {
        this.sizesCount = sizesCount;
    }

    @Override
    public String toString() {
        return "ClassPojo [sizesList = " + sizesList + ", sizesCount = " + sizesCount + "]";
    }
}
		