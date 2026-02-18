# Peak Client (v2)
gRPC Streaming Client for Peak Leaderboard.

---

## What’s New in v2

Peak Client v2 aligns with the improved server architecture and now supports both streaming and unary interactions.

### Key Improvements

- Added support for unary `getLeaderboard` RPC (read-only query)
- Compatible with shared ranking model from server v2
- Cleaner separation between streaming and query calls
- Improved event validation flow (JOIN required before SCORE_UPDATE)
- Explicit snapshot printing with score + rank visibility
- Refactored client methods for clearer API usage

---

## Updated Client Responsibilities (v2)

The client now supports two interaction modes:

### Streaming Mode (Real-Time)

- Opens bidirectional gRPC stream
- Sends `JOIN`
- Sends `SCORE_UPDATE`
- Receives full leaderboard snapshots after each mutation

### Unary Mode (Read-Only Query)

- Calls `getLeaderboard`
- Retrieves current snapshot without joining
- Useful for dashboard or admin-style viewing

---

##  Design Philosophy

The client:

- Does NOT store leaderboard state
- Does NOT compute ranking
- Does NOT persist data
- Does NOT apply business logic

All ranking and ordering are handled by the server.

The client only:

- Sends events
- Receives snapshots
- Displays results

---

## Client Architecture (v2)

```
ManagedChannel
      ↓
Async Stub (Streaming) → JOIN / SCORE_UPDATE
      ↓
Blocking Stub (Unary) → GetLeaderboard
      ↓
Console Output (Snapshot Rendering)
```

---

## Snapshot Behavior (v2)

The client receives **full snapshots** after each mutation.

Example output:

```
------ NEW SNAPSHOT ------
User: gooner Score: 500 Rank: 1
User: alpha-male Score: 100 Rank: 2
--------------------------
```

The client replaces the entire leaderboard view on every snapshot.

---
# Peak Client 
gRPC Streaming Client for Peak Leaderboard.  

## Tech Stack

- Java 21
- Spring Boot 
- Spring gRPC
- Protobuf
- gRPC (Async Stub)

The client:

- Does NOT store leaderboard state
- Does NOT compute ranking
- Does NOT persist data

It simply:
- Sends events
- Listens for server responses.

## How It Works

### Application Startup

On startup:

- A `ManagedChannel` is created
- An async gRPC stub is initialized
- A bidirectional stream is opened

### Client → Server Events

The client can send:

- `JOIN` — request current leaderboard snapshot
- `SCORE_UPDATE` — update a user's score

### Server → Client Events

The client listens for:

- `LeaderboardSnapshot` — full ranked leaderboard

Snapshots are printed to the console in real time.

## Start the client with:
```bash
./mvnw spring-boot:run
```


