$(function() {

    class createTable {
        constructor(option) {
            this.option = {
                userlevelList: [],
                classList: [],
                levelMapperArr: [],
                addPic: '', // 添加的图片base64编码 
                editPic: '' // 编辑的图片base64编码 
            };
            this.option = $.extend(this.option, option);
            this.picListener(); // 监听添加中图片变化
            this.formVal(); // 表单验证
            this.drawLevel(); // 描绘等级 
            this.drawClassList(); // 描绘班级
            this.tableInit("/springboot-course/user/list", ''); // 表格初始化
            this.edit(); // 修改
            this.deleteData(); // 删除
            this.findData(); // 查询数据
            this.resetPwd(); // 重置密码
            this.editId = "";
        }

        // 描绘等级 
        drawLevel() {
            let userlevelList = this.option.userlevelList;
            this.option.levelMapperArr = [];
            $.each(userlevelList, (index, item) => {
                this.option.levelMapperArr[item.name] = item.levelId;
                $(`<option value="${item.levelId}">${item.name}</option>`).appendTo($('#findGrade'));
                $(`<option value="${item.levelId}">${item.name}</option>`).appendTo($('#level1'));
                $(`<option value="${item.levelId}">${item.name}</option>`).appendTo($('#level2'));
            });
        }

        // 描绘班级
        drawClassList() {

            let classList = this.option.classList;

            $.each(classList, (index, item) => {

                $(`<option value="${item.classId}">${item.className}</option>`).appendTo($('#findClass'));
                $(`<option value="${item.classId}">${item.className}</option>`).appendTo($('#class1'));
                $(`<option value="${item.classId}">${item.className}</option>`).appendTo($('#class2'));
            });
        }

        // 转化为时间戳
        turnTime(date) {
            date = date.replace(/-/g, '/');
            return new Date(date);
        }

        // 表格初始化
        tableInit(url, option) {
            //先销毁表格
            $('#GridBox').bootstrapTable('destroy');
            //加载表格

            $('#GridBox').bootstrapTable({
                showHeader: true, //是否显示列头
                undefinedText: '',
                theadClasses: "thead-light", //这里设置表头样式
                toolbar: '#toolbar',
                toolbarAlign: 'left',
                paginationHAlign: 'right',
                silent: true,
                url: url,
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",
                striped: true, //是否显示行间隔色
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true, //是否显示分页（*）
                sortable: false, //是否启用排序
                sortOrder: "asc", //排序方式
                queryParams: function(params) { //上传服务器的参数
                    var temp = { //如果是在服务器端实现分页，limit、offset这两个参数是必须的
                        limit: params.limit, // 每页显示数量
                        offset: params.pageNumber // SQL语句起始索引
                    };

                    if (option != '') {
                        var temp = { //如果是在服务器端实现分页，limit、offset这两个参数是必须的
                            limit: params.limit, // 每页显示数量
                            offset: params.pageNumber, // SQL语句起始索引
                            name: option.name,
                            classId: option.classId,
                            levelId: option.levelId,
                            type: option.type
                        };
                    }
                    return temp;
                },
                paginationPreText: '上一页',
                paginationNextText: '下一页',
                sidePagination: "server", //服务端处理分页
                // sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1, //初始化加载第一页，默认第一页
                pageSize: 7, //每页的记录行数（*）
                pageList: [5, 7, 10], //可供选择的每页的行数（*）
                search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                clickToSelect: false,
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                columns: [{
                        checkbox: true
                    },
                    {
                        field: 'portrait',
                        title: '',
                        formatter: function(value, row, index) {
                            if (row.userImg == null || row.userImg == "") {
                                return '<img  src= ../img/user.png class="img-rounded" >';
                            } else {
                                return '<img  src= data:image/png;base64,' + row.userImg + ' class="img-rounded" >';
                            }

                        },
                        align: 'center'
                    },
                    {
                        field: 'name',
                        title: '姓名',
                        align: 'center'
                    },
                    {
                        field: 'sex',
                        title: '性别',
                        align: 'center'
                    },
                    // {
                    //   field: 'age',
                    //   title: '年龄',
                    //   align: 'center'
                    // },
                    {
                        field: 'className',
                        title: '单位',
                        align: 'center'
                    },
                    {
                        field: 'levelName',
                        title: '级别',
                        align: 'center'
                    },
                    {
                        field: 'cardId',
                        title: '证件号',
                        align: 'center'
                    },
                    // {
                    //   field: 'information',
                    //   title: '联系方式',
                    //   align: 'center'
                    // },
                    {
                        field: 'jurisdiction',
                        title: '权限',
                        align: 'center'
                    },
                    {
                        field: 'operation',
                        title: '操作',
                        align: 'center',
                        formatter: function(value, row, index) {
                            return '<button class="btn btn-default reset" type="button">重置</button>'
                        }
                    }
                ]
            });
        }

        // 更改数据
        edit() {
            let _this = this;
            $('#btn-edit').on('click', function() {
                $('.modal2').attr('id', 'exampleModal2');
                var selectData = $('#GridBox').bootstrapTable('getSelections');
                if (selectData.length) {
                    $('#name2').val(selectData[0].name);
                    if (selectData[0].sex == '男') {
                        $('#sex2').val("1");
                    } else if (selectData[0].sex == '女') {
                        $('#sex2').val("0");
                    } else {
                        $('#sex2').val('');
                    }
                    $('#level2').val(_this.option.levelMapperArr[selectData[0].levelName]);

                    $("#class2").val(selectData[0].classId);

                    // $('#birthday2').val(selectData[0].birthday);

                    $('#cardId2').val(selectData[0].cardId);
                    // $('#phone2').val(selectData[0].information);

                    if (selectData[0].userImg == null || selectData[0].userImg == "") {
                        _this.option.editPic = '../img/user.png'
                    } else {
                        _this.option.editPic = 'data:image/png;base64,' + selectData[0].userImg
                    }



                    if (selectData[0].jurisdiction == '学员') {
                        $('#qx2').val("1");
                    } else if (selectData[0].jurisdiction == '管理员') {
                        $('#qx2').val("0");
                    } else {
                        $('#qx2').val('');
                    }
                    _this.editId = selectData[0].id;
                    layui.form.render('select');
                    $('#password2').val(selectData[0].password);
                } else {
                    $('.modal2').attr('id', '');
                }

            });

        }

        // 删除数据
        deleteData() {
            var needDeleteList = [];
            var needDelete = '';

            $('#btn-del').on('click', function() {
                needDelete = '';
                needDeleteList = $('#GridBox').bootstrapTable('getSelections');
                $.each(needDeleteList, function(i, item) {
                    needDelete += item.id + ';';
                });
                needDelete = needDelete.slice(0, needDelete.length - 1);
                if (needDelete != '') {
                    $("#tipMes").text("确认删除");
                    $(this).attr('data-target', "#exampleModal3");

                }
            });

            $('#delete').on('click', function() {

                new Promise((resolve, reject) => {
                    $.post('/springboot-course/user/Delete', { id: needDelete }, function(res) {
                        resolve(res);
                    });
                }).then(res => {
                    $('#isDeleteText').text(res.msg);
                    $('#isDelete').modal();
                    $("#GridBox").bootstrapTable('refresh');
                }).catch(err => console.log(err));

            });
        }

        // 查询
        findData() {
            // $('#find').on('click', function () {
            //   let findData = {};
            //   findData.findName = $('#findName').val();
            //   findData.findClass = $('#findClass').val();
            //   findData.findGrade = $('#findGrade').val();
            //   findData.findQx = $('#findQx').val();

            //   $.ajax({
            //     url: '../js/manage/find.json?find=' + findData,
            //     dataType: 'json',
            //     success: function (res) {
            //       $("#GridBox").bootstrapTable('load', res.data);
            //     }
            //   });
            // });
        }

        // 表单校验
        formVal() {
            var _this = this;
            layui.use(['form', 'layedit', 'laydate', 'upload', 'layer'], function() {
                var form = layui.form,
                    layer = layui.layer,
                    layedit = layui.layedit,
                    laydate = layui.laydate,
                    upload = layui.upload;

                //日期
                laydate.render({
                    elem: '#birthday1',
                    type: 'datetime'
                });
                laydate.render({
                    elem: '#birthday2',
                    type: 'datetime'
                });

                //执行实例
                let uploadInst = upload.render({
                    elem: '#excel-input' //绑定元素
                        ,
                    accept: 'file',
                    url: '/springboot-course/user/addUserExcel' //上传接口
                        ,
                    done: function(res) {
                        if (res.code === 500) {
                            layer.msg('上传失败', {
                                time: 5000
                            });
                        } else if (res.code === 400) {
                            layer.msg('添加失败', {
                                time: 5000
                            });
                        } else {

                            if (!res.level && !res.class) {
                                layer.msg('录入成功', {
                                    time: 5000
                                });
                                return;
                            }

                            let str = `${res.msg}<br/>`;
                            let userKeys = Object.keys(res.user);
                            if (res.level.length > 0) {
                                str += `某人缺少级别${res.level.join(',')}<br/>`;
                            }

                            if (res.class.length > 0) {
                                str += `某人缺少单位${res.class.join(',')}<br/>`;
                            }

                            if (userKeys.length > 0) {
                                for (let key in res.user) {
                                    if (res.user[key]) {
                                        str += `${key}:${res.user[key]}<br/>`;
                                    }
                                }
                            }
                            if (str)
                            //配置一个透明的询问框
                                layer.msg(str, {
                                time: 20000, //20s后自动关闭
                                btn: ['确定']
                            });
                        }
                    },
                    error: function() {
                        //请求异常回调
                    }
                });

                //自定义验证规则
                form.verify({
                    nameAdd: function(value) {
                        if (value.length < 5) {
                            return '标题至少得5个字符';
                        }
                    },
                    name: [
                        /^[\u4E00-\u9FA5\uf900-\ufa2d·s]{2,20}$/, '请输入正确的姓名'
                    ],
                    pass: [
                        /^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'
                    ],
                    identity: [
                        /^[a-zA-Z0-9]{7}$/, "请输入正确的证件号"
                    ],
                    tel: [
                        /^1(3|4|5|7|8|9)\d{9}$/, "请输入正确的手机号"
                    ],
                    content: function(value) {
                        layedit.sync(editIndex);
                    }
                });
                _this.form = form;

                //监听提交
                form.on('submit(demo1)', function(data) {
                    let option = {};
                    let addImg = _this.option.addPic.split(',');
                    option.photo = addImg[1];
                    option.name = $('#name').val();
                    option.sex = $('#sex').val();
                    // option.birthday1 = $('#birthday1').val();
                    option.classId = $('#class1').val();
                    if (option.classId == '1') {
                        option.className = '2019届11班';
                    } else if (option.classId == '2') {
                        option.className = '2019届12班';
                    }
                    option.levelId = $('#level1').val();
                    option.idNumber = $('#cardId').val();
                    // option.tel = $('#phone').val();
                    option.type = $('#qx').val();

                    // option.password = $('#password').val();
                    new Promise((resolve, reject) => {
                        $.post('/springboot-course/user/Add', option, function(res) {
                            resolve(res);
                        });
                    }).then(res => {
                        $("#exampleModal1").modal('hide');
                        $("#exampleModal4text").text(res.msg);
                        $("#exampleModal4").modal("show");
                        $("#GridBox").bootstrapTable('refresh');
                        $("#addRest")[0].reset();
                        _this.option.addPic = '';
                    })
                    return false;
                });

                form.on('submit(demo2)', function(data) {
                    let option = {};
                    option.photo = _this.option.editPic.split(',');
                    option.photo = option.photo[1];
                    option.name = $('#name2').val();
                    option.sex = $('#sex2').val();
                    // option.birthday = $('#birthday2').val();
                    option.classId = $('#class2').val();
                    option.levelId = $('#level2').val();
                    option.idNumber = $('#cardId2').val();
                    // option.tel = $('#phone2').val();
                    option.type = $('#qx2').val();
                    option.id = _this.editId;


                    new Promise((resolve, reject) => {
                        $.post('/springboot-course/user/Edit', option, function(res) {
                            resolve(res);
                        });
                    }).then(res => {

                        // if (res.data) {
                        $("#exampleModal2").modal('hide');
                        $("#exampleModal4text").text(res.msg);
                        $("#exampleModal4").modal("show");
                        $("#GridBox").bootstrapTable('refresh');
                        _this.option.editPic = '';
                        // }
                    })
                    return false;
                });
                form.on('submit(demo3)', function(res) {

                    let findData = {};
                    findData.name = $('#findName').val();

                    findData.classId = $('#findClass').val();
                    findData.levelId = $('#findGrade').val();
                    findData.type = $('#findQx').val();
                    if (findData.name == '') {
                        findData.name = null;
                    }
                    if (findData.classId == '') {
                        findData.classId = null;
                    }
                    if (findData.levelId == '') {
                        findData.levelId = null;
                    }
                    if (findData.type == '') {
                        findData.type = null;
                    }

                    new Promise((resolve, reject) => {
                        _this.tableInit('/springboot-course/user/Find', findData);
                    })
                });

            });
        }


        // 转为base64编码
        imgChange(obj, changeUrl) {
            let _this = this;
            let image = obj.files[0]; //获取文件域中选中的图片
            let reader = new FileReader(); //实例化文件读取对象
            reader.readAsDataURL(image); //将文件读取为 DataURL,也就是base64编码
            reader.onload = function(ev) { //文件读取成功完成时触发
                let dataUrl = ev.target.result; //获得文件读取成功后的DataURL,也就是base64编码
                changeUrl(dataUrl);
            }
        }

        // 监听图像变化
        picListener() {
            let _this = this;
            $('#pic').on('change', function() {
                _this.imgChange(this, res => {
                    _this.option.addPic = res;
                });
            });
            $('#pic2').on('change', function() {
                _this.imgChange(this, res => {
                    _this.option.editPic = res;

                });
            });
        }

        // 重置
        resetPwd() {
            let resetIdNumber = '';
            $('#GridBox').on('click-cell.bs.table', function(field, value, row, $element) {
                if (value == 'operation') {
                    resetIdNumber = $element.cardId;
                    $('#resetPwdSure').modal();
                }
            });

            // 确认重置
            $('#resetTure').on('click', function() {
                new Promise((resolve, reject) => {
                    $.post('/springboot-course/user/resetPd', { idNumber: resetIdNumber }, function(res) {
                        resolve(res);
                    });
                }).then(res => {
                    $('#modal-body-reset').text(res.msg);
                    $('#resetPwdSure2').modal();
                });
            });
        }
    }



    // 获取才开始渲染的数据
    const userlevelList = new Promise((resolve, reject) => {
        $.ajax({
            url: '/springboot-course/userlevel/list',
            type: 'get',
            dataType: 'json',
            success: (data) => {
                resolve(data);
            }
        });
    });
    const classList = new Promise((resolve, reject) => {
        $.ajax({
            url: '/springboot-course/class/list',
            type: 'get',
            dataType: 'json',
            success: (data) => {
                resolve(data);
            }
        });
    });

    Promise.all([userlevelList, classList]).then(results => {
        let option = {
            userlevelList: results[0],
            classList: results[1]
        }

        var newObj = new createTable(option);
        window.updata = function() {
            newObj.tableInit("/springboot-course/user/list", '');
        }
    });
})