class navObj {

    constructor() {
        this.svgConfig = {
            svgns: 'http://www.w3.org/2000/svg',
            timepath: '',
            container: null,
            svgcontainer: null,
            timmerHandle: null,
            isDrag: false
        }
        this.canvas,
            this.context;
        this.img, //图片对象
            this.icon, //中间目标图标
            this.carIcon, //小车的位置图标
            this.imgIsLoaded, //图片是否加载完成;
            this.iconIsLoaded, //中间目标图标是否加载完成;
            this.carIsLoaded, //小车图标是否加载完成;
            this.imgX = 0,
            this.imgY = 0,
            this.imgScale = 1;
        this.initCarX = 96 - 12; //小车初始地方
        this.initCarY = 100 - 12; //小车初始地方
        this.carIconX = this.initCarX;
        this.carIconY = this.initCarY;
        this.iconX = 96 - 20; //中间目标图标位置
        this.iconY = 100 - 20; //中间目标图标位置
        this.X, this.Y, this.X1, this.Y1; //画图坐标
        this.pointArr = [
            [84, 88],
            [this.carIconX, this.carIconY],
            // [20, 20],
            // [50, 40],
            // [10, 50],
            // [10, 100]
        ];
        this.isMouseDown = false; //记录鼠标是否按下
        this.flag = 0; //记录绘图路径
        this.scaleFlag = 0; //缩放等级
        this.init();
    }
    init() {
        // this.getDom();              // 获取Dom
        this.allClickEvents(); // 所有点击事件
        // this.moveMap();             // 移动地图
        this.canvas = document.getElementById('canvas');
        this.context = canvas.getContext('2d');
        this.loadImg();
        this.mapClick();
        this.scaleBtnClick();
        this.bigBtnClick();
        this.editNowPos();
    }

