package com.ygg.webapp.entity;

public class OrderSourceChannel extends BaseEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String name;
    
    private String responsibilityPerson;
    
    private String mark;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getResponsibilityPerson()
    {
        return responsibilityPerson;
    }
    
    public void setResponsibilityPerson(String responsibilityPerson)
    {
        this.responsibilityPerson = responsibilityPerson;
    }
    
    public String getMark()
    {
        return mark;
    }
    
    public void setMark(String mark)
    {
        this.mark = mark;
    }
    
}
