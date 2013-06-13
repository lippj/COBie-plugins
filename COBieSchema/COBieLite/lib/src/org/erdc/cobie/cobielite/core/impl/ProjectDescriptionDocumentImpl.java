/*
 * An XML document type.
 * Localname: ProjectDescription
 * Namespace: http://core.cobielite.cobie.erdc.org
 * Java type: org.erdc.cobie.cobielite.core.ProjectDescriptionDocument
 *
 * Automatically generated - do not modify.
 */
package org.erdc.cobie.cobielite.core.impl;
/**
 * A document containing one ProjectDescription(@http://core.cobielite.cobie.erdc.org) element.
 *
 * This is a complex type.
 */
public class ProjectDescriptionDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.erdc.cobie.cobielite.core.ProjectDescriptionDocument
{
    private static final long serialVersionUID = 1L;
    
    public ProjectDescriptionDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PROJECTDESCRIPTION$0 = 
        new javax.xml.namespace.QName("http://core.cobielite.cobie.erdc.org", "ProjectDescription");
    
    
    /**
     * Gets the "ProjectDescription" element
     */
    public java.lang.String getProjectDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROJECTDESCRIPTION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ProjectDescription" element
     */
    public org.erdc.cobie.cobielite.core.CobieDescriptionSimpleType xgetProjectDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.erdc.cobie.cobielite.core.CobieDescriptionSimpleType target = null;
            target = (org.erdc.cobie.cobielite.core.CobieDescriptionSimpleType)get_store().find_element_user(PROJECTDESCRIPTION$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ProjectDescription" element
     */
    public void setProjectDescription(java.lang.String projectDescription)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROJECTDESCRIPTION$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROJECTDESCRIPTION$0);
            }
            target.setStringValue(projectDescription);
        }
    }
    
    /**
     * Sets (as xml) the "ProjectDescription" element
     */
    public void xsetProjectDescription(org.erdc.cobie.cobielite.core.CobieDescriptionSimpleType projectDescription)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.erdc.cobie.cobielite.core.CobieDescriptionSimpleType target = null;
            target = (org.erdc.cobie.cobielite.core.CobieDescriptionSimpleType)get_store().find_element_user(PROJECTDESCRIPTION$0, 0);
            if (target == null)
            {
                target = (org.erdc.cobie.cobielite.core.CobieDescriptionSimpleType)get_store().add_element_user(PROJECTDESCRIPTION$0);
            }
            target.set(projectDescription);
        }
    }
}
