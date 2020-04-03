var iframeControlObj = function(option) {
    this.option = {
        muteAnswer: "false",
        soundAnswer: "false",

    };
    this.option = $.extend(option, this.option);
    // 配置对象
    this.elevationFree = false;
    this.setSelectData = false; // 发送位置报告1成功
    this.standingPoint = false; // 设置站立点成功
    this.init(); // 初始化
    this.scjres = {};
    this.scjAllContent = {

    }

    // F1和F2需要跳转的变量
    // 系统          
    this.machineControlIndex = 0; // 首页
    this.systemIndex = 0; // 系统首页
    this.systemWatchIndex = 0; // 系统查看
    this.thisMachineIndex = 0; // 本机信息
    this.systemSettingsIndex = 0; // 系统设置
    this.messageIndex = 0; // 通讯
    this.locationIndex = 0; // 定位
    this.locationRDSSIndex = 0; // RDSS定位
    this.saveRoadSignsIndex = 0; // 保存路标
    this.locationRNSSIndex = 0; // RNSS定位
    this.saveRoadSignsIndex2 = 0; // RNSS保存路标

    this.newsArr = []; // 已发送电文
    this.draftIndex = 0; // 草稿箱
    this.draftText = '2222'; // 草稿箱传给编辑页标题
    this.draftTime = '2020-01-06'; // 编辑页时间
    this.editPageIndex = 0; // 编辑页
}
iframeControlObj.prototype.initOption = function() {
    this.machineControlIndex = 0; // 首页
    this.systemIndex = 0; // 系统首页
    this.systemWatchIndex = 0; // 系统查看
    this.thisMachineIndex = 0; // 本机信息
    this.systemSettingsIndex = 0; // 系统设置
    this.messageIndex = 0; // 通讯
    this.locationIndex = 0; // 定位
    this.locationRDSSIndex = 0; // RDSS定位
    this.saveRoadSignsIndex = 0; // 保存路标
    this.locationRNSSIndex = 0; // RNSS定位
    this.saveRoadSignsIndex2 = 0; // RNSS保存路标
}



// 初始化
iframeControlObj.prototype.init = function() {
    this.getNowTime(); // 获取当前时间
    this.getIframeList(); // iframe映射数据
    this.allEvent(); // 所有事件函数
}

// 所有事件函数
iframeControlObj.prototype.allEvent = function() {
    this.clickHorn(); // 点击音量图标
}

// iframe映射数据
iframeControlObj.prototype.getIframeList = function() {
    var iframeList = this.option.iframeList;
    var arr = [];
    for (var i = 0; i < iframeList.length; i++) {
        arr[iframeList[i].name] = {
            src: iframeList[i].src,
            beforeSrc: iframeList[i].beforeSrc,
            xId: iframeList[i].xId
        }
    }
    this.option.iframeMapper = arr;
}

// 获取当前时间
iframeControlObj.prototype.getNowTime = function() {
    t = setTimeout(time, 0); //開始运行
    function time() {
        clearTimeout(t); //清除定时器
        dt = new Date();
        var h = dt.getHours(); //获取时
        var m = dt.getMinutes(); //获取分
        var s = dt.getSeconds(); //获取秒

        h < 10 ? h = '0' + h : h = h;
        m < 10 ? m = '0' + m : m = m;
        s < 10 ? s = '0' + s : s = s;

        document.getElementById("machine-time").innerHTML = h + ":" + m + ":" + s;
        t = setTimeout(time, 1000); //设定定时器，循环运行     
    }
}

// iframe 切换页面
iframeControlObj.prototype.changeIframe = function(name) {
    var _this = this;
    if (_this.option.iframeMapper[name] && _this.option.iframeMapper[name].src) {
        $('#machine-view-iframe').attr({ "src": _this.option.iframeMapper[name].src, "x-id": _this.option.iframeMapper[name].xId });
    }
}

// 返回
iframeControlObj.prototype.goBackBefore = function(name) {

    var backSrc = this.option.iframeMapper[name].beforeSrc;
    console.log(backSrc);
    if (backSrc != "") {
        $('#machine-view-iframe').attr('src', backSrc);
    }
    if (backSrc == "") {
        this.backSrc == './machine-control-list.html';
        console.log(this.backSrc);
        $('#machine-view-iframe').attr('src', this.backSrc);
    }
}

