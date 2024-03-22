package oy.tol.tra;

/**
 * This class instantiates different types of stacks implementing the {@code StackInterface} interface.
 * <p>
 * The class provides static methods to create instances of stack objects.
 * </p>
 */
public class StackFactory {

    private StackFactory() {
    }
    /**
     * Creates an instance of StackImplementation for Integer type.
     *
     * @return The stack object.
     */
    public static StackInterface<Integer> createIntegerStack() {
        return new StackImplementation<>();
    }

    /**
     * Creates an instance of StackImplementation for Integer type.
     *
     * @param capacity Number of elements the stack can hold.
     * @return The stack object.
     */
    public static StackInterface<Integer> createIntegerStack(int capacity) {
        return new StackImplementation<>(capacity);
    }

    /**
     * Creates an instance of StackImplementation for Character type.
     *
     * @return The stack object.
     */
    public static StackInterface<Character> createCharacterStack() {
        return new StackImplementation<>();
    }

    /**
     * Creates an instance of StackImplementation for Character type.
     *
     * @param capacity Number of elements the stack can hold.
     * @return The stack object.
     */
    public static StackInterface<Character> createCharacterStack(int capacity) {
        return new StackImplementation<>(capacity);
    }
}
