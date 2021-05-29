/*
 * The MIT License
 *
 * Copyright 2021 Thomas Lehmann.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package magic.system.hyperion.components.tasks;

import magic.system.hyperion.components.Component;
import magic.system.hyperion.components.TaskParameters;
import magic.system.hyperion.components.TaskResult;
import magic.system.hyperion.components.Variable;
import magic.system.hyperion.interfaces.IRunnable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A task represents a a concrete task represented as file (script) or inline
 * code.
 *
 * @author Thomas Lehmann
 */
public abstract class AbstractTask extends Component
        implements IRunnable<TaskResult, TaskParameters> {

    /**
     * Path and name of file of script or inline script.
     */
    private String strCode;

    /**
     * Storing the task result.
     */
    private final Variable variable;

    /**
     * List of tags.
     */
    private List<String> tags;

    /**
     * Initialize task.
     *
     * @param strInitTitle - title of the task.
     * @param strInitCode - Path and name of file of script or inline script.
     */
    public AbstractTask(final String strInitTitle, final String strInitCode) {
        super(strInitTitle);
        this.strCode = strInitCode;
        this.variable = new Variable();
        this.variable.setName("default");
        this.tags = new ArrayList<>();
    }

    /**
     * Provide Script code or path and filename to script.
     *
     * @return Path and name of file of script or inline script.
     */
    public String getCode() {
        return this.strCode;
    }

    /**
     * Provide variable representing task result.
     *
     * @return variable.
     */
    public Variable getVariable() {
        return this.variable;
    }

    /**
     * Change code.
     *
     * @param strInitCode new value for code.
     */
    public void setCode(final String strInitCode) {
        this.strCode = strInitCode;
    }

    /**
     * Get list of tags (read only).
     * @return list of tags.
     */
    public List<String> getTags() {
        return Collections.unmodifiableList(this.tags);
    }

    /**
     * Adding a tag that doesn't exist yet.
     *
     * @param strTag new tag to add.
     */
    public void addTag(final String strTag) {
        if (!this.tags.contains(strTag)) {
            this.tags.add(strTag);
        }
    }

    /**
     * Checking for code to represent an existing path and filename.
     *
     * @return true when given code represents an existing path and filename.
     */
    public boolean isRegularFile() {
        boolean success;
        try {
            success = Files.isRegularFile(Paths.get(this.strCode));
        } catch (InvalidPathException e) {
            success = false;
        }
        return success;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getTitle())
                .append(this.strCode)
                .append(this.variable)
                .append(this.tags)
                .build();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final AbstractTask other = (AbstractTask) obj;
        return new EqualsBuilder()
                .append(this.getTitle(), other.getTitle())
                .append(this.strCode, other.getCode())
                .append(this.variable, other.getVariable())
                .append(this.tags, other.getTags())
                .build();
    }
}