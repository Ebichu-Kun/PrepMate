# PrepMate

A clean, functional React frontend for PrepMate — a study/interview prep dashboard with Notes, AI Interview practice, a DSA Tracker, Resume Review, and a Quick Quiz.

## Tech Stack

- React (JavaScript, functional components + hooks)
- React Router v6
- Axios
- Plain CSS (no UI libraries, no Tailwind)

## Getting Started

```bash
npm install
cp .env.example .env   # set REACT_APP_API_BASE_URL to your backend URL
npm start
```

The app runs at `http://localhost:3000`. All data (notes, interview questions, DSA problems, resume analysis, quiz content) is fetched from your backend — nothing is hardcoded or mocked in the frontend.

## Project Structure

```
src/
  pages/        One file per screen (Login, Dashboard, Notes, AIInterview, DSATracker, ResumeReview, QuickQuiz)
  components/   Reusable UI (Sidebar, Header, Layout, Loader, ErrorMessage, DashboardCard, ProtectedRoute)
  services/     One Axios service module per API resource
  utils/        auth.js (session/token helpers), constants.js (shared lists + formatDate)
  css/          Global styles and design tokens (index.css)
```

Every page follows the same pattern: fetch on mount (or on user action) → show a loader while pending → show an inline error message with the option to retry if the request fails → render the data once it arrives.

## Authentication

- `POST /api/auth/login` is called with `{ email, password }` and is expected to return `{ token, user: { name, ... } }`.
- The token is stored in `localStorage` and attached as a `Bearer` token to every subsequent request via an Axios request interceptor (`src/services/api.js`).
- A response interceptor clears the session and redirects to `/login` on any `401`.
- `ProtectedRoute` guards all authenticated pages and redirects unauthenticated users to `/login`.

## API Endpoints Used

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/login` | Authenticate and receive a token |
| GET | `/api/notes` | List notes |
| POST | `/api/notes` | Create a note (or upload one) |
| PUT | `/api/notes/{id}` | Update a note |
| DELETE | `/api/notes/{id}` | Delete a note |
| GET | `/api/interview/questions` | Get interview questions |
| POST | `/api/interview/submit` | Submit answers, get scored result |
| GET | `/api/dsa` | List DSA questions |
| POST | `/api/dsa` | Add a DSA question |
| PUT | `/api/dsa/{id}` | Update a DSA question |
| DELETE | `/api/dsa/{id}` | Delete a DSA question |
| POST | `/api/resume/upload` | Upload a resume PDF |
| POST | `/api/resume/analyze` | Analyze the uploaded resume |
| GET | `/api/quiz/{subject}` | Get quiz questions for a subject |
| POST | `/api/quiz/submit` | Submit quiz answers, get scored result |

Expected response shapes are inferred from what each page renders — adjust the service files in `src/services/` if your backend's field names differ.

## Notes on Backend Integration

- `REACT_APP_API_BASE_URL` in `.env` controls the Axios base URL (`src/services/api.js`).
- All list/detail data is rendered directly from API responses — there is no sample or placeholder content anywhere in the app.
- Forms (Notes, DSA Tracker) reuse the same component for create and edit, keyed off whether an `id` is being edited.
