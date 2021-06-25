/**
 * @fileoverview Functions should not be called inside the declaration of a loop
 * @author
 */
"use strict";

//------------------------------------------------------------------------------
// Rule Definition
//------------------------------------------------------------------------------

module.exports = {
  meta: {
    type: "suggestion",

    docs: {
      description:
        "Functions should not be called inside the declaration of a loop",
      category: "Green IT",
      recommended: false,
    },
    fixable: null, // or "code" or "whitespace"
    schema: [
      // fill in your schema
    ],
  },

  create: function (context) {
    // variables should be defined here

    //----------------------------------------------------------------------
    // Helpers
    //----------------------------------------------------------------------

    // any helper functions should go here or else delete this section

    //----------------------------------------------------------------------
    // Public
    //----------------------------------------------------------------------

    return {
      ForStatement: function (node) {
        if (node.test.right.type === "CallExpression") {
          context.report(
            node.test.right,
            "There should be no function call in loop declaration"
          );
        }
      },
    };
  },
};
