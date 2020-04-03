var machineObj = function(option) {
    this.option = {
        testJsonList: [], // 试题及答案JSON
        questionsList: {
            data: []
        }, // 考试题
        answerList: [], // 答案
        staringAnswer: "false", // 开机答案
        muteAnswer: "false", // 静音
        soundAnswer: "false", // 全音
        highLightAnswer: "false", // 高亮
        alwaysAnswer: "false", // 常亮}
        noElevationAnswer: "false", // 无高程
        resetNameAnswer: "false", // 重命名站立点
        sendLocationAnswer: "false", // 发送位置 
        examId: '', // 考试卷子id号,
        examName: '', // 考试卷子名称
        idNumber: '', // 考生id
        type: 1 // 考试类型
    };

    this.selectType = '';

    // 通信题flag
    this.messageFlag = false;

    this.option = $.extend(option, this.option);
    this.init();

    this.timerDown = null; // 剩余时间
    this.timerUp = null; // 已过时间
    this.examId = '';

    this.isNineKey = "false"; // 是否9键
};

// 初始化
machineObj.prototype.init = function() {
    this.$itemBtnBox = $('#item-btn-ul'); // 选题父级DOM
    this.$machineBtnBox = $('#machine-btn-box'); // 按键
    this.$itemBank = $('#item-bank'); // 题库
    this.$answerBank = $('#answerBank');
    this.$hand = $('#hand'); // 交卷按钮

    this.reset(); //初始化答案

    this.selectQuestion(); // 选题
    this.handExam(); // 交卷
    this.clickCollect(); // 收藏题目
    this.clickBtn(); // 点击手持机按键
    // this.starting();            //开机
}

// 时间函数
machineObj.prototype.callTime = function() {

    var _this = this;
    $.ajax({
        type: "get",
        url: "/springboot-course/examTime/getexamtime?examId=" + _this.examId,
        dataType: "JSON",
        success: function(res) {
            if (res.code == '200') {
                var dataTime = Number(res.data.dataTime.slice(1));

                // 倒计时
                _this.TimeDown({
                    id: 'remain-time',
                    time: dataTime
                });

                // 已过时间
                _this.TimeUp({
                    id: "timed",
                    startTime: 0,
                    endTime: dataTime
                });
            }

        },
        error: function(err) {
            console.log('数据返回错误', err);
        }
    });

}

// 剩余时间
machineObj.prototype.TimeDown = function(timeObj) {
    var maxtime = timeObj.time;
    var id = timeObj.id;
    var _this = this;

    function CountDown() {
        if (maxtime >= 0) {
            hours = Math.floor(maxtime / 3600);
            minutes = Math.floor(maxtime / 60 % 60);
            seconds = Math.floor(maxtime % 60);

            msg = hours + "时" + minutes + "分" + seconds + "秒";
            document.getElementById(id).innerHTML = msg;
            if (maxtime == 5 * 60) {
                $("#tipMess").text("距离考试结束还有五分钟");
                $("#exampleModal2").modal("show");
            }
            --maxtime;
        } else {
            clearInterval(_this.timerDown);
            _this.handCoding();
            // window.top.window.titleClose();
            $("#tipMess").text("考试结束");
            $("#exampleModal2").modal("show");
        }
    }
    _this.timerDown = setInterval(CountDown, 1000);
}

// 已过时间
machineObj.prototype.TimeUp = function(timeObj) {
    var endTime = timeObj.endTime;
    var startTime = timeObj.startTime;
    var id = timeObj.id;
    var _this = this;

    function CountUp() {
        if (endTime >= startTime) {
            hours = Math.floor(startTime / 3600);
            minutes = Math.floor(startTime / 60 % 60);
            seconds = Math.floor(startTime % 60);
            msg = hours + "时" + minutes + "分" + seconds + "秒";
            document.getElementById(id).innerHTML = msg;
            ++startTime;
        } else {
            clearInterval(_this.timerUp);
        }
    }
    _this.timerUp = setInterval(CountUp, 1000);

}


