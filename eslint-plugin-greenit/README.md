# eslint-plugin-greenit

GreenIT rules for JavaScript

## Installation

You'll first need to install [ESLint](http://eslint.org):

```
$ npm i eslint --save-dev
```

Next, install `eslint-plugin-greenit`:

```
$ npm install eslint-plugin-greenit --save-dev
```

## Usage

Add `greenit` to the plugins section of your `.eslintrc` configuration file. You can omit the `eslint-plugin-` prefix:

```json
{
    "plugins": [
        "greenit"
    ]
}
```

Then configure the rules you want to use under the rules section.

```json
{
    "rules": {
        "greenit/rule-name": 2
    }
}
```

## Supported Rules

| Name | Description |
| :--  | :--         |
| [measure-triggering-reflow](docs/rules/measure-triggering-reflow.md) | Signal measurements that might force the browser to reflow |
| [no-function-call-in-loop-declaration](docs/rules/no-function-call-in-loop-declaration.md) | Prevent calling a function at each iteration of a for loop |
| [no-multiple-css-modifications](docs/rules/no-multiple-css-modifications.md) | Signal stylistic changes that could be grouped to limit repaint and reflow |
