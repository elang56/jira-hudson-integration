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

package com.marvelution.hudson.plugins.apiv2;

import com.marvelution.hudson.plugins.apiv2.servlet.filter.HudsonAPIV2ServletFilter;

import hudson.Plugin;
import hudson.util.PluginServletFilter;

/**
 * Main Plugin implementation of the Hudson REST plugin.
 * This class is responsible for the registration of the
 * <a href="http://incubator.apache.org/wink/">Wink Application</a> implementation
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark rekveld</a>
 * @plugin
 */
public class PluginImpl extends Plugin {

	private HudsonAPIV2ServletFilter filter;

	/**
	 * {@inheritDoc}
	 */
	public void start() throws Exception {
		super.start();
		filter = new HudsonAPIV2ServletFilter();
		PluginServletFilter.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() throws Exception {
		super.stop();
		if (filter != null) {
			PluginServletFilter.removeFilter(filter);
		}
	}

}
