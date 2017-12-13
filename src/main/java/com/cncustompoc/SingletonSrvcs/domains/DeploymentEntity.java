package com.cncustompoc.SingletonSrvcs.domains;
public class DeploymentEntity {

    private Deployment deployment;
    public void setDeployment(Deployment deployment) {
         this.deployment = deployment;
     }
     public Deployment getDeployment() {
         return deployment;
     }

    @Override
    public String toString() {
        return "DeploymentEntity{" +
                "deployment=" + deployment +
                '}';
    }
}