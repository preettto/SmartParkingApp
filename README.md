# Smart Parking App

A smart parking solution with reservation, QR entry/exit, payments, and admin analytics.

## Features
- Reserve parking slots (in advance or on the spot)
- Digital slip with QR code
- Scan QR to start/end parking
- Automatic charge calculation (INR)
- Map view of parking slots
- UPI payments
- Push notifications (reminders, updates)
- Admin panel (slot management, analytics)

## Setup
1. Clone the repo and open in Android Studio.
2. Add your `google-services.json` (Firebase).
3. Sync Gradle.
4. Set up Firestore with collections: `users`, `slots`, `reservations`.
5. (Optional) Enable Firebase Cloud Messaging for notifications.

## Firestore Structure
- `users/{userId}`: { name, email, isAdmin, reservations }
- `slots/{slotId}`: { status, reservedBy, location }
- `reservations/{reservationId}`: { userId, slotId, startTime, endTime, status, slipUrl }

## Running
- Register/login as a user.
- Reserve a slot, scan QR at entry/exit.
- Admins can access analytics and manage slots.

## Contributors
- Your Team Names 