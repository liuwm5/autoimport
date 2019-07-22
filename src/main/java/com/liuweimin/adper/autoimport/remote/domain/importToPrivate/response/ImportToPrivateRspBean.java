/**
  * Copyright 2019 bejson.com 
  */
package com.liuweimin.adper.autoimport.remote.domain.importToPrivate.response;

import java.io.Serializable;

/**
 * Auto-generated: 2019-07-08 21:16:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ImportToPrivateRspBean implements Serializable {

    private int code;
    private Data data;
    public void setCode(int code) {
         this.code = code;
     }
     public int getCode() {
         return code;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

}