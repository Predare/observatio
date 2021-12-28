
    function sendAuthRequest(){

        var login = $("#username").val();
        var password = $("#password").val();

        var authReqObject = {
            username: login,
            password: password
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
