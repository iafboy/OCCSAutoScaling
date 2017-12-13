package com.cncustompoc.SingletonSrvcs.domains;

public class ContainerStatus {
    private String deployment_id;
    private String statusMsg;

    public String getDeployment_id() {
        return deployment_id;
    }

    public void setDeployment_id(String deployment_id) {
        this.deployment_id = deployment_id;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg_) {
        statusMsg = statusMsg_;
    }

    @Override
    public String toString() {
        return "ContainerStatus{" +
                "deployment_id='" + deployment_id + '\'' +
                ", statusMsg='" + statusMsg + '\'' +
                '}';
    }
}
