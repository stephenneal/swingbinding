package com.swing.binding.bbb;

import com.swing.binding.bbb.AbstractModel;

/**
 * Model for demos that has support for title.
 * 
 * @author Stephen Neal
 * @since 10/04/2013
 */
public class AbstractPanelModel extends AbstractModel {

    private String title;

    protected AbstractPanelModel() {
        super();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String newValue) {
        String oldValue = this.title;
        this.title = newValue;
        getPropertyChangeSupport().firePropertyChange("title", oldValue, newValue);
    }

}
