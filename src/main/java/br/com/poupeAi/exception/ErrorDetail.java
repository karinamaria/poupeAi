package br.com.poupeAi.exception;

public class ErrorDetail {
    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String developerMessage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public static final class ErrorDetailBuilder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;

        private ErrorDetailBuilder() {
        }

        public static ErrorDetailBuilder newBuilder() {
            return new ErrorDetailBuilder();
        }

        public ErrorDetailBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ErrorDetailBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ErrorDetailBuilder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public ErrorDetailBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorDetailBuilder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ErrorDetail build() {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setTitle(title);
            errorDetail.setStatus(status);
            errorDetail.setDetail(detail);
            errorDetail.setTimestamp(timestamp);
            errorDetail.setDeveloperMessage(developerMessage);
            return errorDetail;
        }
    }
}