const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const [N, M] = input[0].split(" ").map(Number);
const parent = Array.from({ length: N + 1 }, (_, idx) => idx);

const find = (x) => {
  if (parent[x] !== x) {
    parent[x] = find(parent[x]);
  }
  return parent[x];
};

const union = (u, v) => {
  const root1 = find(u);
  const root2 = find(v);
  if (root1 != root2) {
    parent[root2] = root1;
  }
};

for (let i = 0; i < M; i++) {
  const [u, v] = input[1 + i].split(" ").map(Number);
  union(u, v);
}

const roots = new Set();
for (let i = 1; i < N + 1; i++) {
  roots.add(find(i));
}

console.log(roots.size);
