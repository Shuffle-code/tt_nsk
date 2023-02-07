
// let button = document.getElementById("calculate");
// let but = document.getElementById("save");
// button.addEventListener("click", checkTest);
//
// const kt = (((Number(getRating('player1')) + Number(getRating('player1')) + Number(getRating('player1'))) / 3)/2000).toFixed(1);
let x1y2 = document.getElementById("x1y2");
let x1y3 = document.getElementById("x1y3");
let x2y3 = document.getElementById("x2y3");
let x2y1 = document.getElementById("x2y1");
let x3y1 = document.getElementById("x3y1");
let x3y2 = document.getElementById("x3y2");
x1y2.addEventListener('input', () => x2y1.value = getCount("x1y2"));
x1y3.addEventListener('input', () => x3y1.value = getCount("x1y3"));
x2y3.addEventListener('input', () => x3y2.value = getCount("x2y3"));
x2y1.addEventListener('input', () => x1y2.value = getCount("x2y1"));
x3y1.addEventListener('input', () => x1y3.value = getCount("x3y1"));
x3y2.addEventListener('input', () => x2y3.value = getCount("x3y2"));
function getCount(id) {
    let elementById = document.getElementById(id).value;
    if (Number(elementById) == 0) return ""
    else if (
        (elementById).localeCompare('0.') == 0 ||
        (elementById).localeCompare('0,') == 0 ||
        (elementById).localeCompare('0/') == 0 ||
        (elementById).localeCompare('0 ') == 0 ||
        (elementById).localeCompare('0*') == 0 ||
        (elementById).localeCompare('0:') == 0 ||
        (elementById).localeCompare('0-') == 0 ||
        (elementById).localeCompare('0.2') == 0 ||
        (elementById).localeCompare('0,2') == 0 ||
        (elementById).localeCompare('0 2') == 0 ||
        (elementById).localeCompare('0*2') == 0 ||
        (elementById).localeCompare('0:2') == 0 ||
        (elementById).localeCompare('0-2') == 0 ||
        (elementById).localeCompare('0/2') == 0
    ) {
        return "2/0"
    }else if (
        (elementById).localeCompare('0.4') == 0 ||
        (elementById).localeCompare('0,4') == 0 ||
        (elementById).localeCompare('0 4') == 0 ||
        (elementById).localeCompare('0*4') == 0 ||
        (elementById).localeCompare('0:4') == 0 ||
        (elementById).localeCompare('0-4') == 0 ||
        (elementById).localeCompare('0/4') == 0
    ) {
        return "4/0"
    }else if (Number(elementById) == 1 ||
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
        (elementById).localeCompare('1/3') == 0
    ) {
        return "3/1"
    }else if (
        (elementById).localeCompare('1.4') == 0 ||
        (elementById).localeCompare('1,4') == 0 ||
        (elementById).localeCompare('1 4') == 0 ||
        (elementById).localeCompare('1*4') == 0 ||
        (elementById).localeCompare('1:4') == 0 ||
        (elementById).localeCompare('1-4') == 0 ||
        (elementById).localeCompare('1/4') == 0
    ) {
        return "4/1"
    }else if (
        (elementById).localeCompare('1.2') == 0 ||
        (elementById).localeCompare('1,2') == 0 ||
        (elementById).localeCompare('1 2') == 0 ||
        (elementById).localeCompare('1*2') == 0 ||
        (elementById).localeCompare('1:2') == 0 ||
        (elementById).localeCompare('1-2') == 0 ||
        (elementById).localeCompare('1/2') == 0
    ) {
        return "2/1"
    }else if (
        (elementById).localeCompare('2,0') == 0 ||
        (elementById).localeCompare('2 0') == 0 ||
        (elementById).localeCompare('2*0') == 0 ||
        (elementById).localeCompare('2:0') == 0 ||
        (elementById).localeCompare('2-0') == 0 ||
        (elementById).localeCompare('2.0') == 0 ||
        (elementById).localeCompare('2/0') == 0
    ) {
        return "0/2"
    }else if (
        (elementById).localeCompare('2.1') == 0 ||
        (elementById).localeCompare('2,1') == 0 ||
        (elementById).localeCompare('2 1') == 0 ||
        (elementById).localeCompare('2*1') == 0 ||
        (elementById).localeCompare('2:1') == 0 ||
        (elementById).localeCompare('2-1') == 0 ||
        (elementById).localeCompare('2/1') == 0
    ) {
        return "1/2"
    }else if (
        (elementById).localeCompare('2.4') == 0 ||
        (elementById).localeCompare('2,4') == 0 ||
        (elementById).localeCompare('2 4') == 0 ||
        (elementById).localeCompare('2*4') == 0 ||
        (elementById).localeCompare('2:4') == 0 ||
        (elementById).localeCompare('2-4') == 0 ||
        (elementById).localeCompare('2/4') == 0
    ) {
        return "4/2"
    } else if (Number(elementById) == 2 ||
        (elementById).localeCompare('2.') == 0 ||
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
    }else if (
        (elementById).localeCompare('3.4') == 0 ||
        (elementById).localeCompare('3,4') == 0 ||
        (elementById).localeCompare('3 4') == 0 ||
        (elementById).localeCompare('3*4') == 0 ||
        (elementById).localeCompare('3:4') == 0 ||
        (elementById).localeCompare('3-4') == 0 ||
        (elementById).localeCompare('3/4') == 0
    ) {
        return "4/3"
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

    }else if (Number(elementById) == 4 ||
        (elementById).localeCompare('4.0') == 0 ||
        (elementById).localeCompare('4,0') == 0 ||
        (elementById).localeCompare('4 0') == 0 ||
        (elementById).localeCompare('4*0') == 0 ||
        (elementById).localeCompare('4:0') == 0 ||
        (elementById).localeCompare('4-0') == 0 ||
        (elementById).localeCompare('4/0') == 0
    ) {
        return "0/4"
    }else if (
        (elementById).localeCompare('4.1') == 0 ||
        (elementById).localeCompare('4,1') == 0 ||
        (elementById).localeCompare('4 1') == 0 ||
        (elementById).localeCompare('4*1') == 0 ||
        (elementById).localeCompare('4:1') == 0 ||
        (elementById).localeCompare('4-1') == 0 ||
        (elementById).localeCompare('4/1') == 0
    ) {
        return "1/4"
    }else if (
        (elementById).localeCompare('4.2') == 0 ||
        (elementById).localeCompare('4,2') == 0 ||
        (elementById).localeCompare('4 2') == 0 ||
        (elementById).localeCompare('4*2') == 0 ||
        (elementById).localeCompare('4:2') == 0 ||
        (elementById).localeCompare('4-2') == 0 ||
        (elementById).localeCompare('4/2') == 0
    ) {
        return "2/4"
    }else if (
        (elementById).localeCompare('4.3') == 0 ||
        (elementById).localeCompare('4,3') == 0 ||
        (elementById).localeCompare('4 3') == 0 ||
        (elementById).localeCompare('4*3') == 0 ||
        (elementById).localeCompare('4:3') == 0 ||
        (elementById).localeCompare('4-3') == 0 ||
        (elementById).localeCompare('4/3') == 0
    ) {
        return "3/4"
    }return "3/0"
    function split(count){
        const str = String(count);
        return str.split(/[., /;:*-]/);
    }
    function win(countSet){
        let cs = split(countSet);
        const a = cs[0], b = cs[1];
        return (Number(a) > Number(b));
    }
}




