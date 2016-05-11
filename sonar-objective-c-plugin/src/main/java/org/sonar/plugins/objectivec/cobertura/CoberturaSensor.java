/*
 * SonarQube Objective-C (Community) Plugin
 * Copyright (C) 2012-2016 OCTO Technology, Backelite, and contributors
 * mailto:sonarqube@googlegroups.com
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
package org.sonar.plugins.objectivec.cobertura;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.CoverageExtension;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;
import org.sonar.api.scan.filesystem.PathResolver;

import java.io.File;


public final class CoberturaSensor implements Sensor, CoverageExtension {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoberturaSensor.class);

    public static final String REPORT_PATH_KEY = "sonar.objectivec.cobertura.reportPath";

    private final FileSystem fileSystem;
    private final PathResolver pathResolver;
    private final Settings settings;

    public CoberturaSensor(final FileSystem fileSystem, final PathResolver pathResolver, final Settings settings) {
        this.fileSystem = fileSystem;
        this.pathResolver = pathResolver;
        this.settings = settings;
    }

    @Override
    public boolean shouldExecuteOnProject(Project project) {
        return StringUtils.isNotEmpty(settings.getString(REPORT_PATH_KEY));
    }

    @Override
    public void analyse(Project project, SensorContext context) {
        String path = settings.getString(REPORT_PATH_KEY);
        File report = pathResolver.relativeFile(fileSystem.baseDir(), path);

        if (!report.isFile()) {
            LOGGER.warn("Cobertura report not found at {}", report);
            return;
        }

        LOGGER.info("parsing {}", report);
        CoberturaReportParser.parseReport(report, fileSystem, context);
    }

    @Override
    public String toString() {
        return "Objective-C Cobertura Sensor";
    }
}
