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
package magic.system.hyperion.reader;

import com.fasterxml.jackson.databind.JsonNode;
import magic.system.hyperion.components.Model;
import magic.system.hyperion.data.AttributeMap;
import magic.system.hyperion.exceptions.HyperionException;

/**
 * Reader for the model part of the document.
 *
 * @author Thomas Lehmann
 */
public class ModelReader implements INodeReader {
    /**
     * Model to fill.
     */
    private final Model model;

    /**
     * Initialize reader with empty model.
     *
     * @param initModel model to fill.
     * @since 1.0.0
     */
    public ModelReader(final Model initModel) {
        this.model = initModel;
    }

    @Override
    public void read(JsonNode node) throws HyperionException {
        final var newAttributeMap = new AttributeMap();
        new AttributeMapReader(newAttributeMap).read(node);
        this.model.getData().addAll(newAttributeMap);
    }
}
