package com.example.athletics.Model;

import com.google.gson.annotations.SerializedName;

public class PaymentDataItem {

    @SerializedName("amount")
    private String amount;

    @SerializedName("role")
    private String role;

    @SerializedName("max_usage")
    private String maxUsage;

    @SerializedName("payment_token")
    private String paymentToken;

    @SerializedName("purchased_by")
    private Object purchasedBy;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("subscription_id")
    private String subscriptionId;

    @SerializedName("coupon_code_id")
    private Object couponCodeId;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("days")
    private String days;

    @SerializedName("free_day")
    private Object freeDay;

    @SerializedName("id")
    private int id;

    @SerializedName("unique_code")
    private String uniqueCode;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("status")
    private String status;

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setMaxUsage(String maxUsage) {
        this.maxUsage = maxUsage;
    }

    public String getMaxUsage() {
        return maxUsage;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPurchasedBy(Object purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    public Object getPurchasedBy() {
        return purchasedBy;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setCouponCodeId(Object couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public Object getCouponCodeId() {
        return couponCodeId;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDays() {
        return days;
    }

    public void setFreeDay(Object freeDay) {
        this.freeDay = freeDay;
    }

    public Object getFreeDay() {
        return freeDay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}