// 选题
machineObj.prototype.selectQuestion = function() {
        var _this = this;
        this.$itemBtnBox.on('click', '.item-btn', function() {

            $(this).addClass('active').siblings('.item-btn').removeClass('active');
            if ($(this).text() == "重做此题") {
                $('.questions-item.active').find('.answer-line').val('');
                Object.defineProperty(_this.option, $(`.questions-item.active`).attr('data-msg'), {
                    value: "未作答。未作答",
                    writable: true
                });
            } else if ($(this).text() == "上一题") {

                if ($('.questions-item.active').prev('.questions-item').length == 0) {
                    $("#tipMess").text("已经是第一道题了");
                    $("#exampleModal1").modal("show");

                } else {
                    $("#answerBank").css("display", "none");
                    $('.questions-item.active').prev('.questions-item').fadeIn().addClass('active').siblings('.questions-item').removeClass('active').hide();
                    $('.answer-li.active').prev('.answer-li').fadeIn().addClass('active').siblings('.answer-li').removeClass('active').hide();
                }
                $(".collectIcon").css("color", "#fff"); //判断是否已收藏过
                if ($("#questions-ul").children("li.active").hasClass("on")) {
                    $(".collectIcon").css("color", "#f959a3");
                }
            } else if ($(this).text() == "下一题") {
                if ($('.questions-item.active').next('.questions-item').length == 0) {
                    $("#tipMess").text("已经是最后一道题了");
                    $("#exampleModal1").modal("show");
                } else {
                    $("#answerBank").css("display", "none");
                    $('.questions-item.active').next('.questions-item').fadeIn().addClass('active').siblings('.questions-item').removeClass('active').hide();
                    $('.answer-li.active').next('.answer-li').fadeIn().addClass('active').siblings('.answer-li').removeClass('active').hide();
                }
                $(".collectIcon").css("color", "#fff"); //判断是否已收藏过
                if ($("#questions-ul").children("li.active").hasClass("on")) {
                    $(".collectIcon").css("color", "#f959a3");
                }
            } else if ($(this).text() == "查看答案") {
                $("#answerBank").css("display", "block");
            }
        });

    }
    //取消每个按钮和模态框的绑定
$("#delete").on("click", function() {
    $("#preQuestion").attr("data-target", "");
    $("#nextQuestion").attr("data-target", "");
    $("#collectButton").attr("data-target", "");
})
$("#cancal").on("click", function() {
    $("#preQuestion").attr("data-target", "");
    $("#nextQuestion").attr("data-target", "");
    $("#collectButton").attr("data-target", "");
})

// 考试题及答案
machineObj.prototype.questionsList = function(url) {
    var _this = this;
    $.ajax({
        type: "post",
        url: url,
        dataType: "JSON",
        async: false,
        success: function(res) {
            // console.log(666);
            // $('.is-test').css('display', 'none');
            _this.examId = res.data.examId;
            if (res.data == "") {
                if (res.msg.indexOf('/') != -1) {
                    let time = res.msg.split('/');
                    window.parent.window.setOption.openEmptyTest(time[1], time[0], "1");
                } else {
                    window.parent.window.setOption.openEmptyTest(0, res.msg, "1", res.data.examId);
                }

            } else {
                _this.callTime();
                _this.option.testJsonList = res.data;

                // ----------------- made by mc ---------------------

                _this.option.examId = res.data.examId; //卷子id号
                if (_this.selectType != 0) {
                    _this.option.examName = res.data.examName; // 考试卷子名称
                    _this.option.idNumber = res.data.idNumber; // 考生id
                    _this.option.type = 1; // 考试类型
                }

                _this.option.questionsList.data = res.data.testData;
                _this.drawAnswer(_this.option.testJsonList);
                _this.drawQusetions(_this.option.questionsList.data);
                _this.starting();

                window.parent.window.setOption.openFullTest();
                // window.top.window.titleOpen();

                $("#machineIndex")[0].contentWindow.window.iframeObj.scjContent(res.data.scjData);
            }
        },
        error: function(err) {
            console.log('数据返回错误', err);
        }
    });
}

//操作题开机
machineObj.prototype.starting = function() {
    var _this = this;
    if (_this.option.testJsonList.flagData != "" && _this.option.testJsonList.flagData.starting.type == '1') {
        $("#cover").css("display", "block");
        $(".open-btn").on("click", function() {
            _this.option.staringAnswer = "true";
            $("#cover").addClass("starting");
            setTimeout(function() {
                $("#cover").removeClass("starting");
                $("#cover").css("display", "none");
            }, 3000);

        })
    }
}

machineObj.prototype.getTest = function(data, index) {
    let test = data.filter(res => {
        if (res.number == index) {
            return res;
        }
    })

    return test;
}

// 获取第一个值
machineObj.prototype.getFirst = function(data) {
    for (let i in data) {
        return data[i];
    }
}

