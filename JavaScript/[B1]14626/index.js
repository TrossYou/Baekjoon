const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("");
const len = input.length;

const { sum, starIndex } = input.reduce(
  (accumObject, curr, index) => {
    if (curr === "*") {
      accumObject.starIndex = index;
    } else {
      const weight = index % 2 === 0 ? 1 : 3;
      accumObject.sum += weight * curr;
    }
    return accumObject;
  },
  { sum: 0, starIndex: -1 }
);
const starWeight = starIndex % 2 === 0 ? 1 : 3;
const target = 10 - (sum % 10);
const star = starWeight === 1 ? target : (target * 7) % 10;

console.log(star);
