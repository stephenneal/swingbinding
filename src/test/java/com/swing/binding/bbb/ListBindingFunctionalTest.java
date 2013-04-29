package com.swing.binding.bbb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.JTableBinding;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swing.test.TestUtils;
import com.swing.binding.TestBean;

/**
 * Tests the functionality of {@link ListBinding}.
 * <p>
 * This does not test the class in isolation (as per a unit test), it tests with real bindings (BetterBeansBinding).
 * </p>
 * 
 * @author Stephen Neal
 * @since 29/07/2011
 */
public class ListBindingFunctionalTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Test for {@link ListBinding#model(Object, org.jdesktop.beansbinding.Property, JComboBox)}. Verifies binding in
     * both directions.
     */
    @Test
    public void testModelComboBox() {
        // Setup
        final TestBean bean = new TestBean();
        final JComboBox comboBox = new JComboBox();
        // The bean list must be set prior to binding for it to be bound to the list instance
        List<String> l = new ArrayList<String>();
        final String value1 = "value1";
        l.add(value1);
        final ObservableList<String> list = ObservableCollections.observableList(l);
        bean.setStringList(list);

        // Bind
        BeanProperty<TestBean, List<String>> bP = BeanProperty.create("stringList");
        JComboBoxBinding<String, TestBean, JComboBox> binding = ListBinding.model(bean, bP, comboBox);
        binding.bind();

        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(1, comboBox.getModel().getSize());
                assertEquals(value1, comboBox.getModel().getElementAt(0));
                assertEquals(value1, comboBox.getItemAt(0));
            }
        });
        final String value2 = "value2";
        bean.getStringList().add(value2);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(2, comboBox.getModel().getSize());
                assertEquals(value1, comboBox.getModel().getElementAt(0));
                assertEquals(value1, comboBox.getItemAt(0));
                assertEquals(value2, comboBox.getModel().getElementAt(1));
                assertEquals(value2, comboBox.getItemAt(1));
            }
        });
        bean.getStringList().remove(value2);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(1, comboBox.getModel().getSize());
                assertEquals(value1, comboBox.getModel().getElementAt(0));
                assertEquals(value1, comboBox.getItemAt(0));
            }
        });

        // Changing the combo box model will not update the bean! Do this test last.
        comboBox.setModel(new DefaultComboBoxModel(new String[] { value1 }));
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(1, bean.getStringList().size());
                assertEquals(value1, bean.getStringList().get(0));
            }
        });

        // Unbind to ensure no error occurs (test will fail if it does)
        binding.unbind();
    }

    /**
     * Test for {@link ListBinding#selection(Object, org.jdesktop.beansbinding.Property, JComboBox). Verifies binding
     * updates correctly in both directions.
     */
    @Test
    public void testSelectionComboBox() {
        // Setup
        final TestBean bean = new TestBean();
        final JComboBox comboBox = new JComboBox();
        final String value1 = "value1";
        final String value2 = "value2";

        // Bind
        BeanProperty<TestBean, String> bP = BeanProperty.create("string");
        Binding<TestBean, String, Object, String> binding = ListBinding.selection(bean, bP, comboBox);
        binding.bind();

        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, comboBox.getSelectedItem());
            }
        });
        // Combo box model has no items (no data binding) so setting the selected item does nothing
        comboBox.setSelectedItem(value1);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, comboBox.getSelectedItem());
            }
        });
        comboBox.setSelectedItem(null);

        // Manually populate the combo box (not via data binding), defaults selection to the first item.
        comboBox.setModel(new DefaultComboBoxModel(new String[] { value1, value2 }));
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value1, comboBox.getSelectedItem());
                assertEquals(value1, bean.getString());
            }
        });
        comboBox.setSelectedItem(value2);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(value2, bean.getString());
            }
        });
        comboBox.setSelectedItem(null);
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(null, bean.getString());
            }
        });

        // Unbind to ensure no error occurs
        binding.unbind();
    }

    /**
     * Test for {@link ListBinding#model(Object, org.jdesktop.beansbinding.Property, JTable, Map)}. Verifies binding in
     * both directions.
     */
    @Test
    public void testModelTable() {
        // Setup
        final JTable table = new JTable();
        // The bean list must be set prior to binding for it to be bound to the list instance
        final List<TestBean> l = new ArrayList<TestBean>();
        final TestBean bean = new TestBean();
        final Date date = Calendar.getInstance().getTime();
        for (int i = 0; i < 3; i++) {
            final TestBean b = new TestBean();
            b.setString("value" + i);
            b.setDate(date);
            b.setDuble(Double.valueOf(i));
            l.add(b);
        }
        final ObservableList<TestBean> list = ObservableCollections.observableList(l);
        bean.setTestBeans(list);

        // Bind
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("string", "String");
        map.put("duble", "Double");
        map.put("date", "Date");
        BeanProperty<TestBean, List<TestBean>> bP = BeanProperty.create("testBeans");
        JTableBinding<TestBean, TestBean, JTable> binding = ListBinding.model(bean, bP, table, map);
        binding.bind();

        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ListBindingFunctionalTest.this.logger.info("size = " + list.size());
                assertEquals(list.size(), table.getModel().getRowCount());
                for (int i = 0; i < list.size(); i++) {
                    TestBean entry = list.get(i);
                    assertEquals(entry.getString(), table.getModel().getValueAt(i, 0));
                    assertEquals(entry.getString(), table.getValueAt(i, 0));
                    assertEquals(entry.getDuble(), table.getModel().getValueAt(i, 1));
                    assertEquals(entry.getDuble(), table.getValueAt(i, 1));
                    assertEquals(entry.getDate(), table.getModel().getValueAt(i, 2));
                    assertEquals(entry.getDate(), table.getValueAt(i, 2));
                }
            }
        });

        // Add another entry into the list
        int i = list.size();
        final TestBean b = new TestBean();
        b.setString("value" + i);
        b.setDate(date);
        b.setDuble(Double.valueOf(i));
        list.add(b);
        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ListBindingFunctionalTest.this.logger.info("size = " + list.size());
                assertEquals(list.size(), table.getModel().getRowCount());
                for (int i = 0; i < list.size(); i++) {
                    TestBean entry = list.get(i);
                    assertEquals(entry.getString(), table.getModel().getValueAt(i, 0));
                    assertEquals(entry.getString(), table.getValueAt(i, 0));
                    assertEquals(entry.getDuble(), table.getModel().getValueAt(i, 1));
                    assertEquals(entry.getDuble(), table.getValueAt(i, 1));
                    assertEquals(entry.getDate(), table.getModel().getValueAt(i, 2));
                    assertEquals(entry.getDate(), table.getValueAt(i, 2));
                }
            }
        });

        // Remove an entry from the list
        list.remove(0);
        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ListBindingFunctionalTest.this.logger.info("size = " + list.size());
                assertEquals(list.size(), table.getModel().getRowCount());
                for (int i = 0; i < list.size(); i++) {
                    TestBean entry = list.get(i);
                    assertEquals(entry.getString(), table.getModel().getValueAt(i, 0));
                    assertEquals(entry.getString(), table.getValueAt(i, 0));
                    assertEquals(entry.getDuble(), table.getModel().getValueAt(i, 1));
                    assertEquals(entry.getDuble(), table.getValueAt(i, 1));
                    assertEquals(entry.getDate(), table.getModel().getValueAt(i, 2));
                    assertEquals(entry.getDate(), table.getValueAt(i, 2));
                }
            }
        });

        // Change an entry in the list
        TestBean change = list.get(0);
        change.setDuble(Double.valueOf(10));
        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ListBindingFunctionalTest.this.logger.info("size = " + list.size());
                assertEquals(list.size(), table.getModel().getRowCount());
                for (int i = 0; i < list.size(); i++) {
                    TestBean entry = list.get(i);
                    assertEquals(entry.getString(), table.getModel().getValueAt(i, 0));
                    assertEquals(entry.getString(), table.getValueAt(i, 0));
                    ListBindingFunctionalTest.this.logger.info("duble = " + entry.getDuble());
                    assertEquals(entry.getDuble(), table.getModel().getValueAt(i, 1));
                    assertEquals(entry.getDuble(), table.getValueAt(i, 1));
                    assertEquals(entry.getDate(), table.getModel().getValueAt(i, 2));
                    assertEquals(entry.getDate(), table.getValueAt(i, 2));
                }
            }
        });

        // Change an entry in the table
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                table.setValueAt(Double.valueOf(20), 0, 1);
            }
        });
        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ListBindingFunctionalTest.this.logger.info("size = " + list.size());
                assertEquals(list.size(), table.getModel().getRowCount());
                for (int i = 0; i < list.size(); i++) {
                    TestBean entry = list.get(i);
                    assertEquals(entry.getString(), table.getModel().getValueAt(i, 0));
                    assertEquals(entry.getString(), table.getValueAt(i, 0));
                    ListBindingFunctionalTest.this.logger.info("duble = " + entry.getDuble());
                    assertEquals(entry.getDuble(), table.getModel().getValueAt(i, 1));
                    assertEquals(entry.getDuble(), table.getValueAt(i, 1));
                    assertEquals(entry.getDate(), table.getModel().getValueAt(i, 2));
                    assertEquals(entry.getDate(), table.getValueAt(i, 2));
                }
            }
        });

        // Changing the table model updates the bean! Do this test last.
        table.setModel(new DefaultTableModel());
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertNull(bean.getStringList());
                assertEquals(0, table.getModel().getRowCount());
            }
        });

        // Unbind to ensure no error occurs (test will fail if it does)
        binding.unbind();
    }

    /**
     * Test for {@link ListBinding#selection(Object, org.jdesktop.beansbinding.Property, JTable)}. Verifies binding in
     * both directions.
     */
    @Test
    public void testSingleSelectionTable() {
        // Setup
        final JTable table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // The bean list must be set prior to binding for it to be bound to the list instance
        final List<TestBean> l = new ArrayList<TestBean>();
        final TestBean bean = new TestBean();
        final Date date = Calendar.getInstance().getTime();
        for (int i = 0; i < 3; i++) {
            final TestBean b = new TestBean();
            b.setString("value" + i);
            b.setDate(date);
            b.setDuble(Double.valueOf(i));
            l.add(b);
        }
        final ObservableList<TestBean> list = ObservableCollections.observableList(l);
        bean.setTestBeans(list);

        final List<TestBean> s = new ArrayList<TestBean>();
        final ObservableList<TestBean> selected = ObservableCollections.observableList(s);
        bean.setTestBeansSelected(selected);

        // Bind
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("string", "String");
        map.put("duble", "Double");
        map.put("date", "Date");
        BeanProperty<TestBean, List<TestBean>> bP = BeanProperty.create("testBeans");
        JTableBinding<TestBean, TestBean, JTable> listBinding = ListBinding.model(bean, bP, table, map);
        listBinding.bind();
        BeanProperty<TestBean, List<TestBean>> bP2 = BeanProperty.create("testBeansSelected");
        Binding<Object, List<TestBean>, TestBean, List<TestBean>> selectionBinding = ListBinding.selection(bean, bP2,
                        table);
        selectionBinding.bind();

        // Table selection binding is currently read-only (restriction of better beans binding). Adding an entry to the
        // selection list will have no effect on the table.
        selected.add(list.get(0));
        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(list.size(), table.getModel().getRowCount());
                for (int i = 0; i < list.size(); i++) {
                    assertEquals(0, table.getSelectedRowCount());
                    assertEquals(1, selected.size());
                    assertEquals(0, bean.getTestBeansSelected().size());
                }
            }
        });
        selected.clear();

        // Select a row in the table
        for (int i = 0; i < list.size(); i++) {
            final Integer select = i;
            TestUtils.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    table.getSelectionModel().setSelectionInterval(select, select);
                }
            });
            TestUtils.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    // expect selected list contains the selected row
                    assertEquals(1, bean.getTestBeansSelected().size());
                    assertEquals(list.get(select), bean.getTestBeansSelected().get(0));
                }
            });
        }

        // Select contiguous rows in the table
        for (int i = 0; i < list.size(); i++) {
            final Integer selectFirst = 0;
            final Integer selectLast = i;
            TestUtils.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    table.getSelectionModel().setSelectionInterval(selectFirst, selectLast);
                }
            });
            TestUtils.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    // being single selection only the last index should be selected
                    assertEquals(1, bean.getTestBeansSelected().size());
                    assertEquals(list.get(selectLast), bean.getTestBeansSelected().get(0));
                }
            });
        }

        listBinding.unbind();
        selectionBinding.unbind();
    }

    /**
     * Test for {@link ListBinding#selection(Object, org.jdesktop.beansbinding.Property, JTable)}. Verifies binding in
     * both directions.
     */
    @Test
    public void testSingleIntervalSelectionTable() {
        // Setup
        final JTable table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        // The bean list must be set prior to binding for it to be bound to the list instance
        final List<TestBean> l = new ArrayList<TestBean>();
        final TestBean bean = new TestBean();
        final Date date = Calendar.getInstance().getTime();
        for (int i = 0; i < 3; i++) {
            final TestBean b = new TestBean();
            b.setString("value" + i);
            b.setDate(date);
            b.setDuble(Double.valueOf(i));
            l.add(b);
        }
        final ObservableList<TestBean> list = ObservableCollections.observableList(l);
        bean.setTestBeans(list);

        final List<TestBean> s = new ArrayList<TestBean>();
        final ObservableList<TestBean> selected = ObservableCollections.observableList(s);
        bean.setTestBeansSelected(selected);

        // Bind
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("string", "String");
        map.put("duble", "Double");
        map.put("date", "Date");
        BeanProperty<TestBean, List<TestBean>> bP = BeanProperty.create("testBeans");
        JTableBinding<TestBean, TestBean, JTable> listBinding = ListBinding.model(bean, bP, table, map);
        listBinding.bind();
        BeanProperty<TestBean, List<TestBean>> bP2 = BeanProperty.create("testBeansSelected");
        Binding<Object, List<TestBean>, TestBean, List<TestBean>> selectionBinding = ListBinding.selection(bean, bP2,
                        table);
        selectionBinding.bind();

        // Table selection binding is currently read-only (restriction of better beans binding). Adding an entry to the
        // selection list will have no effect on the table.
        selected.add(list.get(0));
        // Test
        TestUtils.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                assertEquals(list.size(), table.getModel().getRowCount());
                for (int i = 0; i < list.size(); i++) {
                    assertEquals(0, table.getSelectedRowCount());
                    assertEquals(1, selected.size());
                    assertEquals(0, bean.getTestBeansSelected().size());
                }
            }
        });
        selected.clear();

        // Select a row in the table
        for (int i = 0; i < list.size(); i++) {
            final Integer select = i;
            TestUtils.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    table.getSelectionModel().setSelectionInterval(select, select);
                }
            });
            TestUtils.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    // expect selected list contains the selected row
                    assertEquals(1, bean.getTestBeansSelected().size());
                    assertEquals(list.get(select), bean.getTestBeansSelected().get(0));
                }
            });
        }

        // Select contiguous rows in the table
        for (int i = 0; i < list.size(); i++) {
            final Integer selectFirst = 0;
            final Integer selectLast = i;
            TestUtils.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    table.getSelectionModel().setSelectionInterval(selectFirst, selectLast);
                }
            });
            TestUtils.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    // expect the selected list contains the selected rows
                    assertEquals(selectLast.intValue() + 1, bean.getTestBeansSelected().size());
                    assertEquals(list.get(selectLast), bean.getTestBeansSelected().get(selectLast));
                }
            });
        }

        listBinding.unbind();
        selectionBinding.unbind();
    }
}
