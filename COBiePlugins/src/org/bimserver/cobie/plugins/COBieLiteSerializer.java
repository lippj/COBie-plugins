package org.bimserver.cobie.plugins;
/******************************************************************************
 * Copyright (C) 2011  ERDC
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.bimserver.cobie.cobielite.COBIEDocument;
import org.bimserver.cobie.cobielite.COBIEType;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcPersonOrganizationToContacts;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToAssembly;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToAttribute;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToComponent;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToConnection;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToContact;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToDocument;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToFacility;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToFloor;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToFloorCoordinates;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToJob;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToResource;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToSpace;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToSpaceCoordinates;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToSpare;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToSystem;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToType;
import org.bimserver.cobie.utils.serializer.sheetserializers.IfcToZone;
import org.bimserver.ifc.IfcModel;
import org.bimserver.models.ifc2x3tc1.IfcObjectDefinition;
import org.bimserver.models.ifc2x3tc1.IfcRelAssociates;
import org.bimserver.models.ifc2x3tc1.IfcRoot;
import org.bimserver.plugins.PluginManager;
import org.bimserver.plugins.ifcengine.IfcEngine;
import org.bimserver.plugins.serializers.EmfSerializer;
import org.bimserver.plugins.serializers.IfcModelInterface;
import org.bimserver.plugins.serializers.ProjectInfo;
import org.bimserver.plugins.serializers.SerializerException;
import org.bimserver.utils.UTF8PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author chrisbogen
 * This serializer class generates a flattened XML data view of COBie that is
 * independent of the typical spreadsheetML format.  This class is a superclass
 * of all serializers in the COBiePlugins project.  The Coordinate, Issue, and Impact
 *  portions of the COBie document are not yet implemented.
 */
public class COBieLiteSerializer extends EmfSerializer{
	private static final String DEFAULT_PROJECT_NAME = "Project Name Unknown";
	private static final String LOGGER_MESSAGE_EXCEPTION = "An exception was encountered while deriving COBie from the model:  ";
	private static final String LOGGER_MESSAGE_DONE = "End Extracting COBie from model server...";
	private static final String LOGGER_MESSAGE_BEGIN = "Begin Extracting COBie from model server...";
	protected String projectName;
	private boolean cobieLiteLoaded;
	private static final Logger LOGGER = LoggerFactory.getLogger(COBieLiteSerializer.class);
	private COBIEDocument COBie;
	private PrintWriter out;
	//private final File configurationFile;
	
	/*public COBieLiteSerializer(File configurationFile) {
		this.configurationFile = configurationFile;
	}*/
	
	protected String getLoggerPrefix()
	{
		return projectName+":  ";
	}
	
	public COBieLiteSerializer()
	{
		super();
	}

	@Override
	protected void reset() {
		setMode(Mode.BODY);
		this.out = null;
	}
	
	//public File getConfigurationFile() {
	//	return configurationFile;
	//}
	public void init(IfcModelInterface model, ProjectInfo projectInfo, PluginManager pluginManager, IfcEngine ifcEngine) throws SerializerException {
		super.init(model, projectInfo, pluginManager, ifcEngine);
		if(projectInfo==null)
			projectName = DEFAULT_PROJECT_NAME;
		else
			projectName = projectInfo.getName();
		init(model,projectInfo,pluginManager);
		setCobieLiteLoaded(false);
	}
	
	public void init(IfcModelInterface model, ProjectInfo projectInfo, 
			PluginManager pluginManager) throws SerializerException 
	{
		//super.init(model, projectInfo, pluginManager);		
		COBie = COBIEDocument.Factory.newInstance();
		initializeModelInverseSlots();
		setCobieLiteLoaded(false);
	}
	public void init(IfcModelInterface model,PluginManager pluginManager) throws SerializerException 
	{
		super.init(model, null, pluginManager, null);	
		COBie = COBIEDocument.Factory.newInstance();
		initializeModelInverseSlots();
		setCobieLiteLoaded(false);
	}
	
