package com.dailylocally.ui.signup.faqs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqResponse {

    @Expose
    @SerializedName("result")
    private List<ProductList> data;

    @Expose
    @SerializedName("success")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FaqResponse)) {
            return false;
        }

        FaqResponse that = (FaqResponse) o;

        if (!statusCode.equals(that.statusCode)) {
            return false;
        }
        if (!message.equals(that.message)) {
            return false;
        }
        return data != null ? data.equals(that.data) : that.data == null;

    }

    @Override
    public int hashCode() {
        int result = statusCode.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public List<ProductList> getData() {
        return data;
    }

    public void setData(List<ProductList> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public static class ProductList {

        @Expose
        @SerializedName("type")
        private String type;

        @Expose
        @SerializedName("question")
        private String question;

        @Expose
        @SerializedName("answer")
        private String answer;

        @Expose
        @SerializedName("faqid")
        private String faqid;

        @Expose
        @SerializedName("created_at")
        private String created_at;

        @Expose
        @SerializedName("preparetime")
        private String preparetime;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FaqResponse.ProductList)) {
                return false;
            }

            FaqResponse.ProductList repo = (FaqResponse.ProductList) o;

            if (!type.equals(repo.type)) {
                return false;
            }
            if (!question.equals(repo.question)) {
                return false;
            }
            if (!answer.equals(repo.answer)) {
                return false;
            }
            if (!preparetime.equals(repo.preparetime)) {
                return false;
            }
            if (!created_at.equals(repo.created_at)) {
                return false;
            }
            return faqid.equals(repo.faqid);
        }

        @Override
        public int hashCode() {
            int result = faqid.hashCode();
            result = 31 * result + type.hashCode();
            result = 31 * result + question.hashCode();
            result = 31 * result + answer.hashCode();
            result = 31 * result + preparetime.hashCode();
            result = 31 * result + created_at.hashCode();
            return result;
        }


        public String getType() {
            return type;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        public String getFaqid() {
            return faqid;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getPreparetime() {
            return preparetime;
        }
    }
}
