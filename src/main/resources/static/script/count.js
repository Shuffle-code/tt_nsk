let button = document.getElementById("calculate");
let but = document.getElementById("save");
button.addEventListener("click", checkTest);

function addSlash(int){
    int.padEnd(2, "/");
}
function split(int){
    const str = String(int);
    return str.split("");
}

function sum(str){
    const spl = split(str);
    const a = spl[0], b = spl[1];
    return Number(a) + Number(b);
}

//
// const array = str.split("");
// a = array[0], b = array[1];

"Hello Tproger".split("");

function checkTest() {
    let set;
    let rating;
    let delta;
    let place;
    let set1;
    let rating1;
    let delta1;
    let place1;
    let set2;
    let rating2;
    let delta2;
    let place2;
    let f2 = Number(document.getElementById("f2").value);
    // addSlash(f2);
    let f3 = Number(document.getElementById("f3").value);
    let f11 = Number(document.getElementById("f11").value);
    let f13 = Number(document.getElementById("f13").value);
    let f21 = Number(document.getElementById("f21").value);
    let f22 = Number(document.getElementById("f22").value);
    set = sum(f2)+sum(f3);
    rating = set * 4;
    delta = rating - set;
    place = 1;
    document.getElementById("set").innerHTML = set.toString();
    document.getElementById("rating").innerHTML = rating.toString();
    document.getElementById("delta").innerHTML = delta.toString();
    document.getElementById("place").innerHTML = place.toString();
    set1 = sum(f11) + sum(f13);
    rating1 = set1 * 4;
    delta1 = rating1 - set1;
    place1 = 2;
    document.getElementById("set1").innerHTML = set1.toString();
    document.getElementById("rating1").innerHTML = rating1.toString();
    document.getElementById("delta1").innerHTML = delta1.toString();
    document.getElementById("place1").innerHTML = place1.toString();
    set2 = sum(f21) + sum(f22);
    rating2 = set2 * 4;
    delta2 = rating2 - set2;
    place2 = 3;
    document.getElementById("set2").innerHTML = set2.toString();
    document.getElementById("rating2").innerHTML = rating2.toString();
    document.getElementById("delta2").innerHTML = delta2.toString();
    document.getElementById("place2").innerHTML = place2.toString();
    return rating;
}