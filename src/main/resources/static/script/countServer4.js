let x1y2 = document.getElementById("x1y2");
let x1y3 = document.getElementById("x1y3");
let x1y4 = document.getElementById("x1y4");
let x2y1 = document.getElementById("x2y1");
let x2y3 = document.getElementById("x2y3")
let x2y4 = document.getElementById("x2y4")
let x3y1 = document.getElementById("x3y1");
let x3y2 = document.getElementById("x3y2");
let x3y4 = document.getElementById("x3y4");
let x4y1 = document.getElementById("x4y1");
let x4y2 = document.getElementById("x4y2");
let x4y3 = document.getElementById("x4y3");
x1y2.addEventListener('input', () => x1y2.value = getSlash("x1y2"));
x1y2.addEventListener('input', () => x2y1.value = getCount("x1y2"));

x1y3.addEventListener('input', () => x1y3.value = getSlash("x1y3"));
x1y3.addEventListener('input', () => x3y1.value = getCount("x1y3"));

x1y4.addEventListener('input', () => x1y4.value = getSlash("x1y4"));
x1y4.addEventListener('input', () => x4y1.value = getCount("x1y4"));

x2y1.addEventListener('input', () => x2y1.value = getSlash("x2y1"));
x2y1.addEventListener('input', () => x1y2.value = getCount("x2y1"));

x2y3.addEventListener('input', () => x2y3.value = getSlash("x2y3"));
x2y3.addEventListener('input', () => x3y2.value = getCount("x2y3"));

x2y4.addEventListener('input', () => x2y4.value = getSlash("x2y4"));
x2y4.addEventListener('input', () => x4y2.value = getCount("x2y4"));

x3y1.addEventListener('input', () => x3y1.value = getSlash("x3y1"));
x3y1.addEventListener('input', () => x1y3.value = getCount("x3y1"));

x3y2.addEventListener('input', () => x3y2.value = getSlash("x3y2"));
x3y2.addEventListener('input', () => x2y3.value = getCount("x3y2"));

x3y4.addEventListener('input', () => x3y4.value = getSlash("x3y4"));
x3y4.addEventListener('input', () => x4y3.value = getCount("x3y4"));

x4y1.addEventListener('input', () => x4y1.value = getSlash("x4y1"));
x4y1.addEventListener('input', () => x1y4.value = getCount("x4y1"));

x4y2.addEventListener('input', () => x4y2.value = getSlash("x4y2"));
x4y2.addEventListener('input', () => x2y4.value = getCount("x4y2"));

x4y3.addEventListener('input', () => x4y3.value = getSlash("x4y3"));
x4y3.addEventListener('input', () => x3y4.value = getCount("x4y3"));
