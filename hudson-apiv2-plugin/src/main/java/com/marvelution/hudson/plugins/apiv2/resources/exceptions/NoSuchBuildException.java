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

package com.marvelution.hudson.plugins.apiv2.resources.exceptions;

/**
 * Exception thrown in case the build identified by job name and build number doesn't exist
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld<a/>
 */
public class NoSuchBuildException extends NotFoundException {

	/**
	 * Default Serial version ID
	 */
	private static final long serialVersionUID = 1L;
	private String jobName;
	private Object build;

	/**
	 * Constructor
	 * 
	 * @param jobName the Job name
	 * @param build the build
	 */
	public NoSuchBuildException(String jobName, Object build) {
		this.jobName = jobName;
		this.build = build;
	}

	/**
	 * Getter for jobName
	 *
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * Getter for build
	 *
	 * @return the build
	 */
	public Object getBuild() {
		return build;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getReasonPhrase() {
		return "Build " + String.valueOf(build) + " for Job " + jobName + " doesn't exist";
	}

}
