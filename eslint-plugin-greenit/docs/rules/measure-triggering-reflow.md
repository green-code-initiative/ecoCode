# Taking measurement force browser reflow (measure-triggering-reflow)

Browser may cache some reflow operations but taking measurements will force the browser to apply them in order to give the most update measure of the desired element.

## Rule Details

This rule aims to detect measurement operations to help developers group them and minimize the number of reflow the browser will be forced to do.

Examples of **incorrect** code for this rule:

```js
var width = document.querySelector('div').offsetWidth;
```

Examples of **correct** code for this rule:

```js
document.querySelector('div').margin = '0';
```

## Further Reading

- [Taking measurements](https://dev.opera.com/articles/efficient-javascript/#measuring)
