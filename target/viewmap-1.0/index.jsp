<%--
  Created by IntelliJ IDEA.
  User: John
  Date: 2016/12/16
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Hello, World</title>
  <style type="text/css">
    html{height:100%}
    body{height:100%;margin:0px;padding:0px}
    #container{height:100%}
  </style>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=tnbGXXB6NzqAfqOqblKW8EGxkgCzIFgw">
    //v2.0版本的引用方式：src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥"
    //v1.4版本及以前版本的引用方式：src="http://api.map.baidu.com/api?v=1.4&key=您的密钥&callback=initialize"
  </script>
  <script type="text/javascript" src="/jquery-2.2.3.min.js" charset="UTF-8"></script>
</head>

<body>
<div id="container"></div>
  <script type="text/javascript">

    var newpoint;

    function ajaxGet(){
      $.get("getLocation.do",function(data,status){
        console.log("Data: " + data + "\nStatus: " + status);
        if(data){
          var jingwei = $.parseJSON(data);
          <%--var jingdu = <%=request.getAttribute("jingdu")%>;--%>
          <%--var weidu = <%=request.getAttribute("weidu")%>;--%>
          var jingdu = jingwei.jingdu;
          var weidu = jingwei.weidu;
          newpoint = new BMap.Point(jingdu, weidu);
          move2NewPoint(newpoint);
        }else{
          if(newpoint){
            var opts = {
              position : newpoint,    // 指定文本标注所在的地理位置
              offset   : new BMap.Size(30, -30)    //设置文本偏移量
            };

            var label = new BMap.Label("失去定位信息，此为最后位置", opts);  // 创建文本标注对象
            label.setStyle({
              color : "red",
              fontSize : "12px",
              height : "20px",
              lineHeight : "20px",
              fontFamily:"微软雅黑"
            });
            map.removeOverlay(label);
            map.addOverlay(label);
          }else{
            alert("暂无坐标信息返回");
          }
        }
        console.log(<%=request.getAttribute("jingdu")%>);
        console.log(<%=request.getAttribute("weidu")%>);
      });
    }

    var marker;
    function move2NewPoint(newPoint){
      map.removeOverlay(marker);
//      var newPoint = new BMap.Point(jingdu, weidu);
      map.panTo(newPoint);
      marker = new BMap.Marker(newPoint);  // 创建标注

      map.addOverlay(marker);               // 将标注添加到地图中
      marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    }

    var map = new BMap.Map("container");          // 创建地图实例
    var point = new BMap.Point(116.404, 39.915);  // 创建点坐标
    map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别
    map.enableScrollWheelZoom();
/*    window.setTimeout(function(){
      ajaxGet();
//      move2NewPoint(116.409, 39.918)
    }, 1000);*/

    setInterval(function(){
      ajaxGet();
    }, 10000 ); // 10s

  </script>
</body>
</html>
