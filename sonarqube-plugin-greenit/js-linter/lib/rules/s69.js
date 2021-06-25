/**
 * @fileoverview Ne pas appeler de fonction dans la déclaration d’une boucle de type for
 * @author Gael Pellevoizin
 */
"use strict";

//------------------------------------------------------------------------------
// Rule Definition
//------------------------------------------------------------------------------

module.exports = {
    meta: {
        docs: {
            description: "Ne pas appeler de fonction dans la déclaration d’une boucle de type for",
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

        function obvious(node) {
            //console.log(node.update)
            if (node.init.right.type == 'CallExpression' ||
                node.init.left.type == 'CallExpression' ||
                node.test.right.type == 'CallExpression' ||
                node.test.left.type == 'CallExpression' ||
                node.update.right.type == 'CallExpression' ||
                node.update.left.type == 'CallExpression') {
                context.report({
                    node,
                    message: "Ne pas appeler de fonction dans la déclaration d’une boucle de type for",
                });
            }
        }

        return {
            "ForStatement:exit": obvious,

        };
    }
};