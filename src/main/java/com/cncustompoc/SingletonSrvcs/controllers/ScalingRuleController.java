package com.cncustompoc.SingletonSrvcs.controllers;

import com.alibaba.fastjson.JSON;
import com.cncustompoc.SingletonSrvcs.domains.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ScalingRuleController {
    Logger logger = LoggerFactory.getLogger(ScalingRuleController.class);
    @Autowired
    private DockerScalingController dockerScalingController;

    private DeploymentEntity deploymentEntity;
    private static Map<String,ScalingRule> scalingRuleMap=new HashMap<String,ScalingRule>();
	
    @RequestMapping(value = "/ScalingRule/{InstanceID}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
    public ScalingRule getScalingRule(@PathVariable(value = "InstanceID") String deploymentId) {
        ScalingRule scalingRule=initScalingRule(deploymentId,null);
        return scalingRule;
    }

    @RequestMapping(value = "/ScalingRule", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
    public ScalingRule updateScalingRule(@Valid @RequestBody ScalingRule scalingRule_) {
        logger.debug("updated ScalingRule: " + JSON.toJSONString(scalingRule_));
        return this.initScalingRule(scalingRule_.getDeploymentid(),scalingRule_);
    }

    private ScalingRule initScalingRule(String deploymentId,ScalingRule newRule) {
        if(newRule!=null){
            scalingRuleMap.put(deploymentId,newRule);
            return newRule;
        }
        if(deploymentId==null||"".equals(deploymentId))return null;
        ScalingRule scalingRule = new ScalingRule();
        scalingRule.setDeploymentid(deploymentId);
        deploymentEntity = dockerScalingController.getDeployFileTemplate_(deploymentId);
        logger.debug("get DeploymentEntity：\n " + JSON.toJSONString(deploymentEntity));
        if (deploymentEntity != null) {
            Map<String, Integer> currentQuantities = deploymentEntity.getDeployment().getQuantities();
            Set<String> keys = currentQuantities.keySet();
            for (String key : keys) {
                if (scalingRule.getModules()!=null&&scalingRule.getModules().containsKey(key)) {
                    scalingRule.getModules().get(key).setQuantity(currentQuantities.get(key).intValue());
                } else {
                    ModuleScalingRule msr = new ModuleScalingRule();
                    msr.setQuantity(currentQuantities.get(key).intValue());
                    if(scalingRule.getModules()==null){
                        scalingRule.setModules(new HashMap<String,ModuleScalingRule>());
                    }
                    scalingRule.getModules().put(key, msr);
                }
            }
        }
        logger.debug("ScalingRule: " + JSON.toJSONString(scalingRule));
        scalingRuleMap.put(deploymentId,scalingRule);
        return scalingRule;
    }

    private int updateDeployment(final String deploymentId) {
        //图方便不写deep clone了
            DeploymentEntity de = JSON.parseObject(JSON.toJSONString(deploymentEntity), DeploymentEntity.class);
            return dockerScalingController.updateDeployScaling(deploymentId, de) ? 1 : -1;
    }

    //返回-2没有数据传入，-1伸缩指令发出失败，1伸缩指令发出成功
    @RequestMapping(value = "/InstanceStatus", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public int updateDeployScaling(@Valid @RequestBody ContainerStatus message) {
        if (message == null) return -2;
        logger.debug("message: "+message.toString());
        Set<String> deploymentIds=scalingRuleMap.keySet();
        for(String deploymentId:deploymentIds) {
            if (ifTriggered(message, (ScalingRule)scalingRuleMap.get(deploymentId))) {
                logger.info(deploymentId+" triggered scaling");
                updateDeployment(deploymentId);
            } else {
                logger.info("nothing to do");
            }
        }
        return 1;
    }

    private boolean ifTriggered(ContainerStatus statusMsg,ScalingRule scalingRule) {
        if (statusMsg.getStatusMsg() != null && !"".equals(statusMsg.getStatusMsg().trim())&&statusMsg.getDeployment_id()!=null&&!"".equals(statusMsg.getDeployment_id())) {
            Map<String, ContainerCurrentStatus> currentStatus = anaysisStatusMessage(statusMsg.getStatusMsg());
            ContainersEntity all_containers = dockerScalingController.getDeploymentContainer(statusMsg.getDeployment_id());
            if(deploymentEntity==null){
                deploymentEntity=dockerScalingController.getDeployFileTemplate_(statusMsg.getDeployment_id());
            }
            Map<String, List<String>> containers = getContainersByServiceGroups(all_containers);
            //按service
            Set<String> keys=containers.keySet();
            for(String serviceid:keys) {
                List<String> cids=containers.get(serviceid);
                for(String cid:cids) {
                    if(currentStatus.containsKey(cid.substring(0,12))) {
                        ContainerCurrentStatus ccstatus=currentStatus.get(cid.substring(0,12));
                        logger.info(JSON.toJSONString(ccstatus)+"|"+scalingRule.getCpuMaxThreadshold()+"|"+scalingRule.getCpuMinThreadshold()+"|"+scalingRule.getMemMaxThreadshold()+"|"+scalingRule.getMemMinThreadshold());
                        //判断是否到达上限
                        if(ccstatus.getCpuUsage()>scalingRule.getCpuMaxThreadshold()&&ccstatus.getMemUsage()>scalingRule.getMemMaxThreadshold()) {
                            logger.info("deploymentEntity "+serviceid);
                            //更新deployment
                            if(deploymentEntity!=null&&deploymentEntity.getDeployment()!=null&&deploymentEntity.getDeployment().getQuantities()!=null) {
                                Integer currentValue = deploymentEntity.getDeployment().getQuantities().get(serviceid);
                                if ((currentValue!=null)&&
                                        (scalingRule.getModules()!=null)&&
                                        (!scalingRule.getModules().isEmpty())&&
                                        (scalingRule.getModules().get(serviceid)!=null)&&
                                        ((currentValue.intValue() + 1) <= scalingRule.getModules().get(serviceid).getMaxQuantity())) {
                                    deploymentEntity.getDeployment().getQuantities().put(serviceid, new Integer(currentValue.intValue() + 1));
                                    logger.info("update serviceid[" + serviceid + "] instance number to " + deploymentEntity.getDeployment().getQuantities().get(serviceid).intValue());
                                    return true;
                                }
                            }
                        }
                        if(ccstatus.getCpuUsage()<scalingRule.getCpuMinThreadshold()&&ccstatus.getMemUsage()<scalingRule.getCpuMinThreadshold()){
                            //判断是否到达下限
                            //更新deployment
                            if(deploymentEntity!=null&&deploymentEntity.getDeployment()!=null&&deploymentEntity.getDeployment().getQuantities()!=null) {
                                Integer currentValue = deploymentEntity.getDeployment().getQuantities().get(serviceid);
                                if ((currentValue!=null)&&
                                        (scalingRule.getModules()!=null)&&
                                        (!scalingRule.getModules().isEmpty())&&
                                        (scalingRule.getModules().get(serviceid)!=null)&&
                                        ((currentValue.intValue() - 1) >= scalingRule.getModules().get(serviceid).getMinQuantity())) {
                                    deploymentEntity.getDeployment().getQuantities().put(serviceid, new Integer(currentValue.intValue() - 1));
                                    logger.info("update serviceid[" + serviceid + "] instance number to " + deploymentEntity.getDeployment().getQuantities().get(serviceid).intValue());
                                    ;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private Map<String, ContainerCurrentStatus> anaysisStatusMessage(String statusMsg) {
        Map<String, ContainerCurrentStatus> result = new HashMap<String, ContainerCurrentStatus>();
        try {
            String[] entitys = statusMsg.split(";");
            for (String entity : entitys) {
                String[] items = entity.split("-");
                if(items.length!=3) continue;
                ContainerCurrentStatus ccs = new ContainerCurrentStatus();
                ccs.setCpuUsage(Double.parseDouble(items[1].trim()));
                ccs.setMemUsage(Double.parseDouble(items[2].trim()));
                result.put(items[0].trim().substring(0,12), ccs);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    private Map<String, List<String>> getContainersByServiceGroups(ContainersEntity all_containers) {
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        for(Container container:all_containers.getContainers()){
            if(result.containsKey(container.getService_id())){
                result.get(container.getService_id()).add(container.getContainer_id().substring(0,12));
            }else{
                List<String> containerids=new ArrayList<String>();
                containerids.add(container.getContainer_id().substring(0,12));
                result.put(container.getService_id(),containerids);
            }
        }
        return result;
    }
}