//答案渲染
machineObj.prototype.drawAnswer = function(data) {
        var _this = this;
        let needTest = [];
        $.each(data.scjData, function(key, value) { //遍历键值对
            if (value.type == "1") {
                needTest.push(value);
            }
        })
        let lengthList = needTest.map(res => {
            return res.number;
        });
        let max = Number(lengthList[0]);
        for (let i = 0; i < lengthList.length; i++) {
            if (max < Number(lengthList[i])) {
                max = Number(lengthList[i]);
            }
        }

        let arr = [];
        for (var i = 1; i <= max; i++) {

            arr.push(this.getTest(needTest, i));
        }



        // 前三题答案
        let strArr = [];
        for (let i = 0; i < arr.length; i++) {

            for (let j = 0; j < arr[i].length; j++) {
                if (j != arr[i].length - 1) {
                    strArr[arr[i][j].number] += _this.getFirst(arr[i][j]) + ';';
                } else {
                    strArr[arr[i][j].number] += _this.getFirst(arr[i][j]);
                }
            }
        }


        _this.option.strArr = strArr;

        let testArr = [];
        for (let i = 1; i <= data.testData.length; i++) {
            testArr[i] = "此题为操作题，暂无答案";
            for (let j in _this.option.strArr) {
                if (i == j) {
                    testArr[i] = _this.option.strArr[j];
                }
            }
        }
        for (let i in testArr) {
            if (testArr[i].indexOf("undefined") != -1) {
                testArr[i] = testArr[i].replace(/undefined/g, '')
            }
        }

        _this.option.strArr = testArr;

        //训练页面渲染答案(马晨不要改)
        var $ul = $('<ul id="answerBox" class="answer-box">');
        var trainAnsewr = [];

        data.testData.map(item => {
            if (item.type == "0") {
                trainAnsewr.push("此题为操作题，暂无答案");
            } else {
                trainAnsewr.push(item.result);
            }
        })
        for (var i in trainAnsewr) {
            if (i == 0) {
                var $li = $('<li class="answer-li active" data-id="' + i + '">');
            } else {
                var $li = $('<li class="answer-li" data-id="' + i + '">');
            }
            $li.html(trainAnsewr[i]);
            $li.appendTo($ul);
        }
        $ul.appendTo(this.$answerBank);
    }
    // 渲染题库
machineObj.prototype.drawQusetions = function(data) {

    var _this = this;
    var j = 0;
    var $ul = $('<ul id="questions-ul" class="questions-ul">');
    var $li;
    for (let i = 0; i < data.length; i++) {
        if (data[i].type == "" || data[i].type == "0") {
            $li = $('<li class="questions-item" data-id="' + data[i].number + '">');
        } else if (data[i].type == "1") {
            j++;
            $li = $('<li class="questions-item sendMsg sendMsg1" data-msg="messageNews' + j + '" data-sendMsg = "未作答。未作答" data-id="' + data[i].number + '" >');
        } else if (data[i].type == "2") {
            j++;
            $li = $('<li class="questions-item sendMsg sendMsg2" data-msg="messageNews' + j + '" data-sendMsg = "未作答。未作答" data-id="' + data[i].number + '" >');
        } else if (data[i].type == "3") {
            $li = $('<li class="questions-item danSelect" data-id="' + data[i].number + '" >');
        } else if (data[i].type == "4") {
            $li = $('<li class="questions-item multSelect" data-id="' + data[i].number + '" >');
        }

        var subject = data[i].number + "." + data[i].question_name.replace(/_/g, '<input class="answer-line" data-id="' + data[i].number + '">');
        $li.html(subject);

        if (data[i].img != null && data[i].img != "") {
            $('<div class="img-box"><img src="data:image/png;base64,' + data[i].img + '" class="img" id="questionImg"/></div>').appendTo($li);
        }
        if (data[i].select != null && data[i].select != "") {
            var selectArr = data[i].select.split(";");
            var $selectBox = $('<ul class="select-box"></ul>');
            $.each(selectArr, function(index, item) {
                if (data[i].type == "3") {
                    $('<li class="selectLi"><label><input type="radio" name="' + i + '" class="selectRadio oneSelect" value="' + item + '"/>' + item + '</label></li>').appendTo($selectBox);
                } else if (data[i].type == "4") {
                    $('<li class="selectLi"><label><input name="~' + i + '" type="checkbox" class="selectRadio moreSelect" value="' + item + '""/>' + item + '</label></li>').appendTo($selectBox);
                }
            })
            $selectBox.appendTo($li);
        }

        $li.appendTo($ul);
    }

    // ------------- made by mc --------------
    for (var i = 0; i < j; i++) {
        Object.defineProperty(_this.option, `messageNews${i + 1}`, {
            value: '未作答。未作答',
            writable: true
        });
    }

    this.$itemBank.empty();

    $ul.appendTo(this.$itemBank);
    this.$itemBank.find('.questions-item[data-id="1"]').addClass('active');
}

