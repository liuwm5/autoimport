/**
  * Copyright 2019 bejson.com 
  */
package com.liuweimin.adper.autoimport.remote.domain.importToPrivate.request;


import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2019-07-07 18:15:10
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ImportToPrivateBean implements Serializable {

    private static final long serialVersionUID = 8324164495520038376L;
    private String type;
    private String category;
    private Params params;
    private String as;
    private List<String> children;
    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setCategory(String category) {
         this.category = category;
     }
     public String getCategory() {
         return category;
     }

    public void setParams(Params params) {
         this.params = params;
     }
     public Params getParams() {
         return params;
     }

    public void setAs(String as) {
         this.as = as;
     }
     public String getAs() {
         return as;
     }

    public void setChildren(List<String> children) {
         this.children = children;
     }
     public List<String> getChildren() {
         return children;
     }

}