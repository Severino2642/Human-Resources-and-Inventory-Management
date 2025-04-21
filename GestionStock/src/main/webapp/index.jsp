<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.getSession().invalidate();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/index.css">
</head>
<body>
    <nav>
        <a href="Pages/loginAdmin.jsp">
            <div>
                <p>Administrateur</p>
            </div>
        </a>
        <a href="Pages/logInDepartement.jsp">
            <div>
                <p>Departement</p>
            </div>
        </a>
    </nav>
</body>
</html>