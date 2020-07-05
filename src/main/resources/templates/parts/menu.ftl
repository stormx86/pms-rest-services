<#macro menu>
    <div class="row">
        <div class="col">
            <span style="font-size: 16px" id="logged_user">Logged-in as: <b><i>${loggedUser}</i></b></span>
            <br><br>
        <table>
            <tr>
                <td>
                    <form action="/logout" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input type="submit" class="btn btn-secondary" value="Sign Out"/>
                    </form>
                </td>
                <td>
                    <form action="/projects" method="get">
                        <input class="btn btn-secondary" type="submit" value="Project list" />
                    </form>
                </td>
                <td>
                    <form action="/projects/newProject" method="get">
                        <input class="btn btn-secondary" type="submit" value="Create new project" />
                    </form>
                </td>
            </tr>
        </table>
    </div>
    </div>
</#macro>