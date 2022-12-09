
let x1y2 = document.getElementById("x1y2");
let x1y3 = document.getElementById("x1y3");
let x1y4 = document.getElementById("x1y4");
let x1y5 = document.getElementById("x1y5");
let x2y1 = document.getElementById("x2y1");
let x2y3 = document.getElementById("x2y3");
let x2y4 = document.getElementById("x2y4");
let x2y5 = document.getElementById("x2y5");
let x3y1 = document.getElementById("x3y1");
let x3y2 = document.getElementById("x3y2");
let x3y4 = document.getElementById("x3y4");
let x3y5 = document.getElementById("x3y5");
let x4y1 = document.getElementById("x4y1");
let x4y2 = document.getElementById("x4y2");
let x4y3 = document.getElementById("x4y3");
let x4y5 = document.getElementById("x4y5");
let x5y1 = document.getElementById("x5y1");
let x5y2 = document.getElementById("x5y2");
let x5y3 = document.getElementById("x5y3");
let x5y4 = document.getElementById("x5y4");
x1y2.addEventListener('input', () => x2y1.value = getCount("x1y2"));
x1y3.addEventListener('input', () => x3y1.value = getCount("x1y3"));
x1y4.addEventListener('input', () => x4y1.value = getCount("x1y4"));
x1y5.addEventListener('input', () => x5y1.value = getCount("x1y5"));
x2y1.addEventListener('input', () => x1y2.value = getCount("x2y1"));
x2y3.addEventListener('input', () => x3y2.value = getCount("x2y3"));
x2y4.addEventListener('input', () => x4y2.value = getCount("x2y4"));
x2y5.addEventListener('input', () => x5y2.value = getCount("x2y5"));
x3y1.addEventListener('input', () => x1y3.value = getCount("x3y1"));
x3y2.addEventListener('input', () => x2y3.value = getCount("x3y2"));
x3y4.addEventListener('input', () => x4y3.value = getCount("x3y4"));
x3y5.addEventListener('input', () => x5y3.value = getCount("x3y5"));
x4y1.addEventListener('input', () => x1y4.value = getCount("x4y1"));
x4y2.addEventListener('input', () => x2y4.value = getCount("x4y2"));
x4y3.addEventListener('input', () => x3y4.value = getCount("x4y3"));
x4y5.addEventListener('input', () => x5y4.value = getCount("x4y5"));
x5y1.addEventListener('input', () => x1y5.value = getCount("x5y1"));
x5y2.addEventListener('input', () => x2y5.value = getCount("x5y2"));
x5y3.addEventListener('input', () => x3y5.value = getCount("x5y3"));
x5y4.addEventListener('input', () => x4y5.value = getCount("x5y4"));
function getCount(id) {
    let elementById = document.getElementById(id).value;
    if (Number(elementById) == 0) return ""
    else if (Number(elementById) == 1 ||
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




