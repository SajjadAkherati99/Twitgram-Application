package Controllers.view;

public interface ResponseResult {
    public void showResponse(String response);
    public <T> void showResponse(String header, T body);
}
