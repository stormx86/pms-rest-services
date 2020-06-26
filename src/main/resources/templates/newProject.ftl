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
    <title>Create new project</title>

    <script>
        var autocompl_opt = {source: "/projects/getUserNames", minLength: 2};
        $(document).ready(function autocompleteReady () {
            $(".form-control").each(function () {
                $(this).autocomplete(autocompl_opt);
            })
        });
    </script>

    <script>
        function createProject() {
            var roles = [], users = [], object = {};cre
            $("select").each(function () {
                roles.push($(this).val());
            })
            $('input[name="userMember"]').each(function(){
                users.push($(this).val());
            })
            var title = $('input[name="title"]').val();
            var description =$('textarea[name="description"]').val();
            $.ajax({
                //headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                url: "/projects/add",
                type: "POST",
                data: {title: title, description: description, roles: roles, users: users},
                success: function(response){
                    if(response="OK"){
                        window.location.replace("http://localhost:8080/projects")
                    }
                }
            })
        }
    </script>

    <#--Adding new select with roles and users-->
    <script>
        $(document).ready(function () {
            $("#add_role").click(function () {
                var markup = $("<tr><td><select class=\"form-control\" name=\"role\">\n" +
                    "                                <#list existingRoles as er>\n" +
                    "                                    <option value=\"${er}\">${er}</option>\n" +
                    "                                </#list>\n" +
                    "                            </select></td><td><input class=\"form-control\" id=\"user_member\" type=\"text\"  name=\"userMember\" ></td></tr>");
                tableBody = $("#tb_roles tbody");
                $(".form-control", markup).autocomplete(autocompl_opt);
                tableBody.append(markup);
            });
        });
    </script>

</head>
<body>

<div class="container-fluid">
    <@m.menu/>
    <br>
    <h3>Create new project</h3>
    <div class="row">

        <div class="col-3">
            <table id="tb_roles" class="table table-sm table-bordered">
                <thead>
                <label style="font-weight:bold; font-size:16px">Project members:</label><br>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <select  class="form-control" name="role">
                                <#list existingRoles as er>
                                    <option value="${er}">${er}</option>
                                </#list>
                            </select>
                        </td>
                        <td><input class="form-control" id="user_member" type="text"  name="userMember"></td>
                    </tr>
                </tbody>
            </table>
            <button id="add_role" class="btn btn-primary btn-sm">Add role</button><br><br>
            <button onclick="createProject()" class="btn btn-success btn-sm">Create</button><br>
        </div>
        <div class="col-6">
            <label style="font-weight:bold; font-size:16px">Project title:</label><br>
            <input class="form-control" type="text" name="title" style="width: 80%"><br><br>
            <label style="font-weight:bold; font-size:16px">Description:</label><br>
            <textarea class="form-control" name="description" rows="12" style="width: 80%"></textarea>
        </div>

    </div>
</div>



<#--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

</body>
</html>