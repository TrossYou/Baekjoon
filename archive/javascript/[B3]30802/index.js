const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const n = Number(input[0]);
const sizeCounts = input[1].split(" ").map(Number);
const [t, p] = input[2].split(" ").map(Number);

const tshirtBundleCount = sizeCounts.reduce((total, count) => {
  return total + Math.ceil(count / t);
}, 0);

pencilBundleCount = Math.floor(n / p);
singlePencilCount = n % p;

console.log(tshirtBundleCount);
console.log(`${pencilBundleCount} ${singlePencilCount}`);
