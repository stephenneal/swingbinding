package com.swingbinding.bbb;

import com.swingbinding.bbb.CustomDateBinding;

/**
 * Tests the functionality of {@link CustomDateBinding}.
 * <p>
 * This does not test the class in isolation (as per a unit test), it tests with real bindings (BetterBeansBinding).
 * </p>
 * 
 * @author Stephen Neal
 * @since 18/07/2011
 */
public class CustomDateBindingFunctionalTest {

    // /**
    // * Test for {@link CustomDateBinding#date(Object, String, JDateChooser)}. Verifies binding updates correctly in both
    // * directions.
    // *
    // * @throws InvocationTargetException
    // * @throws InterruptedException
    // */
    // @Test
    // public void testDateDateChooser() throws InterruptedException, InvocationTargetException {
    // // Setup
    // final TestBean bean = TestBean.newInstance();
    // final JDateChooser dateField = new JDateChooser();
    //
    // // Bind
    // Binding<TestBean, Date, JDateChooser, Date> binding = CustomDateBinding.date(bean, "date", dateField);
    // binding.bind();
    //
    // // Test
    // assertEquals(null, bean.getString());
    // assertEquals(null, dateField.getDate());
    //
    // // Update the bean value
    // final Calendar cal = Calendar.getInstance();
    // final Date value = cal.getTime();
    // bean.setDate(value);
    // // The following test fails periodically, perhaps there is an issue with the date chooser? For now add a sleep.
    // Thread.sleep(100);
    // SwingUtilities.invokeAndWait(new Runnable() {
    // @Override
    // public void run() {
    // assertEquals(value, dateField.getDate());
    // }
    // });
    // // Clear the date chooser
    // dateField.setDate(null);
    // SwingUtilities.invokeAndWait(new Runnable() {
    // @Override
    // public void run() {
    // assertEquals(null, bean.getDate());
    // }
    // });
    // // Update the date chooser with a value
    // dateField.setDate(value);
    // SwingUtilities.invokeAndWait(new Runnable() {
    // @Override
    // public void run() {
    // assertEquals(value, bean.getDate());
    // }
    // });
    // // Clear the bean value
    // bean.setDate(null);
    // SwingUtilities.invokeAndWait(new Runnable() {
    // @Override
    // public void run() {
    // assertEquals(null, dateField.getDate());
    // }
    // });
    // }

}
