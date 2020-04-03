var machineObj = function (option) {
  this.option = {
    testJsonList: [],              // 试题及答案JSON
    questionsList: {
      data: []
    },            // 考试题
    answerList: [],               // 答案
    staringAnswer: "false",        // 开机答案
    muteAnswer: "false",          // 静音
    soundAnswer: "false",         // 全音
    highLightAnswer: "false",     // 高亮
    alwaysAnswer: "false",        // 常亮
    noElevationAnswer: "false",   // 无高程
    resetNameAnswer: "false",     // 重命名站立点
    sendLocationAnswer: "false",  // 发送位置 
    messageNews1: "null。null",   // 发送电文第一道答案
    messageNews2: "null。null"    // 发送电文第二道答案
  };
  this.selectType = '';

  this.option = $.extend(option, this.option);
  this.init();
};

// 初始化
machineObj.prototype.init = function () {
  this.$itemBtnBox = $('#item-btn-ul');             // 选题父级DOM
  this.$machineBtnBox = $('#machine-btn-box');      // 按键
  this.$itemBank = $('#item-bank');                 // 题库
  this.$answerBank = $('#answerBank');

  // this.questionsList();       // 题目及答案数据
  this.selectQuestion();      // 选题
  this.changeBtn();           // 点击按键 

}


// 选题
machineObj.prototype.selectQuestion = function () {

  this.$itemBtnBox.on('click', '.item-btn', function () {
    if ($(this).text() == "重做此题") {
      $('.questions-item.active').find('.answer-line').val('');

    } else if ($(this).text() == "查看答案") {
      $("#answerBank").css("display", "block");
    }
  });
}

// 点击按键
machineObj.prototype.changeBtn = function () {
  this.$machineBtnBox.on('click', '.machine-btn', function () {
    $('.machine-btn').removeClass('active');
    $(this).addClass('active');
  });
}

// 考试题及答案
machineObj.prototype.questionsList = function (table, name) {
  var _this = this;

  $.ajax({
    type: "get",
    url: '/springboot-course/exam/checkCollectPapers?table=' + name + '&questionCode=' + table,
    dataType: "json",
    success: function (res) {
      _this.option.testJsonList = res;
      _this.option.questionsList.data = res.data;
      _this.drawQusetions(_this.option.questionsList.data);

      $("#machineIndex")[0].contentWindow.window.iframeObj.scjContent(res.scjData);
    },
    error: function (err) {
      console.log('数据返回错误');
    }
  });
}

machineObj.prototype.getTest = function (data, index) {
  let test = data.filter(res => {
    if (res.number == index) {
      return res;
    }
  })

  return test;
}

// 获取第一个值
machineObj.prototype.getFirst = function (data) {
  for (let i in data) {
    return data[i];
  }
}

// 渲染题库
machineObj.prototype.drawQusetions = function (data) {

  var $ul = $('<ul id="questions-ul" class="questions-ul">');
  for (var i in data) {
    if (data[i].number == "1") {
      $li = $('<li class="questions-item active" data-id="' + data[i].number + '" >');
    } else {
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
    }

    // 考试题
    var subject = data[i].number + "." + data[i].question_name.replace(/_/g, '<input class="answer-line" data-id="' + data[i].number + '">');
    var $subjectDiv = $('<div class="subject"></div>');
    $subjectDiv.html(subject);
    $subjectDiv.appendTo($li);

    if (data[i].img != null && data[i].img != "") {
      $('<div class="img-box"><img src="data:image/png;base64,' + data[i].img + '" class="img" id="questionImg"/></div>').appendTo($li);
    }
    if (data[i].select != null && data[i].select != "") {
      var selectArr = data[i].select.split(";");
      var $selectBox = $('<ul class="select-box"></ul>');
      $.each(selectArr, function (index, item) {
        if (data[i].type == "3") {
          $('<li class="selectLi"><label><input type="radio" name="flag" class="selectRadio oneSelect" value="' + item + '"/>' + item + '</label></li>').appendTo($selectBox);
        } else if (data[i].type == "4") {
          $('<li class="selectLi"><label><input type="checkbox" class="selectRadio moreSelect" value="' + item + '""/>' + item + '</label></li>').appendTo($selectBox);
        }
      })
      $selectBox.appendTo($li);
    }
    if (data[i].result == 'true') {
      $('<div class="answer" id="answerBank" style="display: none;">参考答案: 此题为操作题</div>').appendTo($li);
    } else {
      $('<div class="answer" id="answerBank" style="display: none;">参考答案:' + data[i].result + '</div>').appendTo($li);
    }

    $li.appendTo($ul);
  }
  this.$itemBank.empty();
  $ul.appendTo(this.$itemBank);
}


machineObj.prototype.reset = function () {
  $("#answerBank").css("display", "none");
  $("#item-bank").html('');
  $("#answerBank").html('');
}
