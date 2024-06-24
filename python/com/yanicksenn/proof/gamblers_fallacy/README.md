# Gambler's Fallacy

An excercise proofing the [Gambler's Fallacy](https://en.wikipedia.org/wiki/Gambler%27s_fallacy).

## Usage

```bash
bazel run //python/com/yanicksenn/proof/gamblers_fallacy -- --runs=10000

INFO: Total played: 4467
INFO: Total correct: 2105
INFO: Won: 47.123349003805686%
```

## Conclusion

Previous results are not indicative of future results. E.g. having had two consecutive black rolls before does not mean its more likely to get a red roll next. However, the scenario of getting the same color twice in a row happens in ~25% of the rolls.