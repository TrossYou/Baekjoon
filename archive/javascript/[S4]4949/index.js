fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

function solve(line) {
  const stack = [];
  const bracketPairs = { ")": "(", "]": "[" };
  const openBracket = new Set(["(", "["]);

  for (const char of line) {
    if (openBracket.has(char)) stack.push(char);
    else if (char in bracketPairs) {
      const latest = stack.pop();
      if (latest === undefined || latest !== bracketPairs[char]) {
        return "no";
      }
    }
  }
  return stack.length === 0 ? "yes" : "no";
}

input.forEach((line) => {
  if (line !== ".") console.log(solve(line));
});
