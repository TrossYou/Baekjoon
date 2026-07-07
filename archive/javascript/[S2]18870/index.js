const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const n = Number(input[0]);
const arr = input[1].split(" ").map(Number);

const sortArr = [...new Set(arr)].sort((a, b) => a - b);

const calculated = new Map();
for (let i = 0; i < sortArr.length; i++) {
  calculated.set(sortArr[i], i);
}

const out = arr.map((v) => calculated.get(v)).join(" ");
process.stdout.write(out);
