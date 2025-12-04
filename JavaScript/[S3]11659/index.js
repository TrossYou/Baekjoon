const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const [N, M] = input[0].split(" ").map(Number);
const arr = input[1].split(" ").map(Number);
const prefixSum = new Array(N + 1).fill(0);

for (let i = 0; i < N; i++) {
  prefixSum[i + 1] = prefixSum[i] + arr[i];
}

// I/O 메모리 절약
const result = [];
for (let t = 0; t < M; t++) {
  const [s, e] = input[2 + t].split(" ").map(Number);
  result.push(prefixSum[e] - prefixSum[s - 1]);
}
console.log(result.join("\n"));
