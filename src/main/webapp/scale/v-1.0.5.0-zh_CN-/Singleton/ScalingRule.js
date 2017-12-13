define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	var Model = function(){
		this.callParent();
	};

	Model.prototype.modelLoad = function(event){

	};

		var arr = [
		"auth-mongodb",
		"cottonquotas-service",
		"statistics-service",
		"account-mongodb",
		"declaration-service",
		"cncustom-registry",
		"license-service",
		"monitoring",
		"goodsinfo-service",
		"manifestinfo-service",
		"notification-service",
		"importgoodsfreetax-service",
		"auth-service",
		"statistics-mongodb",
		"cncustom-rabbitmq",
		"cncustom-config",
		"notification-mongodb",
		"zipkin-server",
		"account-service",
		"gateway",
		"empty-service"
		];
	Model.prototype.windowReceiverReceive = function(event){
		var sd = this.comp("sd");
		var row = sd.add();
		row.assign(event.data);
		var did = sd.getValue("col0");
		var cd = this.comp("cd");
		var self = this;
			$.ajax({
				url: '/ScalingRule/'+did,
				datatype: 'json',
				type: 'get',
				contentType: 'application/json;charset=UTF-8',
				async: false,
				success: function (data) {
//					alert("ok");
//					alert(JSON.stringify(data));
					//console.log(data);
					self.comp("cpuMaxThreadshold").val(data.cpuMaxThreadshold);
					self.comp("memMaxThreadshold").val(data.memMaxThreadshold);
					self.comp("cpuMinThreadshold").val(data.cpuMinThreadshold);
					self.comp("memMinThreadshold").val(data.memMinThreadshold);
//					self.comp("input1-auth-mongodb").val(data.modules["auth-mongodb"].quantity);
//					self.comp("input2-auth-mongodb").val(data.modules["auth-mongodb"].minQuantity);
//					self.comp("input3-auth-mongodb").val(data.modules["auth-mongodb"].maxQuantity);
					for(var i=0;i<arr.length;i++)
					{
						cd.newData();
						cd.setValue("modulesName",arr[i]);
						cd.setValue("quantity",data.modules[arr[i]].quantity);
						cd.setValue("minQuantity",data.modules[arr[i]].minQuantity);
						cd.setValue("maxQuantity",data.modules[arr[i]].maxQuantity);
					}
				},
				error: function (err) {
					alert("DeploymentID未找到");
				}
			});
		
	};

	Model.prototype.button2Click = function(event){
		this.comp("windowReceiver").windowCancel();
	};

//	{
//  "deploymentid": "cncustompoc-20171018-191336",
//  "cpuMaxThreadshold": 90,
//  "memMaxThreadshold": 90,
//  "cpuMinThreadshold": 5,
//  "memMinThreadshold": 5,
//  "modules": {
//    "auth-mongodb": {
//      "quantity": 1,
//      "minQuantity": 1,
//      "maxQuantity": 10
//    },
//		.......
//  }
//}

	Model.prototype.okBtnClick = function(event){
		var self = this;
		var cd = this.comp("cd");
		var sd = this.comp("sd");
		var md = "{";
		cd.each(function(row){
			var r = row.row;
			md+="\""+r.val("modulesName")+"\":{\"quantity\":"+r.val("quantity")+",\"minQuantity\":"+r.val("minQuantity")+",\"maxQuantity\":"+r.val("maxQuantity")+"},";
		});
		md=md.substring(0, md.length-1);
		md+="}";
		debugger;
		var sd={
				"deploymentid":sd.getValue("col0"),
				 "cpuMaxThreadshold": self.comp("cpuMaxThreadshold").val(),
				  "memMaxThreadshold": self.comp("memMaxThreadshold").val(),
				  "cpuMinThreadshold": self.comp("cpuMinThreadshold").val(),
				  "memMinThreadshold": self.comp("memMinThreadshold").val(),
				  "modules":JSON.parse(md)
		};
		console.log(sd);
			$.ajax({
				url: '/ScalingRule',
				datatype: 'json',
				type: 'post',
				data:JSON.stringify(sd),
				contentType: 'application/json;charset=UTF-8',
				async: false,
				success: function (data) {
					alert("ok");
					self.comp("windowReceiver").windowCancel();
				},
				error: function (err) {
					alert(JSON.stringify(err));
				}
			});
	};

	return Model;
});