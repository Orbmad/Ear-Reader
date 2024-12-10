package earreader;

import earreader.model.ModelImpl;
import earreader.view.ViewImpl;

import earreader.model.Model;
import earreader.view.View;

public class Controller implements ControllerInterface{
    
    private Model model = new ModelImpl();
    private View view = new  ViewImpl();

    public Controller(final Model model, final View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void login(final String email, final String password) {
        model.login(email, password);
    }
    
}
