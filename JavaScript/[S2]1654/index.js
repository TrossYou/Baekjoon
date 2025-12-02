const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const [K, N] = input[0].split(" ").map(Number);

const calculate = (val) => {
  let res = 0;
  for (let i = 0; i < K; i++) {
    res += Math.floor(inputArr[i] / val);
  }
  return res;
};

const inputArr = new Array();
let sum = 0;
for (let i = 0; i < K; i++) {
  inputArr[i] = Number(input[1 + i]);
  sum += inputArr[i];
}

const startVal = Math.floor(sum / N);
for (let i = startVal; i > 0; i--) {
  const res = calculate(i);
  if (res >= N) {
    console.log(i);
    break;
  }
}
