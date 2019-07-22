/**
  * Copyright 2019 bejson.com 
  */
package com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.response;
import java.util.List;

/**
 * Auto-generated: 2019-07-07 15:18:1
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Result {

    private int total;
    private List<Items> items;
    private int intelligentSortTag;
    public void setTotal(int total) {
         this.total = total;
     }
     public int getTotal() {
         return total;
     }

    public void setItems(List<Items> items) {
         this.items = items;
     }
     public List<Items> getItems() {
         return items;
     }

    public void setIntelligentSortTag(int intelligentSortTag) {
         this.intelligentSortTag = intelligentSortTag;
     }
     public int getIntelligentSortTag() {
         return intelligentSortTag;
     }

}