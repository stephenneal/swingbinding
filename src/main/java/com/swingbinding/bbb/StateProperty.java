package com.swingbinding.bbb;

/**
 * State property extends {@link SwingProperty} to ensure the value is never {@code null}.
 * 
 * @author Stephen Neal
 * @since 18/04/2013
 * 
 * @param <S> the type of source object that this {@code SwingProperty} operates on
 */
class StateProperty<S> extends SwingProperty<S, Boolean> {

    /**
     * Creates an instance of {@code SwingProperty} for the given path.
     * 
     * @param path the path
     * @return an instance of {@code SwingProperty} for the given path
     * @throws IllegalArgumentException if the path is null, or contains no property names
     */
    public static final <S> StateProperty<S> createState(String path) {
        return new StateProperty<S>(path);
    }

    /**
     * @throws IllegalArgumentException for empty or {@code null} path.
     */
    private StateProperty(String path) {
        super(null, path, null);
    }

    /**
     * Overrides the default implementation to ensure the value is never {@code null}.
     */
    @Override
    public Boolean getValue(S source) {
        Boolean value = super.getValue(source);
        if (value == null) {
            return Boolean.FALSE;
        }
        return value;
    }

    /**
     * Overrides the default implementation to set the value to a non-{@code null} value.
     */
    @Override
    public void setValue(S source, Boolean value) {
        if (value == null) {
            value = Boolean.FALSE;
        }
        super.setValue(source, value);
    }

}
