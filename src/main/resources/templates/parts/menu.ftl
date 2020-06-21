<#macro menu>
    <div class="row">
        <div class="col">
            <br>
        <table>
            <tr>
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