# Monty Hall

An excercise proofing the [Monty Hall Problem](https://en.wikipedia.org/wiki/Monty_Hall_problem).

## Usage

```bash
bazel run //python/com/yanicksenn/proof/monty_hall -- --runs=10000

INFO: Total played: 10000
INFO: Total correct: 6601
INFO: Won: 66.01%
```

## Conclusion

Changing the door after the game master has revealed one of the goats your chance of winning the Ferrari is ~66%.