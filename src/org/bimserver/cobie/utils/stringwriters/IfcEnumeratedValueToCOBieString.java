package org.bimserver.cobie.utils.stringwriters;
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
import java.util.ArrayList;

import org.bimserver.models.ifc2x3.IfcPropertyEnumeratedValue;
import org.bimserver.models.ifc2x3.IfcPropertyEnumeration;
import org.bimserver.models.ifc2x3.IfcValue;
import org.eclipse.emf.common.util.EList;
public class IfcEnumeratedValueToCOBieString extends IfcPropertyToCOBieString 
{
	private String unitString;
	private ArrayList<String> enumeratedValuesStrings;
	private ArrayList<String> enumerationReferenceStrings;
	
	public String getUnitString() 
	{
		return unitString;
	}
	public IfcEnumeratedValueToCOBieString(IfcPropertyEnumeratedValue property) 
	{
		super(property);
		this.setUnitString(IfcUnitToCOBieString.stringFromUnit
				(property.getEnumerationReference().getUnit()));
		this.setEnumerationReferenceStrings(property.getEnumerationReference());
		this.setEnumeratedValuesStrings(property.getEnumerationValues());
		this.setUnitString(IfcUnitToCOBieString.stringFromUnit
				(property.getEnumerationReference().getUnit()));	
		String tmpVal = "";
		for(String valStr : this.getEnumeratedValuesStrings())
		{
			tmpVal += valStr + ",";
		}
		if (tmpVal.endsWith(","))
			tmpVal = tmpVal.substring(0,tmpVal.length()-1);
		this.setValueString(tmpVal);
		this.setPropertyTypeString(IfcPropertyEnumeratedValue.class.getSimpleName());
		// TODO Auto-generated constructor stub
	}
	
	
	
	private void setUnitString(String unitString) 
	{
		this.unitString = unitString;
	}
	public ArrayList<String> getEnumeratedValuesStrings() {
		return enumeratedValuesStrings;
	}
	private void setEnumeratedValuesStrings(EList<IfcValue> values) 
	{
		for(IfcValue val : values)
		{
			enumeratedValuesStrings.add(val.toString());
		}
	}
	public ArrayList<String> getEnumerationReferenceStrings()
	{
		return enumerationReferenceStrings;
	}
	private void setEnumerationReferenceStrings(IfcPropertyEnumeration ref) 
	{
		for(IfcValue val : ref.getEnumerationValues())
			enumerationReferenceStrings.add(val.toString());
		
	}

	public IfcEnumeratedValueToCOBieString() 
	{
		super();
		// TODO Auto-generated constructor stub
	}


}