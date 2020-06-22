<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <#import "parts/menu.ftl" as m>
    <title>Welcome to the Project Management System</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <#--Set selected status in <select> according to the actual project status-->
    <script>
        $(function() {
            var x="${project.status.getStatusName()}";
            $("#sta").val(x);
        });
    </script>

    <script>
        function changeStatus () {
            var id = ${project.getId()};
            var status = $("#sta option:selected").val();
            $.ajax({
                //headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                url: "/projects/changeStatus",
                type: "POST",
                data: {id: id, status: status},
                success: function(response){
                    $("#status_success").empty();
                    $("#status_success").append(response);
                }
            });
        }
    </script>


</head>
<body>




<div class="container-fluid">
    <@m.menu/>
    <br>
    <div class="row">
        <div class="col">
            <form action="/projects/edit/${project.id}" method="get">
                <input class="btn btn-primary btn-sm" type="submit" value="Edit project"/>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-2">
            <div class="row">
                <div class="col">
                    <table class="table table-sm table-bordered">
                        <thead>
                        <tr>
                            <th scope="col">Project members</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list project.roleUser?keys as key>
                            <tr>
                                <td>${key} : ${project.roleUser[key]}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>

                    <table class="table table-sm table-bordered">
                        <thead>
                        <tr>
                            <th scope="col">Project status</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <select onChange="changeStatus()" class="form-control" id="sta" name="sta">
                                        <#list statuses as status>
                                            <option value="${status}">${status}</option>
                                        </#list>
                                    </select>
                                    <span style="font-size: 12px" id="status_success" class="badge badge-success"></span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <div class="col-6">
            <table class="table table-sm table-bordered">
                <tbody>

                    <tr>
                        <td>
                            <b>${project.title}</b>
                        </td>
                    </tr>

                    <tr>
                        <td style="white-space:pre-wrap">${project.description}</td>
                    </tr>

                </tbody>
            </table>


        </div>
    </div>
</div>

<#--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>