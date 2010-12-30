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

package com.marvelution.hudson.plugins.apiv2.wink;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.wink.common.WinkApplication;
import org.apache.wink.common.annotations.Parent;
import org.apache.wink.common.internal.registry.metadata.ProviderMetadataCollector;
import org.apache.wink.common.internal.registry.metadata.ResourceMetadataCollector;
import org.apache.wink.common.internal.utils.FileLoader;

import com.marvelution.hudson.plugins.apiv2.resources.BaseRestResource;
import com.marvelution.hudson.plugins.apiv2.servlet.filter.HudsonAPIV2ServletFilter;

/**
 * {@link WinkApplication} implementation specific for Hudson
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark rekveld</a>
 * 
 * @see 1.0.0
 */
public class HudsonWinkApplication extends WinkApplication {

	public static final String[] DEFAULT_APPLICATION_RESOURCE_PACKAGES =
		{"com.marvelution.hudson.plugins.apiv2.resources", "com.marvelution.hudson.plugins.apiv2.resources.exceptions",
			"com.marvelution.hudson.plugins.apiv2.resources.exceptions.mappers",
			"com.marvelution.hudson.plugins.apiv2.resources.impl",
			"com.marvelution.hudson.plugins.apiv2.resources.model"};

	private static final Logger logger = Logger.getLogger(HudsonAPIV2ServletFilter.class.getName());

	private Set<Class<?>> jaxRSClasses = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Class<?>> getClasses() {
		if (jaxRSClasses == null) {
			jaxRSClasses = new HashSet<Class<?>>();
			final Set<Class<?>> classes = new HashSet<Class<?>>();
			for (String resourcePackageName : DEFAULT_APPLICATION_RESOURCE_PACKAGES) {
				try {
					final URL resourcePackage = FileLoader.loadFile(resourcePackageName.replace(".", "/"));
					for (String filename : new File(resourcePackage.toURI()).list()) {
						if (filename.endsWith(".class")) {
							final String className = filename.replace(".class", "");
							classes.add(Class.forName(resourcePackageName + "." + className));
						} else {
							logger.log(Level.FINE, "Ignoring [" + filename + "]; Its not a class file");
						}
					}
				} catch (FileNotFoundException e) {
					logger.log(Level.SEVERE, "Failed to load REST Resources in package: " + resourcePackageName, e);
				} catch (URISyntaxException e) {
					logger.log(Level.SEVERE, "Failed to load REST Resources in package: " + resourcePackageName, e);
				} catch (ClassNotFoundException e) {
					logger.log(Level.SEVERE, "Failed to load REST Resources in package: " + resourcePackageName, e);
				}
			}
			processClasses(classes);
			// TODO Load all JAX-RS classes from the plugins
		}
		return jaxRSClasses;
	}

	/**
	 * Process the given {@link Set} of {@link Class} objects and add them to the JAX-RS Class List if they are valid JAX_RS Classes
	 * 
	 * @param classes {@link Set} of {@link Class} objects to process
	 */
	private void processClasses(Set<Class<?>> classes) {
		for (Class<?> cls : classes) {
			if (ProviderMetadataCollector.isProvider(cls)) {
				jaxRSClasses.add(cls);
			} else if (ResourceMetadataCollector.isResource(cls)) {
				final Parent parent = (Parent) cls.getAnnotation(Parent.class);
				if ((parent != null && BaseRestResource.class.equals(parent.value())) || BaseRestResource.class.equals(cls)) {
					logger.info("Loaded REST Resource class [" + cls.getName() + "]");
					jaxRSClasses.add(cls);
				} else {
					logger.log(Level.FINE, "Class [" + cls.getName() + "] is not a valid REST Resource, "
						+ "the @Parent(RestBaseResource.class) annotation is missing");
				}
			} else {
				logger.log(Level.FINE, "Skipping class [" + cls.getName() + "]; Its not a REST Resource or Provider");
			}
		}
	}

}
