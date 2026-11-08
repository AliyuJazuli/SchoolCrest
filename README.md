# SchoolCrest

> **Under development** — SchoolCrest is an active prototype. Features, interfaces, and data flows may change; authentication and school records are not yet backed by a production service.

SchoolCrest is an Android school-management app built with Kotlin and Jetpack Compose. Its role-based UI is designed for platform owners, school administrators, teachers, and parents.

## What is in the app

- **Platform owner:** school oversight, school verification, account management, analytics, security, and audit-log destinations.
- **School administrator:** student and teacher management, class setup, and school overview.
- **Teacher:** dedicated destinations for classes, attendance, results, and assignments.
- **Parent:** dedicated destinations for children, attendance, results, and announcements.
- **Shared experience:** light/dark themes, onboarding, staged authentication UI, and role-aware navigation.

The dashboards prioritize role-relevant summaries, review queues, and shortcuts into existing app destinations. They adapt metric layouts to available content width and provide clear empty states where a feature has no connected data yet.

The shared palette is built around SchoolCrest navy, teal, aqua, and green. Light and dark themes use matching semantic roles, high-contrast foregrounds, and graduated cool-toned surface levels so cards and controls remain distinct in either theme; success, warning, and error chips use semantic theme roles throughout the app.

## Current implementation boundaries

Authentication and most school records are not backed by a production service. Several screens use local in-memory sample state to demonstrate interactions; these changes do not add real student, teacher, attendance, or academic records. Dashboard summaries use the state available to the app and avoid representing unavailable data as live operational metrics.

## Build and test

Open the project in Android Studio or use the Gradle wrapper:

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:assembleDebug
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

### Firebase configuration

The Firebase Analytics dependency is included, but Firebase services are not required to build or run the current UI prototype. To enable the Google Services Gradle plugin, add your own `app/google-services.json` from your Firebase project. This local configuration file is intentionally excluded from version control.

## UI design references

- [Android adaptive layouts and window size classes](https://developer.android.com/develop/ui/compose/layouts/adaptive/use-window-size-classes) — adapt layouts to the app window rather than assuming a device type.
- [Material Design 3 card guidelines](https://m3.material.io/components/cards/guidelines) — use cards for related content and actions, with a clear hierarchy.
- [Material Design 3 color roles](https://m3.material.io/styles/color/roles) — use semantic color roles to communicate hierarchy, states, and brand consistently across light and dark themes.
- [Compose floating action buttons](https://developer.android.com/develop/ui/compose/components/fab) — reserve a FAB for a screen's primary action and anchor it near the lower-right edge.
