package com.cncustompoc.SingletonSrvcs.domains;

import java.util.Map;

public class ScalingRule {
    private String deploymentid;
    private double cpuMaxThreadshold=90.0;
    private double memMaxThreadshold=90.0;
    private double cpuMinThreadshold=5.0;
    private double memMinThreadshold=5.0;
    private Map<String,ModuleScalingRule> modules;

    public String getDeploymentid() {
        return deploymentid;
    }

    public void setDeploymentid(String deploymentid) {
        this.deploymentid = deploymentid;
    }

    public double getCpuMaxThreadshold() {
        return cpuMaxThreadshold;
    }

    public void setCpuMaxThreadshold(double cpuMaxThreadshold) {
        this.cpuMaxThreadshold = cpuMaxThreadshold;
    }

    public double getMemMaxThreadshold() {
        return memMaxThreadshold;
    }

    public void setMemMaxThreadshold(double memMaxThreadshold) {
        this.memMaxThreadshold = memMaxThreadshold;
    }

    public double getCpuMinThreadshold() {
        return cpuMinThreadshold;
    }

    public void setCpuMinThreadshold(double cpuMinThreadshold) {
        this.cpuMinThreadshold = cpuMinThreadshold;
    }

    public double getMemMinThreadshold() {
        return memMinThreadshold;
    }

    public void setMemMinThreadshold(double memMinThreadshold) {
        this.memMinThreadshold = memMinThreadshold;
    }

    public Map<String, ModuleScalingRule> getModules() {
        return modules;
    }

    public void setModules(Map<String, ModuleScalingRule> modules) {
        this.modules = modules;
    }
}
