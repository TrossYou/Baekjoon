const fs = require("fs");
const input = fs
  .readFileSync("/dev/stdin")
  .toString()
  .trim()
  .split("\n")
  .map(Number);

const [N, M] = input[0].split(" ");
const sumMap = new Array.from(new Array(N));
const arr = input[1].split(" ");

sumMap;

for (let i = 0; i < M; i++) {
  let sum = 0;
  const [s, e] = input[2 + i].split(" ");
  for (let j = s - 1; j < e; j++) {
    sum += arr[j];
  }
  console.log(sum);
}
