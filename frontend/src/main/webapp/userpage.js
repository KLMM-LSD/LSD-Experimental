var username, usertype, xhttp, built_string;
var len, arr_postid, arr_postthreadid, arr_postcontent;

function escape_html(unsafe)
{
    return unsafe
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;");
}

function append_entry(idx)
{
    built_string += "<div class=\"postinfo\">";
    built_string += "<a class=\"backlink\" href=\"thread.html?id=" + arr_postthreadid[idx] + "#" + arr_postid[idx] + "\">" + arr_postid[idx] + "</a>";
    built_string += "</div><div>" + escape_html(arr_postcontent[idx]) + "</div>";
    built_string += "<hr/>";
}

function fetch_posts()
{
    var json;

    if (xhttp.readyState !== 4 || xhttp.status !== 200)
        return;
    json = JSON.parse(xhttp.responseText);

    username = json["username"];
    usertype = json["usertype"];
    len = json["len"];
    arr_postid = json["arr_postid"];
    arr_postthreadid = json["arr_postthreadid"];
    arr_postcontent = json["arr_postcontent"];

    built_string = "<br/><hr/>";

    for (var i = 0; i !== len; i++)
        append_entry(i);

    document.getElementById("username").innerHTML = "<b>Username:</b> " + username;
    document.getElementById("usertype").innerHTML = "<b>Usertype:</b> " + usertype;
    document.getElementById("recent_posts").innerHTML = built_string;
}

function main()
{
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = fetch_posts;
    xhttp.open("GET", "../latest/user" + window.location.search);
    xhttp.send();
}

document.onreadystatechange = function ()
{
    if (document.readyState !== "interactive")
        return;
    main();
};

