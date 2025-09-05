fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const targetIndex = input.findIndex((v) => !isNaN(Number(v)));
const target = Number(input[targetIndex]);

const fourth = target + (3 - targetIndex);

let result = "";
if (fourth % 3 === 0) result += "Fizz";
if (fourth % 5 === 0) result += "Buzz";

result = result || fourth;

console.log(result);
