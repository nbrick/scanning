/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.scanning.api.event.queues.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * PositionerAtom is a type of {@link QueueAtom} which may be processed within 
 * an active-queue of an {@link IQueueService}. It contains all the 
 * configuration necessary to create an {@link IPositioner} which is used to 
 * set the positions of one or more positioners/motors. Positioner moves may 
 * occur  simultaneously, depending on the configured levels.
 * 
 * @author Michael Wharmby
 *
 */
public class PositionerAtom extends QueueAtom {
	
	/**
	 * Version ID for serialization. Should be updated when class changed. 
	 */
	private static final long serialVersionUID = 20161021L;
	
	private Map<String, Object> positionerConfig;
	
	/**
	 * No arg constructor for JSON
	 */
	public PositionerAtom() {
		super();
	}
	
	/**
	 * Constructor with required arguments to configure one positioner.
	 * 
	 * @param posShrtNm String short name used within the QueueBeanFactory
	 * @param positionDev String name of positioner to set.
	 * @param target Object representing the target position.
	 */
	public PositionerAtom(String posShrtNm, String positionDev, Object target) {
		super();
		setShortName(posShrtNm);
		positionerConfig = new LinkedHashMap<String, Object>();
		positionerConfig.put(positionDev, target);
	}
	
	/**
	 * Constructor with required arguments for multiple motor positions.
	 * 
	 * @param posShrtNm String short name used within the QueueBeanFactory
	 * @param positionerConfig Map of form String positionerDev name Object 
	 *                         target position.
	 */
	public PositionerAtom(String posShrtNm, Map<String, Object> positionerConfig) {
		super();
		setShortName(posShrtNm);
		this.positionerConfig = positionerConfig;
	}
	
	/**
	 * Return all the names of the positioners controlled by this 
	 * PositionerAtom.
	 * 
	 * @return List of String names of the positioners in the configuration.
	 */
	public List<String> getPositionerNames() {
		return new ArrayList<String>(positionerConfig.keySet());
	}
	
	/**
	 * Return the target to which this positioner will be set.
	 * 
	 * @param positionDev String name of positioner to set.
	 * @return Object representing the target position.
	 */
	public Object getPositioner(String positionDev) {
		return positionerConfig.get(positionDev);
	}
	
	/**
	 * Change or add a new positioner to be set by this PositionerAtom.
	 * 
	 * @param positionDev String name of positioner to set.
	 * @param target Object representing the target position.
	 */
	public void addPositioner(String positionDev, Object target) {
		positionerConfig.put(positionDev, target);
	}
	
	/**
	 * Remove a positioner from the configuration of this PositionerAtom.
	 * 
	 * @param positionDev String name of positioner to remove.
	 */
	public void removePositioner(String positionDev) {
		positionerConfig.remove(positionDev);
	}
	
	/**
	 * Report the number of positioners which are set by this PositionerAtom.
	 * 
	 * @return int number of positioners in the configuration.
	 */
	public int nPositioners() {
		return positionerConfig.size();
	}

	/**
	 * Return complete set of positioner names and target positions.
	 * 
	 * @return Map<String, Object> String key name of motor and Object target.
	 */
	public Map<String, Object> getPositionerConfig() {
		return positionerConfig;
	}

	/**
	 * Change the complete set of positioner names and target positions.
	 * 
	 * @param positionerConfig Map<String, Object> String key positionDev and 
	 *                         Object target.
	 */
	public void setPositionerConfig(Map<String, Object> positionerConfig) {
		this.positionerConfig = positionerConfig;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((positionerConfig == null) ? 0 : positionerConfig.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionerAtom other = (PositionerAtom) obj;
		if (positionerConfig == null) {
			if (other.positionerConfig != null)
				return false;
		} else if (!positionerConfig.equals(other.positionerConfig))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String clazzName = this.getClass().getSimpleName();
		
		String positConf = "{";
		for (Map.Entry<String, Object> poserCfg : positionerConfig.entrySet()) {
			positConf = positConf+poserCfg.getKey()+" : "+poserCfg.getValue()+", ";
		}
		positConf = positConf.replaceAll(", $", "}"); //Replace trailing ", "
		
		return clazzName + " [name=" + name + " (shortName="+shortName+"), positionerConfig=" + positConf 
				+ ", status=" + status + ", message=" + message + ", percentComplete=" + percentComplete 
				+ ", previousStatus=" + previousStatus + ", runTime=" + runTime + ", userName=" + userName 
				+ ", hostName=" + hostName + ", beamline="+ beamline + ", submissionTime=" + submissionTime 
				+ ", properties=" + getProperties() + ", id=" + getUniqueId() + "]";
	}

}
