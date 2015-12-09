<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>编辑客户</title>

    <style>
        tr td {
            text-align: center;
        }
    </style>
</head>
<body>
    <h1>客户列表</h1>
    <form id="customerForm" action="<c:url value='/customer_edit'/>" method="post">
        <input type="hidden" name="id" value="${customer.id}"/>
        <table>
            <tr>
                <th>客户名称</th>
                <th>联系人</th>
                <th>电话号码</th>
                <th>邮箱地址</th>
            </tr>
            <tr>
                <td><input name="name" value="${customer.name}"/></td>
                <td><input name="conpact" value="${customer.conpact}"/></td>
                <td><input name="telephone" value="${customer.telephone}"/></td>
                <td><input name="email" value="${customer.email}"/></td>
            </tr>
            <tr>
                <td colspan="4">
                    <input type="submit" value="保存"/>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
