# Limit repaint and reflow by limiting the change on CSS properties one by one (no-multiple-css-modifications)

Setting multiple style change at once may reduce the number of reflow and repaint.

## Rule Details

This rule aims to reduce the number of browser’s repaint and reflow.

Examples of **incorrect** code for this rule:

```js

myNode.style.fontSize = '2em';
myNode.style.color = 'red';

```

Examples of **correct** code for this rule:

```js

myNode.style.fontSize = '2em';
myOtherNode.style.color = 'red';

```

The following case is not supported:

```js

let myNode = document.getElementById('myNode');
myNode.style.fontSize = '2em';
document.getElementById('myNode').style.color = 'red';

```

## Further Reading

- [Making several style changes at once](https://dev.opera.com/articles/efficient-javascript/#stylechanges)
