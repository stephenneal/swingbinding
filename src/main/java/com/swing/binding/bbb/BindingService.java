package com.swing.binding.bbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingListener;

/**
 * Manage bindings. Provides a mechanism to release all bindings that are managed by this instance. It is also possible
 * to release bindings for a single source object. Bindings should be released to guard against memory leaks.
 * 
 * @author Stephen Neal
 * @since 11/04/2013
 */
public class BindingService {

    private static final Logger LOGGER = Logger.getLogger(BindingService.class);
    private Map<Object, List<Binding<?, ?, ?, ?>>> bindingMap;
    private boolean released;

    /**
     * Default constructor.
     */
    public BindingService() {
        super();
        this.released = false;
        this.bindingMap = new HashMap<Object, List<Binding<?, ?, ?, ?>>>(50);
    }

    /**
     * Invokes {@link Binding#bind()} and adds the binding to the list of bindings to manage.
     * 
     * @param binding binding
     */
    public void bind(final Binding<?, ?, ?, ?> binding) {
        if (binding == null) {
            return;
        }
        // Synchronise to prevent binding during or after release
        synchronized (this) {
            if (this.released) {
                throw new IllegalStateException("cannot add bindings after the instance is released");
            }
            Object key = binding.getSourceObject();
            List<Binding<?, ?, ?, ?>> value = this.bindingMap.get(key);
            if (value == null) {
                value = new ArrayList<Binding<?, ?, ?, ?>>(50);
                value.add(binding);
                this.bindingMap.put(key, value);
            } else {
                value.add(binding);
            }
        }
        binding.bind();
    }

    /**
     * Release all bindings managed by this instance.
     */
    public void release() {
        // Synchronise to prevent binding during or after release
        synchronized (this) {
            if (this.released) {
                return;
            }
            LOGGER.debug("releasing binding service");
            Iterator<Entry<Object, List<Binding<?, ?, ?, ?>>>> itr = this.bindingMap.entrySet().iterator();
            Entry<Object, List<Binding<?, ?, ?, ?>>> e = null;
            while (itr.hasNext()) {
                e = itr.next();
                BindingService.release(e.getKey(), e.getValue());
                itr.remove();
            }
            this.released = true;
        }
    }

    /**
     * Release all bindings for a bean instance.
     */
    public void release(Object bean) {
        if (bean == null) {
            return;
        }
        // Synchronise to prevent binding during this release
        synchronized (this) {
            if (this.released) {
                return;
            }
            LOGGER.debug("releasing bean: " + bean);
            BindingService.release(bean, this.bindingMap.get(bean));
            this.bindingMap.remove(bean);
        }
    }

    /**
     * Release all bindings for a bean instance.
     */
    static void release(Object bean, List<Binding<?, ?, ?, ?>> bindings) {
        if (bean == null) {
            return;
        }
        BindingService.release(bindings);
    }

    /**
     * Release a list of bindings.
     */
    static void release(List<Binding<?, ?, ?, ?>> bindings) {
        if (bindings == null) {
            return;
        }
        Iterator<Binding<?, ?, ?, ?>> itr = bindings.iterator();
        Binding<?, ?, ?, ?> b = null;
        while (itr.hasNext()) {
            b = itr.next();
            BindingService.release(b);
            itr.remove();
        }
    }

    /**
     * Release a binding.
     */
    static void release(Binding<?, ?, ?, ?> b) {
        if (b == null) {
            return;
        }
        if (b.isBound()) {
            b.unbind();
        }
        if (b.getBindingListeners() != null) {
            for (BindingListener l : b.getBindingListeners()) {
                b.removeBindingListener(l);
            }
        }
    }

}
