<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>创建客户</title>
    <script src="${BASE}/asset/js/jQuery/jquery-2.1.4.min.js"></script>
    <script src="${BASE}/asset/js/jQuery/jquery-form-3.64.js"></script>
    <script>
        $(function () {
            $("#customer_form").ajaxForm({
                type: "post",
                url: "${BASE}/customer_submit",
                success: function (data) {
                    if (data) {
                        location.href = "${BASE}/customer"
                    }
                }
            });
        });
    </script>
</head>
<body>
<h1>创建客户</h1>

<div>
    <form id="customer_form" enctype="multipart/form-data">
        <table>
            <tr>
                <td>客户名字：</td>
                <td><input type="text" name="name" value="${customer.name}"/></td>
            </tr>
            <tr>
                <td>联系人：</td>
                <td><input type="text" name="contact" value="${customer.contact}"/></td>
            </tr>
            <tr>
                <td>邮箱地址：</td>
                <td><input type="text" name="email" value="${customer.email}"/></td>
            </tr>
            <tr>
                <td>照片：</td>
                <td><input type="file" name="photo" value="${customer.photo}"/></td>
            </tr>
        </table>
        <button type="submit">保存</button>
    </form>
</div>
</body>
</html>
