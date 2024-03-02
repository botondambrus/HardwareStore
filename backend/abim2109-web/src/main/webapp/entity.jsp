<!DOCTYPE html>
<html>
<head>
    <title>Item List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container m-4">
    <div class="text-center">
        <h2>Item List</h2>
    </div>
    <div class="mt-4">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Category</th>
                <th>Description</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            <%@ page import="edu.bbte.idde.abim2109.backend.model.HardwareStore" %>
            <%@ page import="java.util.List" %>
            <%
                List<HardwareStore> items = (List<HardwareStore>) request.getAttribute("items");
                for (HardwareStore item : items) {
            %>
            <tr>
                <td><%=item.getId() %>
                </td>
                <td><%=item.getName() %>
                </td>
                <td><%=item.getCategory()%>
                </td>
                <td><%=item.getDescription()%>
                </td>
                <td><%=item.getQuantity()%>
                </td>
                <td><%=item.getPrice()%>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <div class="mt-4">
        <a href="<%=request.getContextPath()%>/logout" class="btn btn-primary">Logout</a>
    </div>
</div>
</body>
</html>
