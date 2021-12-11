package br.com.poupeAi.exception;

public class ValidationErrorDetails extends ErrorDetail {
    private String field;
    private String fieldMessage;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }


    public void setFieldMessage(String fieldMessage) {
        this.fieldMessage = fieldMessage;
    }


    public static final class Builder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String developerMessage;
        private String field;
        private String fieldMessage;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public Builder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }

        public ValidationErrorDetails build() {
            ValidationErrorDetails ne = new ValidationErrorDetails();
            ne.setTitle(this.title);
            ne.setDetail(this.detail);
            ne.setStatus(this.status);
            ne.setDeveloperMessage(this.developerMessage);
            ne.setTimestamp(this.timestamp);
            ne.setField(this.field);
            ne.setFieldMessage(this.fieldMessage);
            return ne;
        }
    }
}