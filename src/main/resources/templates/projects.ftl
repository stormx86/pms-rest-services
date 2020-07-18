<!doctype html>
<html lang="en">
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <#import "parts/menu.ftl" as m>
    <#import "parts/projectListPager.ftl" as p>
    <title>Welcome to the Project Management System</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

    <link rel="stylesheet" type="text/css" href ="css/main.css">


    <#--Autocomplete-->
    <script>
        var autocompl_opt = {source: "/projects/getUserNames", minLength: 2};
        $(document).ready(function autocompleteReady () {
            $(".form-control").each(function () {
                $(this).autocomplete(autocompl_opt);
            })
        });
    </script>


    <#--Adding filter ProjectManager checkbox-->
    <script>
        $(document).ready(function () {
            $("#cb_projectManager").change(function () {
                if (this.checked) {
                    var markup = $("<input class=\"form-control\" type=\"text\" name=\"projectManagerFilter\" id=\"projectManagerFilter\" placeholder=\"Enter Project Manager's Name\" form=\"findForm\">");
                    $(".tb_filter").append(markup);
                    $(".tb_filter").find('input[type=text]:last').autocomplete(autocompl_opt);
                }
                else {
                    $("#projectManagerFilter").remove();
                }
            });
        });
    </script>

    <#--Adding filter Creator checkbox-->
    <script>
        $(document).ready(function () {
            $("#cb_createdBy").change(function () {
                if (this.checked) {
                    var markup = $("<input class=\"form-control\" type=\"text\" name=\"createdByFilter\" id=\"createdByFilter\" placeholder=\"Enter Creator's Name\" form=\"findForm\">");
                    $(".tb_filter").append(markup);
                    $(".tb_filter").find('input[type=text]:last').autocomplete(autocompl_opt);
                }
                else {
                    $("#createdByFilter").remove();
                }
            });
        });
    </script>




</head>
<body>


<div class="container-fluid">
    <@m.menu/>
    <br>
    <div class="row">
        <div class="col-4">
            <button class="btn btn-primary btn-sm" data-toggle="collapse" data-target="#filter_checkboxes" title="Open filter">
                Filter
            </button>
        </div>
    </div>
    <div class="row collapse" id="filter_checkboxes">
        <div class="col-3">
            <br>
            <form method="get" id="findForm" action="/projects">
                <button type="submit" class="btn btn-success btn-sm">Find</button>
            </form>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" value="" id="cb_projectManager">
                <label class="form-check-label" for="projectManager">
                    Project Manager
                </label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" value="" id="cb_createdBy">
                <label class="form-check-label" for="createdBy">
                    Created By
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-2 tb_filter">

        </div>
    </div>
    <br>
    <div class="row">
        <div class="col">
            <table class="table table-hover table-bordered">
                <thead style="background-color: #cfecfa">
                <tr>
                    <th style="width: 8%" scope="col">Project #</th>
                    <th style="width: 11%" scope="col"><a class="projects" href="${url}?page=${page.getNumber()}&size=${page.getSize()}&sort=createdAt,${nextSortDirection}">Created On</a></th>
                    <th scope="col"><a class="projects" href="${url}?page=${page.getNumber()}&size=${page.getSize()}&sort=title,${nextSortDirection}">Project name</a></th>
                    <th style="width: 10%" scope="col"><a class="projects" href="${url}?page=${page.getNumber()}&size=${page.getSize()}&sort=status,${nextSortDirection}">Status</a></th>
                    <th style="width: 10%" scope="col"><a class="projects" href="${url}?page=${page.getNumber()}&size=${page.getSize()}&sort=projectManager,${nextSortDirection}">Project Manager</a></th>
                    <th style="width: 10%" scope="col"><a class="projects" href="${url}?page=${page.getNumber()}&size=${page.getSize()}&sort=creator,${nextSortDirection}">Created By</a></th>
                </tr>
                </thead>
                <tbody>
                <#list page.content as project>
                    <tr>
                        <th scope="row" onclick="location.href='/projects/${project.id}'">${project.id}</th>
                        <td onclick="location.href='/projects/${project.id}'">${project.createdAtFormatted}</td>
                        <td onclick="location.href='/projects/${project.id}'">${project.title}</td>
                        <td onclick="location.href='/projects/${project.id}'">${project.status.getStatusName()}</td>
                        <td onclick="location.href='/projects/${project.id}'">${project.projectManager}</td>
                        <td onclick="location.href='/projects/${project.id}'">${project.creator}</td>
                    </tr>
                <#else>
                    No projects
                </#list>

                </tbody>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <@p.pager url page sort nextSortDirection/>
        </div>
    </div>
</div>


<#--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>