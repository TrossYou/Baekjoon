const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const t = input[0];
let idx = 1;

const dfs = (x, y, cabbages) => {
  cabbages[x][y] = false;
  const dx = [0, 0, -1, 1];
  const dy = [-1, 1, 0, 0];

  for (let i = 0; i < 4; i++) {
    const targetX = x + dx[i];
    const targetY = y + dy[i];

    if (
      targetX >= 0 &&
      targetX < n &&
      targetY >= 0 &&
      targetY < m &&
      cabbages[targetX][targetY]
    )
      dfs(targetX, targetY);
  }
};

for (let time = 1; time <= t; time++) {
  const [n, m, k] = input[idx++].split(" ").map(Number);

  const cabbages = new Array(n);
  for (let i = 0; i < n; i++) {
    cabbages[i] = new Array(m).fill(false);
  }

  const cabbageList = [];
  for (let i = 0; i < k; i++) {
    const [x, y] = input[idx++].split(" ").map(Number);
    cabbageList[i] = [x, y];
    cabbages[x][y] = true;
  }

  for (let i = 0; i < k; i++) {
    const [x, y] = cabbageList[i];
    dfs(x, y, cabbages);
  }

  console.log(cabbages);
}
