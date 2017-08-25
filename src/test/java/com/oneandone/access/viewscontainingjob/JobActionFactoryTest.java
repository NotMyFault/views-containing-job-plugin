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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import com.cloudbees.hudson.plugins.folder.Folder;

import hudson.model.AbstractItem;
import hudson.model.Action;
import hudson.model.FreeStyleProject;

public class JobActionFactoryTest {

	@Rule
	public JenkinsRule j = new JenkinsRule();

	@Test
	public void testFreestyle() throws IOException {
		FreeStyleProject freestyle = j.createFreeStyleProject();

		JobActionFactory factory = new JobActionFactory();
		Collection<? extends Action> collection = factory.createFor(freestyle);

		assertTrue(collection != null);
		assertTrue(collection.size() == 1);
		assertEquals(JobAction.class, collection.iterator().next().getClass());
	}

	@Test
	public void testFolders() throws IOException {
		Folder folder = j.createProject(Folder.class);
		FreeStyleProject freestyle = folder
				.createProject(FreeStyleProject.class, "FreestyleInFolder");

		JobActionFactory factory = new JobActionFactory();
		Collection<? extends Action> collection = factory.createFor(folder);

		assertTrue(collection != null);
		assertTrue(collection.size() == 1);
		assertEquals(JobAction.class, collection.iterator().next().getClass());

		JobActionFactory factory2 = new JobActionFactory();
		Collection<? extends Action> collection2 = factory2
				.createFor(freestyle);

		assertTrue(collection2 != null);
		assertTrue(collection2.size() == 0);
	}

	@Test
	public void testType() {
		JobActionFactory factory = new JobActionFactory();
		assertEquals(AbstractItem.class, factory.type());
	}

}
