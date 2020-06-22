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
        $( function() {
            $( "#users" ).autocomplete({
                source: "/projects/getUserNames",
                minLength: 2
            });
        } );
    </script>

</head>
<body>

<div class="container-fluid">
    <@m.menu/>
    <br>
    <h3>Create new project</h3>
    <div class="row">
        <form id="createProject" method="post" action="/projects/add">
        </form>
        <div class="col-2">
            <label style="font-weight:bold; font-size:16px">Project manager:</label><br>
            <input class="form-control" id="users" type="text" form="createProject" name="pmUser"><br><br><br>
            <button type="submit" class="btn btn-primary btn-sm" form="createProject">Create</button>
        </div>
        <div class="col-6">
            <label style="font-weight:bold; font-size:16px">Project title:</label><br>
            <input class="form-control" type="text" name="title" form="createProject" style="width: 80%"><br><br>
            <label style="font-weight:bold; font-size:16px">Description:</label><br>
            <textarea class="form-control" name="description" form="createProject" rows="12" style="width: 80%"></textarea>
        </div>
        </form>
    </div>
</div>



<#--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

</body>
</html>