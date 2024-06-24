# Logging

Simple logging library to provide log messages in a consistent format.

## Usage

```python
from python.com.yanicksenn.libraries.logging import logging

logging.info("Hello, World!")
```

```bash
INFO: Hello, World!
```

## Different logging levels

```python
logging.info("Hello, Info!")
logging.warning("Hello, Warning!")
logging.error("Hello, Error!")
```

```bash
INFO: Hello, Info!
WARNING: Hello, Warning!
ERROR: Hello, Error!
```

## Minimal logging level

```python
logging.set_minimal_logging_level(LoggingLevel.ERROR)
logging.info("Hello, Info!")
logging.warning("Hello, Warning!")
logging.error("Hello, Error!")
```

```bash
ERROR: Hello, Error!
```

NOTE: The default minimal logging level is `INFO`.


## Muting

```python
logging.info("Hello, Info!")

logging.make_mute()
logging.warning("Hello, Warning!")

logging.make_noisy()
logging.error("Hello, Error!")
```

```bash
INFO: Hello, Info!
ERROR: Hello, Error!
```