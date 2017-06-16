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

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import com.cloudbees.hudson.plugins.folder.Folder;

import hudson.model.FreeStyleProject;
import hudson.model.ListView;

public class JobActionTest {

	@Rule
	public JenkinsRule j = new JenkinsRule();

	@Test
	public void testFreestyleDefaultViews() throws IOException {
		FreeStyleProject freestyle = j.createFreeStyleProject();
		JobAction action = new JobAction(freestyle);
		assertTrue(action.getViews().size() == 1);
	}

	@Test
	public void testFreestyle() throws IOException {
		FreeStyleProject freestyle = j.createFreeStyleProject();

		ListView viewNonEmpty = new ListView("NonEmptyView");
		j.jenkins.addView(viewNonEmpty);
		ListView viewEmpty = new ListView("EmptyView");
		j.jenkins.addView(viewEmpty);

		viewNonEmpty.add(freestyle);

		JobAction action = new JobAction(freestyle);
		assertTrue(action.getViews().size() == 2);
		assertTrue(action.getViews().contains(viewNonEmpty));
		assertTrue(!action.getViews().contains(viewEmpty));
	}

	@Test
	public void testFolders() throws IOException {
		Folder folder = j.createProject(Folder.class);
		folder.createProject(FreeStyleProject.class, "FreestyleInFolder");

		assertTrue(folder.getItems().size() == 1);

		ListView viewNonEmpty = new ListView("NonEmptyView");
		j.jenkins.addView(viewNonEmpty);
		ListView viewEmpty = new ListView("EmptyView");
		j.jenkins.addView(viewEmpty);

		viewNonEmpty.add(folder);

		JobAction action = new JobAction(folder);
		assertTrue(action.getViews().size() == 2);
		assertTrue(action.getViews().contains(viewNonEmpty));
		assertTrue(!action.getViews().contains(viewEmpty));

	}

	@Test
	public void testFoldersAction() throws IOException {
		Folder folder = j.createProject(Folder.class);
		folder.createProject(FreeStyleProject.class, "FreestyleProject");

		assertTrue(folder.getAction(JobAction.class) != null);
		FreeStyleProject freestyle = (FreeStyleProject) folder
				.getItem("FreestyleProject");
		assertTrue(freestyle.getAction(JobAction.class) == null);
	}

}
