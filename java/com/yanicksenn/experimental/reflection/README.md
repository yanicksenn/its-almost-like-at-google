# ClassPathLister

Leveraging Guava's ClassPath util, however this could be easily recreated to the prevent the binary from include the Guava libaray.

```bash
blaze run //java/com/yanicksenn/experimental/reflection:class_path_lister
com.yanicksenn.experimental.reflection.ClassPathLister
com.yanicksenn.flags.Flags
com.yanicksenn.functions.ThrowingSupplier
com.yanicksenn.numbers.Numbers
```

This is convenient when building runtime value injection libraries.