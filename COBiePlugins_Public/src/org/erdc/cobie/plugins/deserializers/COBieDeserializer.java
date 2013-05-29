package org.erdc.cobie.plugins.deserializers;

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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.XFactory;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.lex.ExcelReader;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.deserializers.DeserializeException;
import org.bimserver.plugins.deserializers.EmfDeserializer;
import org.bimserver.plugins.schema.SchemaDefinition;
import org.erdc.cobie.plugins.serializers.COBieCheckSerializerPlugin;
import org.erdc.cobie.shared.COBieSheetXMLDataProducable;
import org.erdc.cobie.shared.deserializer.sheetxmldata.COBieIfcModel;
import org.erdc.cobie.shared.deserializer.sheetxmldata.FromCOBieToIfc;
import org.erdc.cobie.sheetxmldata.COBIEDocument;
import org.erdc.cobie.sheetxmldata.COBIEType;
import org.erdc.cobie.sheetxmldata.parsers.spreadsheetml.SpreadsheetParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class COBieDeserializer extends EmfDeserializer implements
		COBieSheetXMLDataProducable
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(COBieDeserializer.class);
	private String templateFilePath;
	private ExcelReader reader = null;
	private Workbook xlWorkbook = null;
	private COBIEDocument COBie;
	private final File configurationFile;
	private static final String LOGGER_STATUS_SPREADSHEET_BEGIN_MSG = "Deserializing COBie SpreadsheetML to COBie Sheet XML Data.";
	private static final String LOGGER_STATUS_SPREADSHEET_END_MSG = "COBie SpreadsheetML converted to COBie Sheet XML Data.";

	public COBieDeserializer(File configurationFile,
			File preImportComplianceReportDirectory,
			COBieCheckSerializerPlugin checkSerializer)
	{
		this.configurationFile = configurationFile;
	}

	private COBIEType GetCobie()
	{
		COBIEType cType;
		if (COBie.getCOBIE() == null)
		{
			cType = COBie.addNewCOBIE();
		}
		else
		{
			cType = COBie.getCOBIE();
		}
		return cType;
	}

	private Workbook getWorkBookFromInputStream(InputStream in)
	{
		Workbook cobieWorkBook = null;
		try
		{
			InputSource inSrc = new InputSource(in);
			// inSrc.setEncoding("UTF-8");
			// inSrc.setSystemId("");
			nl.fountain.xelem.lex.ExcelReader rdr = new nl.fountain.xelem.lex.ExcelReader();
			cobieWorkBook = rdr.getWorkbook(inSrc);
		}
		catch (ParserConfigurationException e)
		{
			LOGGER.error("", e);
		}
		catch (SAXException e)
		{
			LOGGER.error("", e);
		}
		catch (IOException e)
		{
			LOGGER.error("", e);
		}
		return cobieWorkBook;

	}

	@Override
	public void init(SchemaDefinition schema)
	{
		// TODO Auto-generated method stub
	}

	public void initCOBie(File incomingFile) throws IOException
	{
		COBie = COBIEDocument.Factory.newInstance();
		templateFilePath = configurationFile.getAbsolutePath();

		try
		{
			XFactory.setConfigurationFileName(templateFilePath);
			try
			{
				reader = new ExcelReader();
				xlWorkbook = reader.getWorkbook(templateFilePath);
			}
			catch (ParserConfigurationException e)
			{
				LOGGER.error("", e);
			}
			catch (SAXException e)
			{
				LOGGER.error("", e);
			}
			xlWorkbook = getWorkBookFromInputStream(new FileInputStream(
					incomingFile));
			populateCobieDocument();
			try
			{
				// File complianceFile = new
				// File(preImportComplianceDirectory,filename2.replace("xml",
				// "html"));
				// FileOutputStream complianceFileOutput =
				// new FileOutputStream (complianceFile);
				// handlePreImportComplianceReport(complianceFileOutput);
			}
			catch (Exception e)
			{

			}
		}
		catch (IOException e)
		{
			LOGGER.error("", e);
		}
	}

	// general initialize function that any of the constructors will call
	public void initCOBie(InputStream in, String filename2) throws IOException
	{
		COBie = COBIEDocument.Factory.newInstance();
		templateFilePath = configurationFile.getAbsolutePath();

		try
		{
			XFactory.setConfigurationFileName(templateFilePath);
			try
			{
				reader = new ExcelReader();
				xlWorkbook = reader.getWorkbook(templateFilePath);
			}
			catch (ParserConfigurationException e)
			{
				LOGGER.error("", e);
			}
			catch (SAXException e)
			{
				LOGGER.error("", e);
			}
			xlWorkbook = getWorkBookFromInputStream(in);
			populateCobieDocument();
			try
			{
				// File complianceFile = new
				// File(preImportComplianceDirectory,filename2.replace("xml",
				// "html"));
				// FileOutputStream complianceFileOutput =
				// new FileOutputStream (complianceFile);
				// handlePreImportComplianceReport(complianceFileOutput);
			}
			catch (Exception e)
			{

			}
		}
		catch (IOException e)
		{
			LOGGER.error("", e);
		}
	}

	/*
	 * TODO: 1: Ignore first row in in sheet to avoid re-writing the column
	 * names 2: Check to make sure data is being written to the right column 3:
	 * Parallelize this function
	 */
	private void populateCobieDocument()
	{
		LOGGER.info(LOGGER_STATUS_SPREADSHEET_BEGIN_MSG);

		SpreadsheetParser parser = new SpreadsheetParser(xlWorkbook, GetCobie());
		parser.parse();
		
		LOGGER.info(LOGGER_STATUS_SPREADSHEET_END_MSG);
	}

	@Override
	public IfcModelInterface read(InputStream in, String filename, long fileSize)
			throws DeserializeException
	{
		COBieIfcModel cobieModel = null;
		try
		{
			initCOBie(in, filename);
			FromCOBieToIfc cobieTransform;
			cobieTransform = new FromCOBieToIfc(GetCobie());
			cobieModel = cobieTransform.getModelFromCOBie();
		}
		catch (Exception e)
		{
			throw new DeserializeException(e);
		}

		return cobieModel;

	}

	@Override
	public COBIEDocument toCOBieSheetXMLData(File incomingFile)
			throws Exception
	{
		try
		{
			initCOBie(incomingFile);
		}
		catch (Exception ex)
		{
			throw ex;
		}

		return COBie;
	}

}
