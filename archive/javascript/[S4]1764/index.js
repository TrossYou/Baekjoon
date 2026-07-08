const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const [n, m] = input[0].split(" ").map(Number);
const heardSet = new Set(input.slice(1, n + 1));
const resultArr = [];

for (let i = n + 1; i <= n + m; i++) {
  const name = input[i];
  if (heardSet.has(name)) resultArr.push(name);
}

resultArr.sort();

process.stdout.write(resultArr.length + "\n" + resultArr.join("\n"));