    //绘制画图   
    drowline() {
            this.context.strokeStyle = "red";
            //移动画笔的初始位置
            var pointStart = this.pointArr[0];
            this.context.beginPath();
            this.context.moveTo(pointStart[0], pointStart[1]);
            for (let i = 0; i < this.pointArr.length - 1; i++) {
                this.context.lineTo(this.pointArr[i + 1][0], this.pointArr[i + 1][1]);
            }
            //开始绘制
            this.context.stroke();

            if (this.flag != 0) {
                this.X = this.X1;
                this.Y = this.Y1;
            }
        }
        //图片拖放缩小
    loadImg() {
            this.img = new Image();
            let that = this;
            this.img.onload = function() {
                that.imgIsLoaded = true;
                that.drawImage();
            }
            this.img.src = "../img/image/map.jpg"; //矢量图

            this.icon = new Image();
            this.icon.onload = function() {
                that.iconIsLoaded = true;
                that.drawImage();
            }
            this.icon.src = "../img/point.png"; // 地图上的图标

            this.carIcon = new Image();
            this.carIcon.onload = function() {
                that.carIsLoaded = true;
                that.drawImage();
            }
            this.carIcon.src = "../img/navigation/right5.png"; // 地图上的图标

        }
        //绘制地图
    drawImage() {
            this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
            this.context.drawImage(this.img, 0, 0, this.img.width, this.img.height, this.imgX, this.imgY, this.img.width * this.imgScale, this.img.height * this.imgScale);
            this.context.drawImage(this.carIcon, this.carIconX, this.carIconY);
            this.context.drawImage(this.icon, this.iconX, this.iconY);
            // this.drowline();
        }
        //地图移动
    mapClick() {
        let that = this;
        this.canvas.onclick = function(event) {
            var pos = that.windowToCanvas(that.canvas, event.clientX, event.clientY);

            var x = 96 - pos.x;
            var y = 100 - pos.y;
            // if (imgX + x > 0 || imgY + y > 0 || imgY + y < -img.height || imgX + x < -img.width) {
            //     return;
            // } else { //边界值判断
            that.imgX += x;
            that.imgY += y;
            that.carIconX = that.imgX + that.initCarX;
            that.carIconY = that.imgY + that.initCarY;
            for (let i = 0; i < that.pointArr.length; i++) {
                that.pointArr[i][0] += x;
                that.pointArr[i][1] += y;
            }
            that.drawImage();
            let carIconXY = [that.carIconX + 10, that.carIconY + 10];
            let initCarXY = [that.initCarX + 10, that.initCarY + 10];
            that.pointArr.splice(0, 2, initCarXY, carIconXY);
            // console.log(that.pointArr);

            // }
        }
    }
    scale(type) {
        var pos = this.windowToCanvas(this.canvas, 96, 100); //缩放中心点
        if (type == 0) {
            if (this.scaleFlag >= 2) {
                return;
            } else {
                this.imgScale *= 2;
                this.imgX = this.imgX * 2 - pos.x;
                this.imgY = this.imgY * 2 - pos.y;
                this.initCarX = this.initCarX * 2;
                this.initCarY = this.initCarY * 2;
                this.carIconX = this.imgX + this.initCarX;
                this.carIconY = this.imgY + this.initCarY;
                for (let i = 0; i < this.pointArr.length; i++) {
                    this.pointArr[i][0] = this.imgX + this.pointArr[i][0] * 2;
                    this.pointArr[i][1] = this.imgY + this.pointArr[i][1] * 2;
                }
                this.scaleFlag++;
            }

        } else {
            if (this.scaleFlag <= -2) {
                return;
            } else {
                this.imgScale /= 2;
                this.imgX = this.imgX * 0.5 + pos.x * 0.5;
                this.imgY = this.imgY * 0.5 + pos.y * 0.5;
                this.initCarX = this.initCarX / 2;
                this.initCarY = this.initCarY / 2;
                this.carIconX = this.imgX + this.initCarX;
                this.carIconY = this.imgY + this.initCarY;
                for (let i = 0; i < this.pointArr.length; i++) {
                    this.pointArr[i][0] = this.imgX + this.pointArr[i][0] / 2;
                    this.pointArr[i][1] = this.imgY + this.pointArr[i][1] / 2;
                }
                this.scaleFlag--;
            }
        }
        this.drawImage();
    }
    windowToCanvas(canvas, x, y) {
            var bbox = canvas.getBoundingClientRect();
            return {
                x: x - bbox.left - (bbox.width - canvas.width) / 2,
                y: y - bbox.top - (bbox.height - canvas.height) / 2
            };
        }
        //缩小地图
    scaleBtnClick() {
            var that = this;
            $("#scaleBtn").on("click", function() {
                that.scale(1);
            })
        }
        //放大地图
    bigBtnClick() {
        var that = this;
        $("#bigBtn").on("click", function() {
            that.scale(0);
        })
    }
    editNowPos() {
        var that = this;
        $("#editNow").on("click", function() {
            that.initCarX = 84;
            that.initCarY = 88;
            that.carIconX = that.initCarX;
            that.carIconY = that.initCarY;
            that.drawImage();
        })
    }










    /**
     * @description: 所有点击事件
     * @method: allClickEvents
     * @param: {}
     * @return: void
     */
    allClickEvents() {
        this.clickLeftTools(); // 点击左侧工具栏
        // this.imgClick();          // 点击图片
        this.setCorClick(); // 是否将当前点设置成新的目的地提示框
        this.isSaveClick(); // 是否将保存路径计算结果
        this.saveRoadName(); // 请输入道路名称
        this.startSimulate(); // 开始模拟
    }

    /**
     * @description: 点击左侧工具栏
     * @method: clickLeftTools
     * @param: {}
     * @return: void
     */
    clickLeftTools() {
        $('#navigationItem').off('click').on('click', function() {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('#nav-serve-ul').show();
                return;
            }
            $(this).removeClass('active').siblings('#nav-serve-ul').hide();
        })

