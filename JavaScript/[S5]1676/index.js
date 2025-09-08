fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim();
const n = Number(input);

let count = 0;

for (let i = 5; i <= n; i *= 5) {
  count += Math.floor(n / i);
}
console.log(count);
