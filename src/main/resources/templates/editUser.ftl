<!doctype html>
<html lang="en">
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <#import "parts/menu.ftl" as m>
    <title>Project Management System - Admin Panel - Manage Users - Edit User</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href ="../../css/main.css">


    <script>
        function saveUser() {
            $('input[type=text]').each(function(){
                $(this).attr("class","form-control");
            })
            $('.invalid-feedback d-block').each(function(){
                $(this).attr("class","invalid-feedback");
            })
            $("[id$='response']").each(function(){
                $(this).empty();
            })

            var newUsername =$('input[name="newUsername"]').val();
            var roles = [];
            $('input[type="checkbox"]:checked').each(function() {
                roles.push($(this).attr("name"));
            })
            $.ajax({
                headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                url: "/admin/saveUser",
                type: "POST",
                data: {id: ${user.id}, newUsername: newUsername, roles: roles},
                success: function(response){
                    if(response=="Successfully saved!") {
                        $("#saveResponse").empty();
                        $("#saveResponse").attr("style", "color:green");
                        $("#saveResponse").append(response);
                        response.clear();
                    }
                    else {
                        $('#username').attr("class","form-control is-invalid");
                        $('#username_fb').attr("class","invalid-feedback d-block");
                        $('#username_response').append(response);
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
    <form>
        <input class="btn btn-primary btn-sm" type="button" value="<--Back" onclick="window.location.replace('http://localhost:8080/admin/adminPanelUserManage')">
    </form>
    <br><br>
    <div class="row">
        <div class="col-4">

            <div class="card">
                <h5 class="card-header">Edit User:</h5>
                <div class="card-body">
                    <div class="row">
                        <div class="col-3">
                            <h6>Username:</h6>
                        </div>
                        <div class="col-5">
                            <input type="text" class="form-control" id="username" name="newUsername" value="${user.username}">
                            <div class="invalid-feedback" id="username_fb">
                                <h7 id="username_response"></h7>
                            </div>
                        </div>

                        <div class="col-3">
                            <button onclick="saveUser()" class="btn btn-success btn-sm">Save User</button>
                            <br><br>
                            <form action="/admin/resetUserPassword/${user.id}" method="get">
                                <input type="submit" class="btn btn-primary btn-sm" value="Reset password"/>
                            </form>
                            <br><br>
                            <form action="/admin/deleteUser/${user.id}" method="get">
                                <input type="submit" class="btn btn-danger btn-sm" value="Delete User"/>
                            </form>
                        </div>

                    </div>
<br>
                    <div class="row">
                        <div class="col">
                            <h6>Has roles:</h6>
                                <#list globalRoles as role>
                                    <label><input type="checkbox" name="${role}" ${user.globalRoles?seq_contains(role)?string("checked", "")}>${role}</label><br>
                            </#list>
                            <br>
                            <p class="card-text" id="saveResponse"></p>
                            <#if resetResponseMessage??>
                                <div class="alert alert-${resetResponseMessage}" role="alert">
                                    Password successfully reset!
                                </div>
                            </#if>
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