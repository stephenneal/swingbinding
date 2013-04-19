package com.swing.binding.bbb;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.beansbinding.Binding;

@SuppressWarnings("serial")
public abstract class AbstractPanel<M extends AbstractPanelModel> extends JPanel {

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
        if (model != null) {
            // TODO replace with binding for title and border
            TitleListener listener = new TitleListener();
            getModel().addPropertyChangeListener("title", listener);
            listener.propertyChange(new PropertyChangeEvent(model, "title", null, model.getTitle()));
        }
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

    private class TitleListener implements PropertyChangeListener {
        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Border border = getBorder();
                    TitledBorder t = null;
                    if (border instanceof TitledBorder) {
                        t = (TitledBorder) border;
                    } else if (border instanceof CompoundBorder) {
                        CompoundBorder c = (CompoundBorder) border;
                        if (c.getOutsideBorder() instanceof TitledBorder) {
                            t = (TitledBorder) c.getOutsideBorder();
                        } else if (c.getInsideBorder() instanceof TitledBorder) {
                            t = (TitledBorder) c.getInsideBorder();
                        }
                    }
                    if (t != null) {
                        t.setTitle((String) evt.getNewValue());
                    }
                }
            });
        }
    }
}
