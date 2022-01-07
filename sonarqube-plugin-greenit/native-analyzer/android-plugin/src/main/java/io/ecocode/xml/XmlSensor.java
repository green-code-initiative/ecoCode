/*
 * ecoCode SonarQube Plugin
 * Copyright (C) 2020-2021 Snapp' - Université de Pau et des Pays de l'Adour
 * mailto: contact@ecocode.io
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
package io.ecocode.xml;

import com.google.common.annotations.VisibleForTesting;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.analyzer.commons.ProgressReport;
import org.sonarsource.analyzer.commons.xml.ParseException;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class XmlSensor implements Sensor {

    private static final Logger LOG = Loggers.get(XmlSensor.class);

    private final Checks<SonarXmlCheck> checks;

    public XmlSensor(CheckFactory checkFactory) {
        this.checks = checkFactory.<SonarXmlCheck>create(Xml.REPOSITORY_KEY).addAnnotatedChecks((Iterable) XmlCheckList.getXmlChecks());
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("EcoCodeXmlSensor").onlyWhenConfiguration(configuration -> !checks.all().isEmpty());
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        FilePredicate xmlFilesPredicate = fs.predicates().matchesPathPattern("**/*.xml");

        List<InputFile> inputFiles = new ArrayList<>();
        fs.inputFiles(xmlFilesPredicate).forEach(inputFile1 -> {
            context.markForPublishing(inputFile1);
            inputFiles.add(inputFile1);
        });

        if (inputFiles.isEmpty()) {
            return;
        }

        ProgressReport progressReport = new ProgressReport("Report about progress of Java XML analyzer", TimeUnit.SECONDS.toMillis(10));
        progressReport.start(inputFiles.stream().map(InputFile::toString).collect(Collectors.toList()));

        boolean successfullyCompleted = false;
        boolean cancelled = false;
        try {
            for (InputFile inputFile : inputFiles) {
                if (context.isCancelled()) {
                    cancelled = true;
                    break;
                }
                scanFile(context, inputFile);
                progressReport.nextFile();
            }
            successfullyCompleted = !cancelled;
        } finally {
            if (successfullyCompleted) {
                progressReport.stop();
            } else {
                progressReport.cancel();
            }
        }
    }

    private void scanFile(SensorContext context, InputFile inputFile) {
        XmlFile xmlFile;
        try {
            xmlFile = XmlFile.create(inputFile);
        } catch (ParseException | IOException e) {
            LOG.debug("Skipped '{}' due to parsing error", inputFile);
            return;
        } catch (Exception e) {
            // Our own XML parsing may have failed somewhere, so logging as warning to appear in logs
            LOG.warn(String.format("Unable to analyse file '%s'.", inputFile), e);
            return;
        }

        checks.all().forEach(check -> {
            RuleKey ruleKey = checks.ruleKey(check);
            scanFile(context, xmlFile, check, ruleKey);
        });
    }

    @VisibleForTesting
    void scanFile(SensorContext context, XmlFile xmlFile, SonarXmlCheck check, RuleKey ruleKey) {
        try {
            check.scanFile(context, ruleKey, xmlFile);
        } catch (Exception e) {
            LOG.error(String.format("Failed to analyze '%s' with rule %s", xmlFile.getInputFile().toString(), ruleKey), e);
        }
    }
}

