/**
 * @fileoverview Taking measurement force browser reflow
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
      description: "Taking measurement force browser reflow",
      category: "Green IT",
      recommended: false,
    },
    fixable: null, // or "code" or "whitespace"
    schema: [
      // fill in your schema
    ],
    messages: {
      reflow: "Taking measurement will likely trigger a reflow.",
    },
  },

  create: function (context) {
    // variables should be defined here
    const measurementProperties = [
      "offsetWidth",
      "offsetHeight",
      "clientWidth",
      "clientHeight",
    ];

    const getComputedStyleName = "getComputedStyle"

    //----------------------------------------------------------------------
    // Helpers
    //----------------------------------------------------------------------
    function checkNodeIsCallingComputedStyle(node) {
      if (!node.callee) {
        return false;
      }
      return node.callee.name === getComputedStyleName;
    }

    function checkNodeIsComputingMeasurement(node) {
      if (!node.property) {
        return false;
      }
      return measurementProperties.includes(node.property.name);
    }

    // any helper functions should go here or else delete this section

    //----------------------------------------------------------------------
    // Public
    //----------------------------------------------------------------------

    return {
      CallExpression(node) {
        const isCallingGetComputedStyle = checkNodeIsCallingComputedStyle(node);

        if (!isCallingGetComputedStyle) {
          return false;
        }

        context.report({
          node: node.callee,
          messageId: "reflow",
        });
      },

      MemberExpression(node) {
        const isComputingMeasurement = checkNodeIsComputingMeasurement(
          node
        );

        if (!isComputingMeasurement) {
          return;
        }

        context.report({
          node: node.property,
          messageId: "reflow",
        });
      },
    };
  },
};