// 交卷的逻辑代码
machineObj.prototype.handCoding = function() {

    var _this = this;

    arr = _this.option.questionsList.data.map((item, index) => {
        var itemStr = "";
        $('.questions-item[data-id=' + item.number + ']').find('.answer-line').each(function() {
            if ($(this).val() == '') {
                var $val = "未作答";
            } else {
                var $val = $(this).val().replace(/\s/g, null)
            }
            itemStr += "。" + $val;
        });
        itemStr = itemStr.slice(1);

        return {
            number: item.number,
            table: item.table,
            question_code: item.question_code,
            question_name: item.question_name,
            result: item.result,
            answer: itemStr,
            grade: item.grade
        }
    });

    let controlArr = [];

    $.each(_this.option.testJsonList.flagData, function(index, item) {
        if (item.type == "1") {
            if (index == 'starting') { // 开关机操作题答案
                controlArr.push({
                    index: item.number,
                    answer: _this.option.staringAnswer
                });
            } else if (index == 'highLight') { // 高亮
                controlArr.push({
                    index: item.number,
                    answer: _this.option.highLightAnswer
                });
            } else if (index == 'sound') { // 全音
                controlArr.push({
                    index: item.number,
                    answer: _this.option.soundAnswer
                });
            } else if (index == 'noElevation') { // 无高程
                controlArr.push({
                    index: item.number,
                    answer: _this.option.noElevationAnswer
                });
            } else if (index == 'mute') { // 静音
                controlArr.push({
                    index: item.number,
                    answer: _this.option.muteAnswer
                });
            } else if (index == 'alwaysLight') { // 常开
                controlArr.push({
                    index: item.number,
                    answer: _this.option.alwaysAnswer
                });
            } else if (index == 'resetName') { // 站立点
                controlArr.push({
                    index: item.number,
                    answer: _this.option.resetNameAnswer
                });
            } else if (index == 'sendLocation') { // 常开
                controlArr.push({
                    index: item.number,
                    answer: _this.option.sendLocationAnswer
                });
            }
        }
    });

    // 通信题
    var msgArr = [];
    $('.sendMsg').each(function() {
        msgArr['-' + $(this).attr('data-id')] = $(this).attr('data-sendMsg');
    });

    // 单选题 || 判断题
    var danArr = [];
    $('.danSelect').each(function() {
        let danSelect = [];
        if ($(this).find('input[type="radio"]:checked').val()) {
            danSelect = $(this).find('input[type="radio"]:checked').val().split('.');
        } else {
            danSelect[0] = '未作答'
        }

        danArr['-' + $(this).attr('data-id')] = danSelect[0];
    });

    // 多选题
    var multArr = [];
    $('.multSelect').each(function() {
        let valArr = '';
        if ($(this).find('.moreSelect:checked').length > 0) {
            $(this).find('.moreSelect:checked').each(function() {
                let str = $(this).val().split('.');
                valArr += str[0];
            });
        } else {
            valArr = '未作答';
        }

        multArr['-' + $(this).attr('data-id')] = valArr;
    });

    // 操作题
    if (controlArr.length > 0) {
        for (let index in arr) {
            let answer = '';

            for (let i in controlArr) {
                if (arr[index].number == controlArr[i].index) {
                    answer = controlArr[i].answer;
                    break;
                } else {
                    answer = arr[index].answer;
                }
            }
            arr[index].answer = answer;
        }

    }

    // 通信题
    if (Object.keys(msgArr).length != 0) {
        for (let index in arr) {
            let answer = '';

            for (let i in msgArr) {
                if ((i.split('-'))[1] == arr[index].number) {
                    answer = msgArr[i];
                    break;
                } else {
                    answer = arr[index].answer;
                }
            }
            arr[index].answer = answer;
        }
    }


    // 单选题
    if (Object.keys(danArr).length != 0) {
        for (let index in arr) {
            let answer = '';

            for (let i in danArr) {
                if ((i.split('-'))[1] == arr[index].number) {
                    answer = danArr[i];
                    break;
                } else {
                    answer = arr[index].answer;
                }
            }
            arr[index].answer = answer;
        }
    }

    // 多选题
    if (Object.keys(multArr).length != 0) {
        for (let index in arr) {
            let answer = '';

            for (let i in multArr) {
                if ((i.split('-'))[1] == arr[index].number) {
                    answer = multArr[i];
                    break;
                } else {
                    answer = arr[index].answer;
                }
            }
            arr[index].answer = answer;
        }
    }

    debugger
    let newData = {
        "data": arr,
        "examId": _this.option.examId,
        "examName": _this.option.examName,
        "type": _this.option.type,
        "idNumber": _this.option.idNumber,
        "subject": "bdscj"
    }


    $.ajax({
        type: "post",
        url: "/springboot-course/exampaper/handResolve",
        data: "hand=" + JSON.stringify(newData),
        dataType: "JSON",
        success: function(res) {
            debugger
            if (res.data == 'true') {
                let msg = `交卷成功<div class="stu-grad-show">${res.msg}</div>`;
                $('#handSuccessText').html(msg);
                $('#handSuccess').modal("show");

                $('#handSure').off('click').on('click', function() {
                    window.parent.window.setOption.displayScore();
                    window.top.window.indexObj.refreshTest();
                });
                $('#handcancle').off('click').on('click', function() {
                    window.parent.window.setOption.displayScore();
                    window.top.window.indexObj.refreshTest();
                });
                $('#closeBtn').off('click').on('click', function() {
                    window.parent.window.setOption.displayScore();
                    window.top.window.indexObj.refreshTest();
                });
            } else {
                $('#handSuccessText').text('交卷失败');
                $('#handSuccess').modal("show");
            }
        },
        error: function(err) {
            console.log('答案正确率', err);
        }
    });

    clearInterval(_this.timerUp);
    clearInterval(_this.timerDown);
}

