package org.gfg;

public class NetworkCallTask implements Runnable{

    private String requestPayload;

    private String response;

    private boolean responseAvailable;
    @Override
    public void run() {
        // network call with requestPayload
        responseAvailable = true;
    }

    public NetworkCallTask(String requestPayload) {
        this.requestPayload = requestPayload;

    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isResponseAvailable() {
        return responseAvailable;
    }

    public void setResponseAvailable(boolean responseAvailable) {
        this.responseAvailable = responseAvailable;
    }
}
