$(function(){

      $.ajax({
        url:"./js/json/manage-nav.json",
        type:"get",
        dataType:'json',
        success:function(data){
            createMainMenu(data.data);
        }
      })
      clickEvent();

function createMainMenu(jsonData){

    var selfThis = this; 
    var $sliderMenu = $("#leftNav");
    var rootList = {};
    var childList = {};
    var thirdList={};
    var i = 0;
    var j = 0;
    var l=0;

    $.each(jsonData,function(i,m){
    	
        if(!m.pid)
        {
            var $firstMenu = $('<li class="first-menu" x-id="'+m.id+'">');
            // 父级a标签
            if(m.url && m.url!=="")
            {
                var $sliderLink = $('<a href="javascript:void(0);" class="slider-link active" x-src="'+m.url+'" x-id="'+m.id+'">');
                
            }else{     
                    var $sliderLink = $('<a href="javascript:void(0);" class="slider-link active" x-id="'+m.id+'">');
            }
            
          
            // 判断一级菜单有无图标
            if(m.ico || m.ico!=="")
            {
                $('<i class="slider-icon-left '+m.ico+'"></i>').appendTo($sliderLink);
            }else{
                $('<i class="slider-icon-left"></i>').appendTo($sliderLink);
            }
            
            $('<span class="slider-text">'+m.text+'</span>').appendTo($sliderLink);            // 一级菜单标题

            if(hasChildren(m,jsonData))
            {
                $('<i class="slider-icon-right fa fa-angle-right"></i>').appendTo($sliderLink);    // 向右的箭头
            }
            
            $sliderLink.appendTo($firstMenu);
            $('<ul style="display:block" class="slider-child-level secondMenu"></ul>').appendTo($firstMenu);
            $firstMenu.appendTo($sliderMenu);
            
            
            rootList[i] = $firstMenu;
            rootList[i].id = m.id;
            rootList[i].pid = m.pid;
            rootList[i].text = m.text;
            i++;
        }else{
            // 子菜单
        	if(!m.isChildren){
        		var $childMenu = $('<li class="slider-child-menu active">');
        	}
        	else {
        		var $childMenu = $('<li class="slider-child-menu active" style="font-size: 17px;">');       		
        	}
            
           
            // 子级a标签
            if(m.url && m.url!=="")
            {
                var $sliderChildLink = $('<a href="javascript:void(0);" class="slider-child-link" x-src="'+m.url+'" x-id="'+m.id+'">');
            }else{
                var $sliderChildLink = $('<a href="javascript:void(0);" class="slider-child-link" x-id="'+m.id+'">');
            }
            
            
            // 判断子级菜单有无图标

            if(m.ico && m.ico!=="")
            {
                $('<i class="slider-icon-left '+m.ico+'"></i>').appendTo($sliderChildLink);
            }

            $('<span class="slider-text slider-child-text">'+m.text+'</span>').appendTo($sliderChildLink);            // 菜单标题
            
            if(hasChildren(m,jsonData))
            {
                $('<i class="slider-icon-right fa fa-angle-right"></i>').appendTo($sliderChildLink);    // 向右的箭头
            }
            if (m.openWin && m.openWin === "blank" || m.openWin === "newWin") {
                $("<i class='fa fa-desktop windowIcon'></i>").appendTo($sliderChildLink);
            }
            $sliderChildLink.appendTo($childMenu);
        
            childList[j] = $childMenu;
            childList[j].id = m.id;
            childList[j].pid = m.pid;
            childList[j].text = m.text;
            j++;
        }
    });

    // 渲染子级菜单
    $.each(childList,function(i,m){

        // 填充二级菜单
        $.each(rootList,function(i,n){
            if(m.pid === n.id)
            {             
               m.appendTo(n.children(".slider-child-level"));
            }
        });

        // 填充子级菜单
        $('<ul class="slider-child-level childMenuUl active" style="display:block;"></ul>').appendTo(m); 
        $.each(childList,function(i,k){
            if(m.id === k.pid){  
               k.appendTo(m.children(".slider-child-level"));
            }
        })
    });
}
// 点击展开下级菜单 各种点击事件绑定
function clickEvent(){
    var selfThis = this;
    $("#leftNav").on("click","a",function(){    
       
        var $this = $(this);
        if(!$this.hasClass("active"))
        {
            // $this.parent("li").siblings("li").children("a").removeClass("active").siblings("ul").slideUp(150);  //其它下级菜单收缩
            $this.addClass("active").siblings("ul").slideDown(150);                                             //点击的下级菜单展开
        }else{
            $this.removeClass("active").siblings("ul").slideUp(150);
        }
        if($(this).text() !== '考试监控') {
            createIframe($(this));//创建iframe
        }else {
            window.open('/springboot-course/manage/rank.html');
        }
        
        
    });
}
function hasChildren(thisData,jsonData){
    for(var i=0;i<jsonData.length;i++)
    {
        if(thisData.id === jsonData[i].pid)
        {
            return true;
        }
    }
    return false;
}
//创建iframe
function createIframe(thatLi){
    var url = thatLi.attr("x-src");
    var id = thatLi.attr("x-id");
    var title = thatLi.find(".slider-text").text();
    var selfThis = this;
    var iframeBox = $("#iframe-box");
    if (!url) return;
    if(thatLi.attr("x-src")){
        $(".slider-link").css({"background-image":"none","color":"#04331A"});
        $(".slider-icon-left").css("color","#04331A");
        $(".slider-child-link").css({"background-image":"none","color":"#6F737A"});
        $(".first-menu").children("a").removeClass("firstActic");
        if(thatLi.parent(".first-menu").children("a").attr("x-src")){
            thatLi.css({"background-image":"linear-gradient(to right,#016938,#B9E5B1)","color":"#fff"});
            thatLi.children(".slider-icon-left").css("color","#fff");
        }else{
            thatLi.closest(".first-menu").children("a").addClass("firstActic");//一级标题选中效果
        }
        
        thatLi.css({"background-image":"linear-gradient(to right,#016938,#B9E5B1)","color":"#fff"});
        // iframeBox.children('.iframe-main').attr("src",thatLi.attr("x-src"));
        if(iframeBox.children('.iframe-main[x-target="'+thatLi.attr("x-id")+'"]').length===0)
        {          
            var $iframe = $('<iframe class="iframe-main" x-target="'+thatLi.attr("x-id")+'" src="'+thatLi.attr("x-src")+'">');
            $iframe.appendTo(iframeBox);
            $iframe.addClass("active").fadeIn(300).siblings(".iframe-main").removeClass("active").fadeOut(300);
        }else{
            iframeBox.children('.iframe-main[x-target="'+thatLi.attr("x-id")+'"]').addClass("active").fadeIn(300).siblings(".iframe-main").removeClass("active").fadeOut(300);
            $('.iframe-main[x-target="'+thatLi.attr("x-id")+'"]')[0].contentWindow.window.updata();
        }   
    }
}

})