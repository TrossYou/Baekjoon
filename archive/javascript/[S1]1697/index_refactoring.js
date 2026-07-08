const fs = require("fs");
const [n, k] = fs
  .readFileSync("/dev/stdin")
  .toString()
  .trim()
  .split(" ")
  .map(Number);

const MAX = 100000;
const dist = new Int32Array(MAX + 1).fill(-1);

const que = new Int32Array(MAX + 1);
let head = 0;
let tail = 0;

que[tail++] = n;
dist[n] = 0;

while (head < tail) {
  const num = que[head++];
  const numDist = dist[num];
  if (k === num) break;

  if (num - 1 >= 0 && dist[num - 1] === -1) {
    que[tail++] = num - 1;
    dist[num - 1] = numDist + 1;
  }
  if (num + 1 <= MAX && dist[num + 1] === -1) {
    que[tail++] = num + 1;
    dist[num + 1] = numDist + 1;
  }
  if (num * 2 <= MAX && dist[num * 2] === -1) {
    que[tail++] = num * 2;
    dist[num * 2] = numDist + 1;
  }
}

console.log(dist[k]);
