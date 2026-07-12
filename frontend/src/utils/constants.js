export const DIFFICULTY_LEVELS = ["Easy", "Medium", "Hard"];

export const SOLVED_STATUS = ["Solved", "Unsolved"];

export const QUIZ_SUBJECTS = [
  "Operating Systems",
  "DBMS",
  "Computer Networks",
  "OOP",
  "Java",
  "Spring Boot"
];

export function formatDate(dateString) {
  if (!dateString) return "-";
  const date = new Date(dateString);
  if (Number.isNaN(date.getTime())) return dateString;
  return date.toLocaleDateString("en-US", {
    year: "numeric",
    month: "short",
    day: "numeric"
  });
}
