/**
 * Copyright 2019 bejson.com
 */
package com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.request;

import java.util.List;

/**
 * Auto-generated: 2019-07-07 15:14:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Params {

    private int mainCategory;
    private List<String> category;
    private int mainRegion;
    private List<String> region;
    private int ownerType;
    private int city;
    private int sortBy;
    private List<String> shopStatus;
    private String keyword;
    private int pageIndex;
    private int pageSize;

    public void setMainCategory(int mainCategory) {
        this.mainCategory = mainCategory;
    }

    public int getMainCategory() {
        return mainCategory;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setMainRegion(int mainRegion) {
        this.mainRegion = mainRegion;
    }

    public int getMainRegion() {
        return mainRegion;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public List<String> getRegion() {
        return region;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getCity() {
        return city;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setShopStatus(List<String> shopStatus) {
        this.shopStatus = shopStatus;
    }

    public List<String> getShopStatus() {
        return shopStatus;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

}