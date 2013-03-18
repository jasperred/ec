//2位小数验证,金额
Ext.apply(Ext.form.field.VTypes, {
	money :  function(v) {
        return /^[0-9]+(.[0-9]{1,2})?$/.test(v);
    },
    moneyText: '请输入数字，保留2位小数',
    moneyMask: /[\d\.]/i
});