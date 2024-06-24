# Templating engine

Simple templating engine with small set of replacement rules.

## Run the templating engine

```bash
# Interactive mode
bazel run //python/com/yanicksenn/tools/templating \
    --template_path=/path/to/template/file \
    --target_path=/path/to/output/file \
    --interactive
```

```bash
# Silent mode
bazel run //python/com/yanicksenn/tools/templating \
    --template_path=/path/to/template/file \
    --target_path=/path/to/output/file \
    --__ID="123" \
    --__TITLE="This is a title" \
    --AnyIdentifiableString="Bazinga!"
```

```bash
# Custom rules file
bazel run //python/com/yanicksenn/tools/templating \
    --template_path=/path/to/template/file \
    --rule_path=/path/to/rules/file \
    --target_path=/path/to/output/file \
    --interactive
```

## Setup

```bash
# Required files
/path/to/template/file
/path/to/template/file.rules
```

### Template file

Example is json file but it could also be anything else.

```json
{
    "id": "__ID",
    "title": "__TITLE",
    "description": "__DESCRIPTION",
    "tag": "__TAG",
    "author": "__AUTHOR",
    "version": "__VERSION",
    "bogus": "AnyIdentifiableString"
}
```

### Rules file

The rules file has a very simple and limited syntax.

```
# Comment
__ID [ NUMERIC ];

__TITLE [
    MIN_LENGTH(3), MAX_LENGTH(128)
];
__DESCRIPTION [
    MAX_LENGTH(1024)
];


__TAG;

__AUTHOR=Yanick Senn [
    MIN_LENGTH(3), 
    MAX_LENGTH(128)
];
__VERSION=1.0.0 [
    REGEX([1-9][0-9]*\.[0-9]+\.[0-9]+),
];
AnyIdentifiableString [ MAX_LENGTH(1024) ];
```

## Rules

- `ALPHABETIC`
- `ALPHANUMERIC`
- `NUMERIC`
- `REGEX([a-z]+)`
- `MIN_LENGTH(3)`
- `MAX_LENGTH(3)`

## Todos

- Add tests.
- Replace `target` file with `output` file.
- Get rid of hacks and code smells.
- Parse the rules file sequentially and not by using regex.
- Allow more than one parameter on rules.
- Add more rules (E.g. `NOT_EMPTY`, `MIN_VALUE(1)`, `MAX_VALUE(99)`, `OR(...)`, `AND(...)`)