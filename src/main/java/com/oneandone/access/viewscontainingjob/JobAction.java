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

import java.util.LinkedList;
import java.util.List;

import hudson.model.AbstractItem;
import hudson.model.Action;
import hudson.model.TopLevelItem;
import hudson.model.View;
import jenkins.model.Jenkins;

/**
 * Class implementing the job action.
 * 
 * @author Jochen A. Fuerbacher
 *
 */
public class JobAction implements Action {

	private AbstractItem project;

	/**
	 * Constructor for the job action.
	 * 
	 * @param project
	 *            The project to create this action for.
	 */
	public JobAction(AbstractItem project) {
		this.project = project;
	}

	/**
	 * Getter for the display name of this action.
	 * 
	 * @return The display name as String.
	 */
	@Override
	public String getDisplayName() {
		return Messages.displayName();
	}

	/**
	 * Getter for the icon file name of this action.
	 * 
	 * @return The icon file name (incl. the file path) for this action.
	 */
	@Override
	public String getIconFileName() {
		return "/plugin/views-containing-job/img/view.svg";
	}

	/**
	 * Getter for the url to the site that shows the views.
	 * 
	 * @return The (relative) url to the site that shows the views.
	 */
	@Override
	public String getUrlName() {
		return "view";
	}

	/**
	 * Getter for a list of all views containing the job with this action.
	 * 
	 * @return A list of all views containing the job with this action.
	 */
	public List<View> getViews() {
		List<View> views = new LinkedList<>();
		Jenkins instance = Jenkins.getInstance();

		for (View view : instance.getViews()) {
			for (TopLevelItem item : view.getAllItems()) {
				if (((AbstractItem) item).equals(this.project)) {
					views.add(view);
				}
			}
		}

		return views;
	}

}
