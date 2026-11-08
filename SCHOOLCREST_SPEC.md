# SchoolCrest — Complete Project Specification & Master Architecture

**Tagline:** Connecting Classrooms, Informing Homes.  
**Target Region:** Northern Nigeria & beyond.

---

## 1. Product Overview & Platforms
SchoolCrest is a school administration and parent communication platform consisting of:
* **Android Application:** Built with Kotlin, Jetpack Compose (Material 3), Room, and Firebase.
* **Progressive Web App (PWA):** Web interface following identical business rules, role hierarchies, and permission policies.

---

## 2. User Roles & Access Control
1. **Platform Owner (Super-Admin):**
   * Oversees the platform, verifies schools, manages platform-level users & security.
   * Review applications, approve/reject/suspend/reactivate schools, manage verification codes.
   * Access to global analytics, platform overview, and append-only platform audit logs.
   * *Account provisioning:* Secure, developer-configured process outside normal public registration.
2. **School Admin:**
   * Manages school profile, classes, subjects, academic sessions, terms, and timetables.
   * Authorizes teachers, assigns classes/subjects, configures teacher permissions.
   * Registers students, manages credentials, and verifies parent phone numbers.
   * Controls the review and publication of academic results to parents.
   * Views school-specific audit logs.
3. **Teacher:**
   * Accesses assigned classes, subjects, timetable, and students.
   * Updates class status (`In Progress`, `Completed`, `Absent`).
   * Marks attendance and enters/updates subject results for assigned classes.
   * *Restrictions:* Cannot publish results, cannot edit core student details or parent links, cannot modify official timetable, cannot mark absent as completed.
4. **Parent:**
   * Accesses linked children's records only.
   * Views attendance history, published academic results, timetables, and relevant announcements.
   * Receives push notifications (e.g., student marked absent).
   * *Restrictions:* No direct parent-teacher messaging in initial core scope. Cannot view unpublished results or teacher class status.

---

## 3. Account Recovery, Suspension & Data Retention
* **Account Recovery:** Parents and teachers recover via verified contact details. School Admin requires additional verification. Platform Owner requires verified contact details, identity checks, and secure process.
* **Suspension:** Blocks access immediately without deleting historical records.
* **Archiving & Retention:** Graduated or departing student records are archived rather than immediately deleted. Controlled permanent deletion follows retention policies.
* **Audit Logs:** Append-only logs for Platform Owner (global) and School Admin (school-specific). Logs record actor, action, target record, and timestamp. Ordinary users cannot edit/delete logs.

---

## 4. Attendance & Academic Results Mechanics
* **Attendance:** Recorded by assigned teachers, monitored/corrected by School Admins, viewed by parents. Triggers push notifications on absence.
* **Academic Results:**
  * Teachers enter/update marks for assigned classes/subjects.
  * School Admins control publication.
  * Parents see results ONLY post-publication.
  * **ONLINE-ONLY RULE:** Result updates STRICTLY require active internet connectivity and MUST NOT be queued for offline submission.

---

## 5. Offline Capabilities & Synchronization
* **Selective Offline Operations:** Routine read/write actions (schedule view, daily attendance marking, announcements) function offline via Room.
* **Hybrid Sync:** Real-time sync for important online operations; background sync for routine data.
* **Conflict Resolution:** Safe retries with limits, duplicate submission prevention, permission re-verification upon reconnect. Critical records must never be overwritten blindly.

---

## 6. Color Palette & UI Foundations
* **Primary:** `#389A9C`
* **Primary Dark:** `#245643`
* **Secondary:** `#41ABA8`
* **Background / Surface:** `#FFFFFF`
* **Error / Absent:** `#C62828`
* **Warning / Late:** `#F57F17`
* **Present:** `#41ABA8`

### Navigation Paradigm
* Floating bottom navigation bar for primary destinations.
* Navigation drawer for secondary destinations.
* Adaptive layouts supporting phones, tablets, and resizable windows.

---

## 7. Phased Development Sequence

- **Phase 1: Shared UI Foundations** (Inspect project, review theme, colors, typography, components, navigation structure, establish reusable UI kit).
- **Phase 2: Authentication UI** (Staged email/phone login, role-specific registration, account recovery, and verification/approval states. The current authentication screens are UI-only: sign-in routes to the selected demo role without authenticating an account, and verification codes are not sent or checked.)
- **Phase 3: Navigation Architecture** (Floating bottom nav, navigation drawer, role-adaptive routing).
- **Phase 4: Four Role Dashboards** (Platform Owner, School Admin, Teacher, Parent).
- **Phase 5: Feature Screens** (Incremental feature UI screens).
- **Phase 6: Local Persistence** (Room DB & offline caching).
- **Phase 7: Firebase Integration** (Auth, Firestore, Security Rules, Sync, Notifications).
