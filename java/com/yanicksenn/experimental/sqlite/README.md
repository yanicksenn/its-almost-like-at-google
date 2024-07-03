# SQLite

SQLite is suitable embedded database. E.g. storage on mobile devices or local caches.

## Usage

Running the writer:

```bash
bazel run //java/com/yanicksenn/experimental/sqlite:writer -- --db_path=/private/var/tmp/test/db.sqlite

# Output
Total rows inserted: 1
```

Running the reader:

```bash
bazel run //java/com/yanicksenn/experimental/sqlite:reader -- --db_path=/private/var/tmp/test/db.sqlite

# Output
1: 2024-07-03 20:50:27.901
2: 2024-07-03 20:50:30.758
Total rows read: 2
```
