/*
 * Licensed to Marvelution under one or more contributor license 
 * agreements.  See the NOTICE file distributed with this work 
 * for additional information regarding copyright ownership.
 * Marvelution licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.marvelution.jira.plugins.hudson.model;

import java.util.ArrayList;
import java.util.Collection;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Model class for a list of {@link Job}
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@XStreamAlias("jobs")
public class Jobs {

	@XStreamImplicit(itemFieldName = "job")
	private Collection<Job> jobs;

	/**
	 * Gets the {@link Collection} of {@link Job} object
	 * 
	 * @return the {@link Collection} of {@link Job} object
	 */
	public Collection<Job> getJobs() {
		if (jobs == null) {
			jobs = new ArrayList<Job>();
		}
		return jobs;
	}

	/**
	 * Sets the {@link Collection} of {@link Job} object
	 * 
	 * @param jobs the {@link Collection} of {@link Job} object
	 */
	public void setJobs(Collection<Job> jobs) {
		this.jobs = jobs;
	}

}
