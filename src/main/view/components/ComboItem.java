package main.view.components;

public class ComboItem {
	private final String label, strValue;
    private final int intValue;

    /**
     * Generate the combo item with a label and a value.
     * @param label
     * @param value
     */
    public ComboItem(String label, int value) {
        this.label = label;
        this.intValue = value;
        this.strValue = null;
    }
    
    public ComboItem(String label, String value) {
        this.label = label;
        this.strValue = value;
        this.intValue = 0;
    }
    
    /**
     * Return the int value.
     * @return
     */
    public int getIntValue() {
        return this.intValue;
    }

    /**
     * Return the String value.
     * @return
     */
    public String getStrValue() {
    	return this.strValue;
    }
    
    /**
     * Return the label.
     */
    public String toString() {
        return this.label;
    }
}
