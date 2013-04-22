package com.swing.binding;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jdesktop.beansbinding.Property;
import org.jdesktop.observablecollections.ObservableList;

import com.swing.binding.bbb.mvc.PresentationModel;

public class TestBean extends PresentationModel {

    /**
     * Defines properties that can be bound on this model.
     */
    public static class Properties extends PresentationModel.Properties {
        public static Property<TestBean, Date> DATE = create("date");
        public static Property<TestBean, Double> DOUBLE = create("duble");
        public static Property<TestBean, Integer> INTEGER = create("integr");
        public static Property<TestBean, String> STRING = create("string");
        public static Property<TestBean, List<String>> STRING_LIST = create("stringList");
        public static Property<TestBean, List<TestBean>> TEST_BEANS = create("testBeans");
        public static Property<TestBean, List<TestBean>> TEST_BEANS_SELECTED = create("testBeansSelected");
    }

    private Date date;
    private Double duble;
    private Integer integr;
    private ObservableList<String> stringList;
    private String string;
    private ObservableList<TestBean> testBeans;
    private ObservableList<TestBean> testBeansSelected;

    private boolean statePrimitive;
    private Boolean state;

    public TestBean() {
        super();
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date newValue) {
        Date oldValue = this.date;
        this.date = newValue;
        getPropertyChangeSupport().firePropertyChange("date", oldValue, newValue);
    }

    public Double getDuble() {
        return this.duble;
    }

    public void setDuble(Double newValue) {
        Double oldValue = this.duble;
        this.duble = newValue;
        getPropertyChangeSupport().firePropertyChange("duble", oldValue, newValue);
    }

    public Integer getIntegr() {
        return this.integr;
    }

    public void setIntegr(Integer newValue) {
        Integer oldValue = this.integr;
        this.integr = newValue;
        getPropertyChangeSupport().firePropertyChange("integr", oldValue, newValue);
    }

    public ObservableList<String> getStringList() {
        return this.stringList;
    }

    public void setStringList(ObservableList<String> newValue) {
        ObservableList<String> oldValue = this.stringList;
        this.stringList = newValue;
        getPropertyChangeSupport().firePropertyChange("stringList", oldValue, newValue);
    }

    public String getString() {
        return this.string;
    }

    public void setString(String newValue) {
        String oldValue = this.string;
        this.string = newValue;
        getPropertyChangeSupport().firePropertyChange("string", oldValue, newValue);
    }

    public ObservableList<TestBean> getTestBeans() {
        return this.testBeans;
    }

    public void setTestBeans(ObservableList<TestBean> newValue) {
        ObservableList<TestBean> oldValue = this.testBeans;
        this.testBeans = newValue;
        getPropertyChangeSupport().firePropertyChange("testBeans", oldValue, newValue);
    }

    public ObservableList<TestBean> getTestBeansSelected() {
        return this.testBeansSelected;
    }

    public boolean isStatePrimitive() {
        return this.statePrimitive;
    }

    public void setStatePrimitive(boolean newValue) {
        boolean oldValue = this.statePrimitive;
        this.statePrimitive = newValue;
        getPropertyChangeSupport().firePropertyChange("statePrimitive", oldValue, newValue);
    }

    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean newValue) {
        Boolean oldValue = this.state;
        this.state = newValue;
        getPropertyChangeSupport().firePropertyChange("state", oldValue, newValue);
    }

    public void setTestBeansSelected(ObservableList<TestBean> newValue) {
        ObservableList<TestBean> oldValue = this.testBeansSelected;
        this.testBeansSelected = newValue;
        getPropertyChangeSupport().firePropertyChange("testBeansSelected", oldValue, newValue);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE, false);
    }

}
