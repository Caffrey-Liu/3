<%--
  Created by IntelliJ IDEA.
  User: 47682
  Date: 2021/9/4
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--<form action="CameraServlet" method="get">--%>
<%--    <p>First name: <input type="text" name="fname" /></p>--%>
<%--    <p>Last name: <input type="text" name="lname" /></p>--%>
<%--    <input type="submit" value="Submit" />--%>
<%--</form>--%>

<br>
<br>
<br>
<br>
<br>
<div class="" align="center">
<img id="pic" >
</div>
<br>
<%--<div align="center">--%>
<%--&lt;%&ndash;<button type="button" onclick="loadDoc()">请求数据</button>&ndash;%&gt;--%>
<%--</div>--%>
<br>
<script>
    var xhttp = new XMLHttpRequest();
    var url = "data:image/png;base64,";
    document.getElementById("pic").src = url+ String;
    function loadDoc() {
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                var String = this.responseText;
                document.getElementById("pic").src = url+ String;
                console.log(String);
            }
        };
        xhttp.open("GET", "CameraServlet", true);
        xhttp.send();
    }

    setInterval('loadDoc()',200);


</script>

</body>
</html>
