
let button = document.getElementById("calculate");
let but = document.getElementById("save");
but.addEventListener("click",checkTest);
button.addEventListener("click", checkTest);

const kt = (((Number(getRating('player1')) + Number(getRating('player1')) + Number(getRating('player1'))) / 3)/2000).toFixed(1);

let F2 = document.getElementById("f2");
let F3 = document.getElementById("f3");
let F13 = document.getElementById("f13")
let F11 = document.getElementById("f11");
let F21 = document.getElementById("f21");
let F22 = document.getElementById("f22");
F2.addEventListener('input', () => F11.value = getCount("f2"));
F3.addEventListener('input', () => F21.value = getCount("f3"));
F13.addEventListener('input', () => F22.value = getCount("f13"));
F11.addEventListener('input', () => F2.value = getCount("f11"));
F21.addEventListener('input', () => F3.value = getCount("f21"));
F22.addEventListener('input', () => F13.value = getCount("f22"));
function getCount(id) {
    let elementById = document.getElementById(id).value;
    if (Number(elementById) == 1 ||
        (elementById).localeCompare('1.') == 0 ||
        (elementById).localeCompare('1,') == 0 ||
        (elementById).localeCompare('1/') == 0 ||
        (elementById).localeCompare('1 ') == 0 ||
        (elementById).localeCompare('1*') == 0 ||
        (elementById).localeCompare('1:') == 0 ||
        (elementById).localeCompare('1-') == 0 ||
        (elementById).localeCompare('1.3') == 0 ||
        (elementById).localeCompare('1,3') == 0 ||
        (elementById).localeCompare('1 3') == 0 ||
        (elementById).localeCompare('1*3') == 0 ||
        (elementById).localeCompare('1:3') == 0 ||
        (elementById).localeCompare('1-3') == 0 ||
        (elementById).localeCompare('1/3') == 0) {
        return "3/1"
    } else if (Number(elementById) == 2 ||
        (elementById).localeCompare('2,') == 0 ||
        (elementById).localeCompare('2/') == 0 ||
        (elementById).localeCompare('2 ') == 0 ||
        (elementById).localeCompare('2*') == 0 ||
        (elementById).localeCompare('2:') == 0 ||
        (elementById).localeCompare('2-') == 0 ||
        (elementById).localeCompare('2.3') == 0 ||
        (elementById).localeCompare('2,3') == 0 ||
        (elementById).localeCompare('2 3') == 0 ||
        (elementById).localeCompare('2*3') == 0 ||
        (elementById).localeCompare('2:3') == 0 ||
        (elementById).localeCompare('2-3') == 0 ||
        (elementById).localeCompare('2/3') == 0) {
        return "3/2"
    } else if (Number(elementById) == 3 ||
        (elementById).localeCompare('3/') == 0 ||
        (elementById).localeCompare('3,') == 0 ||
        (elementById).localeCompare('3/') == 0 ||
        (elementById).localeCompare('3 ') == 0 ||
        (elementById).localeCompare('3*') == 0 ||
        (elementById).localeCompare('3:') == 0 ||
        (elementById).localeCompare('3-') == 0 ||
        (elementById).localeCompare('3,0') == 0 ||
        (elementById).localeCompare('3.0') == 0 ||
        (elementById).localeCompare('3 0') == 0 ||
        (elementById).localeCompare('3*0') == 0 ||
        (elementById).localeCompare('3:0') == 0 ||
        (elementById).localeCompare('3-0') == 0 ||
        (elementById).localeCompare('3/0') == 0) {
        return "0/3"
    } else if ((elementById).localeCompare('3/1') == 0||
    (elementById).localeCompare('3.1') == 0 ||
    (elementById).localeCompare('3,1') == 0 ||
    (elementById).localeCompare('3 1') == 0 ||
    (elementById).localeCompare('3*1') == 0 ||
    (elementById).localeCompare('3:1') == 0 ||
    (elementById).localeCompare('3-1') == 0) {
        return "1/3"
    } else if ((elementById).localeCompare('3/2') == 0 ||
        (elementById).localeCompare('3,2') == 0 ||
        (elementById).localeCompare('3 2') == 0 ||
        (elementById).localeCompare('3*2') == 0 ||
        (elementById).localeCompare('3:2') == 0 ||
        (elementById).localeCompare('3.2') == 0 ||
        (elementById).localeCompare('3-2') == 0) {

        return "2/3"
    }return "3/0"
}

function split(int){
    const str = String(int);
    return str.split(/[., /;:*-]/);
}

