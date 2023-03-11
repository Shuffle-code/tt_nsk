
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
//




