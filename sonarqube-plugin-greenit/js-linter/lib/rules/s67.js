/**
 * @fileoverview Remplacer les $i++ par ++$i
 * @author Gael Pellevoizin
 */
"use strict";

//------------------------------------------------------------------------------
// Rule Definition
//------------------------------------------------------------------------------

module.exports = {
    meta: {
        docs: {
            description: "Remplacer les $i++ par ++$i",
            category: "Fill me in",
            recommended: false,
        },
        fixable: null, // or "code" or "whitespace"
        schema: [
            // fill in your schema
        ],
    },

    create: function(context) {
        // variables should be defined here

        //----------------------------------------------------------------------
        // Helpers
        //----------------------------------------------------------------------

        // any helper functions should go here or else delete this section

        //----------------------------------------------------------------------
        // Public
        //----------------------------------------------------------------------
        function findPostIncrement(node) {
            if (!node.prefix) {
                context.report({
                    node,
                    message: "Remplacer les $i++ par ++$i.",
                });
            }
        }

        return {
            "UpdateExpression:exit": findPostIncrement,
        };
    },
};