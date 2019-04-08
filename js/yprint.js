/**
 *  注意事项: 1. 在加载该js文件之前需要先加载common.js,因为该js中引用了common.js中的getCookie()方法
 *           2. 在调用该打印方法之前,需要先在cookie中储存连接websocket的IP(YHPRINT_IP)和端口(YHPRINT_PORT)
 *
 **/


(function (win) {
    let YHPRINT = {
        PageData: {},
        ItemData: new Array(),
        webskt: null,
        SocketOpened: false,
        iTrySendTimes: 0,
        DoInit:
            function () {
                this.PageData = {};
            },
        OpenWebSocket:
            function () {
                //检查浏览器是否支持WebSocket
                if (!window.WebSocket) {
                    console.log('当前浏览器不支持WebSocket');
                }
                try {
                    if (!this.webskt || this.webskt.readyState == 3) {
                        //从cookie中获取IP和PORT
                        let ip = getCookie('YHPRINT_IP');
                        let port = getCookie('YHPRINT_PORT');
                        if (ip == undefined || port == undefined || ip == '' || port == '') {
                            ip = '127.0.0.1';
                            port = '30000';
                        }
                        this.webskt = new WebSocket('ws://' + ip + ':' + port + '/websocket');
                        this.webskt.onopen = function (e) {
                            YHPRINT.SocketOpened = true;
                        };
                        // 为onmessage事件绑定监听器，接收消息
                        this.webskt.onmessage = function (event) {
                            // 接收消息
                            console.log("收到的消息是：" + event.data)
                        }
                        this.webskt.onclose = function (e) {
                            if (!YHPRINT.SocketOpened) {
                                return;
                            }
                            setTimeout(YHPRINT.OpenWebSocket(), 2000);
                        };
                        this.webskt.onerror = function (e) {
                        };
                    }
                } catch (err) {
                    console.log("websocket连接失败,请检查cookie中YHPRINT_IP和YHPRINT_PORT是否配置正确");
                    this.webskt = null;
                    setTimeout(YHPRINT.OpenWebSocket(), 2000);
                }
            },
        wsSend:
            function (strData) {
                if (this.webskt && this.webskt.readyState == 1) {
                    this.iTrySendTimes = 0;
                    this.webskt.send(strData);
                    return true;
                } else {
                    this.iTrySendTimes++;
                    if (this.iTrySendTimes <= 1) {
                        setTimeout(YHPRINT.wsSend(strData), 500);
                    } else {
                        this.OpenWebSocket();
                    }
                }
            },
        createDataString:
            function () {
                this.PageData["ItemData"] = this.ItemData;
                return this.PageData;
            },
        //初始化运行环境，清理异常打印遗留的系统资源，设定打印任务名。
        PRINT_INIT:
            function (strPrintTask) {
                this.DoInit();
                if (strPrintTask !== undefined && strPrintTask !== null) this.PageData["strPrintTask"] = strPrintTask;
                return true;
            },
        //设置打印方向和纸张大小(页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点)
        /*
        * intOrient的值
        * 0: 原点在纸的左下角，x从下到上，y从左到右。
        * 1: 原点在纸的左上角，x向右，y向下。
        * 2: 原点在纸的右上角，x *从上到下，y从右到左。
        * */
        SET_PRINT_PAGESIZE:
            function (intOrient, pageWidth, pageHeight, strPageName) {
                if (intOrient !== undefined && intOrient !== null) this.PageData["orient"] = intOrient;
                if (pageWidth !== undefined && pageWidth !== null) this.PageData["pageWidth"] = pageWidth;
                if (pageHeight !== undefined && pageHeight !== null) this.PageData["pageHeight"] = pageHeight;
                if (strPageName !== undefined && strPageName !== null) this.PageData["pageName"] = strPageName;
            },
        //设置要使用的打印机名称
        SET_PRINTER_INDEXA:
            function (printerName) {
                if (printerName !== undefined && printerName !== null) this.PageData["printerName"] = printerName;
            },
        //设置打印类型(LABEL A4)
        SET_PRINT_TYPE:
            function (printType) {
                if (printType !== undefined && printType !== null) this.PageData["printType"] = printType;
            },
        //设置打印份数(暂无法使用)
        SET_COPIES:
            function (copies) {
                this.PageData["copies"] = copies;
            },
        // 添加打印条形码
        // barCode 条码内容
        // x 条码x轴坐标
        // y 条码y轴坐标
        ADD_BARCODE:
            function (barCode, x, y, width, height, moduleWidth, dpi, pageNum) {
                return this.AddItemArray("BARCODE", x, y, width, height, barCode, moduleWidth, dpi, pageNum);
            },
        //添加打印内容
        ADD_TEXT:
            function (text, x, y, width, height, fontName, fontSize) {
                return this.AddItemArray("TEXT", x, y, width, height, text, null, null, null, fontName, fontSize);
            },
        AddItemArray:
            function (type, x, y, width, height, strContent, moduleWidth, dpi, pageNum, fontName, fontSize) {
                if (x === undefined || y === undefined || width === undefined || height === undefined || strContent === undefined) {
                    return false;
                }
                let oneItem = {};
                oneItem["type"] = type;
                oneItem["x"] = x;
                oneItem["y"] = y;
                oneItem["width"] = width;
                oneItem["height"] = height;
                if ((strContent !== undefined) && (strContent != null)) oneItem["strContent"] = strContent;
                if ((moduleWidth !== undefined) && (moduleWidth != null)) oneItem["moduleWidth"] = moduleWidth;
                if ((dpi !== undefined) && (dpi != null)) oneItem["dpi"] = dpi;
                if ((fontName !== undefined) && (fontName != null)) oneItem["fontName"] = fontName;
                if ((fontSize !== undefined) && (fontSize != null)) oneItem["fontSize"] = fontSize;
                if ((pageNum !== undefined) && (pageNum != null)) oneItem["pageNum"] = pageNum;
                this.ItemData.push(oneItem);
                return true;
            },
        //添加html
        ADD_HTML:
            function (html) {
                if (this.PageData["html"] == undefined) {
                    this.PageData["html"] = "";
                }
                this.PageData["html"] += html;
            },
        //添加页眉
        ADD_HTML_HEADER:
            function (headerText) {
                if (this.PageData["html"] == undefined) {
                    this.PageData["html"] = "";
                }
                this.PageData["html"] += "<div class='header1'>" + headerText + "</div>";
            },
        //添加页脚
        ADD_HTML_FOOTER:
            function (footerText) {
                if (this.PageData["html"] == undefined) {
                    this.PageData["html"] = "";
                }
                this.PageData["html"] += "<div class='footer'>" + footerText + "</div>";
            },
        //添加新页面
        NEWPAGEA:
            function () {
                this.PageData["html"] += "<div class='pageNext'></div>";
            },
        //执行打印
        PRINT:
            function () {
                let strData = this.createDataString();
                return this.wsSend(JSON.stringify(strData));
                this.PageData["html"] = "";
            }
    };

    win.YHPRINT2019_0304 = YHPRINT;
    setTimeout(YHPRINT.OpenWebSocket(), 1000);
})(window);


//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
}

//关闭WebSocket连接
function closeWebSocket() {
    YHPRINT.webskt.close();
}

function getYPrint() {
    return window.YHPRINT2019_0304;
}
