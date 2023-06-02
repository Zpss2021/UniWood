package info.zpss.uniwood.client.controller;

import info.zpss.uniwood.client.model.PostModel;
import info.zpss.uniwood.client.util.interfaces.Controller;
import info.zpss.uniwood.client.view.PostView;
import info.zpss.uniwood.client.view.window.PostWindow;

public class PostController implements Controller<PostModel, PostView> {
    private static final PostController INSTANCE;
    private static final PostModel model;
    private static final PostView view;

    static {
        model = new PostModel();
        view = new PostWindow();
        INSTANCE = new PostController();
    }

    private PostController() {
    }

    public static PostController getInstance() {
        return INSTANCE;
    }

    @Override
    public PostModel getModel() {
        return model;
    }

    @Override
    public PostView getView() {
        return view;
    }

    @Override
    public void register() {

    }
}
