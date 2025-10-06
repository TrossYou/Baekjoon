const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const n = Number(input[0]);
const meetings = [];

for (let i = 1; i <= n; i++) {
  const [s, e] = input[i].split(" ").map(Number);
  meetings.push([s, e]);
}

meetings.sort((a, b) => (a[1] !== b[1] ? a[1] - b[1] : a[0] - b[0]));

let count = 0;
let end = 0;

for (const [s, e] of meetings) {
  if (s >= end) {
    end = e;
    count++;
  }
}

console.log(count);
