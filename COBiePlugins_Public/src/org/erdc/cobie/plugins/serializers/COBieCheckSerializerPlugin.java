package org.erdc.cobie.plugins.serializers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bimserver.models.store.ObjectDefinition;
import org.bimserver.plugins.Plugin;
import org.bimserver.plugins.PluginException;
import org.bimserver.plugins.PluginManager;
import org.bimserver.plugins.schema.SchemaPlugin;
import org.bimserver.plugins.serializers.EmfSerializer;
import org.bimserver.plugins.serializers.SerializerPlugin;
import org.erdc.cobie.plugins.utils.ConfigUtil;
import org.erdc.cobie.shared.COBieSharedUtilities;
import org.erdc.cobie.shared.enums.COBieSerializerPluginName;

public class COBieCheckSerializerPlugin implements SerializerPlugin 
{
public static final String DEFAULT_SERIALIZER_NAME = COBieSerializerPluginName.COBIE_CONSTRUCTIONQC.toString();
private boolean initialized = false;
private static final String SCHEMATRON_RULEPATH =
"lib/COBieRules.sch";
private static final String PRE_PROCESSOR_PATH =
"lib/iso_svrl_for_xslt2.xsl";
private static final String SVRL_XSD_PATH =
"lib/SVRL.xsd";
private static final String SVRL_HTML_XSLT_PATH =
"lib/SVRL_HTML(Construction).xslt";
private static final String CSS_PATH=
"lib/SpaceReport.css";
private static final String SCHEMATRON_SAXON_SKELETON_PATH="lib/iso_schematron_skeleton_for_saxon.xsl";
private ArrayList<String> configFilePaths;
private HashMap<String,File> configFiles;
	@Override
	public String getDescription() {
		return "Produces a report of compliance to a COBie ruleset.";
	}

	
	public String getName() {
		return getClass().getName();
	}

	@Override
	public String getVersion() {
		return "1.0";
	}


	
	@Override
	public void init(PluginManager pluginManager) throws PluginException {
		configFilePaths = new ArrayList<String>();
		configFilePaths.add(SCHEMATRON_RULEPATH);
		configFilePaths.add(PRE_PROCESSOR_PATH);
		configFilePaths.add(SCHEMATRON_SAXON_SKELETON_PATH);
		configFilePaths.add(SVRL_HTML_XSLT_PATH);
		configFilePaths.add(SVRL_XSD_PATH);
		configFilePaths.add(this.CSS_PATH);
		pluginManager.requireSchemaDefinition();
		try
		{
			this.configFiles = ConfigUtil.prepareSerializerConfigFiles(pluginManager, getDefaultName(), this, configFilePaths);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			throw new PluginException("Could not find configuration files");
		}
		initialized = true;
	}

	
	//i am unsure of what this function does 
	public Set<Class<? extends Plugin>> getRequiredPlugins() {
		Set<Class<? extends Plugin>> set = new HashSet<Class<? extends Plugin>>();
		set.add(SchemaPlugin.class);
		return set;
	}

	@Override
	public EmfSerializer createSerializer() {
		return new org.erdc.cobie.plugins.serializers.COBieCheckSerializer
				(this.configFiles.get(SCHEMATRON_RULEPATH).getAbsolutePath(),
						this.configFiles.get(PRE_PROCESSOR_PATH).getAbsolutePath(),
						this.configFiles.get(SVRL_XSD_PATH).getAbsolutePath(),
						this.configFiles.get(SVRL_HTML_XSLT_PATH).getAbsolutePath(),
						this.configFiles.get(CSS_PATH).getAbsolutePath()
						);
	}
	


	///////////////////////////////
	///need to fix these content and extension functions.
	///had to use html to get it to work
	@Override
	public String getDefaultContentType() {
		return "appliction/html";
	}

	@Override
	public String getDefaultExtension() {
		//return "xml";//Change this to proper extension
		return "html";
	}
 /////////////////////////////////////////////////
	@Override
	public boolean isInitialized() {
		return initialized;
	}


	@Override
	public String getDefaultName()
	{
		return DEFAULT_SERIALIZER_NAME;
	}


	@Override
	public ObjectDefinition getSettingsDefinition()
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean needsGeometry()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
