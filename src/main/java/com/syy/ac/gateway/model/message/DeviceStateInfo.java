/**
  * Copyright 2021 bejson.com 
  */
package com.syy.ac.gateway.model.message;

import java.util.List;


/**
 * Auto-generated: 2021-07-26 20:14:5
 *
 * @author TanGuozheng
 */
public class DeviceStateInfo {

    private String name;
    private String model;
    private String esn;
    private List<CellularList> cellularList;
    private String macAddress;
    private String vendor;
    private String state;
    private String version;
    private String patchVersion;
    private String kernelVersion;
    private String hardwareVersion;
    private Cfgfile cfgfile;
    private Clock clock;
    private DeviceResource resource;
    private Location location;
    private Performance performance;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setModel(String model) {
         this.model = model;
     }
     public String getModel() {
         return model;
     }

    public void setEsn(String esn) {
         this.esn = esn;
     }
     public String getEsn() {
         return esn;
     }

    public void setCellularList(List<CellularList> cellularList) {
         this.cellularList = cellularList;
     }
     public List<CellularList> getCellularList() {
         return cellularList;
     }

    public void setMacAddress(String macAddress) {
         this.macAddress = macAddress;
     }
     public String getMacAddress() {
         return macAddress;
     }

    public void setVendor(String vendor) {
         this.vendor = vendor;
     }
     public String getVendor() {
         return vendor;
     }

    public void setState(String state) {
         this.state = state;
     }
     public String getState() {
         return state;
     }

    public void setVersion(String version) {
         this.version = version;
     }
     public String getVersion() {
         return version;
     }

    public void setPatchVersion(String patchVersion) {
         this.patchVersion = patchVersion;
     }
     public String getPatchVersion() {
         return patchVersion;
     }

    public void setKernelVersion(String kernelVersion) {
         this.kernelVersion = kernelVersion;
     }
     public String getKernelVersion() {
         return kernelVersion;
     }

    public void setHardwareVersion(String hardwareVersion) {
         this.hardwareVersion = hardwareVersion;
     }
     public String getHardwareVersion() {
         return hardwareVersion;
     }

    public void setCfgfile(Cfgfile cfgfile) {
         this.cfgfile = cfgfile;
     }
     public Cfgfile getCfgfile() {
         return cfgfile;
     }

    public void setClock(Clock clock) {
         this.clock = clock;
     }
     public Clock getClock() {
         return clock;
     }

    public void setResource(DeviceResource resource) {
         this.resource = resource;
     }
     public DeviceResource getResource() {
         return resource;
     }

    public void setLocation(Location location) {
         this.location = location;
     }
     public Location getLocation() {
         return location;
     }

    public void setPerformance(Performance performance) {
         this.performance = performance;
     }
     public Performance getPerformance() {
         return performance;
     }

}