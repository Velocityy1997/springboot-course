window.startRotate = function () {
  if (window.libInfoShown !== true) {
      var windStuff = document.getElementsByClassName('wind');
      var libStuff = document.getElementsByClassName('propeller');
      for (var i = 0; i < windStuff.length; i++) {
          var obj = windStuff[i];
          obj.style.display = 'none';
      }
      for (var i = 0; i < libStuff.length; i++) {
          var obj = libStuff[i];
          obj.style.display = 'inline-block';
      }
      window.libInfoShown = true;
  }
}

class machineObj {
  constructor(option) {
      this.option = {
          testJsonList: [], // 试题及答案JSON
          questionsList: {
              data: []
          }, // 考试题
          answerList: [], // 答案
          examId: '', // 考试卷子id号,
          examName: '', // 考试卷子名称
          idNumber: '', // 考生id
          type: 6, // 考试类型
          currentIndex: 2         // 当前整套测量工具的状态
      };

      this.selectType = '';

      this.option = $.extend(option, this.option);
      this.PropellerObj = {};
      this.timerDown = null; // 剩余时间
      this.timerUp = null; // 已过时间
      this.examId = '';
      this.rulerFlag = {
          zoomVal: 1,
          left: 0,
          top: 0,
          currentX: 0,
          currentY: 0,
          flag: false
      };
      this.firstWidth = 0;
      this.firstHeight = 0;
      this.SizeNum = 1;
      this.bgFlag = true;
      this.rotateFlag = false;
      this.init();
  }

  // 初始化
  init() {
      this.$itemBtnBox = $('#item-btn-ul'); // 选题父级DOM
      this.$machineBtnBox = $('#machine-btn-box'); // 按键
      this.$itemBank = $('#item-bank'); // 题库
      this.$answerBank = $('#answerBank');
      this.$hand = $('#hand'); // 交卷按钮

      this.allClickEvents();           // 所有点击事件
      this.moveMap();                  // 移动地图
  }

  // 所有点击事件
  allClickEvents() {
      this.leftNavClick();        // 点击左侧菜单事件
      // this.questionsList('../js/json/examQuestions.json');
  }

  // 考试题及答案
  questionsList(id, name) {
      var _this = this;
      $.ajax({
          type: "get",
          url: '/springboot-course/exam/checkPapers?table=' + name + '&questionCode=' + id,
          dataType: "JSON",
          async: false,
          success: function (res) {
              _this.examId = res.data.examId;
              if (res.data == "") {
                  if (res.msg.indexOf('/') != -1) {
                      let time = res.msg.split('/');
                      window.parent.window.setOption.openEmptyTest(time[1], time[0]);
                  } else {
                      window.parent.window.setOption.openEmptyTest(0, res.msg);
                  }

              } else {
                  _this.option.testJsonList = res.data;

                  // ----------------- made by mc ---------------------

                  _this.option.examId = res.data.examId; //卷子id号
                  if (_this.selectType != 0) {
                      _this.option.examName = res.data.examName; // 考试卷子名称
                      _this.option.idNumber = res.data.idNumber; // 考生id
                      _this.option.type = 1; // 考试类型
                  }

                  $("#imgBox").attr("style", `background: url("../"+${res.data.examPhoto}) 0 0 no-repeat; background-size: 100% 100%;`);
                  _this.option.questionsList.data = res.data.testData;
                  // _this.drawAnswer(_this.option.testJsonList);
                  _this.drawQusetions(_this.option.questionsList.data);
              }
          },
          error: function (err) {
              console.log('数据返回错误', err);
          }
      });
  }
  // 渲染题库
  drawQusetions(data) {
      var _this = this;
      var j = 0;
      var $ul = $('<ul id="questions-ul" class="questions-ul">');
      var $li = $('<li class="questions-item">');

      for (let i = 0; i < data.length; i++) {
          if (data[i].type == "0" || data[i].type == "5") {
              $li = $('<li class="questions-item" data-id="' + data[i].number + '">');
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
              $.each(selectArr, function (index, item) {
                  if (data[i].type == "2") {
                      $('<li class="selectLi"><label><input type="radio" name="' + i + '" class="selectRadio oneSelect" value="' + item + '"/>' + item + '</label></li>').appendTo($selectBox);
                  } else if (data[i].type == "3") {
                      $('<li class="selectLi"><label><input name="~' + i + '" type="checkbox" class="selectRadio moreSelect" value="' + item + '""/>' + item + '</label></li>').appendTo($selectBox);
                  }
              })
              $selectBox.appendTo($li);
          }
          // 我的答案正确
          $('<div class="my-answer">我的答案: <img class="my-answer-img" src="../img/false.png"/></div>').appendTo($li);

          // 我的答案
          if (data[i].answer === 'true') {
              $('<div class="answer">操作成功</div>').appendTo($li);
          } else if (data[i].answer === 'false') {
              $('<div class="answer">操作失败</div>').appendTo($li);
          } else {
              $('<div class="answer">' + data[i].answer + '</div>').appendTo($li);
          }


          // 正确答案
          $('<div class="my-answer">正确答案:</div>').appendTo($li);
          if (data[i].result === 'true' || data[i].result === 'false') {
              $('<div class="answer">此题为操作题</div>').appendTo($li);
          } else {
              $('<div class="answer">' + data[i].result + '</div>').appendTo($li);
          }
          $li.appendTo($ul);
      }

      this.$itemBank.empty();

      $ul.appendTo(this.$itemBank);
      //训练查看答案
      $("#item-bank").on("click", ".answerIcon", function () {
          if ($(this).hasClass("glyphicon-eye-close")) {
              $(this).removeClass("glyphicon-eye-close");
              $(this).addClass("glyphicon-eye-open");
          } else {
              $(this).removeClass("glyphicon-eye-open");
              $(this).addClass("glyphicon-eye-close");
          }
          $(this).siblings("#answerBox").toggle();
      })
  }

