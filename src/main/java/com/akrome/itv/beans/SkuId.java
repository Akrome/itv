package com.akrome.itv.beans;

public class SkuId {
    public final String skuId;

    public SkuId(String skuId) {
        this.skuId = skuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuId skuId = (SkuId) o;

        return this.skuId != null ? this.skuId.equals(skuId.skuId) : skuId.skuId == null;
    }

    @Override
    public int hashCode() {
        return skuId != null ? skuId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return skuId;
    }
}
