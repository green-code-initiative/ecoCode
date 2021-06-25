/**
 * @fileoverview Ne pas appeler de fonction dans la déclaration d’une boucle de type for
 * @author Gael Pellevoizin
 */
"use strict";

//------------------------------------------------------------------------------
// Requirements
//------------------------------------------------------------------------------

var rule = require("../../../lib/rules/s69"),

    RuleTester = require("eslint").RuleTester;


//------------------------------------------------------------------------------
// Tests
//------------------------------------------------------------------------------

var ruleTester = new RuleTester();
ruleTester.run("s69", rule, {

    valid: [

        // give me some code that won't trigger a warning
    ],

    invalid: [{
        code: "for (i = 0; i < getMaxValue(); i++) { console.log(i);} ",
        errors: [{
            message: "Ne pas appeler de fonction dans la déclaration d’une boucle de type for",
            type: "ForStatement"
        }]
    }]
});