// 将手持机发送的信息存到data-sendMsg
machineObj.prototype.saveSendMsg = function(item, text) {
    $(`.questions-item[data-msg = ${item}]`).attr('data-sendmsg', text);
}


// 交卷
machineObj.prototype.handExam = function() {
    var _this = this;

    this.$hand.on('click', function() {
        _this.handCoding();
        // window.top.window.titleClose();
    });

    //返回上个页面
    $("#returnBox").on("click", function() {
        let text = '';
        if (_this.selectType == 0) {
            text = '确认结束训练吗？'
        } else if (_this.selectType == 3) {
            text = '查看试卷结束？'
        } else {
            text = '确认交卷？'
        }

        window.top.window.clickReturn(text, _this.selectType, 1);
    })
}


machineObj.prototype.clearTime = function() {
    debugger
    var _this = this;
    clearInterval(_this.timerUp);
    clearInterval(_this.timerDown);
    _this.handCoding();
    // window.top.window.titleClose();
}


machineObj.prototype.reset = function() {
    $("#answerBank").css("display", "none");
    $("#item-bank").html('');
    $("#answerBank").html('');
}

machineObj.prototype.judgeType = function(type, url) {
    this.selectType = type;
    if (type == 0) {
        $("#handText").text("训练");
        $("#collectButton").css("display", "block");
        $("#handBtn").css("display", "none");
        $("#reform").css("display", "none");
        $("#answer").css("display", "block");
        $("#timedBox").css("display", "none");
        // this.questionsList("/springboot-course/train/gettraining?subject=12345");
        this.questionsList(url);
        window.parent.window.setOption.openFullTest();
    } else if (type == 3) {
        $("#handText").text("试卷");
        $("#collectButton").css("display", "block");
        $("#handBtn").css("display", "none");
        $("#reform").css("display", "none");
        $("#answer").css("display", "block");
        $("#timedBox").css("display", "none");
        this.questionsList("/springboot-course/exam/getExamPaper?id_number=123");
    } else {
        $("#handText").text("考核");
        $("#collectButton").css("display", "none");
        $("#reform").css("display", "block");
        $("#answer").css("display", "none");
        $("#timedBox").css("display", "block");
        // this.questionsList("./js/json/examQuestions.json");
        this.questionsList(url);
        // this.callTime();                                  // 调用倒计时函数和已过时间函数
    }
}

// 考题映射
machineObj.prototype.examMapper = function() {
    var _this = this;
    var arr = [];
    $.each(_this.option.questionsList.data, function(index, item) {
        arr[item.number] = {
            questionCode: item.question_code,
            table: item.table
        }
    });

    return arr;
}

// 收藏题目
machineObj.prototype.clickCollect = function() {
    var _this = this;
    $('#collectButton').off('click').on('click', function() {
        var that = this;
        $(".collectIcon").css("color", "#f959a3");
        $("#questions-ul").children("li.active").addClass("on");

        $("#tipMess").text("收藏成功");
        $(that).attr("data-target", "#exampleModal1");
        var arr = _this.examMapper();
        $.post('/springboot-course/collect/collectExamSingle', arr[$('.questions-item.active').attr("data-id")], function(success) {
            return;
        });
    });
}

// 判断通信文第几题
machineObj.prototype.tagFlag = function() {
    this.messageFlag = $('.questions-item.active').attr('data-msg');
}

// 点击手持机按键
machineObj.prototype.clickBtn = function() {
    var _this = this;
    this.$machineBtnBox.on('click', '.machine-btn', function() {
        $('.machine-btn').removeClass('active');
        $(this).addClass('active');
        setTimeout(function() {
            $('.machine-btn.active').removeClass('active');
        }, 500);


        if ($(this).hasClass('F1-btn')) {
            _this.f1BtnClick();
        } else if ($(this).hasClass('F2-btn')) {
            _this.f2BtnClick();
        } else if ($(this).hasClass('F2-btn')) {
            _this.f2BtnClick();
        } else if ($(this).hasClass('up-btn')) {
            _this.upBtnClick();
        } else if ($(this).hasClass('left-btn')) {
            _this.leftBtnClick();
        } else if ($(this).hasClass('right-btn')) {
            _this.rightBtnClick();
        } else if ($(this).hasClass('bottom-btn')) {
            _this.downBtnClick();
        } else if ($(this).hasClass('sure-btn')) {
            _this.sureBtnClick();
        } else if ($(this).hasClass('num')) {
            _this.clickNumBtn($(this).attr('data-num'));
        } else if ($(this).hasClass('change-btn')) {
            _this.clickChangeBtn();
        } else if ($(this).hasClass('lock-btn')) {
            _this.clickLockBtn();
        }
    });
}


