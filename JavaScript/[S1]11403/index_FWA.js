const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const N = Number(input[0]);
const graph = input.slice(1, N + 1).map((row) => row.split(" ").map(Number));

for (let k = 0; k < N; k++) {
  for (let i = 0; i < N; i++) {
    for (let j = 0; j < N; j++) {
      if (graph[i][k] && graph[k][j]) graph[i][j] = 1;
    }
  }
}

console.log(graph.map((row) => row.join(" ")).join("\n"));
