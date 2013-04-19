package com.swing.binding.bbb;

import javax.swing.JPanel;

import org.jdesktop.beansbinding.Binding;

@SuppressWarnings("serial")
public abstract class AbstractPanel<M> extends JPanel {

    private BindingService bindingService;
    private M model;

    /**
     * Default constructor. Use this when binding is not to be used, any calls to {@link #bind(Binding)} on this
     * instance will throw an {@link UnsupportedOperationException}.
     */
    public AbstractPanel(M model) {
        super();
    }

    /**
     * Constructor that set the binding service.
     */
    public AbstractPanel(M model, BindingService bindingService) {
        super();
        this.model = model;
        this.bindingService = bindingService;
    }

    protected void bind(Binding<?, ?, ?, ?> binding) {
        if (this.bindingService == null) {
            throw new UnsupportedOperationException("binding not supported, no binding service");
        }
        if (binding == null) {
            return;
        }
        this.bindingService.bind(binding);
    }

    public M getModel() {
        return this.model;
    }

}