// 点击确认按键
machineObj.prototype.sureBtnClick = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');

    if (src == './machine-control-list.html') { // 手持机首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.machineControlObj.sureActive();
    } else if (src == './machine/system/system-index.html') { // 管理首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemIndexObj.sureActive();
    } else if (src == './machine/system/system-watch.html') { // 系统查看
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemWatchObj.sureActive();
    } else if (src == './machine/system/this-machine.html') { // 本机信息
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.thisMachineObj.sureActive();
    } else if (src == './machine/system/system-settings.html') { // 系统设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemSettingsObj.sureActive();
    } else if (src == './machine/message/message-index.html') { // 通讯
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.messageIndexObj.sureActive();
    } else if (src == './machine/system/beam-state.html') { // 波束状态
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.beamStateObj.sureActive();
    } else if (src == './machine/system/power-management.html') { // 电源管理
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.powerObj.sureActive();
    } else if (src == './machine/system/time-management.html') { // 时间管理
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.timeObj.sureActive();
    } else if (src == './machine/system/display-settings.html') { // 显示设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.displayObj.sureActive();
    } else if (src == './machine/system/voice-settings.html') { // 声音设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.voiceSettingsObj.sureActive();
    } else if (src == './machine/system/antenna-settings.html') { // 天线设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.antennaObj.sureActive();
    } else if (src == './machine/system/port2.html') { // 串口设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.port2Obj.sureActive();
    } else if (src == './machine/system/nav-mode-select.html') { // 导航模式选择
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.navObj.sureActive();
    } else if (src == './machine/location/machine-location.html') { // 定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationObj.sureActive();
    } else if (src == './machine/location/location-RDSS.html') { // RDSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRDSSObj.needSureActive();
    } else if (src == './machine/location/location-settings.html') { // 定位设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationSettingsObj.sureActive();
    } else if (src == './machine/location/location-report1.html') { // 位置报告1
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport1Obj.sureActive();
    } else if (src == './machine/location/location-report2.html') { // 位置报告2
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport2Obj.sureActive();
    } else if (src == './machine/location/save-road-signs.html') { // 保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj.needSureActive();
    } else if (src == './machine/location/location-RNSS.html') { // RNSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRNSSObj.needSureActive();
    } else if (src == './machine/location/location-report3.html') { // RNSS位置报告1
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport3Obj.sureActive();
    } else if (src == './machine/location/location-report4.html') { // RNSS位置报告2
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport4Obj.sureActive();
    } else if (src == './machine/location/save-road-signs2.html') { // RNSS保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj2.needSureActive();
    } else if (src == './machine/message/draft.html') { // 草稿箱
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.draft.sureActive();
    } else if (src == './machine/message/editPage.html') { // 编辑页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.editPageObj.needSureActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "true") { // 新建电文
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.needSureActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "false") { // 新建电文无输入法
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.needNavActive();
    }
}

// 点击F1按键
machineObj.prototype.f1BtnClick = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');

    if (src == './machine-control-list.html') { // 手持机首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.machineControlObj.sureActive();
    } else if (src == './machine/system/system-index.html') { // 管理首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemIndexObj.sureActive();
    } else if (src == './machine/system/system-watch.html') { // 系统查看
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemWatchObj.sureActive();
    } else if (src == './machine/system/this-machine.html') { // 本机信息
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.thisMachineObj.sureActive();
    } else if (src == './machine/system/system-settings.html') { // 系统设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemSettingsObj.sureActive();
    } else if (src == './machine/message/message-index.html') { // 通讯
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.messageIndexObj.sureActive();
    } else if (src == './machine/system/beam-state.html') { // 波束状态
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.beamStateObj.sureActive();
    } else if (src == './machine/system/power-management.html') { // 电源管理
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.powerObj.sureActive();
    } else if (src == './machine/system/time-management.html') { // 时间管理
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.timeObj.sureActive();
    } else if (src == './machine/system/display-settings.html') { // 显示设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.displayObj.sureActive();
    } else if (src == './machine/system/voice-settings.html') { // 声音设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.voiceSettingsObj.sureActive();
    } else if (src == './machine/system/antenna-settings.html') { // 天线设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.antennaObj.sureActive();
    } else if (src == './machine/system/port2.html') { // 串口设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.port2Obj.sureActive();
    } else if (src == './machine/system/nav-mode-select.html') { // 导航模式选择
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.navObj.sureActive();
    } else if (src == './machine/location/machine-location.html') { // 定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationObj.sureActive();
    } else if (src == './machine/location/location-RDSS.html') { // RDSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRDSSObj.sureActive();
    } else if (src == './machine/location/location-settings.html') { // 定位设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationSettingsObj.sureActive();
    } else if (src == './machine/location/location-report1.html') { // 位置报告1
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport1Obj.sureActive();
    } else if (src == './machine/location/location-report2.html') { // 位置报告2
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport2Obj.sureActive();
    } else if (src == './machine/location/save-road-signs.html') { // 保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj.sureActive();
    } else if (src == './machine/location/location-RNSS.html') { // RNSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRNSSObj.sureActive();
    } else if (src == './machine/location/location-report3.html') { // RNSS位置报告1
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport3Obj.sureActive();
    } else if (src == './machine/location/location-report4.html') { // RNSS位置报告2
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport4Obj.sureActive();
    } else if (src == './machine/location/save-road-signs2.html') { // RNSS保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj2.sureActive();
    } else if (src == './machine/message/draft.html') { // 草稿箱
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.draft.sureActive();
    } else if (src == './machine/message/editPage.html') { // 编辑页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.editPageObj.sureActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "true") { // 新建电文有输入法
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.clickActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "false") { // 新建电文无输入法
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.sureActive();
    }
}

