/**
 * @fileoverview Remplacer les $i++ par ++$i
 * @author Gael Pellevoizin
 */
"use strict";

//------------------------------------------------------------------------------
// Requirements
//------------------------------------------------------------------------------

var rule = require("../../../lib/rules/s67"),

    RuleTester = require("eslint").RuleTester;


//------------------------------------------------------------------------------
// Tests
//------------------------------------------------------------------------------

var ruleTester = new RuleTester();
ruleTester.run("s67", rule, {

    valid: [
        "++counter",
        "counter + 73",
        "counter += 73",
        "counter =+ 73",
        "73 + counter"
    ],

    invalid: [{
        code: "counter++;",
        errors: [{
            message: "Remplacer les $i++ par ++$i.",
            type: "UpdateExpression"
        }]
    }]
});