const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const [N, M] = input[0].split(" ").map(Number);
const trees = input[1].split(" ").map(Number);

const canCut = (height) => {
  let length = 0;
  for (const tree of trees) {
    if (tree > height) {
      length += tree - height;
      if (length >= M) return true;
    }
  }
  return false;
};

const binarySearch = (start, end) => {
  let result = 0;
  while (start <= end) {
    const mid = Math.floor((start + end) / 2);
    if (canCut(mid)) {
      result = mid;
      start = mid + 1;
    } else {
      end = mid - 1;
    }
  }
  return result;
};

const max = Math.max(...trees);
console.log(binarySearch(0, max));
