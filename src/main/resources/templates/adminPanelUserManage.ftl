<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <#import "parts/menu.ftl" as m>
    <#import "parts/userListPager.ftl" as p>
    <title>Project Management System - Admin Panel - Manage Users</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/main.css">

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
                            <a class="nav-link active" href="#"><b>Manage Users</b></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/admin/roles">Manage Project Roles</a>
                        </li>
                    </ul>
                </div>
                <div class="card-body">
                    <h5 class="card-title">Create new user</h5>
                    <div class="row">
                        <div class="col-2">
                            <form id="addUser" action="/admin/users/add" method="post">
                                <input class="form-control ${(username??)?string('is-invalid', '')}" type="text"
                                       name="username" placeholder="Enter username">
                                <#if username??>
                                    <div class="invalid-feedback">
                                        ${username}
                                    </div>
                                </#if>
                            </form>
                            <br><br>
                            <#if responseMessage??>
                                <div class="alert alert-${responseMessage}" role="alert">
                                    Successfully created!
                                </div>
                            </#if>
                        </div>
                        <div class="col-1">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" form="addUser"/>
                            <input type="submit" class="btn btn-success btn-sm" value="Create" form="addUser">
                        </div>
                    </div>
                    <hr>
                    <h5 class="card-title">User List</h5>
                    <div class="row">
                        <div class="col-2">
                            <ul class="list-group" id="userList">
                                <#list page.content as user>
                                    <li class="list-group-item"><a href="/admin/users/${user.id}">${user.username}</a>
                                    </li>
                                </#list>
                            </ul>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col">
                            <@p.pager url page/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>