// 点击F2按键
machineObj.prototype.f2BtnClick = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');

    if (src == './machine-control-list.html') { // 手持机首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.machineControlObj.returnActive();
    } else if (src == './machine/system/system-index.html') { // 管理首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemIndexObj.returnActive();
    } else if (src == './machine/system/system-watch.html') { // 系统查看
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemWatchObj.returnActive();
    } else if (src == './machine/system/this-machine.html') { // 本机信息
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.thisMachineObj.returnActive();
    } else if (src == './machine/system/system-settings.html') { // 系统设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemSettingsObj.returnActive();
    } else if (src == './machine/message/message-index.html') { // 通讯
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.messageIndexObj.returnActive();
    } else if (src == './machine/system/beam-state.html') { // 波束状态
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.beamStateObj.returnActive();
    } else if (src == './machine/system/BD2-state.html') { // BD2状态
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.BD2StateObj.returnActive();
    } else if (src == './machine/system/power-management.html') { // 电源管理
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.powerObj.returnActive();
    } else if (src == './machine/system/time-management.html') { // 时间管理
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.timeObj.returnActive();
    } else if (src == './machine/system/display-settings.html') { // 显示设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.displayObj.returnActive();
    } else if (src == './machine/system/voice-settings.html') { // 声音设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.voiceSettingsObj.returnActive();
    } else if (src == './machine/system/antenna-settings.html') { // 天线设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.antennaObj.returnActive();
    } else if (src == './machine/system/port2.html') { // 串口设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.port2Obj.returnActive();
    } else if (src == './machine/system/nav-mode-select.html') { // 导航模式选择
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.navObj.returnActive();
    } else if (src == './machine/system/nav-mode-select.html') { // 导航
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.navigationObj.returnActive();
    } else if (src == './machine/location/machine-location.html') { // 定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationObj.returnActive();
    } else if (src == './machine/location/location-RDSS.html') { // RDSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRDSSObj.returnActive();
    } else if (src == './machine/tool.html') { // 工具
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.toolObj.returnActive();
    } else if (src == './machine/time.html') { // 时间
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.machineTimeObj.returnActive();
    } else if (src == './machine/location/location-settings.html') { // 定位设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationSettingsObj.returnActive();
    } else if (src == './machine/location/location-report1.html') { // 位置报告1
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport1Obj.returnActive();
    } else if (src == './machine/location/location-report2.html') { // 位置报告2
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport2Obj.returnActive();
    } else if (src == './machine/location/save-road-signs.html') { // 保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj.returnActive();
    } else if (src == './machine/location/location-RNSS.html') { // RNSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRNSSObj.returnActive();
    } else if (src == './machine/location/location-report3.html') { // RNSS位置报告1
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport3Obj.returnActive();
    } else if (src == './machine/location/location-report4.html') { // RNSS位置报告2
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationReport4Obj.returnActive();
    } else if (src == './machine/location/save-road-signs2.html') { // RNSS保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj2.returnActive();
    } else if (src == './machine/message/draft.html') { // 草稿箱
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.draft.returnActive();
    } else if (src == './machine/message/editPage.html') { // 编辑页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.editPageObj.returnActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "true") { // 新建电文有输入法
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.cancleActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "false") { // 新建电文无输入法
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.returnActive();
    }
}

// 点击向上按键
machineObj.prototype.upBtnClick = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');

    if (src == './machine-control-list.html') { // 手持机首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.machineControlObj.upActive();
    } else if (src == './machine/system/system-index.html') { // 管理首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemIndexObj.upActive();
    } else if (src == './machine/system/system-watch.html') { // 系统查看
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemWatchObj.upActive();
    } else if (src == './machine/system/system-settings.html') { // 系统设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemSettingsObj.upActive();
    } else if (src == './machine/message/message-index.html') { // 通讯
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.messageIndexObj.upActive();
    } else if (src == './machine/location/location-RDSS.html') { // RDSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRDSSObj.upActive();
    } else if (src == './machine/location/save-road-signs.html') { // 保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj.upActive();
    } else if (src == './machine/location/location-RNSS.html') { // RNSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRNSSObj.upActive();
    } else if (src == './machine/location/save-road-signs2.html') { // RNSS保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj2.upActive();
    } else if (src == './machine/message/draft.html') { // 草稿箱
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.draft.upActive();
    } else if (src == './machine/message/editPage.html') { // 编辑页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.editPageObj.upActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "true") { // 新建电文
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.upActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "false") { // 新建电文无输入法
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.upNavActive();
    }
}

