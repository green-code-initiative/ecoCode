/**
 * @fileoverview Functions should not be called inside the declaration of a loop
 * @author
 */
"use strict";

//------------------------------------------------------------------------------
// Requirements
//------------------------------------------------------------------------------

const rule = require("../../../lib/rules/no-function-call-in-loop-declaration"),
  RuleTester = require("eslint").RuleTester;

RuleTester.setDefaultConfig({
  parserOptions: {
    ecmaVersion: 6,
  },
});

//------------------------------------------------------------------------------
// Helpers
//------------------------------------------------------------------------------

const callExpressionError = {
  message: "There should be no function call in loop declaration",
  type: "CallExpression",
};

//------------------------------------------------------------------------------
// Tests
//------------------------------------------------------------------------------

const ruleTester = new RuleTester();

ruleTester.run("no-function-call-in-loop-declaration", rule, {
  valid: [
    "for (let i = 0; i < 5; i++);",
    "for (let i = 0; i < arr.length; i++);",
    "for (let i of arr);",
    "for (let i in arr);",
  ],

  invalid: [
    {
      code: "for (let i = 0; i < parseInt('5'); i++);",
      errors: [
        callExpressionError
      ],
    },
    {
      code: "for (let i = 0; i < getRandom(); i++);",
      errors: [
        callExpressionError
      ],
    },
  ],
});
