Women Safety App
An end-to-end personal safety platform for reporting emergencies, sharing location, and helping police respond faster.

The project has three parts:

Android app — SOS, contacts, and safety tools for users
Spring Boot API — authentication, emergency location, nearest-station assignment
Web portal — police dashboard for reports, live tracking, and officers
Features
Android app
Register and log in
SOS button with countdown
Emergency resources (police, ambulance, fire, helplines)
Emergency contacts
Location sharing
Safety check-in (timed)
Fake call
Personal alarm
Safety tips and profile
Backend API (Spring Boot)
JWT auth (/auth/register, /auth/login)
Emergency location reporting (/api/emergency)
Geo-based assignment of nearby police stations
MongoDB storage
CORS and Spring Security
Web portal
Police login and registration
Dashboard with stats and recent reports
Live tracking (Leaflet / Google Maps)
Officer management and admin tools
Risk detection for SOS text / voice transcript (high / medium / low)
Optional OpenAI scoring, with a heuristic fallback
Tech stack
Layer	Stack
Android
Kotlin, Jetpack Compose, Retrofit
Mobile API
Spring Boot 3, Java 17, Spring Security, JWT, MongoDB
Portal UI
Next.js 15, React 19, TypeScript, Tailwind CSS
Portal API
Node.js, Express, MongoDB, Socket.IO, OpenAI (optional)
Repository structure

WomenSafetyApp-main/
├── AndroidApp/          # Kotlin Android client
├── BackendAPI/          # Spring Boot REST API
└── WebPortal/
    └── women-safety-portal-main/
        ├── app/         # Next.js pages
        ├── components/  # Dashboard UI
        └── backend/     # Express API + risk detection
Prerequisites
JDK 17+
Maven
Node.js 18+
MongoDB
Android Studio (for the mobile app)
Optional: OpenAI API key (risk detection)
Getting started
1. Spring Boot API

cd BackendAPI
Configure MongoDB and JWT in src/main/resources/application.properties, then:


mvn spring-boot:run
Default auth endpoints:

POST /auth/register
POST /auth/login
Emergency (JWT required):

POST /api/emergency
POST /api/emergency/location
2. Web portal (frontend)

cd WebPortal/women-safety-portal-main
npm install
npm run dev
3. Web portal (backend)

cd WebPortal/women-safety-portal-main/backend
npm install
npm run dev
Risk analysis:

POST /api/risk-detection/analyze
Example body:


{
  "text": "I think someone is following me",
  "voiceTranscript": "please help me"
}
If OPENAI_API_KEY is set, the service uses an LLM. If the key is missing or the call fails, it falls back to the built-in classifier.

4. Android app
Open AndroidApp in Android Studio.
Point the Retrofit base URL at your running API.
Build and run on an emulator or device.
How it works
A user signs in on the Android app and triggers SOS or location sharing.
The Spring Boot API stores the emergency and finds nearby police stations.
Officers see the report on the web dashboard and can track the user live.
Optional risk detection scores the report (P1 / P2 / P3) so urgent cases surface first.
Environment
Typical variables (names may differ by module):


MONGODB_URI=
JWT_SECRET=
OPENAI_API_KEY=
RISK_LLM_MODEL=gpt-4o-mini
Do not commit secrets. Use .env or your host’s secret store.

