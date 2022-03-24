/*
 * Sonar CodeNarc Converter
 * Copyright (C) 2010-2021 SonarQube Community
 *  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.groovy.codenarc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.codenarc.rule.AbstractRule;

import java.lang.reflect.Field;
import java.util.*;

public class Rule {

    public final AbstractRule ruleInstance;
    public final String key;
    public final String internalKey;
    public final String name;
    public final String description;
    public final String severity;
    public final String version;
    public final Set<String> tags;
    public final Set<RuleParameter> parameters;

    // SONARGROOV-40
    private static final String[] FIXED_RULES_WITH_NULL_PARAMETERS = {
            "org.codenarc.rule.design.PrivateFieldCouldBeFinalRule",
            "org.codenarc.rule.generic.IllegalRegexRule",
            "org.codenarc.rule.generic.RequiredRegexRule",
            "org.codenarc.rule.generic.RequiredStringRule",
            "org.codenarc.rule.generic.StatelessClassRule",
            "org.codenarc.rule.generic.IllegalPackageReferenceRule",
            "org.codenarc.rule.generic.IllegalClassReferenceRule",
            "org.codenarc.rule.generic.IllegalClassMemberRule",
            "org.codenarc.rule.generic.IllegalStringRule",
            "org.codenarc.rule.generic.IllegalSubclassRule",
            "org.codenarc.rule.grails.GrailsPublicControllerMethodRule",
            "org.codenarc.rule.junit.SpockIgnoreRestUsedRule",
            "org.codenarc.rule.junit.JUnitPublicPropertyRule",
            "org.codenarc.rule.naming.AbstractClassNameRule",
            "org.codenarc.rule.naming.FieldNameRule",
            "org.codenarc.rule.naming.InterfaceNameRule",
            "org.codenarc.rule.naming.MethodNameRule",
            "org.codenarc.rule.naming.ParameterNameRule",
            "org.codenarc.rule.naming.PropertyNameRule",
            "org.codenarc.rule.naming.VariableNameRule",
            "org.codenarc.rule.naming.PackageNameMatchesFilePathRule",
            "org.codenarc.rule.size.CyclomaticComplexityRule",
            "org.codenarc.rule.size.MethodSizeRule",
            "org.codenarc.rule.size.CrapMetricRule",
            "org.codenarc.rule.size.AbcMetricRule",
            "org.codenarc.rule.unused.UnusedVariableRule"
    };

    public Rule(
            Class<? extends AbstractRule> ruleClass,
            String since,
            Properties props,
            Map<String, ? extends ParseResult> parametersByRule)
            throws ReflectiveOperationException {
        ruleInstance = ruleClass.newInstance();
        key = ruleClass.getCanonicalName();
        internalKey = StringUtils.removeEnd(ruleClass.getSimpleName(), "Rule");
        name = cleanName(internalKey);
        severity = severity(ruleInstance.getPriority());
        tags = getTags(key, internalKey);
        version = since;

        ParseResult dataFromAptFile = parametersByRule.get(internalKey);
        String descriptionFromProperty = props.getProperty(internalKey + ".description.html");
        description = extractDescription(dataFromAptFile, descriptionFromProperty);
        parameters = extractParameters(dataFromAptFile, descriptionFromProperty);
    }

    private static String cleanName(String internalKey) {
        String result = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(internalKey), ' ');
        return result.replace("J Unit", "JUnit");
    }

    private Set<RuleParameter> extractParameters(ParseResult results, String description) {
        Map<String, RuleParameter> extractedParameters = Maps.newHashMap();
        addParameters(results, extractedParameters);

        String[] params1 = StringUtils.substringsBetween(description, "<em>", "</em> property");
        if (params1 != null) {
            for (int i = 0; i < params1.length; i++) {
                String paramName = params1[i];
                if (paramName.contains("<em>")) {
                    params1[i] = paramName.substring(paramName.lastIndexOf("<em>") + 4);
                }
            }
            addParameters(params1, ruleInstance, extractedParameters);
        }
        String[] params2 = StringUtils.substringsBetween(description, "configured in <em>", "</em>");
        if (params2 != null) {
            addParameters(params2, ruleInstance, extractedParameters);
        }
        if (StringUtils.contains(description, "length property")) {
            addParameter("length", ruleInstance, extractedParameters);
        }
        if (StringUtils.contains(description, "sameLine property")) {
            addParameter("sameLine", ruleInstance, extractedParameters);
        }
        return Sets.newHashSet(extractedParameters.values());
    }

    private static void addParameters(ParseResult results, Map<String, RuleParameter> parameters) {
        if (results.hasParameters()) {
            for (RuleParameter param : results.getParameters()) {
                parameters.put(param.key(), param);
            }
        }
    }

    private void addParameters(
            String[] parameterNames, AbstractRule ruleInstance, Map<String, RuleParameter> parameters) {
        for (String parameterName : parameterNames) {
            addParameter(parameterName, ruleInstance, parameters);
        }
    }

    private void addParameter(
            String parameterName, AbstractRule ruleInstance, Map<String, RuleParameter> parameters) {
        RuleParameter current = parameters.get(parameterName);
        RuleParameter parameter =
                RuleParameter.create(parameterName, extractDefaultValue(parameterName, ruleInstance));
        if (current == null) {
            current = parameter;
        } else {
            current = current.merge(parameter);
        }
        parameters.put(current.key(), current);
    }

    private String extractDefaultValue(String parameterName, AbstractRule ruleInstance) {
        String result = "";
        try {
            // Hack to get the default value
            Field f = ruleInstance.getClass().getDeclaredField(parameterName);
            f.setAccessible(true);
            Object value = f.get(ruleInstance);
            if (value != null) {
                return value.toString();
            }
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            // NoSuchFieldException: do nothing, there is probably no default value
            // IllegalArgumentException: do nothing, that's probably not the correct name
            // IllegalAccessException: do nothing, can not access field
        }
        return result;
    }

    private String extractDescription(ParseResult dataFromAptFile, String property) {
        String result = property;
        if (dataFromAptFile != null && StringUtils.isNotBlank(dataFromAptFile.getDescription())) {
            result = dataFromAptFile.getDescription();
        }
        return cleanDescription(result);
    }

    private static String severity(int priority) {
        switch (priority) {
            case 1:
                return "INFO";
            case 2:
                return "MINOR";
            case 3:
                return "MAJOR";
            default:
                throw new IllegalStateException("Should never happen");
        }
    }

    private static Set<String> getTags(String key, String internalKey) {
        String[] split = key.split("\\.");
        String codeNarcCategory = split[split.length - 2];
        Set<String> results = Sets.newHashSet();

        switch (codeNarcCategory) {
            case "unnecessary":
                results.add("clumsy");
                break;
            case "formatting":
            case "naming ":
                results.add("convention");
                break;
            case "concurrency":
                results.add("multi-threading");
                break;
            case "exceptions":
                results.add("error-handling");
                break;
            case "basic":
                results.addAll(handleBasicCategory(internalKey));
                break;
            case "grails":
            case "groovyism":
            case "junit":
            case "design":
                results.add(codeNarcCategory);
                break;
            default:
                results.add("bug");
                break;
        }
        return results;
    }

    private static Set<String> handleBasicCategory(String internalKey) {
        Set<String> results = Sets.newHashSet();
        if (internalKey.startsWith("Empty")) {
            results.add("unused");
        } else if (internalKey.startsWith("Broken")) {
            results.add("bug");
        } else if (internalKey.startsWith("Equals")) {
            results.add("pitfall");
        } else if (internalKey.contains("Get") && !internalKey.startsWith("Get")) {
            results.add("bug");
        } else if (internalKey.endsWith("FinallyBlock")) {
            results.add("error-handling");
        } else {
            results.addAll(handleParticularCases(internalKey));
        }
        return results;
    }

    private static Set<String> handleParticularCases(String internalKey) {
        Set<String> results = Sets.newHashSet();
        if ("DeadCode".equals(internalKey)) {
            results.add("unused");
        } else if ("ExplicitGarbageCollection".equals(internalKey)) {
            results.add("unpredictable");
        } else if ("HardCodedWindowsFileSeparator".equals(internalKey)
                || "HardCodedWindowsRootDirectory".equals(internalKey)) {
            results.add("pitfall");
        } else if ("ForLoopShouldBeWhileLoop".equals(internalKey)) {
            results.add("clumsy");
        } else if ("ClassForName".equals(internalKey)) {
            results.add("leak");
            results.add("owasp-a1");
        } else {
            results.add("bug");
        }
        return results;
    }

    private String cleanDescription(String description) {
        String copy = description;
        String[] refToParams = StringUtils.substringsBetween(description, " (${rule.", "})");
        if (refToParams != null) {
            for (String ref : refToParams) {
                String paramRef = " (${rule." + ref + "})";
                copy = copy.replace(paramRef, "");
            }
        }
        return handleUrls(copy);
    }

    /**
     * Covers URLs such as:
     * {{http://blog.bjhargrave.com/2007/09/classforname-caches-defined-class-in.html}} <--- direct
     * link {{{http://en.wikipedia.org/wiki/Double-checked_locking}}} <--- direct link
     * {{{http://jira.codehaus.org/browse/JETTY-352}JETTY-352}} <--- renamed link
     */
    private String handleUrls(String description) {
        String result = description;
        String[] urls = extractUrls(description);
        if (urls.length > 0) {
            for (String url : urls) {
                String copy = url;
                boolean trailingAcc = false;
                if (!copy.startsWith("{")) {
                    copy = "<a>" + copy + "</a>";
                } else if (copy.startsWith("{http")) {
                    if ('}' == result.charAt(result.indexOf(copy) + copy.length() + 2)) {
                        trailingAcc = true;
                        copy = "<a>" + copy.substring(1) + "</a>";
                    } else {
                        copy = "<a href=\"" + copy.replace("{", "").replace("}", "\">") + "</a>";
                    }
                } else if (copy.startsWith("{./")) {
                    copy =
                            "<a href=\"http://codenarc.sourceforge.net/"
                                    + copy.replace("{./", "").replace("}", "\">")
                                    + "</a>";
                }
                result = result.replace("{{" + url + "}}" + (trailingAcc ? "}" : ""), copy);
            }
        }
        return result;
    }

    private static String[] extractUrls(String description) {
        List<String> urls = Lists.newArrayList();
        int index = 0;
        while (index < description.length()) {
            int start = description.indexOf("{{", index);
            if (start == -1) {
                break;
            }
            int end = description.indexOf("}}", start);
            if (end == -1) {
                break;
            }
            urls.add(description.substring(start + 2, end));
            index = end;
        }
        if (urls.isEmpty()) {
            return new String[]{};
        }
        return urls.toArray(new String[urls.size()]);
    }

    public String fixedRuleKey() {
        if (hasNullParameters() && isPartOfFixedRules()) {
            return key + ".fixed";
        }
        return key;
    }

    private boolean isPartOfFixedRules() {
        return Arrays.stream(FIXED_RULES_WITH_NULL_PARAMETERS).anyMatch(key::equals);
    }

    private boolean hasNullParameters() {
        for (RuleParameter parameter : parameters) {
            if ("null".equals(parameter.defaultValue())) {
                return true;
            }
        }
        return false;
    }
}
