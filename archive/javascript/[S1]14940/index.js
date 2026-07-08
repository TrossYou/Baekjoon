const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const [n, m] = input[0].split(" ").map(Number);
const board = input.slice(1).map((line) => line.split(" ").map(Number));

const result = Array.from({ length: n }, (_, i) =>
  Array.from({ length: m }, (_, j) => (board[i][j] === 0 ? 0 : -1))
);

const visited = Array.from({ length: n }, () => Array(m).fill(false));

class Queue {
  constructor(cap) {
    this.x = new Int32Array(cap);
    this.y = new Int32Array(cap);
    this.front = 0;
    this.rear = 0;
  }

  enqueue(ix, iy) {
    this.x[this.rear] = ix;
    this.y[this.rear] = iy;
    this.rear++;
  }

  dequeue() {
    const ix = this.x[this.front];
    const iy = this.y[this.front];
    this.front++;
    return [ix, iy];
  }
}

const que = new Queue(n * m);

const findStartPoint = () => {
  for (let i = 0; i < n; i++) {
    for (let j = 0; j < m; j++) {
      if (board[i][j] === 2) return [i, j];
    }
  }
  return [-1, -1];
};

const [x, y] = findStartPoint();

if (x !== -1) {
  visited[x][y] = true;
  result[x][y] = 0;
  que.enqueue(x, y);

  const dx = [-1, 1, 0, 0];
  const dy = [0, 0, -1, 1];

  while (que.front < que.rear) {
    const [x, y] = que.dequeue();

    for (let d = 0; d < 4; d++) {
      const targetX = x + dx[d];
      const targetY = y + dy[d];

      if (targetX < 0 || targetY < 0 || targetX >= n || targetY >= m) continue;
      if (visited[targetX][targetY]) continue;
      if (board[targetX][targetY] !== 1) continue;

      visited[targetX][targetY] = true;
      result[targetX][targetY] = result[x][y] + 1;
      que.enqueue(targetX, targetY);
    }
  }
}

let out = "";
for (let i = 0; i < n; i++) out += result[i].join(" ") + "\n";
process.stdout.write(out);
