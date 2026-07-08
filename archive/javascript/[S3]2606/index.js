const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const N = Number(input[0]);
const K = Number(input[1]);
const arr = new Array(N + 1).fill(0);
arr[1] = 1;
const network = new Array(N + 1).fill(false);
network[1] = true;
let group = 1;

for (let i = 0; i < K; i++) {
  const [a, b] = input[2 + i].split(" ");
  if (!arr[a] && !arr[b]) {
    group++;
    arr[a] = group;
    arr[b] = group;
  } else if (!arr[a] || !arr[b]) {
    if (arr[a]) arr[b] = arr[a];
    else arr[a] = arr[b];
  } else if (arr[a] !== arr[b]) {
    network[arr[a]] = true;
    network[arr[b]] = true;
  }
}

let result = 0;
for (let i = 2; i < N + 1; i++) {
  if (network[arr[i]]) result++;
}
console.log(result);
