/**
 * @fileoverview Limit repaint and reflow by limiting the change on CSS properties one by one
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
      description: "Limit repaint and reflow by grouping style change",
      category: "Green IT",
      recommended: false,
    },
    fixable: null, // or "code" or "whitespace"
    schema: [
      // fill in your schema
    ],
  },

  create: function (context) {
    const stack = [];
    const source = context.getSourceCode();

    //----------------------------------------------------------------------
    // Helpers
    //----------------------------------------------------------------------

    function checkHasPreviousOccurence(node, stack, source) {
      const searchedObjectText = source.getText(node.expression.left.object);

      // find element that have the same object text than the searched node
      const matchingNodes = stack.filter((element) => {
        const elementObjectText = source.getText(
          element.expression.left.object
        );

        return (
          elementObjectText === searchedObjectText &&
          element.range[0] != node.range[0]
        );
      });

      return matchingNodes.length > 0;
    }

    function checkIsVisible(node, stack, source) {
      const searchedObjectText = source.getText(node.expression.left.object);
      let visible = true;

      for (const element of stack) {
        const elementObjectText = source.getText(
          element.expression.left.object
        );
        const elementProprietyText = source.getText(
          element.expression.left.property
        );
        const elementRightValue = element.expression.right.value;

        if (
          searchedObjectText === elementObjectText &&
          elementProprietyText === "display" &&
          elementRightValue === "none"
        ) {
          visible = false;
        }

        if (
          searchedObjectText === elementObjectText &&
          elementProprietyText === "display" &&
          elementRightValue !== "none"
        ) {
          visible = true;
        }
      }

      return visible;
    }

    function checkIsFirstVisibleDisplay(node, stack, source) {
      const searchedObjectText = source.getText(node.expression.left.object);
      const searchedObjectProperty = source.getText(
        node.expression.left.property
      );
      const searchedRightValue = node.expression.right.value;

      if (searchedRightValue === undefined) {
        return false;
      }

      if (
        searchedObjectProperty !== "display" ||
        searchedRightValue === "none"
      ) {
        return false;
      }

      for (const element of stack) {
        const elementObjectText = source.getText(
          element.expression.left.object
        );
        const elementProprietyText = source.getText(
          element.expression.left.property
        );
        const elementRightValue = element.expression.right.value;

        // if we find a element before
        if (
          elementObjectText === searchedObjectText &&
          elementProprietyText === "display" &&
          elementRightValue !== "none" &&
          element.range[0] < node.range[0]
        ) {
          return false;
        }
      }

      return true;
    }

    //----------------------------------------------------------------------
    // Public
    //----------------------------------------------------------------------

    return {
      "ExpressionStatement[expression.left.object.property.name='style']"(
        node
      ) {
        stack.push(node);
      },

      "ExpressionStatement[expression.left.object.property.name='style'] ~ ExpressionStatement[expression.left.object.property.name='style']"(
        node
      ) {
        const hasPreviousOccurence = checkHasPreviousOccurence(
          node,
          stack,
          source
        );
        if (!hasPreviousOccurence) {
          return;
        }

        const isVisible = checkIsVisible(node, stack, source);
        if (!isVisible) {
          return;
        }

        const isFirstVisibleDisplay = checkIsFirstVisibleDisplay(
          node,
          stack,
          source
        );
        if (isFirstVisibleDisplay) {
          return;
        }

        context.report({
          node: node.expression.left,
          message: "Multiple style modifications should be grouped",
        });
      },
    };
  },
};
