class obj {
  constructor() {
    this.option = {
      selectName: '',
      time: null,
      flag: ''
    }
    this.init();
  }

  init() {
    this.getListName();       // 获取本场考试名称
    this.useLayui();
    this.clickAllEvents();    // 所有的点击事件
  }

  useLayui() {
    let _this = this;
    layui.use(['form', 'layedit', 'laydate'], function () {
      var form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate;

      //监听提交
      form.on('select(examName)', function (data) {
        _this.option.selectName = data.value;
        _this.getProgressData();
        _this.getLeftList();
        _this.getRightList();
      });
    });
  }

  clickAllEvents() {
    this.closeHint();         // 关闭弹框
  }

  getLeftList() {
    let _this = this;
    $('#system-hint').removeClass('active');
    new Promise((resolve, reject) => {
      $.post('/springboot-course/exam/getNew', { examName: _this.option.selectName }, function (data) {
        resolve(data);
      })
    }).then(res => {
      _this.drawLeftList(res);
    }).catch(err => console.log('err'));
  }

  getRightList() {
    let _this = this;
    new Promise((resolve, reject) => {
      $.post('/springboot-course/exam/getOrder', { examName: _this.option.selectName }, function (data) {
        resolve(data);
      })
    }).then(res => {
      _this.drawRightList(res);
    }).catch(err => console.log('err'));
  }

  drawLeftList(data) {
    $('#main-ul').empty();
    let result = data.data;

    this.option.flag = result[0].userid;
    for (let i in result) {
      let $li = $('<li class="main-li">');
      let $div = $('<div class="box">');
      let $head = $('<div class="main-li-head">');
      $(`<div class="main-score"><span class="score-num">${result[i].grade}</span>分</div>`).appendTo($head);
      $(`<div class="ranking">排名: <span class="ranking-num">${result[i].id}</span>名</div>`).appendTo($head);
      $head.appendTo($div);
      $(`<div class="main-name">${result[i].name}</div>`).appendTo($div);
      $(`<div class="main-time">${result[i].time}</div>`).appendTo($div);
      $div.appendTo($li);
      $li.appendTo($('#main-ul'));
    }
  }

  drawRightList(data) {
    $('#list').empty();
    let result = data.data;
    for (let i in data.data) {
      let $li = $('<li class="list-li">');
      let $listLeft = $('<div class="list-li-left">');
      if(result[i].id == 1) {
        $('<div class="list-medal first"></div>').appendTo($listLeft);
      }else if(result[i].id == 2) {
        $('<div class="list-medal second"></div>').appendTo($listLeft);
      }else if(result[i].id == 3) {
        $('<div class="list-medal third"></div>').appendTo($listLeft);
      }else {
        $('<div class="list-medal"></div>').appendTo($listLeft);
      }

      $(`<div class="list-num">${result[i].id}</div>`).appendTo($listLeft);
      $listLeft.appendTo($li);
      $(`<div class="list-li-name">${result[i].name}</div>`).appendTo($li);
      $(`<div class="list-li-score">${result[i].grade}</div>`).appendTo($li);
      $li.appendTo($('#list'));
    }
  }

  // 每3秒刷新数据
  setTime() {
    let _this = this;
    clearInterval(_this.option.time);
    _this.option.time = setInterval(() => {
      _this.getHintList();
    }, 5000);
  }

  // 获取系统提示的内容
  getHintList() {
    let _this = this;
    new Promise((resolve, reject) => {
      $.post('/springboot-course/exam/getNewStudent', { examName: _this.option.selectName }, function (data) {
        resolve(data);
      });
    }).then(res => {
      _this.drawHintList(res);
    }).catch(err => console.log('err'));
  }

  // 渲染弹框列表DOM
  drawHintList(data) {
    let _this = this;
    let result = data.data;
    if (Object.keys(result).length > 0 && _this.option.flag !== result.userid) {
      $('#system-hint').removeClass('active').empty();
      let $li = $('<div class="main-li">');
      let $div = $('<div class="box">');
      let $head = $('<div class="main-li-head">');
      $(`<div class="main-score"><span class="score-num" >${result.grade}</span>分</div>`).appendTo($head);
      $(`<div class="ranking">排名: <span class="ranking-num">${result.id}</span>名</div>`).appendTo($head);
      $head.appendTo($div);
      $(`<div class="main-name">${result.name}</div>`).appendTo($div);
      $(`<div class="main-time">${result.time}</div>`).appendTo($div);

      $div.appendTo($li);
      $li.appendTo($('#system-hint'));
      $('#system-hint').addClass('active');
      _this.option.flag = result.userid;

      _this.waitDrawList();
    }
  }

  // 等待2s后渲染左右两侧数据
  waitDrawList() {
    let _this = this;
    setTimeout(() => {
      $('#system-hint').removeClass('active');
      _this.getProgressData(); 
      _this.getLeftList();
      _this.getRightList();
    }, 2000);
  }

  // 获取本场考试名称
  getListName() {
    new Promise((resolve, reject) => {
      $.ajaxSettings.async = false;
      $.get('/springboot-course/exam/getList', function (data) {
        resolve(data);
      });
    }).then(res => {
      this.drawListName(res);
    }).catch(err => console.log('err'));
  }
  drawListName(res) {
    if (res.success === false) {
      $('#pageShow').fadeIn(500);
      $('#hint-text').text(res.msg);
    } else {
      $('#pageShow').fadeOut(500);
      let result = res.data;
      let _this = this;
      $('#select').empty();
      $.each(result, function (index, item) {
        if (index == 0) {
          _this.option.selectName = item;
        }
        $(`<option class="${item}">${item}</option>`).appendTo($('#select'));
      });
      this.getProgressData();       // 进度条数据
      this.getLeftList();       // 渲染左侧数据
      this.getRightList();      // 渲染右侧数据
      this.setTime();           // 每3秒刷新数据
    }
  }

  // 获取进度条数据
  getProgressData() {
    let _this = this;
    new Promise((resolve, reject) => {
      $.post('/springboot-course/exam/getProgress', { examName: _this.option.selectName }, function (data) {
        resolve(data);
      });
    }).then(res => {
      $('#progress').attr('style', `width: ${res.data}%`);
      $('#progress-num').text(`${res.data}%`);
    }).catch(err => console.log('err'));
  }

  // 关闭弹出框
  closeHint() {
    $('.hint-close').off('click').on('click', function () {
      $('#pageShow').fadeOut(500);
    });
  }
}

// -----------------
new obj();