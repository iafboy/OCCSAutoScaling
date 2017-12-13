package com.cncustompoc.SingletonSrvcs.domains;
import java.util.Date;
import java.util.Map;

public class Deployment {

    private String host_name;
    private Placement placement;
    private String created_by;
    private Date created_on;
    private Stack stack;
    private int creation_info_generation;
    private String deployment_name;
    private Map<String,Integer> quantities;
    private int desired_state;
    private String desired_state_changed_at;
    private int generation;
    private int current_state;
    private int pending_state;

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public void setPlacement(Placement placement) {
         this.placement = placement;
     }
     public Placement getPlacement() {
         return placement;
     }

    public void setCreated_by(String created_by) {
         this.created_by = created_by;
     }
     public String getCreated_by() {
         return created_by;
     }

    public void setCreated_on(Date created_on) {
         this.created_on = created_on;
     }
     public Date getCreated_on() {
         return created_on;
     }

    public void setStack(Stack stack) {
         this.stack = stack;
     }
     public Stack getStack() {
         return stack;
     }

    public void setCreation_info_generation(int creation_info_generation) {
         this.creation_info_generation = creation_info_generation;
     }
     public int getCreation_info_generation() {
         return creation_info_generation;
     }

    public void setDeployment_name(String deployment_name) {
         this.deployment_name = deployment_name;
     }
     public String getDeployment_name() {
         return deployment_name;
     }

    public void setQuantities(Map<String,Integer> quantities) {
         this.quantities = quantities;
     }
     public Map<String,Integer> getQuantities() {
         return quantities;
     }

    public void setDesired_state(int desired_state) {
         this.desired_state = desired_state;
     }
     public int getDesired_state() {
         return desired_state;
     }

    public void setDesired_state_changed_at(String desired_state_changed_at) {
         this.desired_state_changed_at = desired_state_changed_at;
     }
     public String getDesired_state_changed_at() {
         return desired_state_changed_at;
     }

    public void setGeneration(int generation) {
         this.generation = generation;
     }
     public int getGeneration() {
         return generation;
     }

    public void setCurrent_state(int current_state) {
         this.current_state = current_state;
     }
     public int getCurrent_state() {
         return current_state;
     }

    public void setPending_state(int pending_state) {
         this.pending_state = pending_state;
     }
     public int getPending_state() {
         return pending_state;
     }
}