$(function () {
  getdata1();
  getdata2();
  getdata();
  window.updata = function () {
    getdata1();
    getdata2();
    getdata();
  }
  function getdata() {
    $.post("/springboot-course/dataAnalysis/zongti", function (res) {
      var data = res.data;
      $("#bjslNum").text(data.classNum);
      $("#xyslNum").text(data.classStudent);
      $("#stslNum").text(data.textNum);
      $("#testxyslNum").text(data.testStudent);
      $("#kskmNum").text(data.testObject);
      $("#wcqkNum").text(data.completePre);
      if (Number(data.completePre) != 0 && Number(data.completePre) < 1) {
        $("#redBlock").attr("style", "width: 1%;");
      } else if (Number(data.completePre) == 0) {
        $("#redBlock").attr("style", "width: 0%;");
      } else {
        $("#redBlock").attr("style", Number(data.completePre) + "%");
      }
    }, "json")
  }
  function getdata1() {
    $.post("/springboot-course/dataAnalysis/classandstudent", function (res) {
      // $.post("../js/manage/data1.json",function(res){
      var data = res.data;
      var yName = [];
      var manVal = [];
      var girlVal = [];
      var total = [];
      $.each(data, function (i, item) {
        yName.push(item.yName);
        manVal.push(item.manVal);
        girlVal.push(item.girlVal);
        total.push(parseInt(item.girlVal) + parseInt(item.manVal));
      })
      chart1(yName, girlVal, manVal, total);
    }, "json")
  }
  function chart1(yName, girlVal, manVal, total) {
    var myChart1 = echarts.init(document.getElementById("bjryChart"));
    var option = {
      grid: {
        left: '3%',
        right: '12%',
        bottom: '5%',
        top: '5%',
        containLabel: true
      },
      tooltip: {
        show: "true",
        trigger: 'axis',
        axisPointer: { // 坐标轴指示器，坐标轴触发有效
          type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
        },
        textStyle: {
          fontSize: 16
        }
      },
      xAxis: {
        type: 'value',
        axisTick: {
          show: false
        },
        axisLine: {
          show: false,
          lineStyle: {
            color: '#333',
          }
        },
        splitLine: {
          show: false
        },
        axisLabel: {
          show: false,
        }
      },
      yAxis: [{
        type: 'category',
        axisTick: {
          show: false
        },
        axisLabel: {
          fontSize: 18
        },
        axisLine: {
          show: false,
          lineStyle: {
            color: '#333',
          }
        },
        data: yName
      },
      {
        type: 'category',
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          show: false
        },
        splitArea: {
          show: false
        },
        splitLine: {
          show: false
        },
        data: yName
      },

      ],
      dataZoom: [{
        type: 'slider',
        show: false,
        yAxisIndex: [0, 1],
        width: 15,
        // startValue: 1,
        // endValue: 10,
        right: '5%'
      }],
      series: [

        {
          name: '女生人数',
          type: 'bar',
          yAxisIndex: 1,
          barWidth: 25,
          stack: '总量',
          itemStyle: {
            normal: {
              show: true,
              color: '#F959A3',
              borderWidth: 0,
            }
          },
          barGap: '0%',
          barCategoryGap: '50%',
          data: girlVal
        },
        {
          name: '男生人数',
          type: 'bar',
          barWidth: 25,
          stack: '总量',
          itemStyle: {
            normal: {
              show: true,
              color: '#B3E0AD',
              borderWidth: 0,
            }
          },
          barGap: '0%',
          barCategoryGap: '50%',
          data: manVal,
          label: {
            show: true,
            formatter: function (params) {
              return total[params.dataIndex];
            },
            position: "right",
            color: "#333",
            fontSize: 18
          }
        }

      ]
    };
    if (option.yAxis[0].data.length > 10) {
      // type: 'slider',
      // show: false,
      // yAxisIndex: [0, 1],
      // width: 15,
      // startValue: 1,
      // endValue: 10,
      // right:'5%'
      option.dataZoom[0].show = true;
      option.dataZoom[0].startValue = 1;
      option.dataZoom[0].endValue = 10;
    }
    myChart1.setOption(option);
    $(window).on("resize", function () {
      myChart1.resize();
    })
  }
  function getdata2() {
    $.post("/springboot-course/dataAnalysis/getexcellent", function (res) {
      var data = res.data;
      var bdscj=[data[0].bdscj.pass,data[0].bdscj.unPass];
      var dtcz=[data[1].dtcz.pass,data[1].dtcz.unPass];
      var llzs=[data[2].llzs.pass,data[2].llzs.unPass];
      var hppd=[data[3].hppd.pass,data[3].hppd.unPass];
      var zzjs=[data[4].zzjs.pass,data[4].zzjs.unPass];
      var styt=[data[5].styt.pass,data[5].styt.unPass];
      chart2(bdscj,dtcz,llzs,hppd,zzjs,styt);
    }, "json")
  }
  function chart2(bdscj,dtcz,llzs,hppd,zzjs,styt) {
    var myChart2 = echarts.init(document.getElementById("bdscjChart"));
    var labelFromatter = {
      normal: {
        label: {
          formatter: function (params) {
            return params.value + '%'
          },
          textStyle: {
            baseline: 'top'
          }
        }
      },
    }
    var radius = [0, 55];
    var option = {
      title: [{
        text: "北斗手持机",
        x: '14%',
        y: '35%',
        textStyle: {
          fontSize: 20,
          color: '#333',
          align: 'center',
          fontWeight: 400,
          fontFamily: 'Microsoft YaHei',
        }
      },
      {
        text: "电台操作",
        x: '45%',
        y: '35%',
        textStyle: {
          fontSize: 20,
          color: '#333',
          align: 'center',
          fontWeight: 400,
          fontFamily: 'Microsoft YaHei',
        }
      }, {
        text: "理论知识",
        x: '75%',
        y: '35%',
        textStyle: {
          fontSize: 20,
          color: '#333',
          fontWeight: 400,
          align: 'center',
          fontFamily: 'Microsoft YaHei',
        }
      }, {
        text: "航片判读",
        x: '15%',
        y: '85%',
        textStyle: {
          fontSize: 20,
          color: '#333',
          fontWeight: 400,
          align: 'center',
          fontFamily: 'Microsoft YaHei',
        }
      }, {
        text: "作战计算",
        x: '45%',
        y: '85%',
        textStyle: {
          fontSize: 20,
          color: '#333',
          fontWeight: 400,
          align: 'center',
          fontFamily: 'Microsoft YaHei',
        }
      },{
        text: "识图用图",
        x: '75%',
        y: '85%',
        textStyle: {
          fontSize: 20,
          color: '#333',
          fontWeight: 400,
          align: 'center',
          fontFamily: 'Microsoft YaHei',
        }
      },
      ],
      series: [
        {
          type: 'pie',
          center: ['20%', '20%'],
          radius: radius,
          itemStyle: labelFromatter,
          color: ["#5492FF", "#F959A3"],
          data: bdscj
        },
        {
          type: 'pie',
          center: ['50%', '20%'],
          radius: radius,
          itemStyle: labelFromatter,
          color: ["#5492FF", "#F959A3"],
          data: dtcz
        },
        {
          type: 'pie',
          center: ['80%', '20%'],
          radius: radius,
          itemStyle: labelFromatter,
          color: ["#5492FF", "#F959A3"],
          data: llzs
        },
        {
          type: 'pie',
          center: ['20%', '70%'],
          radius: radius,
          itemStyle: labelFromatter,
          color: ["#5492FF", "#F959A3"],
          data: hppd
        },
        {
          type: 'pie',
          center: ['50%', '70%'],
          radius: radius,
          itemStyle: labelFromatter,
          color: ["#5492FF", "#F959A3"],
          data: zzjs
        },
        {
          type: 'pie',
          center: ['80%', '70%'],
          radius: radius,
          itemStyle: labelFromatter,
          color: ["#5492FF", "#F959A3"],
          data: styt
        }
      ]
    };
    myChart2.setOption(option);
    $(window).on("resize", function () {
      myChart2.resize();
    })
  }
  // function getdata3() {
  //   $.post("/springboot-course/dataAnalysis/getexcellent?name=dt", function (res) {
  //     var data = res.data;
  //     chart3(data);
  //   }, "json")
  // }
  // function chart3(data) {
  //   var myChart3 = echarts.init(document.getElementById("dtChart"));
  //   var option = {
  //     tooltip: {
  //       trigger: "item",
  //       formatter: "{b} : {c} ({d}%)"
  //     },
  //     title: {
  //       text: '电台',
  //       left: 'center',
  //       bottom: 20,
  //       textStyle: {
  //         color: '#6F737A'
  //       }
  //     },
  //     series: [
  //       {
  //         name: "",
  //         type: "pie",
  //         radius: "55%",
  //         center: ["50%", "50%"],
  //         data: data,
  //         color: ["#5492FF", "#F959A3"],
  //         label: {
  //           normal: {
  //             textStyle: {
  //               color: "#333",
  //               fontSize: 16

  //             },
  //             align: "left",
  //             formatter: "{b} : \n{c} ({d}%)"
  //           },
  //           position: "outside"
  //         },
  //         labelLine: {

  //           lineStyle: {
  //             color: "#333"
  //           },
  //           length: 50,
  //           length2: 55

  //         },
  //         animationType: "scale",
  //         animationEasing: "elasticOut",
  //         animationDelay: function () {
  //           return Math.random() * 200;
  //         }
  //       }
  //     ]
  //   }
  //   // myChart3.setOption(option);
  //   $(window).on("resize", function () {
  //     myChart3.resize();
  //   })
  // }
})