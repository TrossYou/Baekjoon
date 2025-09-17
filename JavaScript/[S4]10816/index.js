fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const nArr = input[1].split(" ").map((num) => Number(num));
const mArr = input[3].split(" ").map((num) => Number(num));

const countMap = new Map();

for (const num of nArr) {
  countMap.set(num, (countMap.get(num) || 0) + 1);
}

const result = mArr.map((targetNum) => countMap.get(targetNum) || 0);

console.log(result.join(" "));
