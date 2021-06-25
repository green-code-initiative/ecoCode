/**
 * @fileoverview Utiliser les opérations primitives
 * @author Gael Pellevoizin
 */
"use strict";

//------------------------------------------------------------------------------
// Rule Definition
//------------------------------------------------------------------------------

module.exports = {
    meta: {
        docs: {
            description: "Eviter d'utiliser try-catch-finally",
            category: "Fill me in",
            recommended: false
        },
        fixable: null, // or "code" or "whitespace"
        schema: [
            // fill in your schema
        ]
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
            context.report({
                node,
                message: "Eviter d'utiliser try-catch-finally",
            });
        }
        return {
            "TryStatement:exit": findPostIncrement,
            // give me methods

        };
    }
};