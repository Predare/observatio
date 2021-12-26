
    function sendAuthRequest(){

        var fa = $("#username").val();
        var aa = $("#password").val();

        var authReqObject = {
            username: fa,
            password: aa
        }

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {              
                //Success
            }
        };

        xhttp.open("POST", "/api/public/perform_login");
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(authReqObject));
    }
