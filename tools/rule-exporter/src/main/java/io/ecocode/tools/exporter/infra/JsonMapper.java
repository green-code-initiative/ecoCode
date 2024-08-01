/*
 * ecoCode Rule Exporter - Export all rules to JSON files usable by the website
 * Copyright © 2024 Green Code Initiative (https://www.ecocode.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.ecocode.tools.exporter.infra;

import io.ecocode.tools.exporter.domain.RuleMetadata;
import io.ecocode.tools.exporter.domain.RuleSeverity;
import io.ecocode.tools.exporter.domain.RuleStatus;
import jakarta.json.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonMapper {

    private static final Pattern FILE_PATTERN = Pattern.compile("^io/ecocode/rules/(.*?)/(.*?)\\.json$");

    public static RuleMetadata toRuleMetadata(String filePath, JsonObject object) {
        Matcher fileMatcher = FILE_PATTERN.matcher(filePath);

        if (!fileMatcher.find() || fileMatcher.groupCount() != 2) {
            throw new IllegalStateException("Invalid file path: " + filePath);
        }

        RuleSeverity severity = RuleSeverity.valueOf(object.getString("defaultSeverity").toUpperCase());
        RuleStatus status = RuleStatus.valueOf(object.getString("status").toUpperCase());

        return new RuleMetadata.Builder()
                .id(fileMatcher.group(2))
                .technology(fileMatcher.group(1))
                .title(object.getString("title"))
                .severity(severity)
                .status(status)
                .build();
    }

}
