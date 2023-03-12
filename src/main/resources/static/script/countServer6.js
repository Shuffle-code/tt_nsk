let x1y2 = document.getElementById("x1y2");
let x1y3 = document.getElementById("x1y3");
let x1y4 = document.getElementById("x1y4");
let x1y5 = document.getElementById("x1y5");
let x1y6 = document.getElementById("x1y6");
let x2y1 = document.getElementById("x2y1");
let x2y3 = document.getElementById("x2y3");
let x2y4 = document.getElementById("x2y4");
let x2y5 = document.getElementById("x2y5");
let x2y6 = document.getElementById("x2y6");
let x3y1 = document.getElementById("x3y1");
let x3y2 = document.getElementById("x3y2");
let x3y4 = document.getElementById("x3y4");
let x3y5 = document.getElementById("x3y5");
let x3y6 = document.getElementById("x3y6");
let x4y1 = document.getElementById("x4y1");
let x4y2 = document.getElementById("x4y2");
let x4y3 = document.getElementById("x4y3");
let x4y5 = document.getElementById("x4y5");
let x4y6 = document.getElementById("x4y6");
let x5y1 = document.getElementById("x5y1");
let x5y2 = document.getElementById("x5y2");
let x5y3 = document.getElementById("x5y3");
let x5y4 = document.getElementById("x5y4");
let x5y6 = document.getElementById("x5y6");
let x6y1 = document.getElementById("x6y1");
let x6y2 = document.getElementById("x6y2");
let x6y3 = document.getElementById("x6y3");
let x6y4 = document.getElementById("x6y4");
let x6y5 = document.getElementById("x6y5");
x1y2.addEventListener('input', () => x1y2.value = getSlash("x1y2"));
x1y2.addEventListener('input', () => x2y1.value = getCount("x1y2"));

x1y3.addEventListener('input', () => x1y3.value = getSlash("x1y3"));
x1y3.addEventListener('input', () => x3y1.value = getCount("x1y3"));

x1y4.addEventListener('input', () => x1y4.value = getSlash("x1y4"));
x1y4.addEventListener('input', () => x4y1.value = getCount("x1y4"));

x1y5.addEventListener('input', () => x1y5.value = getSlash("x1y5"));
x1y5.addEventListener('input', () => x5y1.value = getCount("x1y5"));

x1y6.addEventListener('input', () => x1y6.value = getSlash("x1y6"));
x1y6.addEventListener('input', () => x6y1.value = getCount("x1y6"));

x2y1.addEventListener('input', () => x2y1.value = getSlash("x2y1"));
x2y1.addEventListener('input', () => x1y2.value = getCount("x2y1"));

x2y3.addEventListener('input', () => x2y3.value = getSlash("x2y3"));
x2y3.addEventListener('input', () => x3y2.value = getCount("x2y3"));

x2y4.addEventListener('input', () => x2y4.value = getSlash("x2y4"));
x2y4.addEventListener('input', () => x4y2.value = getCount("x2y4"));

x2y5.addEventListener('input', () => x2y5.value = getSlash("x2y5"));
x2y5.addEventListener('input', () => x5y2.value = getCount("x2y5"));

x2y6.addEventListener('input', () => x2y6.value = getSlash("x2y6"));
x2y6.addEventListener('input', () => x6y2.value = getCount("x2y6"));

x3y1.addEventListener('input', () => x3y1.value = getSlash("x3y1"));
x3y1.addEventListener('input', () => x1y3.value = getCount("x3y1"));

x3y2.addEventListener('input', () => x3y2.value = getSlash("x3y2"));
x3y2.addEventListener('input', () => x2y3.value = getCount("x3y2"));

x3y4.addEventListener('input', () => x3y4.value = getSlash("x3y4"));
x3y4.addEventListener('input', () => x4y3.value = getCount("x3y4"));

x3y5.addEventListener('input', () => x3y5.value = getSlash("x3y5"));
x3y5.addEventListener('input', () => x5y3.value = getCount("x3y5"));

x3y6.addEventListener('input', () => x3y6.value = getSlash("x3y6"));
x3y6.addEventListener('input', () => x6y3.value = getCount("x3y6"));

x4y1.addEventListener('input', () => x4y1.value = getSlash("x4y1"));
x4y1.addEventListener('input', () => x1y4.value = getCount("x4y1"));

x4y2.addEventListener('input', () => x4y2.value = getSlash("x4y2"));
x4y2.addEventListener('input', () => x2y4.value = getCount("x4y2"));

x4y3.addEventListener('input', () => x4y3.value = getSlash("x4y3"));
x4y3.addEventListener('input', () => x3y4.value = getCount("x4y3"));

x4y5.addEventListener('input', () => x4y5.value = getSlash("x4y5"));
x4y5.addEventListener('input', () => x5y4.value = getCount("x4y5"));

x4y6.addEventListener('input', () => x4y6.value = getSlash("x4y6"));
x4y6.addEventListener('input', () => x6y4.value = getCount("x4y6"));

x5y1.addEventListener('input', () => x5y1.value = getSlash("x5y1"));
x5y1.addEventListener('input', () => x1y5.value = getCount("x5y1"));

x5y2.addEventListener('input', () => x5y2.value = getSlash("x5y2"));
x5y2.addEventListener('input', () => x2y5.value = getCount("x5y2"));

x5y3.addEventListener('input', () => x5y3.value = getSlash("x5y3"));
x5y3.addEventListener('input', () => x3y5.value = getCount("x5y3"));

x5y4.addEventListener('input', () => x5y4.value = getSlash("x5y4"));
x5y4.addEventListener('input', () => x4y5.value = getCount("x5y4"));

x5y6.addEventListener('input', () => x5y6.value = getSlash("x5y6"));
x5y6.addEventListener('input', () => x6y5.value = getCount("x5y6"));

x6y1.addEventListener('input', () => x6y1.value = getSlash("x6y1"));
x6y1.addEventListener('input', () => x1y6.value = getCount("x6y1"));

x6y2.addEventListener('input', () => x6y2.value = getSlash("x6y2"));
x6y2.addEventListener('input', () => x2y6.value = getCount("x6y2"));

x6y3.addEventListener('input', () => x6y3.value = getSlash("x6y3"));
x6y3.addEventListener('input', () => x3y6.value = getCount("x6y3"));

x6y4.addEventListener('input', () => x6y4.value = getSlash("x6y4"));
x6y4.addEventListener('input', () => x4y6.value = getCount("x6y4"));

x6y5.addEventListener('input', () => x6y5.value = getSlash("x6y5"));
x6y5.addEventListener('input', () => x5y6.value = getCount("x6y5"));




