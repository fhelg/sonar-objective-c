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
package org.sonar.plugins.objectivec.cpd;

import net.sourceforge.pmd.cpd.Tokenizer;
import org.sonar.api.batch.AbstractCpdMapping;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.resources.Language;
import org.sonar.plugins.objectivec.api.ObjectiveC;

import java.nio.charset.Charset;

public class ObjectiveCCpdMapping extends AbstractCpdMapping {
    private final ObjectiveC language;
    private final Charset charset;

    public ObjectiveCCpdMapping(ObjectiveC language, FileSystem fileSystem) {
        this.language = language;
        this.charset = fileSystem.encoding();
    }

    @Override
    public Tokenizer getTokenizer() {
        return new ObjectiveCTokenizer(charset);
    }

    @Override
    public Language getLanguage() {
        return language;
    }
}