iframeControlObj.prototype.clickHorn = function() {
    var _this = this;
    $('#horn-box').on('click', function() {
        _this.changeIframe('声音设置');
    });

}


// 静音
iframeControlObj.prototype.changeMute = function(item) {
    item == 1 ? $('#horn-box').addClass('active') : $('#horn-box').removeClass('active')
}

// 校准屏幕
iframeControlObj.prototype.showScreen = function() {
    $('#screen').addClass('active');
}

// 隐藏校准屏幕弹窗
iframeControlObj.prototype.hideScreen = function() {
    $('#screen').removeClass('active');
}

// 校准屏幕的确认和返回
iframeControlObj.prototype.screenSure = function() {
    var _this = this;
    $('#sure').on('click', function() {
        _this.hideScreen();
    });
}

iframeControlObj.prototype.screenCancle = function() {
    var _this = this;
    $('#close').on('click', function() {
        _this.hideScreen();
    });
}

// 无高程
iframeControlObj.prototype.setElevationFree = function() {
    this.elevationFree = true;
}

// 设置位置报告
iframeControlObj.prototype.setSelect = function(index) {
    if (index == 1) {
        window.parent.window.newObj.option.sendLocationAnswer = "true";
    } else {
        window.parent.window.newObj.option.sendLocationAnswer = "false";
    }
}

// 定位保存
iframeControlObj.prototype.setLocation = function(item) {
        if (item == '站立点') {
            window.parent.window.newObj.option.resetNameAnswer = "true";
        }
    }
    //手持机填入内容
iframeControlObj.prototype.scjContent = function(res) {
    _this = this;
    _this.scjres = res;
    _this.scjAllContent.bjid = res.bjid.value;
    _this.scjAllContent.txdj = res.txdj.value;
    _this.scjAllContent.fwpd = res.fwpd.value;
    _this.scjAllContent.xlh = res.xlh.value;
    _this.scjAllContent.RDSSddzbX = res.RDSSddzbX.value;
    _this.scjAllContent.RDSSddzbY = res.RDSSddzbY.value;
    _this.scjAllContent.RDSSddzbgc = res.RDSSddzbgc.value;
    _this.scjAllContent.RDSSgszbX = res.RDSSgszbX.value;
    _this.scjAllContent.RDSSgszbY = res.RDSSgszbY.value;
    _this.scjAllContent.RDSSgszbgc = res.RDSSgszbgc.value;
    _this.scjAllContent.RDSSkjzbX = res.RDSSkjzbX.value;
    _this.scjAllContent.RDSSkjzbY = res.RDSSkjzbY.value;
    _this.scjAllContent.RDSSkjzbZ = res.RDSSkjzbZ.value;
    _this.scjAllContent.RNSSddzbX = res.RNSSddzbX.value;
    _this.scjAllContent.RNSSddzbY = res.RNSSddzbY.value;
    _this.scjAllContent.RNSSddzbgc = res.RNSSddzbgc.value;
    _this.scjAllContent.RNSSgszbX = res.RNSSgszbX.value;
    _this.scjAllContent.RNSSgszbY = res.RNSSgszbY.value;
    _this.scjAllContent.RNSSgszbgc = res.RNSSgszbgc.value;
    _this.scjAllContent.RNSSkjzbX = res.RNSSkjzbX.value;
    _this.scjAllContent.RNSSkjzbY = res.RNSSkjzbY.value;
    _this.scjAllContent.RNSSkjzbZ = res.RNSSkjzbZ.value;
    _this.scjAllContent.RDSSmktzbX = res.RDSSmktzbX.value;
    _this.scjAllContent.RDSSmktzbY = res.RDSSmktzbY.value;
    _this.scjAllContent.RDSSmktzbgc = res.RDSSmktzbZ.value;
    _this.scjAllContent.RNSSmktzbX = res.RNSSmktzbX.value;
    _this.scjAllContent.RNSSmktzbY = res.RNSSmktzbY.value;
    _this.scjAllContent.RNSSmktzbgc = res.RNSSmktzbZ.value;
}