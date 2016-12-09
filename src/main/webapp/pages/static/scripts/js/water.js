$(window).on("load",function(){
	    
         var dataInt = {"data":[
                                 {"src":"pic1.jpg","logo":"logo1.jpg","title1":"美国进口水果杏李1",'title2':'1果汁饱满厚实 汁水充足'},
                                 {"src":"pic2.jpg","logo":"logo2.jpg","title1":"美国进口水果杏李2",'title2':'2果汁饱满厚实 汁水充足'},
                                 {"src":"pic1.jpg","logo":"logo1.jpg","title1":"美国进口水果杏李3",'title2':'3果汁饱满厚实 汁水充足'},
                                 {"src":"pic2.jpg","logo":"logo2.jpg","title1":"美国进口水果杏李4",'title2':'4果汁饱满厚实 汁水充足'},
                                 {"src":"pic1.jpg","logo":"logo1.jpg","title1":"美国进口水果杏李5",'title2':'5果汁饱满厚实 汁水充足'},
                                 {"src":"pic2.jpg","logo":"logo2.jpg","title1":"美国进口水果杏李6",'title2':'6果汁饱满厚实 汁水充足'},
                                 {"src":"pic1.jpg","logo":"logo1.jpg","title1":"美国进口水果杏李7",'title2':'7果汁饱满厚实 汁水充足'},
                                 {"src":"pic2.jpg","logo":"logo2.jpg","title1":"美国进口水果杏李8",'title2':'8果汁饱满厚实 汁水充足'}
                               ]
                        };
         $(window).on("scroll",function(){
         	 if(checkScrollSlide){
         	 	$.each(dataInt.data,function(key,value){
			           var _mainObj = $(".main");
			           var _clone = $(".main a:first").clone();
                 console.log(_clone.find("img").size());
			           $(".main").append(_clone);
                       $(_clone).find('img').eq(1).attr("src","images/"+$(value).attr("src"));
                       $(".main a").children('p').find('img').attr("src","images/"+$(value).attr("logo"));
                       $(_clone).find(".goods p").html($(value).attr("title1")+"<span>"+$(value).attr("title2")+"</span>"); 

         	 	});
         	 }

            
        
         	 
         });
});

function checkScrollSlide(){
   var $lastBox = $(".main a .goods").last();//最后一个产品
	var lastBoxDis = $lastBox.offset().top+Math.floor($lastBox.outerHeight()/2);
    var scrollTop=$(window).scrollTop();
    var documentH=$(window).height();
   return (lastBoxDis<scrollTop+documentH)?true:false;

}