// 点击向下按键
machineObj.prototype.downBtnClick = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');
    if (src == './machine-control-list.html') { // 手持机首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.machineControlObj.downActive();
    } else if (src == './machine/system/system-index.html') { // 管理首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemIndexObj.downActive();
    } else if (src == './machine/system/system-watch.html') { // 系统查看
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemWatchObj.downActive();
    } else if (src == './machine/system/system-settings.html') { // 系统设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemSettingsObj.downActive();
    } else if (src == './machine/message/message-index.html') { // 通讯
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.messageIndexObj.downActive();
    } else if (src == './machine/location/location-RDSS.html') { // RDSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRDSSObj.downActive();
    } else if (src == './machine/location/save-road-signs.html') { // 保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj.downActive();
    } else if (src == './machine/location/location-RNSS.html') { // RNSS定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationRNSSObj.downActive();
    } else if (src == './machine/location/save-road-signs2.html') { // RNSS保存路标
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.saveRoadSignsObj2.downActive();
    } else if (src == './machine/message/draft.html') { // 草稿箱
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.draft.downActive();
    } else if (src == './machine/message/editPage.html') { // 编辑页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.editPageObj.downActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "true") { // 新建电文
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.downActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "false") { // 新建电文无输入法

        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.downNavActive();
    }
}

// 点击向左按键
machineObj.prototype.leftBtnClick = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');

    if (src == './machine-control-list.html') { // 手持机首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.machineControlObj.leftActive();
    } else if (src == './machine/system/system-index.html') { // 管理首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemIndexObj.leftActive();
    } else if (src == './machine/system/system-watch.html') { // 系统查看
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemWatchObj.leftActive();
    } else if (src == './machine/system/system-settings.html') { // 系统设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemSettingsObj.leftActive();
    } else if (src == './machine/message/message-index.html') { // 通讯
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.messageIndexObj.leftActive();
    } else if (src == './machine/location/machine-location.html') { // 定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationObj.leftActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "true") { // 新建电文有输入法
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.leftActive();
    } else if (src == './machine/message/news.html') { // 新建电文
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.leftActive();
    }
}

// 点击向右按键
machineObj.prototype.rightBtnClick = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');

    if (src == './machine-control-list.html') { // 手持机首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.machineControlObj.rightActive();
    } else if (src == './machine/system/system-index.html') { // 管理首页
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemIndexObj.rightActive();
    } else if (src == './machine/system/system-watch.html') { // 系统查看
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemWatchObj.rightActive();
    } else if (src == './machine/system/system-settings.html') { // 系统设置
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.systemSettingsObj.rightActive();
    } else if (src == './machine/message/message-index.html') { // 通讯
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.messageIndexObj.rightActive();
    } else if (src == './machine/location/machine-location.html') { // 定位
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.locationObj.rightActive();
    } else if (src == './machine/message/news.html' && this.isNineKey === "true") { // 新建电文有输入法
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.rightActive();
    } else if (src == './machine/message/news.html') { // 新建电文
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.rightActive();
    }
}

// 点击数字按键
machineObj.prototype.clickNumBtn = function(num) {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');

    if (src == './machine/message/news.html') { // 新建电文
        $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.clickNineKeys(num);
    }
}

// 切换
machineObj.prototype.clickChangeBtn = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');
    if (!$('.change-btn').hasClass('btn-active')) {
        alert('切换为数字');
        $('.change-btn').addClass('btn-active');
        if (src == './machine/message/news.html') { // 新建电文      
            $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.changeFlag(0);
        }
    } else {
        alert('切换为汉字');
        $('.change-btn').removeClass('btn-active');
        if (src == './machine/message/news.html') { // 新建电文      
            $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.changeFlag(1);
        }
    }
}

// 符号
machineObj.prototype.clickLockBtn = function() {
    let src = $('#machineIndex')[0].contentWindow.$('#machine-view-iframe').attr('src');
    if (!$('.lock-btn').hasClass('lock-btn-active')) {
        $('.lock-btn').addClass('lock-btn-active');
        if (src == './machine/message/news.html') { // 新建电文      
            $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.lockFlag('show');
        }
    } else {
        $('.lock-btn').removeClass('lock-btn-active');
        if (src == './machine/message/news.html') { // 新建电文      
            $('#machineIndex')[0].contentWindow.$('#machine-view-iframe')[0].contentWindow.news.lockFlag('hide');
        }
    }
}

// 符号删除active
machineObj.prototype.hideActive = function() {
    $('.lock-btn').removeClass('lock-btn-active');
}