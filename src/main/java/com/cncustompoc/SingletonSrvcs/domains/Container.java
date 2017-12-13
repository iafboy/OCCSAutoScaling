/**
  * Copyright 2017 bejson.com 
  */
package com.cncustompoc.SingletonSrvcs.domains;
import java.util.Date;

public class Container {

    private String deployment_id;
    private String deployment_name;
    private String deployment_key;
    private String environment_id;
    private String stack_id;
    private String service_id;
    private int service_slot;
    private String container_name;
    private String container_id;
    private String container_hostname;
    private String host_id;
    private String hostname;
    private String host_group;
    private String subtype;
    private int phase_id;
    private String state;
    private String started_at;
    public void setDeployment_id(String deployment_id) {
         this.deployment_id = deployment_id;
     }
     public String getDeployment_id() {
         return deployment_id;
     }

    public void setDeployment_name(String deployment_name) {
         this.deployment_name = deployment_name;
     }
     public String getDeployment_name() {
         return deployment_name;
     }

    public void setDeployment_key(String deployment_key) {
         this.deployment_key = deployment_key;
     }
     public String getDeployment_key() {
         return deployment_key;
     }

    public void setEnvironment_id(String environment_id) {
         this.environment_id = environment_id;
     }
     public String getEnvironment_id() {
         return environment_id;
     }

    public void setStack_id(String stack_id) {
         this.stack_id = stack_id;
     }
     public String getStack_id() {
         return stack_id;
     }

    public void setService_id(String service_id) {
         this.service_id = service_id;
     }
     public String getService_id() {
         return service_id;
     }

    public void setService_slot(int service_slot) {
         this.service_slot = service_slot;
     }
     public int getService_slot() {
         return service_slot;
     }

    public void setContainer_name(String container_name) {
         this.container_name = container_name;
     }
     public String getContainer_name() {
         return container_name;
     }

    public void setContainer_id(String container_id) {
         this.container_id = container_id;
     }
     public String getContainer_id() {
         return container_id;
     }

    public void setContainer_hostname(String container_hostname) {
         this.container_hostname = container_hostname;
     }
     public String getContainer_hostname() {
         return container_hostname;
     }

    public void setHost_id(String host_id) {
         this.host_id = host_id;
     }
     public String getHost_id() {
         return host_id;
     }

    public void setHostname(String hostname) {
         this.hostname = hostname;
     }
     public String getHostname() {
         return hostname;
     }

    public void setHost_group(String host_group) {
         this.host_group = host_group;
     }
     public String getHost_group() {
         return host_group;
     }

    public void setSubtype(String subtype) {
         this.subtype = subtype;
     }
     public String getSubtype() {
         return subtype;
     }

    public void setPhase_id(int phase_id) {
         this.phase_id = phase_id;
     }
     public int getPhase_id() {
         return phase_id;
     }

    public void setState(String state) {
         this.state = state;
     }
     public String getState() {
         return state;
     }

    public void setStarted_at(String started_at) {
         this.started_at = started_at;
     }
     public String getStarted_at() {
         return started_at;
     }

}