	public void init(COBIEDocument cobie) throws SerializerException 
	{
		//super.init(model, projectInfo, pluginManager);		
		COBie = cobie;
		projectName = DEFAULT_PROJECT_NAME;
		setCobieLiteLoaded(true);
	}

	public void init(PluginManager pluginManager,COBIEDocument cobie) throws SerializerException
	{
		IfcModel emptyModel =
				new IfcModel();
		super.init(model, null, pluginManager, null);
		COBie = cobie;
		projectName = DEFAULT_PROJECT_NAME;
		setCobieLiteLoaded(true);
	}
	public COBIEDocument getCOBieDocument()
	{
		return COBie;
	}
	
	private void initializeModelInverseSlots()
	{
		for(IfcRelAssociates assoc : model.getAllWithSubTypes(IfcRelAssociates.class))
		{
			for(IfcRoot relObj : assoc.getRelatedObjects())
			{
				if (relObj instanceof IfcObjectDefinition)
				{
					IfcObjectDefinition objDef = (IfcObjectDefinition) relObj;
					if (objDef.getHasAssociations().size()==0)
					{
						objDef.getHasAssociations().add(assoc);
					}
				}
			}
		}
	}
	
	
	/**
	 * Writes the COBieLite XML document to the outputstream
	 */
	private void writeCOBIE()
	{
		
		modelToCOBie();

		try {
				this.COBie.save(this.out);
		} catch (TransformerFactoryConfigurationError e) {
			LOGGER.error("", e);
		} /*catch (TransformerException e) {
					LOGGER.error("", e);
		}*/
		//this.out.print(COBie.xmlText());
 catch (IOException e) {
		LOGGER.error("", e);
		}
		finally
		{
		//THIS IS NOT A GOOD IDEA...for mixed use serializer, but was used to save memory on WebApplication use	this.COBie.setNil();
			System.gc();
			Runtime.getRuntime().gc();
		}
	}

	
	/**
	 * Parses the IFC model and populates a corresponding COBieLite XML Document
	 */
	public void modelToCOBie() 
	{
		if(!cobieLiteLoaded)
		{
			LOGGER.info(getLoggerPrefix()+ LOGGER_MESSAGE_BEGIN);
			this.writeContacts();
			this.writeFacilities();
			this.writeFloors();
			this.writeSpaces();
			this.writeZones();
			this.writeTypes();
			this.writeComponents();
			this.writeSystems();
			this.writeAssemblies();
			this.writeSpares();
			this.writeResources();
			this.writeJobs();
			this.writeConnections();
			this.writeDocuments();
			this.writeAttributes();
			this.writeSpaceCoordinates();
			this.writeFloorCoordinates();
			setCobieLiteLoaded(true);
			LOGGER.info(getLoggerPrefix()+LOGGER_MESSAGE_DONE);
		}
		
	}
	
	
	/**
	 * Parses IFC model and populates COBie Facilities
	 */
	private void writeFacilities()
	{
		COBIEType cType = this.GetCobie();
		cType = IfcToFacility.writeFacilitiesToCOBie(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Contacts
	 */
	private void writeContacts()
	{
		
		COBIEType cType = this.GetCobie();		
		COBIEType.Contacts contacts =
				cType.getContacts();
		if(contacts==null)
			contacts = cType.addNewContacts();
		IfcPersonOrganizationToContacts contactSerializer =
				new IfcPersonOrganizationToContacts(contacts, getModel());
		contactSerializer.serializeIfc();
	}
	
	
	/**
	 * Parses IFC model and populates COBie Floors
	 */
	private void writeFloors()
	{
		COBIEType cType = this.GetCobie();
		cType = IfcToFloor.writeFloorsToCOBie(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Spaces
	 */
	private void writeSpaces()
	{
		COBIEType cType = this.GetCobie();		
		cType = IfcToSpace.writeSpacesToCOBie(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Zones
	 */
	private void writeZones()
	{
		COBIEType cType = this.GetCobie();
		cType = IfcToZone.writeZonesToCOBie(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Types
	 */
	private void writeTypes()
	{
		COBIEType cType = this.GetCobie();
		//cType = IfcToType.writeTypesToCOBie(cType, model);
		cType = IfcToType.writeAllTypesToCOBie(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Components
	 */
	private void writeComponents()
	{
		COBIEType cType = this.GetCobie();
		cType = IfcToComponent.writeComponentsToCOBie(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Systems
	 */
	private void writeSystems()
	{
		COBIEType cType = this.GetCobie();
		cType = IfcToSystem.writeSystemsToCOBieComponentPerRow(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Assemblies
	 */
	private void writeAssemblies()
	{
		COBIEType cType = this.GetCobie();
		IfcToAssembly.writeAssembliesToCOBie(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Connections
	 */
	private void writeConnections()
	{
		COBIEType cType = this.GetCobie();
		IfcToConnection.writeConnections(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Documents
	 */
	private void writeDocuments()
	{
		COBIEType cType = this.GetCobie();
		IfcToDocument.writeDocument(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Attributes
	 */
	private void writeAttributes()
	{
		COBIEType cType = this.GetCobie();
		IfcToAttribute.writeAttributes(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Spares
	 */
	private void writeSpares()
	{
		COBIEType cType = this.GetCobie();
		IfcToSpare.writeSpares(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Resources
	 */
	private void writeResources()
	{
		COBIEType cType = this.GetCobie();
		IfcToResource.writeResources(cType, model);
	}
	
	/**
	 * Parses IFC model and populates COBie Jobs
	 */
	private void writeJobs()
	{
		COBIEType cType = this.GetCobie();
		IfcToJob.writeJobs(cType, model);
	}
	
	private void writeSpaceCoordinates()
	{
		COBIEType cType = this.GetCobie();
		COBIEType.Coordinates coordinates;
		if(cType.getCoordinates()==null)
		{
			coordinates = cType.addNewCoordinates();
		}
		else
			coordinates = cType.getCoordinates();
		IfcToSpaceCoordinates spaceSerializer =
				new IfcToSpaceCoordinates(coordinates, getModel());
		spaceSerializer.serializeIfc();
	}
	
	private void writeFloorCoordinates()
	{
		COBIEType cType = this.GetCobie();
		COBIEType.Coordinates coordinates;
		if(cType.getCoordinates()==null)
		{
			coordinates = cType.addNewCoordinates();
		}
		else
			coordinates = cType.getCoordinates();
		IfcToFloorCoordinates floorCoordinateSerializer =
				new IfcToFloorCoordinates(coordinates, getModel());
		floorCoordinateSerializer.serializeIfc();
	}
	
	/**
	 * Returns the COBIEType of the current COBIE Document.  If one does not exist then returns
	 * a new one.
	 * 
	 * @return Returns the COBIEType of the current COBIE DOcument.  If one does not exist then
	 * returns a new one.
	 */
	protected COBIEType GetCobie()
	{
		COBIEType cType;
		if (this.COBie.getCOBIE()==null)
			cType = this.COBie.addNewCOBIE();
		else
			cType = this.COBie.getCOBIE();
		return cType;
	}
	
	@Override
	public boolean write(OutputStream outputStream) throws SerializerException {
		if (out == null) 
		{
			this.out = new UTF8PrintWriter(outputStream);
		}
		
		if (getMode() == Mode.BODY) 
		{
			//this.out = new UTFPrintWriter(out);
			try
			{
				writeCOBIE();
				this.out.flush();
				setMode(Mode.FINISHED);
				return true;
			}
			catch (Exception e)
			{
				this.out.flush();
				setMode(Mode.FINISHED);
				LOGGER.error(getLoggerPrefix()+LOGGER_MESSAGE_EXCEPTION+e.getMessage());
				return false;
			}



		} 
		else if (getMode() == Mode.FINISHED) 
		{
			return false;
		}
		return false;
		
	}
	

	
	public void write(File file) throws FileNotFoundException, SerializerException {
		write(new FileOutputStream(file));
	}

	public boolean isCobieLiteLoaded()
	{
		return cobieLiteLoaded;
	}

	protected void setCobieLiteLoaded(boolean cobieLiteLoaded)
	{
		this.cobieLiteLoaded = cobieLiteLoaded;
	}

}