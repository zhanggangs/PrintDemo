﻿// 用于将返回的json字符串转换成json对象
function json2obj(json) {
    var result = null;
    if (typeof (json) != "object") {
        result = eval('(' + json + ')');
    }
    else
    {
        result = json;
    }

    return result;
}

function setReadOnly(o) {
    $(o).textbox("readonly");
    $(o).addClass("estsh-readonly");
}

function unReadOnly(o) {
    $(o).textbox("readonly", false);
    $(o).removeClass("estsh-readonly");
}


$.extend($.fn.validatebox.defaults.rules, {
	EUC : {
		 validator: function (value) {
			 return /^[A-Z]+$/.test(value);
	     },
	     message: '请输入一位大写字母(A-Z)'
	},
    CHS: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
    english: {// 验证英语 
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    ip: {// 验证IP地址 
        validator: function (value) {
            return /\d+\.\d+\.\d+\.\d+/.test(value);
        },
        message: 'IP地址格式不正确'
    },
    ZIP: {
        validator: function (value, param) {
            return /^[0-9]\d{5}$/.test(value);
        },
        message: '邮政编码不存在'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    mobile: {
        validator: function (value, param) {
            //return /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
            return /^[1][34578][0-9]{9}$/.test(value);
        },
        message: '手机号码不正确'
    },
    tel: {
        validator: function (value, param) {
            return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?(-\d{1,6})?$/.test(value);
        },
        message: '电话号码不正确'
    },
    mobileAndTel: {
        validator: function (value, param) {
            return /(^([0\+]\d{2,3})\d{3,4}\-\d{3,8}$)|(^([0\+]\d{2,3})\d{3,4}\d{3,8}$)|(^([0\+]\d{2,3}){0,1}13\d{9}$)|(^\d{3,4}\d{3,8}$)|(^\d{3,4}\-\d{3,8}$)/.test(value);
        },
        message: '请正确输入电话号码'
    },
    number: {
        validator: function (value, param) {
            return /^[0-9]+.?[0-9]*$/.test(value);
        },
        message: '请输入数字'
    },
    money: {
        validator: function (value, param) {
            return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
        },
        message: '请输入正确的金额'

    },
    mone: {
        validator: function (value, param) {
            return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
        },
        message: '请输入整数或小数'

    },
    integer: {
        validator: function (value, param) {
            return /^[+]?[1-9]\d*$/.test(value);
        },
        message: '请输入最小为1的整数'
    },
    integ: {
        validator: function (value, param) {
            return /^[+]?[0-9]\d*$/.test(value);
        },
        message: '请输入整数'
    },
    range: {
        validator: function (value, param) {
            if (/^[1-9]\d*$/.test(value)) {
                return value >= param[0] && value <= param[1]
            } else {
                return false;
            }
        },
        message: '输入的数字在{0}到{1}之间'
    },
    minLength: {
        validator: function (value, param) {
            return value.length >= param[0]
        },
        message: '至少输入{0}个字'
    },
    maxLength: {
        validator: function (value, param) {
            return value.length <= param[0]
        },
        message: '最多{0}个字'
    },
    //select即选择框的验证 
    selectValid: {
        validator: function (value, param) {
            if (value == param[0]) {
                return false;
            } else {
                return true;
            }
        },
        message: '请选择'
    },
    idCode: {
        validator: function (value) {
        	return checkIDCard(value);
        },
        message: '请输入正确的身份证号'
    },
    loginName: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的字符不一至'
    },
    englishOrNum: {// 只能输入英文和数字 
        validator: function (value) {
            return /^[a-zA-Z0-9_ ]{1,}$/.test(value);
        },
        message: '请输入英文、数字、下划线或者空格'
    },
    xiaoshu: {
        validator: function (value) {
            return /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/.test(value);
        },
        message: '最多保留两位小数！'
    },
    ddPrice: {
        validator: function (value, param) {
            if (/^[1-9]\d*$/.test(value)) {
                return value >= param[0] && value <= param[1];
            } else {
                return false;
            }
        },
        message: '请输入1到100之间正整数'
    },
    jretailUpperLimit: {
        validator: function (value, param) {
            if (/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)) {
                return parseFloat(value) > parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
            } else {
                return false;
            }
        },
        message: '请输入0到100之间的最多俩位小数的数字'
    },
    rateCheck: {
        validator: function (value, param) {
            if (/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)) {
                return parseFloat(value) > parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
            } else {
                return false;
            }
        },
        message: '请输入0到1000之间的最多俩位小数的数字'
    },
    carNo: {
        validator: function (value) {
        	return /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/.test(value);
        },
        message: '请输入正确的车牌号(长度为7,字母必须为大写)'
    },
    emailAddress: {
        validator: function (value) {
        	return /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(value);
        },
        message: '请输入正确的邮箱地址'
    },
});


