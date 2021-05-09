package sample.presentationLayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observer;

public class EmployeeController implements PropertyChangeListener {
    private String alert;
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setAlert((String) evt.getNewValue());
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
}
