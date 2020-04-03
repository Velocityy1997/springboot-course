$(function () {
  window.updata = function () {
    this.tableInit(this.option.defaultName, 1);
  }
  class createTable {
    constructor(option) {
      this.subject = '1';
      this.option = {
        testMessageList: []
      };
      this.option = $.extend(this.option, option);

      this.drawSelectList();      // 下拉框渲染
      this.drawTestList();        // 渲染考试信息数据
      this.tableInit(this.option.defaultName, 1);           // 表格初始化
      this.clickCell();           // 单击查看
      this.changeSelect();        // 下拉框选项
      this.changeSubject();       // 科目下拉框改变
    }

    // 表格初始化
    tableInit(defaultName, subject) {

      //先销毁表格
      $('#GridBox').bootstrapTable('destroy');
      //加载表格
      $('#GridBox').bootstrapTable({
        showHeader: true, //是否显示列头
        undefinedText: '',
        method: 'post',
        paginationHAlign: 'right',
        silent: true,
        url: "/springboot-course/score/getStudentsScore?name=" + defaultName + "&subject=" + subject,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, //是否显示分页（*）
        sortable: false, //是否启用排序
        sortOrder: "asc", //排序方式
        paginationPreText: '上一页',
        paginationNextText: '下一页',
        sidePagination: "client", //服务端处理分页
        // sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 12, //每页的记录行数（*）
        pageList: [8, 12, 15], //可供选择的每页的行数（*）
        search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        clickToSelect: true,
        uniqueId: "id", //每一行的唯一标识，一般为主键列
        columns: [{
          field: 'name',
          title: '姓名',
          align: 'center'
        }, {
          field: 'className',
          title: '单位',
          align: 'center'
        },
        {
          field: 'refrenceState',
          title: '参考状态',
          align: 'center'
        },
        {
          field: 'score',
          title: '得分',
          align: 'center'
        },
        {
          field: 'subject',
          title: '题目',
          align: 'center'
        }
        ]
      });
    }

    // 渲染考试信息数据
    drawTestList() {
      let testMessageList = this.option.testMessageList;
      $('#view-test-ul').empty();
      $.each(testMessageList, (index, item) => {
        let $li = $('<li class="view-test-li">');
        $(`<div class="view-test-text">${item.text}:</div>`).appendTo($li);
        $(`<div class="view-test-value">${item.value}</div>`).appendTo($li);
        $li.appendTo($('#view-test-ul'));
      });
    }

    // 点击查看
    clickCell() {
      $('#GridBox').on("click-cell.bs.table", function (field, value, row, $element) {
        if (row == '查看') {
          window.top.window.openCheckPaper($element.examId);
        }
      });
    }

    // 下拉框渲染
    drawSelectList() {
      $('#examSelectList').empty();
      let examSelectList = this.option.examSelectList;
      $.each(examSelectList, (index, item) => {
        $(`<option value="${item}">${item}</option>`).appendTo($('#examSelectList'));
      });
    }

    // 改变下拉框
    changeSelect() {
      let _this = this;
      $('#selectBox').on('change', '#examSelectList', function () {
        let $val = $(this).val();
        new Promise((resolve, reject) => {
          $.ajax({
            url: '/springboot-course/score/show?examName=' + $val + '&subject=' + _this.subject,
            dataType: "json",
            type: "post",
            success: function (res) {
              resolve(res.data)
            }
          });
        }).then(res => {
          _this.option.testMessageList = res;
          _this.drawTestList();        // 渲染考试信息数据
          _this.tableInit($val, _this.subject);           // 表格初始化
        })
      });
    }

    // 改变科目的下拉框
    changeSubject() {
      let _this = this;

      $('#selectBoxSubject').on('change', '#examSelectSubject', function () {
        _this.subject = $(this).val();
        new Promise((resolve, reject) => {
          $.ajax({
            url: `/springboot-course/score/examList?subject=${_this.subject}`,
            dataType: "json",
            type: "post",
            success: function (res) {
              resolve(res.data)
            }
          });
        }).then(res => {
          _this.option.examSelectList = res;
          _this.option.defaultName = res[0];
          return new Promise((resolve, reject) => {
            $.ajax({
              url: '/springboot-course/score/show?examName=' + res[0] + '&subject=1',
              dataType: "json",
              type: "post",
              success: function (res) {
                resolve(res.data)
              }
            });
          });
        }).then(res => {
          _this.option.testMessageList = res;
          _this.drawSelectList();      // 下拉框渲染
          _this.drawTestList();        // 渲染考试信息数据
          _this.tableInit(_this.option.defaultName, _this.subject);           // 表格初始化
        });
      });
    }
  }

  // -----------------------------------
  // 获取数据
  let option = {};
  const getData1 = new Promise((resolve, reject) => {
    $.ajax({
      url: '/springboot-course/score/examList?subject=1',
      dataType: "json",
      type: "post",
      success: function (res) {
        resolve(res.data)
      }
    });
  }).then(res => {
    option.examSelectList = res;
    option.defaultName = res[0];
    return new Promise((resolve, reject) => {
      $.ajax({
        url: '/springboot-course/score/show?examName=' + res[0] + '&subject=1',
        dataType: "json",
        type: "post",
        success: function (res) {
          resolve(res.data)
        }
      });
    });
  }).then(res => {
    option.testMessageList = res;
    new createTable(option);
  });
})