        $('#nav-serve-ul').off('click').on('click', '.nav-item', function() {
            let xFrom = $(this).attr('x-from');
            $(`.screen-box[x-to=${xFrom}]`).addClass('active');
        });
    }

    /**
     * @description: 获取DOM
     * @method: getDom
     * @param: {}
     * @return: void
     */
    getDom() {
        let _this = this;
        this.svgConfig.container = document.getElementById("img");
        this.svgConfig.svgcontainer = document.getElementById("svgcontainer");
        this.svgConfig.svger = document.createElementNS(_this.svgConfig.svgns, "svg");
        this.svgConfig.svger.setAttribute("width", _this.svgConfig.container.clientWidth);
        this.svgConfig.svger.setAttribute("height", _this.svgConfig.container.clientHeight);
        this.svgConfig.svger.setAttribute("viewBox", "0 0 " + _this.svgConfig.container.clientWidth + " " + _this.svgConfig.container.clientHeight);
        this.svgConfig.svgcontainer.appendChild(_this.svgConfig.svger);

        this.svgConfig.posX = $('#coordinate').offset().left;
        this.svgConfig.posY = $('#coordinate').offset().top;
    }

    /**
     * @description: 点击图片
     * @method: imgClick
     * @param: {}
     * @return: void
     */
    imgClick() {
        let _this = this;
        $('#svgcontainer').off('mousedown').on('click', (event) => {

            // let e = event || window.event; //事件对象
            // //获取点击地鼠标位置
            // let mousePosition = _this.mousePos(e);
            // //新增点
            // _this.creatdot(mousePosition.x, mousePosition.y);
            // //连接点
            // let dots = this.svgConfig.container.children;
            // _this.linedot(dots[dots.length - 2], dots[dots.length - 1]);

            //因为mouseDownFun每次都会调用的，在这里重新初始化这个变量
            _this.svgConfig.isDrag = false;
        });

        $('#svgcontainer').off('mousemove').on('click', (event) => {
            _this.svgConfig.isDrag = true;
        });

        $('#svgcontainer').off('mouseup').on('mouseup', () => {
            if (_this.svgConfig.isDrag) {
                console.log("移动后鼠标松开事件");
            } else {
                console.log("没有移动鼠标松开事件,模拟click");
            }
            _this.svgConfig.isDrag = false; //还原标识，以便下一次使用
        })
    }

    /**
     * @description: 新增点
     * @method: creatdot
     * @param: {}
     * @return: void
     */
    creatdot(posX, posY) {
        //相对container坐标
        let newposX = posX - this.svgConfig.container.offsetLeft;
        let newposY = posY - this.svgConfig.container.offsetTop;
        let dot = document.createElement("div");
        dot.setAttribute("class", "dot");
        //定位
        dot.style.left = newposX + "px";
        dot.style.top = newposY + "px";
        this.svgConfig.container.appendChild(dot);
    }

    /**
     * @description: 连接点
     * @method: linedot
     * @param: {}
     * @return: void
     */
    linedot(dot1, dot2) {
        let _this = this;
        clearTimeout(_this.svgConfig.timepath);
        let start = {
            x: _this.intpixel(dot1.style.left),
            y: _this.intpixel(dot1.style.top)
        };
        let end = {
            x: _this.intpixel(dot2.style.left),
            y: _this.intpixel(dot2.style.top)
        };
        let current = {
            x: start.x,
            y: start.y
        };
        //创建直线
        let line = document.createElementNS(_this.svgConfig.svgns, "line");
        line.setAttribute("x1", dot1.style.left);
        line.setAttribute("y1", dot1.style.top);
        line.setAttribute("x2", dot1.style.left);
        line.setAttribute("y2", dot1.style.top);
        line.setAttribute("stroke", "red");
        line.setAttribute("fill", "none");
        _this.svgConfig.svger.appendChild(line);
        //角度
        let tangle = {
            sin: (end.y - start.y) / Math.sqrt((end.y - start.y) * (end.y - start.y) + (end.x - start.x) * (end.x - start
                .x)),
            cos: (end.x - start.x) / Math.sqrt((end.y - start.y) * (end.y - start.y) + (end.x - start.x) * (end.x - start.x))
        };
        //动画
        let step = function() {
            //定义每帧移动位移大小为10
            if (Math.abs(current.x - end.x) < 10 && Math.abs(current.y - end.y) < 10) {
                current.x = end.x;
                current.y = end.y;
            } else {
                current.x += 10 * tangle.cos;
                current.y += 10 * tangle.sin;
                _this.svgConfig.timepath = setTimeout(step, 17); //浏览器重绘速度为60帧每秒
            }
            line.setAttribute("x2", current.x + "px");
            line.setAttribute("y2", current.y + "px");
        }
        step();
    }

    /**
     * @description: 位置像素 数值化
     * @method: intpixel
     * @param: {}
     * @return: void
     */
    intpixel(str) {
        return str.substring(0, str.length - 2) * 1;
    }

    /**
     * @description: 获取鼠标坐标
     * @method: mousePos
     * @param: {}
     * @return: void
     */
    mousePos(e) {
        if (e.pageX) {
            //IE9及以上支持pageX属性 相对文档
            return {
                x: e.pageX,
                y: e.pageY
            }
        } else {
            return {
                x: e.clientX + document.body.scrollLeft - document.body.clientLeft,
                y: e.clientY + document.body.scrollTop - document.body.clientTop
            }
        }
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
     * @method: startDrag
     * @param {} 
     * @return: 
     */
    startDrag() {
        //获取元素
        let dv = document.getElementById('img');
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
        dv.onmousedown = function(e) {
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
        document.onmousemove = function(e) {
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

                if (nl < -($("#img")[0].offsetWidth - parWidth)) {
                    right = '0px';
                    left = 'auto';
                }

                if (nt < -($("#img")[0].offsetHeight - parHeight)) {
                    bottom = '0px';
                    top = 'auto';
                }

                dv.style.left = left;
                dv.style.right = right;
                dv.style.bottom = bottom;
                dv.style.top = top;
            }
            //鼠标抬起事件
        dv.onmouseup = function() {
            //开关关闭
            isDown = false;
            dv.style.cursor = 'default';
        }

        console.log(this.svgConfig.posX - this.svgConfig.container.offsetLeft,
            this.svgConfig.posY - this.svgConfig.container.offsetTop);
    };


    /**
     * @description: 是否将当前点设置成新的目的地提示框
     * @method: setCorClick
     * @param {} 
     * @return: void
     */
    setCorClick() {
        $('#setCorSure').off('click').on('click', () => {
            $('#setCorScreen').removeClass('active').siblings('#isSaveCal').addClass('active');
        });
        $('#setCorClose').off('click').on('click', () => {
            $('#setCorScreen').removeClass('active');
        });
    }

    /**
     * @description: 是否将保存路径计算结果
     * @method: isSaveClick
     * @param {} 
     * @return: void
     */
    isSaveClick() {
        $('#isSaveCalSure').off('click').on('click', () => {
            $('#isSaveCal').removeClass('active').siblings('#roadName').addClass('active');
        });
        $('#isSaveCalClose').off('click').on('click', () => {
            $('#isSaveCal').removeClass('active');
        });
    }


    /**
     * @description: 请输入道路名称
     * @method: saveRoadName
     * @param {} 
     * @return: void
     */
    saveRoadName() {
        $('#roadNameSure').off('click').on('click', () => {
            $('#roadName').removeClass('active');
            $('#showSimulate').css('display', 'block');
        });
        $('#roadNameClose').off('click').on('click', () => {
            $('#roadName').removeClass('active');
        });
    }

    /**
     * @description: 开始模拟(一步)
     * @method: startSimulate
     * @param {} 
     * @return: void
     */
    startSimulate() {
        $('#showSimulate').off('click').on('click', () => {
            setInterval(() => {
                this.mapClick();
                this.drowline();
                let diffvalX = this.initCarX + 10 - this.carIconX + 10
                let diffvalY = this.initCarY + 10 - this.carIconY + 10
                let diffX = (diffvalX / 8) - 2.5;
                let diffY = (diffvalY / 8) - 2.5;
                this.carIconX += diffX;
                this.carIconY += diffY;
                this.drawImage();
                this.drowline();
            }, 2000);
        })
    }

    /**
     * @description: 返回
     * @method: returnPage
     * @param {} 
     * @return: void
     */
    returnPage() {
        $('#return').off('click').on('click', () => {
            console.log(111);

        })
    }
}