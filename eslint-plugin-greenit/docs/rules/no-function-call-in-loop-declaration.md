# Functions should not be called inside the declaration of a loop (no-function-call-in-loop-declaration)

It is possible to call a function to test the end of a loop. But this require to call the function at each iteration which can lead to CPU overuse.

## Rule Details

The rule enforces that loop declaration do not call a function in the stop test.

Examples of **incorrect** code for this rule:

```js

for (let i = 0; i < parseInt('5'); i++) {
    bar(i);
}

```

Examples of **correct** code for this rule:

```js

for (let i = 0; i < 5; i++) {
    bar(i);
}

```

```js

for (let i = 0; i < arr.length; i++) {
    bar(i);
}

```
