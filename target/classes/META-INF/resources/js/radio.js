var machineObj = function(option) {
    this.option = {
        testJsonList: [], // 试题及答案JSON
        questionsList: {
            data: []
        }, // 考试题
        answerList: [], // 答案
        testTypeArr: [1, 0, 1, 0], //操作题类型 跳频定频等
        thisTestType: 0, //当前操作题类型
        examId: '', // 考试卷子id号,
        examName: '', // 考试卷子名称
        idNumber: '', // 考生id
        type: 6, // 考试类型
        rotateLeftBtn1: null, // 跳明旋转对象
        rotateLeftBtn2: null, // 照明旋转对象
        rotateLeftBtn3: null, // 照明旋转对象
        rotateLeftBtn4: null // 静噪旋转对象    
    };

    this.selectType = '';

    this.option = $.extend(option, this.option);
    this.PropellerObj = {};
    this.timerDown = null; // 剩余时间
    this.timerUp = null; // 已过时间
    this.examId = '';
    this.screenFirst = ""; //记录初始值及上次值
    this.init();
    this.getAnsewr = []; //从后台传过来的答案


};

// 初始化
machineObj.prototype.init = function() {

        this.$itemBtnBox = $('#item-btn-ul'); // 选题父级DOM
        this.$machineBtnBox = $('#machine-btn-box'); // 按键
        this.$itemBank = $('#item-bank'); // 题库
        this.$answerBank = $('#answerBank');
        this.$hand = $('#hand'); // 交卷按钮
        this.selectQuestion(); // 选题
        this.handExam(); // 交卷
        this.screenFirst = "";
        this.clickBtn(); //点击电台按键
        this.allRotateEvents(); // 电台所有的旋转事件
        $("#screen").val(this.screenFirst);
        this.answerArr = [];

    }
    //判断页面为训练、考核
