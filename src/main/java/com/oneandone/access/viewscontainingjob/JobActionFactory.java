/*
 * The MIT License
 *
 * Copyright 2017 Jochen A. Fuerbacher, 1&1 Telecommunication SE
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
package com.oneandone.access.viewscontainingjob;

import java.util.Collection;
import java.util.Collections;

import hudson.Extension;
import hudson.model.AbstractItem;
import hudson.model.Action;
import hudson.model.Hudson;
import jenkins.model.TransientActionFactory;

/**
 * Class implementing the TransientActionFactory extension point.
 * 
 * @author Jochen A. Fuerbacher
 *
 */
@Extension
public class JobActionFactory extends TransientActionFactory<AbstractItem> {

	/**
	 * Creates the JobAction for the provided item.
	 * 
	 * @param target
	 *            Item, to create the action for.
	 * 
	 * @return The job action als singleton, if the parent is instance of
	 *         Hudson, otherwise an empty set.
	 */
	@Override
	public Collection<? extends Action> createFor(AbstractItem target) {
		if (!(target.getParent() instanceof Hudson)) {
			return Collections.emptySet();
		}
		return Collections.singleton(new JobAction(target));
	}

	/**
	 * The type of object this factory cares about.
	 * 
	 * @return The type of object this factory cares about.
	 */
	@Override
	public Class<AbstractItem> type() {
		return AbstractItem.class;
	}

}
