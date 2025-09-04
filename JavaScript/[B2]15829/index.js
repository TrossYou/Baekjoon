const fs = require("fs");
const input = fs.readFileSync("/dev/stdin").toString().trim().split("\n");

const l = input[0];
const strArr = input[1].split("");
const r = 31n;
const m = 1234567891n;

const hash = strArr.reduce((accumulate, char, index) => {
  return (
    (accumulate + BigInt(char.charCodeAt(0) - 96) * r ** BigInt(index)) % m
  );
}, 0n);

console.log(Number(hash));
