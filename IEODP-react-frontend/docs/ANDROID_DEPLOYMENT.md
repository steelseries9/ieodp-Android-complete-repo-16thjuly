# Android Deployment Guide (Capacitor)

This repository is already structured as a 3-tier application:
- React frontend (`IEODP-react-frontend`)
- Spring Boot backend (`ieodp-springboot-backend-14thjan`)
- Database (MySQL/H2)

To deploy as an Android app without rewriting UI, wrap the frontend using Capacitor.

## 1) Backend prerequisites

Before generating Android builds, deploy backend to a public HTTPS URL.

Checklist:
- API is reachable from the internet (or your private network used by testers).
- TLS/HTTPS is enabled.
- CORS allows requests from the mobile webview domain/app context.
- JWT expiry/refresh flow is configured for mobile sessions.

## 2) Configure frontend API URL for Android

From `IEODP-react-frontend`, create a production env file:

```bash
cp .env.android.example .env.android
```

Edit `.env.android`:

```env
VITE_API_BASE_URL=https://api.yourdomain.com
```

## 3) Install dependencies and initialize Android project

```bash
npm install
npm run android:init
```

This creates `IEODP-react-frontend/android/` (native Android project).

## 4) Build + sync web assets into Android

```bash
cp .env.android .env
npm run android:sync
```

What this does:
1. Builds Vite app into `dist/`
2. Copies assets/config into native Android project via Capacitor sync

## 5) Open in Android Studio

```bash
npm run android:open
```

In Android Studio:
1. Wait for Gradle sync
2. Update `applicationId` if needed (default: `com.ieodp.app`)
3. Set signing config for release
4. Build signed APK/AAB

## 6) Optional: run on connected device

```bash
npm run android:run
```

## 7) Recommended follow-up hardening

- Replace localStorage token persistence with secure storage plugin.
- Add certificate pinning if this app is security-sensitive.
- Verify Android back button behavior for React Router flows.
- Add deep link/app link handling if needed.

## 8) CI/CD suggestion

Automate release builds with:
1. Frontend build with production env
2. `npx cap sync android`
3. Gradle assemble/bundle in CI
4. Upload AAB to Play Console
