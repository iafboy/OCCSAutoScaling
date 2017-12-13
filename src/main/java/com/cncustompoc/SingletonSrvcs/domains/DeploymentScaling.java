package com.cncustompoc.SingletonSrvcs.domains;
import java.util.Date;
import java.util.Map;

public class DeploymentScaling {

    private String deployment_id;
    private Placement placement;
    private Stack stack;
    private String deployment_name;
    private Map<String,Integer> quantities;
    private int desired_state;

    public String getDeployment_id() {
        return deployment_id;
    }

    public void setDeployment_id(String deployment_id) {
        this.deployment_id = deployment_id;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public String getDeployment_name() {
        return deployment_name;
    }

    public void setDeployment_name(String deployment_name) {
        this.deployment_name = deployment_name;
    }

    public Map<String, Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(Map<String, Integer> quantities) {
        this.quantities = quantities;
    }

    public int getDesired_state() {
        return desired_state;
    }

    public void setDesired_state(int desired_state) {
        this.desired_state = desired_state;
    }
}