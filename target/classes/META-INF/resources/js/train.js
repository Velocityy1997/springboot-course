var trainObj = function(option) {
    this.option = option || {};
    this.init();
};

// 初始化
trainObj.prototype.init = function() {
    this.$trainUl = $('#train-ul'); // 训练列表父容器
    // this.createTrainList();           // 训练列表
    this.callParent(); // 调用父级方法
    this.allEvent(); // 所有事件
}

// 训练列表
trainObj.prototype.createTrainList = function() {
    var _this = this;
    _this.option.trainIndex = 0;

    $.each(this.option.tarinData, function(index, item) {
        var $li = $('<li class="train-li" data-id="' + (index + 1) + '" id="' + item.text + '">');
        $(' <div class="train-img" style="background: url(' + item.imgSrc + ') 0 0 no-repeat; background-size: 100% 100%;" data-imgActive="' + item.imgActiveSrc + '" data-img="' + item.imgSrc + '"></div>').appendTo($li);
        $(' <div class="train-text">' + item.text + '</div>').appendTo($li);
        $li.appendTo(_this.$trainUl);
    });
}

// 所有事件
trainObj.prototype.allEvent = function() {
    this.clickTrainList(); // 鼠标点击切换
}

trainObj.prototype.reset = function() {
    $('.train-li').each(function() {
        var $img = $(this).children('.train-img').attr('data-img');
        var $style = 'background: url(' + $img + ') 0 0 no-repeat;background-size: 100% 100%';
        $(this).children('.train-img').attr('style', $style);
    });
}

trainObj.prototype.clickTrainList = function() {
    var _this = this;
    this.$trainUl.on('click', '.train-li', function() {
        _this.reset();
        var subject = $(this).attr('data-id');
        window.parent.window.setOption.goMachine(0, "/springboot-course/train/gettraining?subject=" + subject + "&pageNo=1&pageSize=10&id_number=123", subject);

        $(this).addClass('active').siblings('.train-li').removeClass('active');
        var $imgActive = $(this).children('.train-img').attr('data-imgActive');
        var $style = 'background: url(' + $imgActive + ') 0 0 no-repeat;background-size: 100% 100%';
        $(this).children('.train-img').attr('style', $style);
    });
}


// 调用父级方法
trainObj.prototype.callParent = function() {
    window.parent.window.setOption.changeNums('train', this.option.trainIndex);
}