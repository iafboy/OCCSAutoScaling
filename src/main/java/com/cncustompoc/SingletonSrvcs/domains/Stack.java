package com.cncustompoc.SingletonSrvcs.domains;

public class Stack {

    private String service_id;
    private String service_name;
    private String subtype;
    private String content;
    public void setService_id(String service_id) {
         this.service_id = service_id;
     }
     public String getService_id() {
         return service_id;
     }

    public void setService_name(String service_name) {
         this.service_name = service_name;
     }
     public String getService_name() {
         return service_name;
     }

    public void setSubtype(String subtype) {
         this.subtype = subtype;
     }
     public String getSubtype() {
         return subtype;
     }

    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

}