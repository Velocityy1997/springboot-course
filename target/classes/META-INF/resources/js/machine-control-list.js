var machineControlObj = function(option) {
    this.option = option || {};
    this.currentIndex = Number(window.parent.window.iframeObj.machineControlIndex);
    this.init();
}

// 初始化
machineControlObj.prototype.init = function() {
    this.$machineViewUl = $('#machine-view-ul'); // 手持机触屏按键的父级标签
    $('.machine-view-li').eq(this.currentIndex).addClass('active');
    this.allEvent(); // 各种点击事件
    this.findActive(); // 找哪个被选中
}

// 各种事件
machineControlObj.prototype.allEvent = function() {
    this.tapClick(); // 触屏操作
}

// 触屏操作
machineControlObj.prototype.tapClick = function() {
    this.$machineViewUl.on('click', '.machine-view-li', function() {
        var name = $(this).children('.machine-view-text').text();
        window.parent.window.iframeObj.machineControlIndex = $(this).attr("data-index");
        window.parent.window.iframeObj.changeIframe(name);
    });
}

// 点击手持机
machineControlObj.prototype.findActive = function() {
    window.parent.window.iframeObj.toIframe = $('.machine-view-li.active').children('.machine-view-text').text();
}

// 点击上下左右操作active
machineControlObj.prototype.upActive = function() {
    let len = $('.machine-view-li').length - 1;
    if (this.currentIndex == 0) {
        this.currentIndex == 0
    } else if (this.currentIndex == 1) {
        this.currentIndex == 1
    } {
        this.currentIndex -= 2;
    }
    this.changeActive();
}
machineControlObj.prototype.leftActive = function() {
    let len = $('.machine-view-li').length - 1;
    if (this.currentIndex == 0) {
        this.currentIndex == 0
    } else {
        this.currentIndex -= 1;
    }
    this.changeActive();
}
machineControlObj.prototype.rightActive = function() {
    let len = $('.machine-view-li').length - 1;
    if (this.currentIndex == len) {
        this.currentIndex == len
    } else {
        this.currentIndex = Number(this.currentIndex) + 1;
    }
    this.changeActive();
}
machineControlObj.prototype.downActive = function() {
    let len = $('.machine-view-li').length - 1;
    if (this.currentIndex == len) {
        this.currentIndex = len
    } else if (this.currentIndex == len - 1) {
        this.currentIndex = len - 1
    } else {
        this.currentIndex = Number(this.currentIndex) + 2;
    }
    this.changeActive();
}

machineControlObj.prototype.changeActive = function() {
    var currentIndex = this.currentIndex;
    window.parent.window.iframeObj.machineControlIndex = currentIndex;
    $('.machine-view-li').eq(currentIndex).addClass('active').siblings('.machine-view-li').removeClass('active');
    // this.findActive();
}


// 确认
machineControlObj.prototype.sureActive = function() {
    window.parent.window.iframeObj.changeIframe($('.machine-view-li.active').children('.machine-view-text').text());
}

// 返回
machineControlObj.prototype.returnActive = function() {
    window.parent.window.iframeObj.goBackBefore($('.machine-view-li.active').children('.machine-view-text').text());
}