machineObj.prototype.judgeType = function(type, url) {
        this.selectType = type;
        if (type == 0) {

            $("#handText").text("训练");
            $("#handBtn").hide();
            $("#reform").hide();
            $("#answer").show();
            $("#timedBox").hide();
            // this.questionsList("./js/json/examQuestions.json");
            this.questionsList(url);
            window.parent.window.setOption.openFullTest();
        } else if (type == 3) {
            $("#handText").text("试卷");
            $("#handBtn").hide();
            $("#reform").hide();
            $("#answer").show();
            $("#timedBox").hide();
            this.questionsList("/springboot-course/exam/getExamPaper?id_number=123");
        } else {
            $("#handText").text("考核");
            $("#reform").show();
            $("#answer").hide();
            $("#timedBox").show();
            // this.questionsList("./js/json/examQuestions.json");
            this.questionsList(url);
            // this.callTime(); // 调用倒计时函数和已过时间函数
        }
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

// 考试题及答案
machineObj.prototype.questionsList = function(url) {
        var _this = this;
        $.ajax({
            type: "post",
            url: url,
            dataType: "JSON",
            async: false,
            success: function(res) {
                _this.examId = res.data.examId;
                if (res.data == "") {
                    if (res.msg.indexOf('/') != -1) {
                        let time = res.msg.split('/');
                        window.parent.window.setOption.openEmptyTest(time[1], time[0], "2");
                    } else {
                        window.parent.window.setOption.openEmptyTest(0, res.msg, "2", res.data.examId);
                    }

                    _this.option.examId = res.data.examId; //卷子id号
                    if (_this.selectType != 0) {
                        _this.option.examName = res.data.examName; // 考试卷子名称
                        _this.option.idNumber = res.data.idNumber; // 考生id
                        _this.option.type = 1; // 考试类型
                    }

                    _this.option.questionsList.data = res.data.testData;
                    // _this.drawAnswer(_this.option.testJsonList);
                    _this.drawQusetions(_this.option.questionsList.data);
                    window.parent.window.setOption.openFullTest();

                    for (item of res.data.testData) {
                        _this.getAnsewr.push(item.result.split(",")); //将后台传的答案存进数组
                    }
                    window.top.window.titleOpen();
                }

                // error: function(err) {
                //     console.log('数据返回错误', err);
                // }
            }

        });
    }
    // 渲染题库
machineObj.prototype.drawQusetions = function(data) {
        let _this = this;
        let j = 0;
        let $ul = $('<ul id="questions-ul" class="questions-ul">');
        let $li = $('<li class="questions-item">');

        for (let i = 0; i < data.length; i++) {
            if (data[i].type == "1" || data[i].type == "0") {
                $li = $('<li class="questions-item" data-id="' + data[i].number + '">');
            } else if (data[i].type == "2") {
                $li = $('<li class="questions-item danSelect" data-id="' + data[i].number + '" >');
            } else if (data[i].type == "3") {
                $li = $('<li class="questions-item multSelect" data-id="' + data[i].number + '" >');
                for (item of res.data.testData) {
                    // if(item.result.indexOf(",") >= 0){
                    _this.getAnsewr.push(item.result.split(",")); //将后台传的答案存进数组
                    // }else{
                    //     _this.getAnsewr.push(item.result);
                    // }

                }
                // window.top.window.titleOpen();
            }

            let subject = data[i].number + "." + data[i].question_name.replace(/_/g, '<input class="answer-line" data-id="' + data[i].number + '">');
            // console.log('subject', subject);
            $li.html(subject);

            if (data[i].img != null && data[i].img != "") {
                $('<div class="img-box"><img src="data:image/png;base64,' + data[i].img + '" class="img" id="questionImg"/></div>').appendTo($li);
            }
            // if(data[i].picturePath!=null && data[i].picturePath !=""){
            //   $('<div class="img-box"><img src="' + data[i].picturePath + '" class="img" id="questionImg"/></div>').appendTo($li);
            // }

            if (data[i].select != null && data[i].select != "") {
                let selectArr = data[i].select.split(";");
                let $selectBox = $('<ul class="select-box"></ul>');
                $.each(selectArr, function(index, item) {
                    if (data[i].type == "2") {
                        $('<li class="selectLi"><label><input type="radio" name="' + i + '" class="selectRadio oneSelect" value="' + item + '"/>' + item + '</label></li>').appendTo($selectBox);
                    } else if (data[i].type == "3") {
                        $('<li class="selectLi"><label><input name="~' + i + '" type="checkbox" class="selectRadio moreSelect" value="' + item + '""/>' + item + '</label></li>').appendTo($selectBox);
                    }
                })
                $selectBox.appendTo($li);
            }

            let $answerBox = $('<div class="answerIcon glyphicon glyphicon-eye-close"></div><div class="answerBox" id="answerBox">参考答案：' + data[i].result + '</div>');
            $answerBox.appendTo($li);
            $li.appendTo($ul);
        }
        if (_this.selectType == 0) {
            let $answerBox = $('<div class="answerIcon glyphicon glyphicon-eye-close"></div><div class="answerBox" id="answerBox">参考答案：' + data[i].result + '</div>');
            $answerBox.appendTo($li);
        }

        $li.appendTo($ul);
    }
    // 选题
machineObj.prototype.selectQuestion = function() {
        var _this = this;
        this.$itemBtnBox.on('click', '.item-btn', function() {

            $(this).addClass('active').siblings('.item-btn').removeClass('active');
            if ($(this).text() == "重做此题") {
                _this.initRadio(); //初始化电台
            } else if ($(this).text() == "上一题") {

                if ($('.questions-item.active').prev('.questions-item').length == 0) {
                    $("#tipMess").text("已经是第一道题了");
                    $("#exampleModal1").modal("show");

                } else {
                    $('.questions-item.active').prev('.questions-item').fadeIn().addClass('active').siblings('.questions-item').removeClass('active').hide();
                    //   _this.thisTestType=_this.testTypeArr[$(".questions-item .active").attr("data-id")];//获取当前题目类型
                    _this.initRadio(); //初始化电台
                }
            } else if ($(this).text() == "下一题") {
                if ($('.questions-item.active').next('.questions-item').length == 0) {
                    $("#tipMess").text("已经是最后一道题了");
                    $("#exampleModal1").modal("show");
                } else {
                    $('.questions-item.active').next('.questions-item').fadeIn().addClass('active').siblings('.questions-item').removeClass('active').hide();
                    //   _this.thisTestType=_this.testTypeArr[$(".questions-item .active").attr("data-id")];//获取当前题目类型
                    _this.initRadio(); //初始化电台
                }
            }
        });

    }
    // 交卷的逻辑代码
machineObj.prototype.handCoding = function() {

    var _this = this;

    arr = _this.option.questionsList.data.map((item, index) => {
        var itemStr = "";
        if (item.type == 0) { //操作题判断对错
            if (_this.ArrayIsEqual(_this.answerArr[item.number - 1], _this.getAnsewr[item.number - 1])) { //调用数组比较函数
                itemStr = "true";
            } else {
                itemStr = "false";
            }

        } else {
            $('.questions-item[data-id=' + item.number + ']').find('.answer-line').each(function() {
                if ($(this).val() == '') {
                    var $val = "未作答";
                } else {
                    var $val = $(this).val().replace(/\s/g, null)
                }
                itemStr += "。" + $val;
            });
            itemStr = itemStr.slice(1);
        }
        return {
            number: item.number,
            table: item.table,
            question_code: item.question_code,
            question_name: item.question_name,
            // result: item.result,
            answer: itemStr,
            // grade: item.grade
        }


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


    let newData = {
        "data": arr,
        "examId": _this.option.examId,
        "examName": _this.option.examName,
        "type": _this.option.type,
        "idNumber": _this.option.idNumber,
        "subject": "dtcz"
    }
    $.ajax({
        type: "post",
        url: "/springboot-course/exampaper/handResolve",
        data: "hand=" + JSON.stringify(newData),
        dataType: "JSON",
        success: function(res) {
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
    // window.top.window.titleClose();
}
machineObj.prototype.ArrayIsEqual = function(arr1, arr2) { //判断2个数组是否相等
        if (arr1 == undefined || arr1 == "") {
            return false;
        } else if (arr1 === arr2) { //如果2个数组对应的指针相同，那么肯定相等，同时也对比一下类型
            return true;
        } else {
            if (arr1.length != arr2.length) {
                return false;
            } else { //长度相同
                for (let i in arr1) { //循环遍历对比每个位置的元素
                    if (arr1[i] != arr2[i]) { //只要出现一次不相等，那么2个数组就不相等
                        return false;
                    }
                } //for循环完成，没有出现不相等的情况，那么2个数组相等
                return true;
            }
        }
    }
    // 交卷
machineObj.prototype.handExam = function() {
    var _this = this;

    this.$hand.on('click', function() {
        _this.handCoding();
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

        window.top.window.clickReturn(text, _this.selectType, 2);
    })
}
machineObj.prototype.clearTime = function() {
    var _this = this;
    clearInterval(_this.timerUp);
    clearInterval(_this.timerDown);
    _this.handCoding();
}
machineObj.prototype.reset = function() {
        $("#answerBank").css("display", "none");
        $("#item-bank").html('');
        $("#answerBank").html('');
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
    //点击电台按键 
machineObj.prototype.clickBtn = function() {
        var that = this
        var screenString = ""; //最终得到的频率输入(按确定后显示在屏幕上的)
        var clickArr = []; //输入时的实时频率数组(定频或不连续确定输入)
        var continClickArr = []; //输入时的实时数组(跳频或连续输入)
        var clickString = ""; //输入时的频率字符串
        var oldXd = "0"; //上一个信道
        var newXd = ""; //新的信道
        var xdFlag = false;
        var plFlag = false;
        var tpFlag = false; //定频，跳频，信道标识
        var testAnswer = []; //没道题答完提交时储存所做答案

        $("#centerBtnBox").off("click").on("click", ".numBtn i", function() {
            // var that=this;

            $('.numBtn').removeClass('numBtnActive');
            $(this).parent().addClass("numBtnActive");
            setTimeout(function() {
                $('.numBtn.numBtnActive').removeClass('numBtnActive');
            }, 500); //按键按下动画
            if ($("#btn3").hasClass("turn3") || $("#btn3").hasClass("turn4")) {
                if ($(this).attr("x-id") == "xd") {
                    newXd = "";
                    xdFlag = true;
                    plFlag = false;
                    tpFlag = false;
                } else if ($(this).attr("x-id") == "sure") {
                    if (xdFlag == true) { //当前为信道输入时
                        if (newXd <= 9 && newXd.length == 1 && newXd != "") {
                            $("#screenRight").text(newXd);
                            oldXd = newXd;
                        }
                        newXd = "";
                        xdFlag = false; //将新信道显示到屏幕上并赋给旧信道值，清空新信道值，关闭信道标识
                    } else if ($(btn1).attr("class") == "btn1" && $(btn4).attr("class") == "btn4" && plFlag == true) { //当前为频率输入时
                        clickString = "";
                        if (clickArr.length == 5) {
                            screenString = "F" + clickArr[0] + clickArr[1] + ".";
                            if ((clickArr[2] + clickArr[3] + clickArr[4]) % 25 == 0) {

                                screenString = screenString + clickArr[2] + clickArr[3] + clickArr[4];
                                that.screenFirst = screenString;
                                $("#screen").val(screenString);
                            } else {
                                $("#screen").val(that.screenFirst);
                            }
                        } else {
                            $("#screen").val(that.screenFirst);
                        }
                        testAnswer.push("F" + clickArr.join(''));
                        clickArr = [];
                        plFlag = false;
                    } else if ($(btn1).hasClass("turn4") && tpFlag == true) {
                        clickString = "";
                        if (clickArr[0] == 0) { //如果输入0号参数
                            if (clickArr.length >= 2 && clickArr.length < 5) {
                                screenString = "H0" + clickArr[1] + that.screenFirst.substring(3); //如果只输入1、2或3位数则只改变频率号这一位参数其余不变
                                that.screenFirst = screenString;
                                $("#screen").val(screenString);
                            } else if (clickArr.length == 5) {
                                if (clickArr.slice(-3).join('') > 255) {
                                    $("#screen").val(that.screenFirst); //如果输入了四位且后三位大于255，则屏幕显示不变
                                } else {
                                    screenString = "H" + clickArr.join(''); //如果只输入1、2或3位数则只改变频率号这一位参数其余不变
                                    that.screenFirst = screenString;
                                    $("#screen").val(screenString);
                                }
                            } else {
                                $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                            }
                        } else if (clickArr[0] == 1) { //1号参数设置
                            if (clickArr.length == 2) {
                                if (clickArr[1] == 1 || clickArr[1] == 0) {
                                    screenString = "H1   " + clickArr[1];
                                    that.screenFirst = screenString; //参数值为0或1时正确输出
                                    $("#screen").val(screenString);
                                } else {
                                    $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                                }
                            } else {
                                $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                            }
                        } else if (clickArr[0] == 2) { //2号参数设置
                            if (clickArr.length > 1) {
                                screenString = "H2   " + clickArr[1];
                                that.screenFirst = screenString; //参数值为0-1时正确输出,若输入数字大于一位，也被判输入了一位
                                $("#screen").val(screenString);
                            } else {
                                $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                            }
                        } else if (clickArr[0] == 3) { //3号参数设置
                            if (clickArr.length == 5) {
                                if (clickArr.slice(-4).join('') >= 1980 && clickArr.slice(-4).join('') <= 2079) {
                                    screenString = "H" + clickArr.join('');
                                    that.screenFirst = screenString; //参数值为1980-2079时正确输出
                                    $("#screen").val(screenString);
                                } else {
                                    $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                                }

                            } else {
                                $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                            }
                        } else if (clickArr[0] == 4) { //4号参数设置
                            if (clickArr.length == 5) {
                                if (clickArr[1] + clickArr[2] >= 1 && clickArr[1] + clickArr[2] <= 12 && clickArr[3] + clickArr[4] >= 1 && clickArr[3] + clickArr[4] <= 31) {
                                    screenString = "H" + clickArr.join('');
                                    that.screenFirst = screenString; //参数值为前两位1-12，后两位1-31时正确输出
                                    $("#screen").val(screenString);
                                } else {
                                    $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                                }

                            } else {
                                $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                            }
                        } else if (clickArr[0] == 5) { //5号参数设置
                            if (clickArr.length == 5) {
                                if (clickArr[1] + clickArr[2] >= 0 && clickArr[1] + clickArr[2] <= 23 && clickArr[3] + clickArr[4] >= 0 && clickArr[3] + clickArr[4] <= 59) {
                                    screenString = "H" + clickArr.join('');
                                    that.screenFirst = screenString; //参数值为前两位0-23，后两位0-59时正确输出
                                    $("#screen").val(screenString);
                                } else {
                                    $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                                }

                            } else {
                                $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                            }
                        } else if (clickArr[0] == 6) { //6号参数设置
                            if (clickArr.length == 2) {
                                screenString = "H6  0" + clickArr[1];
                                that.screenFirst = screenString; //参数值为两位数前两位0-59时正确输出
                                $("#screen").val(screenString);
                            } else if (clickArr.length == 1) {
                                $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                            } else {
                                if (clickArr[1] + clickArr[2] >= 0 && clickArr[1] + clickArr[2] <= 59) {
                                    screenString = "H6  " + clickArr[1] + clickArr[2];
                                    that.screenFirst = screenString; //参数值为两位数前两位0-59时正确输出
                                    $("#screen").val(screenString);
                                } else {
                                    $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                                }
                            }
                        } else if (clickArr[0] == 7) { //7号参数设置
                            if (clickArr.length == 2 && clickArr[1] >= 0 && clickArr[1] <= 2) {
                                screenString = "H7   " + clickArr[1];
                                that.screenFirst = screenString; //参数值为0-2时正确输出
                                $("#screen").val(screenString);
                            } else {
                                $("#screen").val(that.screenFirst); //其他情况下屏幕都不变
                            }
                        }

                        testAnswer[parseInt(clickArr[0])] = "H" + clickArr.join(''); //将跳频一次参数设置保存到最终答案数组(当前面操作没有进行定频操作时)                 
                        console.log(testAnswer);
                        clickArr = [];
                        tpFlag = false;
                    } else {
                        $("#screen").val(that.screenFirst);
                        clickArr = [];
                        clickString = "";
                    }

                } else if ($(this).attr("x-id") == "sd") {

                } else if ($(this).attr("x-id") == "tg") { //点击退格键时
                    if (xdFlag == true && newXd.length > 0) { //对信道进行推格
                        newXd = newXd.slice(0, -1);
                    }
                    if (clickArr.length > 0) { //对频率输入进行退格
                        clickArr.pop();
                        clickString = clickString.slice(0, -1);
                        $("#screen").val(clickString);
                    }
                } else if ($(this).attr("x-id") == "H") {
                    clicktp = true;
                    xdFlag = false;
                    plFlag = false;
                    tpFlag = true;
                } else if ($(this).attr("x-id") == "F") {
                    clickdp = true;
                    if (xdFlag == true) {
                        if (newXd <= 9) {
                            $("#screenRight").text(newXd);
                            oldXd = newXd;
                            newXd = "";
                            xdFlag = false; //将新信道显示到屏幕上并赋给旧信道值，清空新信道值，关闭信道标识
                        } else {
                            newXd = "";
                            xdFlag = false;
                        }
                    }
                    clickArr = [];
                    clickString = ""; //点击频率键时初始化频率点击字符串及数组
                    plFlag = true;
                    tpFlag = false;
                } else {
                    if (xdFlag == true) { //如果处于信道输入中，得到新输入信道
                        newXd += $(this).attr("x-id");
                    } else {
                        clickArr.push($(this).attr("x-id"));
                        clickString += $(this).attr("x-id");
                        $("#screen").val(clickString);
                    }

                }
            }
        })
        $("#completeBtn").off("click").on("click", function() { //点击提交按钮
            var testIndex = $(".questions-item.active").attr("data-id"); //获取当前答题号
            testAnswer.push($("#screenRight").text());
            that.answerArr[testIndex - 1] = testAnswer;
            console.log(that.answerArr);
        })
    }
    //初始化电台
machineObj.prototype.initRadio = function() {
    this.screenFirst = "";
    $("#screen").val(this.screenFirst); //初始化电台屏幕显示
    this.clickBtn(); //初始化电台内部变量
    $("#screenRight").text(""); //初始化信道
    $("#btn3").attr("class", "btn3"); //将开机按钮复位
    $("#btn2").attr("class", "btn2"); //将照明按钮复位
    $("#btn1").attr("class", "btn1"); //将工作按钮复位
    $("#btn4").attr("class", "btn4"); //将噪声按钮复位
    this.indexInit(); //初始化按钮旋转变量
}

// ----------------------------- made by mc ---------------------
// 所有旋转事件
machineObj.prototype.allRotateEvents = function() {
    this.rotateLeftBtn1(); // 旋转左上角跳明按钮 
    this.rotateLeftBtn2(); // 旋转左下角照明按钮 
    this.rotateLeftBtn3(); // 旋转左下角电源按钮
    this.rotateLeftBtn4(); // 旋转右上角静噪按钮 
}

// 初始化properties 插件
machineObj.prototype.initProperty = function(id, angle) {
    return new Propeller(document.getElementById(id), {
        inertia: 0.99,
        angle
    });
}

// -------------- made by mc -------------------
// 旋转左上角跳明按钮
machineObj.prototype.rotateLeftBtn1 = function() {
    this.option.rotateLeftBtn1 = new rotate('#btn1');
}

// 旋转左下角照明按钮
machineObj.prototype.rotateLeftBtn2 = function() {
    this.option.rotateLeftBtn2 = new rotate('#btn2');
}

// 旋转左下角电源按钮
machineObj.prototype.rotateLeftBtn3 = function() {
    this.option.rotateLeftBtn3 = new rotate('#btn3');
}


// 旋转右上角静噪按钮
machineObj.prototype.rotateLeftBtn4 = function() {
    this.option.rotateLeftBtn4 = new rotate('#btn4');
}


/**
 * @description: 初始化
 * @method: indexInit
 * @param: {}
 * @return: void
 */
machineObj.prototype.indexInit = function() {
    this.option.rotateLeftBtn1.indexInit();
}