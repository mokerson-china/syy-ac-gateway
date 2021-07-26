/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.model.message;

import java.util.Date;
import java.util.Properties;


/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class Clock {

    private Date currentDatetime;
    private Date bootDatetime;
    private long upTimes;

    public Clock(Properties proper){
        currentDatetime = new Date();
        bootDatetime = new Date();
        upTimes = System.currentTimeMillis();
    }

    public void setCurrentDatetime(Date currentDatetime) {
         this.currentDatetime = currentDatetime;
     }
     public Date getCurrentDatetime() {
         return currentDatetime;
     }

    public void setBootDatetime(Date bootDatetime) {
         this.bootDatetime = bootDatetime;
     }
     public Date getBootDatetime() {
         return bootDatetime;
     }

    public void setUpTimes(long upTimes) {
         this.upTimes = upTimes;
     }
     public long getUpTimes() {
         return upTimes;
     }

}