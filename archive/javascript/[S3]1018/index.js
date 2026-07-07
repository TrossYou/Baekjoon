const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const [N, M] = input[0].split(" ").map(Number);
const board = input.slice(1).map((line) => line.split(""));

const makePattern = (start) => {
  const pattern = Array.from({ length: 8 }, () => Array(8).fill(""));
  for (let i = 0; i < 8; i++) {
    for (let j = 0; j < 8; j++) {
      if ((i + j) % 2 === 0) {
        pattern[i][j] = start;
      } else {
        pattern[i][j] = start === "B" ? "W" : "B";
      }
    }
  }
  return pattern;
};

const patternB = makePattern("B");
const patternW = makePattern("W");

const countDiff = (x, y, pattern) => {
  let diff = 0;
  for (let i = 0; i < 8; i++) {
    for (let j = 0; j < 8; j++) {
      if (board[x + i][y + j] !== pattern[i][j]) diff++;
    }
  }
  return diff;
};

let minCount = Infinity;
for (let i = 0; i <= N - 8; i++) {
  for (let j = 0; j <= M - 8; j++) {
    const diffB = countDiff(i, j, patternB);
    const diffW = countDiff(i, j, patternW);
    minCount = Math.min(minCount, diffB, diffW);
  }
}

console.log(minCount);
