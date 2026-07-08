const fs = require("fs");
const [n, k] = fs
  .readFileSync("/dev/stdin")
  .toString()
  .trim()
  .split(" ")
  .map(Number);

let isSeek = false;
let count = 0;

if (n === k) {
  console.log(count);
  isSeek = true;
}

const accumSet = new Set();

const nextStep = (stepSet, num) => {
  if (num - 1 === k || num + 1 === k || num * 2 === k) {
    console.log(count);
    return 1;
  }

  if (!accumSet.has(num - 1) && num - 1 >= 0) stepSet.add(num - 1);
  if (!accumSet.has(num + 1) && num + 1 <= 100000) stepSet.add(num + 1);
  if (!accumSet.has(num * 2) && num * 2 <= 100000) stepSet.add(num * 2);
  return 0;
};

accumSet.add(n);
const stepSet = new Set();
stepSet.add(n);
while (!isSeek) {
  count++;
  const lastArr = Array.from(stepSet);
  stepSet.clear();

  for (let i = 0; i < lastArr.length; i++) {
    if (nextStep(stepSet, lastArr[i])) {
      isSeek = true;
      break;
    }
  }
  for (const num of stepSet) accumSet.add(num);
}