function getRating(player){
    let play = document.getElementById(player);
    let pl = play.innerText.split(" ");
    // const pl2 = player2.split(" ");
    let rating = pl[2];
    return rating;
}



function sum(str){
    if (str == ""){
        return 0;
    }
    const spl = split(str);
    const a = spl[0], b = spl[1];
    return Number(a) + Number(b);
}

function win(countSet){
    let cs = split(countSet);
    const a = cs[0], b = cs[1];
    // if (a == b){
    //     console.log("a=b")
    //     return 0;
    // }
    return (Number(a) > Number(b));
}

function winGame(countSet){
    let cs = split(countSet);
    const a = cs[0], b = cs[1];
    if(a > b) return 2;
    else if (a < b) return 1;
    else return 0;
}

function winSet(countSet){
    let cs = split(countSet);
    return cs[0];
}

// function lossSet(countSet){
//     let cs = split(countSet);
//     const a = cs[0], b = cs[1];
//     if (a > b) return b;
//     else return a;
// }

function totalSet(rating1, rating2, count, kt) {
    let delta;
    let r1 = Number(rating1);
    let r2 = Number(rating2);
    if (count == ""){
        console.log(win(count))
        delta = 0;
    }else
    if(win(count)){
        if ((r1 - r2) > 100){
            delta = 0;
        }
        delta = (100 - r1 + r2)/10 * kt;
    }else delta = -(100 - r2 + r1)/10 * kt;
    return delta;
}


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
    let f2 = document.getElementById("f2").value;
    let f3 = document.getElementById("f3").value;
    let f11 = document.getElementById("f11").value;
    let f13 = document.getElementById("f13").value;
    let f21 = document.getElementById("f21").value;
    let f22 = document.getElementById("f22").value;
    set = sum(f2)+sum(f3);
    let result11 = totalSet(getRating('player1'), getRating('player2'), f2, kt);
    let result12 = totalSet(getRating('player1'), getRating('player3'), f3, kt);
    console.log(result11);
    console.log(result12);
    delta = (Number(result11) + Number(result12)).toFixed(2);
    rating = (Number(getRating('player1')) + Number(delta)).toFixed(2);
    let winPlayer1 = Number(winSet(f2)) + Number(winSet(f3));
    let glasses1 = winGame(f2) + winGame(f3);
    console.log(winPlayer1);
    console.log(glasses1);
    place = 1;

    document.getElementById("set").innerHTML = set.toString();
    document.getElementById("rating").innerHTML = rating.toString();
    document.getElementById("delta").innerHTML = delta.toString();
    document.getElementById("place").innerHTML = place.toString();
    set1 = sum(f11) + sum(f13);
    let result21 = totalSet(getRating('player2'), getRating('player1'), f11, kt);
    let result22 = totalSet(getRating('player2'), getRating('player3'), f13, kt);
    console.log(result21);
    console.log(result22);
    delta1 = (Number(result21) + Number(result22)).toFixed(2);
    rating1 = (Number(getRating('player2')) + Number(delta1)).toFixed(2);
    let winPlayer2 = Number(winSet(f11)) + Number(winSet(f13));
    let glasses2 = winGame(f11) + winGame(f13);
    console.log(winPlayer2);
    console.log(glasses2);
    place1 = 2;
    document.getElementById("set1").innerHTML = set1.toString();
    document.getElementById("rating1").innerHTML = rating1.toString();
    document.getElementById("delta1").innerHTML = delta1.toString();
    document.getElementById("place1").innerHTML = place1.toString();
    set2 = sum(f21) + sum(f22);
    let result31 = totalSet(getRating('player3'), getRating('player1'), f21, kt);
    let result32 = totalSet(getRating('player3'), getRating('player2'), f22, kt);
    console.log(result31);
    console.log(result32);
    delta2 = (Number(result31) + Number(result32)).toFixed(2);
    rating2 = (Number(getRating('player3')) + Number(delta2)).toFixed(2);
    let winPlayer3 = Number(winSet(f21)) + Number(winSet(f22));
    let glasses3 = winGame(f21) + winGame(f22);
    console.log(winPlayer3);
    console.log(glasses3);
    console.log([glasses1, glasses2, glasses3].sort().reverse());
    place2 = 3;
    document.getElementById("set2").innerHTML = set2.toString();
    document.getElementById("rating2").innerHTML = rating2.toString();
    document.getElementById("delta2").innerHTML = delta2.toString();
    document.getElementById("place2").innerHTML = place2.toString();
    return rating;
}
