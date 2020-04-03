var obj = function(option) {
    this.option = option || {};
    this.init();
};

// 初始化
obj.prototype.init = function() {
        this.option.$returnMain = $('.return-main'); // 头部右侧DOM
        this.option.$adminMain = $('.admin-ul'); // 用户信息列表DOM
        this.option.$tabControl = $('#tab-control'); // tab标签页
        this.option.idNumber = '';
        this.option.timerDown = null; // 倒计时

        this.adminMain(); // 用户信息
        this.handleClick();
    }
    // ---------------------- 所有的点击事件 --------------------------
obj.prototype.handleClick = function() {
    this.changeTap(); // 顶部点击切换状态
    this.changeTab(); // 切换tab选项卡
    this.clickSlider(); // 下拉收缩按钮
}

// 点击事件1：顶部点击切换状态
obj.prototype.changeTap = function() {
        var option = this.option;
        option.$returnMain.on('click', '.return-li', function() {
            $(this).siblings('.return-li').removeClass('active');
            $(this).addClass('active');
        });
    }
    // 点击事件2：切换tab选项卡
obj.prototype.changeTab = function() {
    var _this = this;
    var option = this.option;
    let $iframe;
    option.$tabControl.on('click', '.tab-item', function() {
        var $id = $(this).attr('id');
        var $src = $(this).attr('x-src');
        $(this).siblings('.tab-item').removeClass('active');
        $(this).addClass('active');

        $(".body-iframe").removeClass("active");

        if ($('.body-iframe[target="' + $id + '"]').length === 0) {
            if ($id == 'tab-item2') {
                $iframe = `<iframe src="${$src}" frameborder="0" id="iframeTest" class="body-iframe hand" target="${$id}" id="iframeTest"></iframe>`;
            }
            // else if ($id == 'tab-item3') {
            //   $iframe = `<iframe src="${$src}" frameborder="0" class="body-iframe hand" target="${$id}" data-handAfter="handAfter"
            //   id="mainIframe"></iframe>`;
            // }
            $($iframe).addClass('active').appendTo($('#body-contain'));
        } else {
            $(".body-iframe[target=" + $id + "]").addClass("active");
        }

        if ($id == "tab-item3") {
            $("#mainIframe")[0].contentWindow.window.params = _this.option.idNumber;
            $("#mainIframe")[0].contentWindow.window.tableFirst();
        }
    });
}

// 用户列表信息
obj.prototype.adminMain = function() {
    var adminData = this.option.adminData;
    var adminImg = adminData.adminImg;
    var $adminMain = this.option.$adminMain;
    if (adminImg == null) {
        $('#admin-img').attr("src", "./img/user.png");
    } else {
        $('#admin-img').attr("src", "data:image/png;base64," + adminImg);
    }

    $adminMain.empty();

    $.each(adminData.data, function(i, item) {
        var $li = $('<li class="admin-li">');
        $('<sapn class="admin-desc">' + item.text + '： </sapn>').appendTo($li);
        $('<sapn class="admin-val id="value' + i + '">' + item.value + '</sapn>').appendTo($li);
        $li.appendTo($adminMain);
    });
    this.option.idNumber = adminData.data[4].value;
}
obj.prototype.changePhoto = function(data) {
    $('#admin-img').attr("src", "data:image/png;base64," + data);
}

// 考试科目数
obj.prototype.changeNums = function(item, index) {
    $.ajax({
        type: "get",
        url: "/springboot-course/exam/needTest",
        dataType: "JSON",
        async: false,
        success: function(res) {
            var testData = res.data.data;
            var count = 0;
            $.each(testData, function(index, item) {
                if (item.isTest == "true") {
                    count++;
                }
            });
            if (count == 0) {
                $('#test').hide();
            } else {
                $('#test').show();
            }
        },
        error: function(err) {
            console.log('数据返回错误', err);
        }
    });
}

// 下拉收缩按钮
obj.prototype.clickSlider = function() {
    $("#slider-box").on('click', function() {
        if (!$(this).hasClass('active')) {
            $("#infomation-box").addClass('active');
            $(this).addClass('active');
        } else {
            $("#infomation-box").removeClass('active');
            $(this).removeClass('active');
        }
    });
}

// 交卷完后显示成绩
obj.prototype.displayScore = function() {
    $('.hand').removeClass('active');
    $('.hand[data-handAfter=handAfter]').addClass('active');
    $('iframe[target="tab-item2"]')[0].contentWindow.location.reload();
    $('#mainIframe')[0].contentWindow.window.tableFirst();
}