  // 考题映射
  examMapper() {
      var _this = this;
      var arr = [];
      $.each(_this.option.questionsList.data, function (index, item) {
          arr[item.number] = {
              questionCode: item.question_code,
              table: item.table
          }
      });

      return arr;
  }

  /* ----------------- made by mc ----------------------- */
  /**
   * @description: 点击左侧菜单事件
   * @param {} 
   * @return: 
   */
  leftNavClick() {
      let _this = this;
      $('#leftNav').on('click', '.itemIcon', function () {
          $(this).addClass('active').siblings('.itemIcon').removeClass('active');
          let target = $(this).attr('x-target');
          if (target) {
              _this.showRuler(target);    // 显示尺子
          }

          // 放大
          if ($(this).hasClass('bigIcon')) {
              if (_this.option.currentIndex > 3) {
                  _this.option.currentIndex = 3;
              } else {
                  _this.option.currentIndex++;
              }
              _this.setSize();
          }

          // 缩小
          if ($(this).hasClass('reduceIcon')) {
              if (_this.option.currentIndex < 1) {
                  _this.option.currentIndex = 1;
              } else {
                  _this.option.currentIndex--;
              }
              _this.setSize();
          }
      });
  }

  /**
   * @description: 显示尺子
   * @param {target} 
   * @return: null
   */
  showRuler(target) {
      $(`.ruleBox[id=${target}]`).show().siblings('.ruleBox').hide();
      this.moveRuler();              // 移动和旋转尺子
  }

  /**
   * @description: 设置尺寸尺寸
   * @param {} 
   * @return: 
   */
  setSize() {
      switch (this.option.currentIndex) {
          case 1: $('#contentImage').removeClass('size-big size-small').addClass('size-small'); break;
          case 2: $('#contentImage').removeClass('size-big size-small'); break;
          case 3: $('#contentImage').removeClass('size-big size-small').addClass('size-big');
      }
      this.moveRuler();              // 移动和旋转尺子
      $('#imgBox').attr('style', 'top: 0px; left: 0px;');
  }

  /**
   * @description: 移动和旋转尺子
   * @param {} 
   * @return: 
   */
  moveRuler() {
      // 直尺
      new Drag({
          id: 'ruleBox',
          showAngle: true,
          showPosition: true,
          container: 'contentImage',
          canZoom: false,
          canPull: false
      })

      // 半圆仪
      new Drag({
          id: 'circlrRuleBox',
          showAngle: true,
          showPosition: true,
          container: 'contentImage',
          canZoom: false,
          canPull: false
      })

      // 短尺
      new Drag({
          id: 'shortRuler',
          showAngle: true,
          showPosition: true,
          container: 'contentImage',
          canZoom: false,
          canPull: false
      })
  }

  /**
  * @description: 移动地图
  * @param {} 
  * @return: 
  */
  moveMap() {
      this.startDrag();
  }

  /**
  * @description: 拖拽的实现
  * @param {} 
  * @return: 
  */
  startDrag() {
      //获取元素
      let dv = document.getElementById('imgBox');
      let x = 0;
      let y = 0;
      let l = 0;
      let t = 0;
      let top = 'auto';
      let bottom = 'auto';
      let left = 'auto';
      let right = 'auto';
      let isDown = false;

      //鼠标按下事件
      dv.onmousedown = function (e) {
          //获取x坐标和y坐标
          x = e.clientX;
          y = e.clientY;

          //获取左部和顶部的偏移量
          l = dv.offsetLeft;
          t = dv.offsetTop;
          //开关打开
          isDown = true;
          //设置样式  
          dv.style.cursor = 'move';
      }
      //鼠标移动
      document.onmousemove = function (e) {
          if (isDown == false) {
              return;
          }

          // let boxWidth = $('#imgBox')[0].offsetLeft;
          // let boxHeight = $('#imgBox')[0].offsetTop;
          let parWidth = $('#contentImage').width();
          let parHeight = $('#contentImage').height();

          //获取x和y
          let nx = e.clientX;
          let ny = e.clientY;
          //计算移动后的左偏移量和顶部的偏移量
          let nl = nx - (x - l);
          let nt = ny - (y - t);
          left = nl + 'px';
          top = nt + 'px';

          if (nl > 0) {
              left = '0px';
          }

          if (nt > 0) {
              top = '0px';
          }

          if (nl < - ($("#imgBox")[0].offsetWidth - parWidth)) {
              right = '0px';
              left = 'auto';
          }

          if (nt < - ($("#imgBox")[0].offsetHeight - parHeight)) {
              bottom = '0px';
              top = 'auto';
          }

          dv.style.left = left;
          dv.style.right = right;
          dv.style.bottom = bottom;
          dv.style.top = top;
      }
      //鼠标抬起事件
      dv.onmouseup = function () {
          //开关关闭
          isDown = false;
          dv.style.cursor = 'default';
      }
  };
};