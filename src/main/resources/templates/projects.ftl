<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <#import "parts/menu.ftl" as m>
    <title>Welcome to the Project Management System</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

</head>
    <body>


    <div class="container-fluid">
        <@m.menu/>
        <br>
        <div class="row">
            <div class="col">
                <table class="table table-hover table-bordered">
                    <thead class="thead-dark">
                    <tr>
                        <th style="width: 8%" scope="col">Project #</th>
                        <th style="width: 11%" scope="col">Created At</th>
                        <th scope="col">Project name</th>
                        <th style="width: 10%" scope="col">Status</th>
                        <th style="width: 10%" scope="col">Project Manager</th>
                        <th style="width: 10%" scope="col">Created By</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list projects as project>
                        <tr>
                            <th scope="row" onclick="location.href='/projects/${project.id}'">${project.id}</th>
                            <td onclick="location.href='/projects/${project.id}'">${project.createdAt}</td>
                            <td onclick="location.href='/projects/${project.id}'">${project.title}</td>
                            <td onclick="location.href='/projects/${project.id}'">${project.getStatus().getStatusName()}</td>
                            <td onclick="location.href='/projects/${project.id}'">${project.roleUser.ProjectManager}</td>
                            <td onclick="location.href='/projects/${project.id}'">${project.roleUser.Creator}</td>

                        </tr>
                    <#else>
                        No projects
                    </#list>

                    </tbody>
                </table>
            </div>
        </div>
    </div>


    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>