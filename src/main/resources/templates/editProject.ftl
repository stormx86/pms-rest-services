<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <#import "parts/menu.ftl" as m>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">

    <title>Edit project</title>

    <script>
        $(document).ready(function () {
            $(".form-control").each(function () {
                $(this).autocomplete({
                    source: "/projects/getUserNames",
                    minLength: 2
                });
            })
        });
    </script>

    <#--Set selected role in <select> - ADVANCED VERSION-->
    <script>
        $(document).ready(function () {
        $("select.form-control").each(function() {
            var specifiedID = this.id;
            $(this).val(specifiedID);})
        });
    </script>



</head>
<body>

<div class="container-fluid">
    <@m.menu/>
    <br>
    <h3>Edit project</h3>
    <div class="row">
        <form id="editProject" method="post" action="/projects/save/${project.id}">
        </form>
        <div class="col-3">

            <table class="table table-sm table-bordered">
                <thead>
                <label style="font-weight:bold; font-size:16px">Project members:</label><br>
                </thead>
                <tbody>
                <#list project.roleUser?keys as key>
                    <tr>
                        <td>
                            <select form="editProject" class="form-control" id="${key}" name="role_${key}">
                                <#list project.roleUser?keys as key>
                                    <option value="${key}">${key}</option>
                                </#list>
                            </select></td>
                        <td><input class="form-control" id="user_member" type="text" form="editProject" name="user_${project.roleUser[key]}" value="${project.roleUser[key]}"></td>
                    </tr>
                </#list>
                </tbody>
            </table>

            <button type="submit" class="btn btn-primary btn-sm" form="editProject">Save</button>
        </div>
        <div class="col-6">
            <label style="font-weight:bold; font-size:16px">Project title:</label><br>
            <input class="form-control" type="text" form="editProject" name="title" value="${project.title}" style="width: 80%"><br><br>
            <label style="font-weight:bold; font-size:16px">Description:</label><br>
            <textarea class="form-control" name="description" form="editProject" rows="12" style="width: 80%">${project.description}</textarea>
        </div>
    </div>
</div>










<#--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>