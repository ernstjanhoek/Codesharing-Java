<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
</head>
<body>

<form>
    <label for="time_restriction">Time restriction</label>
    <input id="time_restriction" type="number" /><br>
    <label for="views_restriction">Views restriction</label>
    <input id="views_restriction" type="number"/><br>
    <label for="code_snippet">Your code</label><br>
    <textarea id="code_snippet" rows="30" cols="100"></textarea>
    <button id="send_snippet" type="submit" onclick="send()">Submit</button>
</form>

<script type="text/javascript">
    function send() {
        let object = {
            "code": document.getElementById("code_snippet").value,
            "time": Number.parseInt(document.getElementById("time_restriction").value),
            "views": Number.parseInt(document.getElementById("views_restriction").value)
        };
        let json = JSON.stringify(object);
        let xhr = new XMLHttpRequest();
        xhr.open("POST", '/api/code/new', false)
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(json);
        if (xhr.status == 200) {
            alert("Success: " + xhr.responseText);
        }
    }
</script>
</body>
</html>