define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	var Model = function(){
		this.callParent();
	};

	Model.prototype.button1Click = function(event){
			var sd = this.comp("sd");
			var mainpath = sd.getValue("col0");
			if(!mainpath)
			{
				alert("请输入DeploymentID");
				return;
			}
			var nw = this.comp("ScalingRuleDialog");
			var row = sd.getCurrentRow();
		
			if (row) {
				nw.open({
					data : row
				});
			}
		};

	Model.prototype.modelLoad = function(event){
			var sd = this.comp("sd");
			sd.newData();
			sd.setValue("col0","");
	};

	return Model;
});