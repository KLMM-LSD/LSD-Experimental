var threads_id, xhttp, built_string;
var len, arr_postid, arr_postauthorid, arr_postcontent;

function escape_html(unsafe)
{
    return unsafe
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;");
}

function append_entry(idx)
{
    built_string += "<tr><td>";
    built_string += "<a href=\"thread.html?id=" + arr_postid[idx] + "\">" + arr_postid[idx] + "</a>";
    built_string += "</td><td>";
    built_string += escape_html(arr_postcontent[idx]);
    built_string += "</td><td>";
    built_string += arr_postauthorid[idx];
    built_string += "</td></tr>";
}

function fetch_threads()
{
    var json;

    if (xhttp.readyState !== 4 || xhttp.status !== 200)
        return;

    json = JSON.parse(xhttp.responseText);

    len = json["len"];
    arr_postid = json["arr_postid"];
    arr_postauthorid = json["arr_postauthorid"];
    arr_postcontent = json["arr_postcontent"];

    built_string = "<table><tr><th>id</th><th>content</th><th>authorid</th></tr>";
    for (var i = 0; i !== len; i++)
        append_entry(i);
    built_string += "</table>";

    threads_id.innerHTML = built_string;
}

function main()
{
    threads_id = document.getElementById("threads");
    xhttp = new XMLHttpRequest();
    built_string = "Ready<br/>";

    xhttp.onreadystatechange = fetch_threads;
    xhttp.open("GET", "../latest/frontpage");
    xhttp.send();
}

document.onreadystatechange = function ()
{
    if (document.readyState !== "interactive")
        return;
    main();
};
