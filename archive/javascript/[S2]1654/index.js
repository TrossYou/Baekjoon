const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const [K, N] = input[0].split(" ").map(Number);
const cables = input.slice(1, K + 1).map(Number);

const canMake = (length) => {
  let count = 0;
  for (const len of cables) {
    count += Math.floor(len / length);
    if (count >= N) return true;
  }
  return false;
};

const binarySearch = (start, end) => {
  let result = 0;
  while (start <= end) {
    const mid = Math.floor((start + end) / 2);

    if (canMake(mid)) {
      result = mid;
      start = mid + 1;
    } else {
      end = mid - 1;
    }
  }
  return result;
};

const maxLength = Math.max(...cables);
console.log(binarySearch(1, maxLength));
