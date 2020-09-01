package com.dailylocally.ui.signup.faqs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqResponse {

    @Expose
    @SerializedName("result")
    private List<Result> result;

    @Expose
    @SerializedName("success")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    public List<Result> getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static class Result {

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

    }
}
