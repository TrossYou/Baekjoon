const fs = require("fs");
const path = require("path");

const targets = ["baekjoon", "swea"];

function renameBracketFolders(baseDir) {
  if (!fs.existsSync(baseDir)) {
    console.log(`skip: ${baseDir} does not exist`);
    return;
  }

  const entries = fs.readdirSync(baseDir, { withFileTypes: true });

  for (const entry of entries) {
    if (!entry.isDirectory()) continue;

    const oldName = entry.name;

    // 예: [G3]1238 -> G3_1238
    // 예: [D4]1251 -> D4_1251
    // 예: [N]1952  -> N_1952
    const match = oldName.match(/^\[([A-Za-z0-9]+)\](\d+)$/);
    if (!match) continue;

    const prefix = match[1].toUpperCase();
    const number = match[2];
    const newName = `${prefix}_${number}`;

    const oldPath = path.join(baseDir, oldName);
    const newPath = path.join(baseDir, newName);

    if (fs.existsSync(newPath)) {
      console.log(`CONFLICT: ${oldPath} -> ${newPath} already exists`);
      continue;
    }

    fs.renameSync(oldPath, newPath);
    console.log(`renamed: ${oldPath} -> ${newPath}`);
  }
}

for (const target of targets) {
  renameBracketFolders(target);
}