// 跳转手持机考核页面
obj.prototype.goMachine = function(type, url, subject) {
    $('.body').removeClass('active');
    if (!$("#slider-box").hasClass('active')) {
        $("#infomation-box").addClass('active');
        $("#slider-box").addClass('active');
    }
    $('.body').eq(2).addClass('active');
    switch (subject) {
        case "1":
            $("#textIframe").addClass('active').siblings('.machine-iframe').removeClass('active');
            document.getElementById('textIframe').contentWindow.window.newObj.judgeType(type, url + this.option.idNumber);
            break;
        case "2":
            $("#radioIframe").addClass('active').siblings('.machine-iframe').removeClass('active');
            document.getElementById('radioIframe').contentWindow.window.radioObj.judgeType(type, url + this.option.idNumber);
            break;
        case "3":
            $("#knowIframe").addClass('active').siblings('.machine-iframe').removeClass('active');
            document.getElementById('knowIframe').contentWindow.window.knowObj.judgeType(type, url + this.option.idNumber);
            break;
        case "4":
            $("#interpretationIframe").addClass('active').siblings('.machine-iframe').removeClass('active');
            document.getElementById('interpretationIframe').contentWindow.window.interpretationObj.judgeType(type, url + this.option.idNumber);
            break;
        case "5":
            $("#countIframe").addClass('active').siblings('.machine-iframe').removeClass('active');
            document.getElementById('countIframe').contentWindow.window.countObj.judgeType(type, url + this.option.idNumber);
            break;
        case "6":
            $("#imageIframe").addClass('active').siblings('.machine-iframe').removeClass('active');
            document.getElementById('imageIframe').contentWindow.window.imgObj.judgeType(type, url + this.option.idNumber);
            break;
    }
}
obj.prototype.returnMachine = function() {
    $('.body').addClass('active');
    $('.body').eq(1).removeClass('active').children('.machine-iframe').fadeOut();
}

// 切换错题集
obj.prototype.changeCollect = function() {
    $('#mainIframe')[0].contentWindow.window.falseCollect('#collect');
}

// 考题为空的开模态框
obj.prototype.openEmptyTest = function(time, text, type, examId) {
    if (time != 0) {
        this.TimeDown({
            time,
            id: 'time'
        }, type)
        $('#time').show();
    } else {
        $('#time').hide();
    }
    // $('#myModalText').text(text);
    $('.body').removeClass('active');
    $('.body').eq(0).addClass('active');

    // $('#myModal').modal();
    $('#button-Confirm').on('click', function() {
        // window.location.reload();
        // $('#tab-item2').addClass('active');
        // $('#iframeTest').addClass('active');
        // if($(".train-li").eq(type-1).find(".is-test").length>0){
        //     $(".train-li").eq(type-1).find(".is-test").remove();//当考试时间已过
        //     $.ajax({
        //         url:"/springboot-course/exam/endExam",
        //         async:true,
        //         data:{"examId":examId},   
        //         type:"POST", 
        //     });
        //     this.changeNums();
        // } 
        $("#iframeTest")[0].contentWindow.window.testObj.passTest(type, examId);
    })
}

// 存在考题
obj.prototype.openFullTest = function() {
    $('.body').removeClass('active');
    $('.body').eq(1).addClass('active').children('.machine-iframe').fadeIn();
}

// 交卷后跳转到我的考核
obj.prototype.turnToTest = function() {
    $('#mainIframe')[0].contentWindow.window.tableFirst(this.option.idNumber);
}

// 倒计时
obj.prototype.TimeDown = function(timeObj, type) {
    var maxtime = timeObj.time;
    var id = timeObj.id;
    var _this = this;

    clearInterval(_this.timerDown);

    function CountDown() {
        if (maxtime >= 0) {
            hours = Math.floor(maxtime / 3600);
            minutes = Math.floor(maxtime / 60 % 60);
            seconds = Math.floor(maxtime % 60);

            msg = "还剩" + hours + "时" + minutes + "分" + seconds + "秒";
            document.getElementById(id).innerHTML = msg;
            --maxtime;
        } else {
            clearInterval(_this.timerDown);
            $('#myModal').modal('hide');
            _this.goMachine(1, "/springboot-course/exam/getExamPaper?subject=" + type + "&id_number=", type);
        }
    }
    _this.timerDown = setInterval(CountDown, 1000);
}