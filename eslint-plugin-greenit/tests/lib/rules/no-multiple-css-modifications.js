/**
 * @fileoverview Limit repaint and reflow by limiting the change on CSS properties one by one
 * @author
 */
"use strict";

//------------------------------------------------------------------------------
// Requirements
//------------------------------------------------------------------------------

const rule = require("../../../lib/rules/no-multiple-css-modifications"),
  RuleTester = require("eslint").RuleTester;

//------------------------------------------------------------------------------
// Helpers
//------------------------------------------------------------------------------

const memberExpressionError = {
  message: "Multiple style modifications should be grouped",
  type: "MemberExpression",
};

//------------------------------------------------------------------------------
// Tests
//------------------------------------------------------------------------------

const ruleTester = new RuleTester();
ruleTester.run("no-multiple-css-modifications", rule, {
  valid: [
    "a.style.fontSize = '2em'; b.style.color = 'red';",
    "a.style = '';",
    "document.getElementById('a').style.margin = '0';",
    "let a = document.getElementById('a'); document.getElementById('a').style.margin = '0'; a.style.margin = '20px';",
    "a.style.display = 'none'; a.style.fontSize = '2em'; a.style.color = 'red';",
  ],

  invalid: [
    {
      code: "a.style.fontSize = '2em'; a.style.color = 'red';",
      errors: [memberExpressionError],
    },
    {
      code:
        "document.getElementById('b').style.fontSize = '2em'; a.style.color = 'red'; document.getElementById('b').style.color = 'blue';",
      errors: [memberExpressionError],
    },
    {
      code:
        "a.style.fontSize = '2em'; a.style.color = 'red'; a.style.display = 'none'; a.style.fontSize = '2em'; a.style.color = 'red';",
      errors: [memberExpressionError],
    },
    {
      code:
        "c.style.display = 'none'; a.style.fontSize = '2em'; a.style.color = 'red'; a.style.display = 'none'; a.style.fontSize = '2em'; a.style.color = 'red';",
      errors: [memberExpressionError],
    },
    {
      code:
        "a.style.fontSize = '2em'; a.style.display = 'none'; a.style.fontSize = '2em'; a.style.color = 'red'; a.style.display = 'block'; a.style.color = 'blue'",
      errors: [memberExpressionError],
    },
    {
      code:
        "a.style.fontSize = '2em'; a.style.display = 'none'; a.style.fontSize = '2em'; a.style.color = 'red'; a.style.display = 'block'; a.style.display = 'inline-block'",
      errors: [memberExpressionError],
    },
  ],
});