Date.prototype.format = function (fmt) {
    var o = {
        "Y+": this.getYear(),                   //年份 
        "M+": this.getMonth() + 1,                 //月份 
        "d+": this.getDate(),                    //日 
        "h+": this.getHours(),                   //小时 
        "m+": this.getMinutes(),                 //分 
        "s+": this.getSeconds(),                 //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds()             //毫秒 
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

//函数参数必须是字符串，因为二代身份证号码是十八位，而在javascript中，十八位的数值会超出计算范围，造成不精确的结果，导致最后两位和计算的值不一致，从而该函数出现错误。
//详情查看javascript的数值范围
function checkIDCard(idcode){
  // 加权因子
  var weight_factor = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
  // 校验码
  var check_code = ['1', '0', 'X' , '9', '8', '7', '6', '5', '4', '3', '2'];

  var code = idcode + "";
  var last = idcode[17];// 最后一个

  var seventeen = code.substring(0,17);
  // ISO 7064:1983.MOD 11-2
  // 判断最后一位校验码是否正确
  var arr = seventeen.split("");
  var len = arr.length;
  var num = 0;
  for(var i = 0; i < len; i++){
      num = num + arr[i] * weight_factor[i];
  }
  
  // 获取余数
  var resisue = num%11;
  var last_no = check_code[resisue];

  // 格式的正则
  // 正则思路
  /*
		 * 第一位不可能是0 第二位到第六位可以是0-9 第七位到第十位是年份，所以七八位为19或者20
		 * 十一位和十二位是月份，这两位是01-12之间的数值 十三位和十四位是日期，是从01-31之间的数值 十五，十六，十七都是数字0-9
		 * 十八位可能是数字0-9，也可能是X
		 */
  var idcard_patter = /^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$/;
  // 判断格式是否正确
  var format = idcard_patter.test(idcode);
  // 返回验证结果，校验码和格式同时正确才算是合法的身份证号码
  return last === last_no && format ? true : false;
}

function getA4Printer(){
	var printer = getCookie("A4Printer");
	if (printer == "") {
		$.messager.prompt("打印机设置", "请输入A4打印机名称？", function(data) {
			if (data) {
				setcookie("A4Printer", data);
				printer = getCookie("A4Printer");
			}
		});
	}
	return printer;
}

function getLabelPrinter(){
	var printer = getCookie("LabelPrinter");
	if (printer == "") {
		$.messager.prompt("打印机设置", "请输入条码打印机名称？", function(data) {
			if (data) {
				setcookie("LabelPrinter", data);
				printer = getCookie("LabelPrinter");
			}
		});
	}
	return printer;
}

function getBoxPrinter(){
	var printer = getCookie("BoxPrinter");
	if (printer == "") {
		$.messager.prompt("打印机设置", "请输入盒条码打印机名称？", function(data) {
			if (data) {
				setcookie("BoxPrinter", data);
			}
		});
	}
	return printer;
}

function getCasePrinter(){
	var printer = getCookie("CasePrinter");
	if (printer == "") {
		$.messager.prompt("打印机设置", "请输入箱条码打印机名称？", function(data) {
			if (data) {
				setcookie("CasePrinter", data);
			}
		});
	}
	return printer;
}

function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}

function setcookie(name, value) {
	//设置名称为name,值为value的Cookie
	//var expdate = new Date(); //初始化时间
	//expdate.setTime(expdate.getTime() + 200 * 60 * 60 * 1000); //时间
	//document.cookie = name + "=" + value + ";expires="
	//		+ expdate.toGMTString() + ";path=/";
	document.cookie = name + "=" + value + ";path=/";
	//即document.cookie= name+"="+value+";path=/";   时间可以不要，但路径(path)必须要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！~
}

/**
 * 深度拷贝js对象
 * @param source
 * @returns {*}
 */
var objDeepCopy = function (source) {
    var sourceCopy = source instanceof Array ? [] : {};
    for (var item in source) {
        if(source.hasOwnProperty(item)) {
            sourceCopy[item] = typeof source[item] === 'object' ? objDeepCopy(source[item]) : source[item];
        }
    }
    return sourceCopy;
}
// 判断为空
var commonIsNull = function (obj) {
    return obj == null || obj == undefined || obj == '';
}