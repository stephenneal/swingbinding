/**
 * Copyright (C) 2011 Stephen Neal
 */
package com.swing.binding.bbb;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.swingbinding.SwingBindings;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swing.binding.TestBean;
import com.swing.binding.bbb.BindingService;

/**
 * Test the performance of {@link BindingService}.
 * <p>
 * This does not test the class in isolation (as per a unit test), it tests with real bindings (BetterBeansBinding).
 * </p>
 * 
 * @author Stephen Neal
 * @since 11/04/2013
 */
public class BindingServicePerformanceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BindingServicePerformanceTest.class);

    private static final int BEAN_COUNT = 200;
    private static final int BINDINGS_PER_BEAN = 100;

    // TODO make this a meaningful test, not just logging times!
    @Ignore
    @Test
    public void test() {
        BindingService managerMap = new BindingService();

        List<Binding<?, ?, ?, ?>> bindingsForMap = new ArrayList<Binding<?, ?, ?, ?>>(2000);
        // Create bindings for a number of different bean instances
        TestBean bean1 = new TestBean();
        for (int i = 0; i < BEAN_COUNT; i++) {
            if (i == 0) {
                createBindings(bean1, bindingsForMap, BINDINGS_PER_BEAN);
            } else {
                createBindings(new TestBean(), bindingsForMap, BINDINGS_PER_BEAN);
            }
        }

        int bindingCount = bindingsForMap.size();

        long start = System.currentTimeMillis();
        for (Binding<?, ?, ?, ?> b : bindingsForMap) {
            managerMap.bind(b);
        }
        long stop = System.currentTimeMillis() - start;
        LOGGER.info("MAP bind(" + bindingCount + ") took: " + stop + "ms");
        long mapTime = stop;

        start = System.currentTimeMillis();
        managerMap.release(bean1);
        stop = System.currentTimeMillis() - start;
        LOGGER.info("MAP release bean1 took: " + stop + "ms");
        mapTime += stop;

        start = System.currentTimeMillis();
        managerMap.release();
        stop = System.currentTimeMillis() - start;
        LOGGER.info("MAP release took: " + stop + "ms");
        mapTime += stop;

        LOGGER.info("\n");
        LOGGER.info("Total: " + mapTime + "ms");
    }

    private static void createBindings(TestBean bean, List<Binding<?, ?, ?, ?>> b, int count) {
        int stop = b.size() + count;
        // It is not normal to create more than one binding for a given component but for the simplicity of this test
        // it should not matter.
        while (b.size() < stop) {
            b.add(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, bean, BeanProperty.create("string"),
                            new JTextField(), BeanProperty.create("text")));
            BeanProperty<TestBean, List<String>> bP = BeanProperty.create("stringList");
            b.add(SwingBindings.createJComboBoxBinding(UpdateStrategy.READ_WRITE, bean, bP, new JComboBox()));
        }
    }
}
