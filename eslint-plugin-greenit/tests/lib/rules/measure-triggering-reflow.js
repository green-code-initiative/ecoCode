/**
 * @fileoverview Taking measurement force browser reflow
 * @author
 */
"use strict";

//------------------------------------------------------------------------------
// Requirements
//------------------------------------------------------------------------------

const rule = require("../../../lib/rules/measure-triggering-reflow"),
  RuleTester = require("eslint").RuleTester;

//------------------------------------------------------------------------------
// Helpers
//------------------------------------------------------------------------------

const identifierError = {
  message: "Taking measurement will likely trigger a reflow.",
  type: "Identifier",
};

//------------------------------------------------------------------------------
// Tests
//------------------------------------------------------------------------------

const ruleTester = new RuleTester();
ruleTester.run("measure-triggering-reflow", rule, {
  valid: [
    // give me some code that won't trigger a warning
  ],

  invalid: [
    {
      code: "var width = document.querySelector('div').offsetWidth;",
      errors: [identifierError],
    },
    {
      code: "var height = element.offsetHeight;",
      errors: [identifierError],
    },
    {
      code: "var width = element.clientWidth;",
      errors: [identifierError],
    },
    {
      code: "var height = document.querySelector('div').clientHeight;",
      errors: [identifierError],
    },
    {
      code: "var style = getComputedStyle(box)",
      errors: [identifierError],
    },
    {
      code: "getComputedStyle(box)",
      errors: [identifierError],
    },
    {
      code: "width = box.clientWidth",
      errors: [identifierError],
    },
    {
      code: "box.offsetHeight",
      errors: [identifierError],
    },
  ],
});
