const fs = require("fs");
const path = require("path");

const BASE_DIR = "baekjoon";

const LEVEL_MAP = {
  0: "U",
  1: "B5",
  2: "B4",
  3: "B3",
  4: "B2",
  5: "B1",
  6: "S5",
  7: "S4",
  8: "S3",
  9: "S2",
  10: "S1",
  11: "G5",
  12: "G4",
  13: "G3",
  14: "G2",
  15: "G1",
  16: "P5",
  17: "P4",
  18: "P3",
  19: "P2",
  20: "P1",
  21: "D5",
  22: "D4",
  23: "D3",
  24: "D2",
  25: "D1",
  26: "R5",
  27: "R4",
  28: "R3",
  29: "R2",
  30: "R1",
};

function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

function sanitizeTitle(title) {
  return title
    .normalize("NFKC")
    .replace(/[\\/:*?"<>|]/g, "")
    .replace(/[\s\u00A0]+/g, "")
    .trim();
}

function extractProblemId(folderName) {
  const match = folderName.match(/^[A-Z][0-9]_(\d+)(?:_.+)?$/);
  return match ? match[1] : null;
}

async function fetchProblem(problemId) {
  const url = `https://solved.ac/api/v3/problem/show?problemId=${problemId}`;

  const res = await fetch(url, {
    headers: {
      Accept: "application/json",
      "User-Agent": "Mozilla/5.0 algorithm-repo-sync",
    },
  });

  if (!res.ok) {
    throw new Error(`HTTP ${res.status}`);
  }

  return res.json();
}

async function main() {
  if (!fs.existsSync(BASE_DIR)) {
    console.error(`폴더가 없습니다: ${BASE_DIR}`);
    process.exit(1);
  }

  const folders = fs
    .readdirSync(BASE_DIR, { withFileTypes: true })
    .filter((entry) => entry.isDirectory())
    .map((entry) => entry.name);

  for (const folderName of folders) {
    const problemId = extractProblemId(folderName);

    if (!problemId) {
      console.log(`skip: ${folderName}`);
      continue;
    }

    try {
      const problem = await fetchProblem(problemId);

      const levelPrefix = LEVEL_MAP[problem.level] ?? "U";
      const title = sanitizeTitle(problem.titleKo);
      const newFolderName = `${levelPrefix}_${problemId}_${title}`;

      if (folderName === newFolderName) {
        console.log(`ok: ${folderName}`);
      } else {
        const oldPath = path.join(BASE_DIR, folderName);
        const newPath = path.join(BASE_DIR, newFolderName);

        if (fs.existsSync(newPath)) {
          console.log(`CONFLICT: ${folderName} -> ${newFolderName}`);
        } else {
          fs.renameSync(oldPath, newPath);
          console.log(`renamed: ${folderName} -> ${newFolderName}`);
        }
      }
    } catch (error) {
      console.log(`failed: ${folderName} (${error.message})`);
    }

    await sleep(1500);
  }
}

main();
