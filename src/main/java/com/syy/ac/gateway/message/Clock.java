/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class Clock {

    private String currentDatetime;
    private String bootDatetime;
    private long upTimes;

    public Clock(Properties proper){
        currentDatetime =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
        bootDatetime =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
        upTimes = System.currentTimeMillis();
    }

    public void setCurrentDatetime(String currentDatetime) {
         this.currentDatetime = currentDatetime;
     }
     public String getCurrentDatetime() {
         return currentDatetime;
     }

    public void setBootDatetime(String bootDatetime) {
         this.bootDatetime = bootDatetime;
     }
     public String getBootDatetime() {
         return bootDatetime;
     }

    public void setUpTimes(long upTimes) {
         this.upTimes = upTimes;
     }
     public long getUpTimes() {
         return upTimes;
     }

}