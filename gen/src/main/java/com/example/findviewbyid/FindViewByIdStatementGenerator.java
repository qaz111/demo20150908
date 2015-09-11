package com.example.findviewbyid;

public class FindViewByIdStatementGenerator {

    public interface FindViewByIdCallback {
        void onStatementCreated(String statement, AndroidView view);
        String getViewGroupNameFor(AndroidView view);
        boolean shouldProcessView(AndroidView view);
    }

    @SuppressWarnings("ConstantConditions")
    public void createFindViewStatements(AndroidView view, FindViewByIdCallback callback) {
        if (callback.shouldProcessView(view)) {
            String viewGroupName = callback.getViewGroupNameFor(view);
            String statement = createFindViewStatement(viewGroupName, view);
            callback.onStatementCreated(statement, view);
        }
        for (AndroidView subView : view.getChildNodes()) {
            createFindViewStatements(subView, callback);
        }
    }

    public String createFindViewStatement(String viewGroupName, AndroidView view) {
        return ("view".equalsIgnoreCase(view.getClassSimpleName()) ? "" : "(" + view.getClassSimpleName() + ") ")
                + viewGroupName + ".findViewById(R.id." + view.getIdValue() + ");";
    }

}
