  // 缩放图片
  //   var big = document.getElementById('big');
  //   var reduce = document.getElementById('reduce')

  //   big.onclick = function() {
  //       var imgBox = document.getElementById('imgBox');
  //       imgBox.style.zoom = parseInt(imgBox.style.zoom) + 25 + '%';
  //   }

  //   reduce.onclick = function() {
  //       var imgBox = document.getElementById('imgBox');
  //       imgBox.style.zoom = parseInt(imgBox.style.zoom) + -25 + '%';
  //   }


  //   function imgToSize(oBool) {
  //       var imgBox = document.all('imgBox');
  //       imgBox.style.zoom = parseInt(imgBox.style.zoom) + (oBool ? 25 : -25) + '%';
  //   }


  // box是装图片的容器,fa是图片移动缩放的范围
  //   var box = document.getElementsByClassName("content-image");
  //   var fa = document.getElementsByClassName('container');

  //   // 图片移动效果
  //   box.onmousedown = function(ev) {
  //       var oEvent = ev;
  //       // 浏览器有一些图片的默认事件,这里要阻止
  //       oEvent.preventDefault();
  //       var disX = oEvent.clientX - box.offsetLeft;
  //       var disY = oEvent.clientY - box.offsetTop;
  //       fa.onmousemove = function(ev) {
  //               oEvent = ev;
  //               oEvent.preventDefault();
  //               var x = oEvent.clientX - disX;
  //               var y = oEvent.clientY - disY;

  //               // 图形移动的边界判断
  //               x = x <= 0 ? 0 : x;
  //               x = x >= fa.offsetWidth - box.offsetWidth ? fa.offsetWidth - box.offsetWidth : x;
  //               y = y <= 0 ? 0 : y;
  //               y = y >= fa.offsetHeight - box.offsetHeight ? fa.offsetHeight - box.offsetHeight : y;
  //               box.style.left = x + 'px';
  //               box.style.top = y + 'px';
  //           }
  //           // 图形移出父盒子取消移动事件,防止移动过快触发鼠标移出事件,导致鼠标弹起事件失效
  //       fa.onmouseleave = function() {
  //               fa.onmousemove = null;
  //               fa.onmouseup = null;
  //           }
  //           // 鼠标弹起后停止移动
  //       fa.onmouseup = function() {
  //           fa.onmousemove = null;
  //           fa.onmouseup = null;
  //       }
  //   }
  var params = {
      zoomVal: 1,
      left: 0,
      top: 0,
      currentX: 0,
      currentY: 0,
      flag: false
  };
  //点击图片缩放
  var big = document.getElementById("bigIcon");
  var reduce = document.getElementById("reduceIcon");

  big.onclick = function() {

      var imgBox = document.getElementById("imgBox");
      imgBox.style.zoom = parseInt(imgBox.style.zoom) + 25 + '%';
  }

  //滚轮图片缩放
  function bbimg(o) {
      var o = o.getElementsByTagName("img")[0];
      params.zoomVal += event.wheelDelta / 1200;
      if (params.zoomVal >= 0.2) {
          o.style.transform = "scale(" + params.zoomVal + ")";
      } else {
          params.zoomVal = 0.2;
          o.style.transform = "scale(" + params.zoomVal + ")";
          return false;
      }
  }
  //获取相关CSS属性
  var getCss = function(o, key) {
      return o.currentStyle ? o.currentStyle[key] : document.defaultView.getComputedStyle(o, false)[key];
  };
  //拖拽的实现
//   var startDrag = function(bar, target, callback) {
//       if (getCss(target, "left") !== "auto") {
//           params.left = getCss(target, "left");
//       }
//       if (getCss(target, "top") !== "auto") {
//           params.top = getCss(target, "top");
//       }
//       //o是移动对象
//       bar.onmousedown = function(event) {
//           params.flag = true;
//           if (!event) {
//               event = window.event;
//               //防止IE文字选中
//               bar.onselectstart = function() {
//                   return false;
//               }
//           }
//           var e = event;
//           params.currentX = e.clientX;
//           params.currentY = e.clientY;
//       };
//       document.onmouseup = function() {
//           params.flag = false;
//           if (getCss(target, "left") !== "auto") {
//               params.left = getCss(target, "left");
//           }
//           if (getCss(target, "top") !== "auto") {
//               params.top = getCss(target, "top");
//           }
//       };
//       document.onmousemove = function(event) {
//           var e = event ? event : window.event;
//           if (params.flag) {
//               var nowX = e.clientX,
//                   nowY = e.clientY;
//               var disX = nowX - params.currentX,
//                   disY = nowY - params.currentY;
//               target.style.left = parseInt(params.left) + disX + "px";
//               target.style.top = parseInt(params.top) + disY + "px";
//               if (typeof callback == "function") {
//                   callback((parseInt(params.left) || 0) + disX, (parseInt(params.top) || 0) + disY);
//               }
//               if (event.preventDefault) {
//                   event.preventDefault();
//               }
//               return false;
//           }
//       }
//   };
//   startDrag(document.getElementById("imgBox"), document.getElementById("imgBox"))