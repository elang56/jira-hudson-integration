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

package com.marvelution.jira.plugins.hudson.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.marvelution.jira.plugins.hudson.rest.model.Option;
import com.marvelution.jira.plugins.hudson.services.associations.HudsonAssociation;
import com.marvelution.jira.plugins.hudson.services.associations.HudsonAssociationManager;
import com.marvelution.jira.plugins.hudson.services.servers.HudsonServer;
import com.marvelution.jira.plugins.hudson.services.servers.HudsonServerManager;


/**
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@Path("/associations")
@AnonymousAllowed
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class HudsonAssociationsRestResource {

	private HudsonAssociationManager associationManager;
	private HudsonServerManager serverManager;

	/**
	 * Default Constructor
	 * 
	 * @param serverManager the {@link HudsonServerManager}
	 * @param associationManager the {@link HudsonAssociationManager}
	 */
	public HudsonAssociationsRestResource(HudsonServerManager serverManager, HudsonAssociationManager associationManager) {
		this.serverManager = serverManager;
		this.associationManager = associationManager;
	}

	@GET
	public Response getAssociations() {
		final Collection<Option> options = new ArrayList<Option>();
		for (HudsonAssociation association : associationManager.getAssociations()) {
			final HudsonServer server = serverManager.getServer(association.getServerId());
			final String label = association.getJobName() + " (" + server.getName() + ")";
			options.add(new Option(label, String.valueOf(association.getAssociationId())));
		}
		return Response.ok(new Associations(options)).build();
	}

	/**
	 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlRootElement
	public static class Associations {

		private static final ToStringStyle TO_STRING_STYLE = ToStringStyle.SIMPLE_STYLE;

		@XmlElement
		private Collection<Option> associations;

		/**
		 * Default Constructor
		 */
		public Associations() {
		}

		/**
		 * Constructor
		 * 
		 * @param associations the {@link Collection} of associations
		 */
		public Associations(Collection<Option> associations) {
			this.associations = associations;
		}

		/**
		 * Getter for associations
		 * 
		 * @return the associations
		 */
		public Collection<Option> getAssociations() {
			return associations;
		}

		/**
		 * Setter for associations
		 * 
		 * @param associations the associations to set
		 */
		public void setAssociations(Collection<Option> associations) {
			this.associations = associations;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object object) {
			return EqualsBuilder.reflectionEquals(this, object);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, Associations.TO_STRING_STYLE);
		}

	}

}
