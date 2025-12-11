const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");
const N = input[0];

class Queue {
  constructor() {
    this._arr = [];
  }

  enqueue(item) {
    this._arr.push(item);
  }
  dequeue() {
    return this._arr.shift();
  }
  size() {
    return this._arr.length;
  }
}

const queue = new Queue();
const wayMap = input.slice(1, N + 1).map((row) => row.split(" ").map(Number));

const fillMap = (start, end) => {
  for (let i = 0; i < N; i++) {
    if (wayMap[end][i]) {
      if (!wayMap[start][i]) queue.enqueue([start, i]);
      wayMap[start][i] = 1;
    }
  }
};

for (let i = 0; i < N; i++) {
  for (let j = 0; j < N; j++) {
    if (wayMap[i][j]) {
      queue.enqueue([i, j]);
    }
  }
}

while (queue.size()) {
  const [start, end] = queue.dequeue();
  fillMap(start, end);
}

for (let i = 0; i < N; i++) {
  console.log(wayMap[i].join(" "));
}
