$(function () {
  class addQuestions {
    constructor() {
      this.init();            // 初始化
    }

    init() {
      this.useLayuiForm();    // 使用layui表单渲染
      this.issue();           // 出题
    }

    // 使用layui表单渲染
    useLayuiForm() {

      layui.use(['form', 'layedit', 'upload'], function () {

        var form = layui.form,
          layer = layui.layer,
          layedit = layui.layedit;

        //自定义验证规则
        form.verify({
          title: function (value) {
            if (value.length < 5) {
              return '标题至少得5个字符啊';
            }
          },
          pass: [
            /^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'
          ],
          content: function (value) {
            layedit.sync(editIndex);
          }
        });

        //多图片上传
        var $ = layui.jquery
          , upload = layui.upload;
        upload.render({
          elem: '#test10'
          , auto: false
          , multiple: true
          , choose: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
              if ($('#imgList').find('img').length >= 3) {
                alert('上传图片超过三张');
              } else {
                $('#imgList').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
              }
            });
          }
          , done: function (res) {
            //上传完毕
          }
        });

        //监听提交
        form.on('submit(commit)', function (data) {
          console.log('data', data);
          let arr = [];
          $('#imgList').find('.layui-upload-img').each(function () {
            arr.push($(this).attr('src'));
          });
          new Promise((resolve, reject) => {
            $.post('/addQuestion/', {
              answer: data.title,
              issue: data.issue,
              fileList: arr
            }, function (res) {
              resolve(res);
            });
          }).then(data => {
            alert(data);
          });

        });

        // 监听select的change事件
        form.on('select(selectChange)', function (data) {
          debugger
          if (data.value == '填空题') {
           $('#showPic').hide();
          } else {
            $('#showPic').show();
          }
          form.render('select');//select是固定写法 不是选择器
        });
      });
    }

    // 出题
    issue() {
      let $val;
      let $str = '';
      $("textarea[name='issue']").on('keyup', function (event) {
        $val = $(this).val();
        if ($(this).val().indexOf('@') != -1) {
          $val = $val.replace(/@/g, '_');
        }
        // if (13 == event.keyCode) {
        //   $val += 'n';
        //   $str = $val;
        // }


        // if ($val.indexOf('n') == -1 && $str=='') {
        //   $(`<div class="setHeight">${$val}</div>`).appendTo($('#text-box'));
        // } else {
        //   if($str.slice($str.length-1, $str.length) == 'n') {
        //     let arr = $str.split('n');
        //     debugger
        //     for (let i = arr.length-2; i < arr.length; i++) {
        //       $(`<div class="setHeight">${arr[i]}</div>`).appendTo($('#text-box'));
        //     }
        //   }
        // }
        $('#qusetionText').html($val);
      });
    }
  }

  // ------------ 实例化对象 ----------
  new addQuestions();
});