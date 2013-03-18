function initCity(stateId, cityId, districtId) {
	var state = Ext.getCmp(stateId);
	var city = Ext.getCmp(cityId);
	var district = Ext.getCmp(districtId);
	var stateStore = state.getStore();
	var cityStore = city.getStore();
	var districtStore = district.getStore();
	// 初始化省份数据
	stateStore.removeAll();
	stateStore.add({
				"name" : "请选择省份",
				"value" : ""
			});
	stateStore.add(stateArray);
	var stateIndex;
	//省份选择
	state.on('select', function(combo, records, eOpts) {
				//清除城市和地区数据，重新加载
				cityStore.removeAll();
				districtStore.removeAll();
				cityStore.add({
							"name" : "请选择城市",
							"value" : ""
						});
				districtStore.add({
							"name" : "请选择地区",
							"value" : ""
						});
				city.setValue("");
				district.setValue("");
				stateIndex = stateStore.indexOf(records[0]);
				if (stateIndex <= 0)
					return;
				cityStore.add(cityArray[stateIndex - 1]);
			}, this);
	//城市选择
	city.on('select', function(combo, records, eOpts) {
				//清除地区数据，重新加载
				districtStore.removeAll();
				districtStore.add({
							"name" : "请选择地区",
							"value" : ""
						});

				var cityIndex = cityStore.indexOf(records[0]);
				district.setValue("");
				if (stateIndex <= 0 || cityIndex <= 0)
					return;
				districtStore.add(districtArray[stateIndex - 1][cityIndex - 1]);
			}, this);
}
//设置省市区的值
function setCityValue(stateId,stateValue, cityId,cityValue, districtId,districtValue)
{
	var state = Ext.getCmp(stateId);
	var city = Ext.getCmp(cityId);
	var district = Ext.getCmp(districtId);
	var stateStore = state.getStore();
	var cityStore = city.getStore();
	var districtStore = district.getStore();
	var stateIndex = stateStore.find('value',stateValue);
	state.setValue(stateValue);
	cityStore.removeAll();
	cityStore.add({
							"name" : "请选择城市",
							"value" : ""
						});
	if(stateIndex>=1)
	cityStore.add(cityArray[stateIndex - 1]);	
	var cityIndex = cityStore.find('value',cityValue);
	city.setValue(cityValue);
	districtStore.removeAll();
	districtStore.add({
							"name" : "请选择城市",
							"value" : ""
						});
	if(cityIndex>=1)
	districtStore.add(districtArray[stateIndex - 1][cityIndex - 1]);
	district.setValue(districtValue);
	
}
