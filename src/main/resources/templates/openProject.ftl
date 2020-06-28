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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
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
                    $("#status_success").delay(2000).fadeOut("slow", "swing");
                }
            });
        }
    </script>


    <script>
        function addComment() {
            var commentText =$('textarea[name="newComment"]').val();
            $.ajax({
                //headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                url: "/comments/addNew",
                type: "POST",
                data: {id: ${project.getId()}, commentText: commentText},
                success: function(response){
                    if(response == "Comment added!")
                        $("#comments").load(" #comments");
                        $("#add_comment").collapse('hide');
                        $("#newComment").val("");
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
            <form action="/projects/edit/${project.id}" method="get">
                <input class="btn btn-primary btn-sm" type="submit" value="Edit project"/>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-2">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <h5 class="card-header">Project members</h5>
                        <ul class="list-group list-group-flush">
                            <#list project.roleUser as roleUser>
                            <li class="list-group-item"><p class="card-text">${roleUser?keep_before(":")}: ${roleUser?keep_after(":")}</p></li>
                            </#list>
                        </ul>
                    </div>
                    <br>
                    <div class="card">
                        <h5 class="card-header">Project status</h5>

                            <select onChange="changeStatus()" class="form-control" id="sta" name="sta">
                                <#list statuses as status>
                                    <option value="${status}">${status}</option>
                                </#list>
                            </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-6">
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col"><h5>${project.title}</h5></div>
                        <div class="col-3"><span class="align-middle">${project.createdAt}</span></div>
                    </div>
                </div>

                <div class="card-body" style="white-space:pre-wrap">${project.description}</div></div></div>
    </div>
    <span style="font-size: 12px" id="status_success" class="badge badge-success"></span>

<br>
    <div class="row">
        <div class="col-2">
        </div>
        <div class="col-6">
            <div class="card collapse" id="add_comment">
                <h6 class="card-header">Add new comment:</h6>
                <div class="card-body">
                    <div class="row">
                        <div class="col">
                            <textarea class="form-control" name="newComment" id="newComment" rows="4" ></textarea>
                        </div>
                        <div class="col-2">
                            <button onclick="addComment()" class="btn btn-success btn-sm">
                                <i class="fa fa-paper-plane" aria-hidden="true"></i>
                                Add
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col"><h5>Comments</h5></div>
                        <div class="col-2">
                            <button class="btn btn-outline-dark btn-sm" data-toggle="collapse" data-target="#add_comment" title="Add comment">
                                <i class="fa fa-plus" aria-hidden="true"></i>
                            </button></div>
                    </div>
                </div>
                <div class="card-body" id="comments">
                    <#if project.getSortedComments()?size !=0>
                    <#list project.getSortedComments() as comment>
                        <div class="row">
                            <div class="col"><h6 class="card-title">${comment.getUser().getUsername()}</h6></div>
                            <div class="col-3"><span class="card-text">${comment.getCreatedAt()}</span></div>
                        </div>
                        <div class="row">
                            <div class="col"><span class="card-text">${comment.getCommentText()}</span><hr/></div>
                        </div>
                    </#list>
                    <#else>
                        <h6 class="card-title">There are no comments</h6>
                    </#if>
            </div>
        </div>
    </div>
</div>


<#--<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>-->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>