// no-color-blue.js

const stylelint = require("stylelint");
const { report, ruleMessages, validateOptions } = stylelint.utils;

const ruleName = "testim-plugin/standard-policy";

const messages = ruleMessages(ruleName, {
    expected: (unfixed, fixed) => `Expcted "${unfixed}" to be one of "${fixed}"`,
});

const standardPolicies = [
    "serif",
    "sans-serif",
    "monospace",
    "cursive",
    "fantasy",
    "system-ui",
    "emoji",
    "math",
    "fangsong",
    "inherit",
    "initial",
    "unset",
];

module.exports = stylelint.createPlugin(
    ruleName,
    function getPlugin(primaryOption, secondaryOptionObject, context) {
        return function lint(postcssRoot, postcssResult) {
            const validOptions = validateOptions(postcssResult, ruleName, {
                //No options for now...
            });

            if (!validOptions) {
                //If the options are invalid, don't lint
                return;
            }
            const isAutoFixing = Boolean(context.fix);
            postcssRoot.walkDecls(/^font-family/, decl => {
                if (!standardPolicies.includes(decl.value)) {
                    report({
                        ruleName,
                        result: postcssResult,
                        message: messages.expected(decl.value, standardPolicies), // Build the reported message
                        node: decl, // Specify the reported node
                        word: 'blue', // Which exact word caused the error? This positions the error properly
                    });
                }

            })
        };
    }
);

module.exports.ruleName = ruleName;
module.exports.messages = messages;