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


