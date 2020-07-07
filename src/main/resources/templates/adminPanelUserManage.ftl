<!doctype html>
<html lang="en">
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <#import "parts/menu.ftl" as m>
    <title>Project Management System - Admin Panel - Manage Users</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href ="../css/main.css">

    <script>
        function addNewUser() {
            var newUsername =$('input[name="newUsername"]').val();
            $.ajax({
                headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                url: "/admin/addUser",
                type: "POST",
                data: {newUsername: newUsername},
                success: function(response){
                    if(response=="Username already exists") {
                        $("#createResponse").empty();
                        $("#createResponse").attr("style", "color:red");
                        $("#createResponse").append(response);
                        response.clear();
                    }
                    else {
                        $("#createResponse").empty();
                        $("#createResponse").attr("style", "color:green");
                        $("#createResponse").append(response);
                        $("#userList").load(" #userList");
                        response.clear();
                    }
                }
            })
        }
    </script>


</head>
<body>


<div class="container-fluid">
    <@m.menu/>
    <br>
    <div class="row">
        <div class="col">

            <div class="card text-left">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">Manage Users</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/adminPanelProjectRolesManage">Manage Project Roles</a>
                        </li>
                    </ul>
                </div>
                <div class="card-body">
                    <h5 class="card-title">Create new user</h5>
                    <div class="row">
                        <div class="col-2">
                            <input class="form-control" type="text" name="newUsername" placeholder="Enter username">
                            <p class="card-text" id="createResponse"></p>

                        </div>
                        <div class="col-1">
                            <button onclick="addNewUser()" class="btn btn-success btn-sm">Create</button>
                        </div>
                    </div>
                    <hr>
                    <h5 class="card-title">User List</h5>
                    <div class="row">
                        <div class="col-2">
                            <ul class="list-group" id="userList">
                                <#list users as user>
                                <li class="list-group-item"><a href="/admin/editUser/${user.id}">${user.username}</a></li>
                                </#list>
                            </ul>
                        </div>

                    </div>

                </div>
            </div>

        </div>
    </div>
</div>


<#--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>