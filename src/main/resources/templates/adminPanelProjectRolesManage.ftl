<!doctype html>
<html lang="en">
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <#import "parts/menu.ftl" as m>
    <title>Project Management System - Admin Panel - Project Roles Manage</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

    <link rel="stylesheet" type="text/css" href ="../css/main.css">

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
                            <a class="nav-link" href="/admin/adminPanelUserManage">Manage Users</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="#">Manage Project Roles</a>
                        </li>
                    </ul>
                </div>
                <div class="card-body">
                    <h5 class="card-title">Create new Project Role</h5>
                    <div class="row">
                        <div class="col-2">
                            <form id="addProjectRole" action="/admin/addNewProjectRole" method="post">
                                <input class="form-control ${(roleName??)?string('is-invalid', '')}" type="text" name="roleName" placeholder="Enter Project Role">
                                <#if roleName??>
                                    <div class="invalid-feedback">
                                        ${roleName}
                                    </div>
                                </#if>
                            </form>
                        </div>
                        <div class="col-1">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" form="addProjectRole"/>
                            <input type="submit" class="btn btn-success btn-sm" value="Create" form="addProjectRole">
                        </div>
                    </div>
                    <hr>
                    <h5 class="card-title">Project Roles</h5>
                    <div class="row">
                        <div class="col-2">

                            <ul class="list-group" id="projectRoleList">
                                <#list projectRoles as pr>
                                    <li class="list-group-item">
                                        <div class="row">
                                        <div class="col">${pr.roleName}</div>
                                        <div class="col-3"><form action="/admin/deleteProjectRole/${pr.id}" method="get"><button type="submit" class="btn btn-link btn-sm" title="Delete Project Role"><i style="color: dimgray" class="fa fa-trash" aria-hidden="true"></i></button></form></div>
                                        </div>
                                    </li>
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