<#macro menu>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <div class="row">
        <div class="col">
            <span style="font-size: 16px" id="logged_user">Logged-in as: <b><i><a
                                href="/users/${loggedUser}">${loggedUser}</a></i></b></span>
            <br><br>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="submit" class="btn btn-outline-dark btn-sm" value="Sign Out"/>
            </form>
            <form action="/projects" method="get">
                <input class="btn btn-outline-dark btn-sm" type="submit" value="Project list"/>
            </form>
            <form action="/projects/new" method="get">
                <input class="btn btn-outline-dark btn-sm" type="submit" value="Create new project"/>
            </form>
            <@security.authorize access="hasAnyAuthority('ADMIN')">
                <form action="/admin/users" method="get">
                    <input class="btn btn-outline-primary btn-sm" type="submit" value="Admin panel"/>
                </form>
            </@security.authorize>
        </div>
    </div>
</#macro>