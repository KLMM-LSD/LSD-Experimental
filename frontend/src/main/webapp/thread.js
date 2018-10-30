var json;
var comments_id, xhttp, built_string;

function build_one(idx)
{
	var postid = json["arr_postid"][idx];
	var postparentid = json["arr_postparentid"][idx];
	var authorid = json["arr_postauthorid"][idx];
	var content = json["arr_postcontent"][idx];

	built_string += "<div>#" + postid;
	built_string += " | Author " + authorid;
	if (idx != 0 || !json["first_is_story"])
		built_string += " | Re " + postparentid;
	built_string += "</div>";
	built_string += "<div>" + content + "</div>";
	built_string += "<hr/>";
}

function build_posts()
{
	if (json["len"] == 0) {
		built_string = "No such post";
		return;
	}

	for (var i = 0; i != json["len"]; i++)
		build_one(i);
}

function fetch_comments()
{

	if (xhttp.readyState != 4 || xhttp.status != 200)
		return;

	json = JSON.parse(xhttp.responseText);
	build_posts();

	comments_id.innerHTML = built_string;
}

function main()
{
	comments_id	= document.getElementById("comments");
	xhttp		= new XMLHttpRequest();
	built_string	= "";

	xhttp.onreadystatechange = fetch_comments;
	xhttp.open("GET", "../latest/thread" + window.location.search);
	xhttp.send();
}

document.onreadystatechange = function()
{
	if (document.readyState !== "interactive")
		return;
	main();
}
