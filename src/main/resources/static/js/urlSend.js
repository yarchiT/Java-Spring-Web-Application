var myList=[];
var temporaryUnusedList=[];
var count =0;

function post () {
    var inputUrl = document.getElementById("inputURL");

    if(!validateUrl(inputUrl.value)){
        alert("Your url is Invalid");
    }else{
        var data = {"urlS": inputUrl.value};
        inputUrl.value="";

        // construct an HTTP request
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/urls", true);
        xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');

        xhr.send(JSON.stringify(data));

        setInterval(get, 3000);
        testRunning();
        setTimeout(backValue, 3000);
    }

};

function get(){
    var xmlhttp = new XMLHttpRequest();
    var url = "/urls";

    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            myList = JSON.parse(this.responseText);
            fillTable();
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();

}

function backValue(){
    clearInterval(interval);
    var button = document.getElementById('testButton');
    button.style.opacity=1;
    button.innerHTML = "Start Test";
}

var intCount = 0;
var interval;
function testRunning(){
    var button = document.getElementById('testButton');
    button.style.opacity=0.4;
    interval = setInterval(function(){
        intCount++;
        var dots = new Array(intCount % 4).join('.');
        button.innerHTML = "Test is running" + dots;
    }, 1000);
   }

function validateUrl(value) {
    return /^(?:(?:(?:https?|ftp):)?\/\/)(?:\S+(?::\S*)?@)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:[/?#]\S*)?$/i.test(value);
}


// fill table with response and status
function fillTable(){
    expandTable();

    for(var i=0; i<myList.length;i++){
        var url = document.getElementById("url"+i);
        var status = document.getElementById("code"+i);

        if(containsObject(myList[i], temporaryUnusedList)){
            url.innerHTML = "Temporary not checking";
            status.innerHTML = "~";
        }else{
            url.innerHTML = myList[i].urlS;
            addStyle("tr"+i, myList[i].responseCode, status);
        }
    }

}

// add style to the table row depending on the response status
function addStyle(id, code,status){
    var tr = document.getElementById(id);
    var audioError = document.getElementById('audioErr');
    var audioWarning = document.getElementById('audioWarn');

    if(code == 200){
        status.innerHTML="OK"
        tr.setAttribute("class", "OKresponse");
    }else if(code>200 && code<400){
        status.innerHTML="WARNING";
        tr.setAttribute("class", "WARNINGresponse")
        audioWarning.play();
    }else{
        status.innerHTML="CRITICAL"
        tr.setAttribute("class", "CRITICALresponse")
        audioError.play();
    }
}

// dynamically extend table depending on the amount of urls
function expandTable() {
    var table = document.getElementById("contentTable");

    for(var i=count; i<myList.length;i++){
        var tr = document.createElement("TR");
        var td1 = document.createElement("TD");
        var td2 = document.createElement("TD");
        var td3 = document.createElement("TD");

        tr.setAttribute("id", "tr"+i);
        td1.setAttribute("id", "url"+i);
        td2.setAttribute("id", "code"+i);
        td3.setAttribute("id", "offOn"+i);

        addSwitch(td3, i);

        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);

        table.appendChild(tr);
        count++;
    }
}

// add checkBox to be able to turn on and off monitoring of the specified URL
function addSwitch(td3, i){
    var label = document.createElement("LABEL");
    label.setAttribute("class", "switch");

    var input = document.createElement("INPUT");
    input.setAttribute("type", "checkbox" );
    input.setAttribute("onclick", "getCheckBox(this.id)");
    input.setAttribute("id", i+"check");

    var span = document.createElement("SPAN");
    span.setAttribute("class", "slider");

    label.appendChild(input);
    label.appendChild(span);

    td3.appendChild(label);
}

function getCheckBox(id) {
    var checkBox = document.getElementById(id);
    var i = parseInt(id);
    var urlTd = document.getElementById("url"+i);
    var respCode = document.getElementById("code"+i);
    var tr = document.getElementById("tr"+i);

    var object = {id: i+1, urlS: urlTd.innerHTML, responseCode: respCode.innerHTML};

    if(checkBox.checked && !containsObject(object, temporaryUnusedList)){
        temporaryUnusedList.push(object);
        tr.setAttribute("class", "notChecking");
    }else{

        temporaryUnusedList = temporaryUnusedList.filter(function(el) {
            return el.id !== i+1;
        });
        tr.removeAttribute("class");
    }
}

function containsObject(obj, list) {
    var res = false;
    list.forEach( function (arrayItem)
    {
        if( arrayItem.id == obj.id)
        res=true;
    });
    return res;
}