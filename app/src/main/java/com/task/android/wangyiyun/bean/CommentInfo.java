package com.task.android.wangyiyun.bean;

/**
 * Create by 韩振峰 on
 */
public class CommentInfo {
    private String ID;
    private int DID;
    private int CID;
    private String replyerID;
    private String relay;
    private String comment;
    private int praiseNumber;
    private String commenterName;
    private String commenterAvatar;

    public CommentInfo(){}

    public CommentInfo(String ID, int DID, int CID, String comment, int praiseNumber) {
        this.ID = ID;
        this.DID = DID;
        this.CID = CID;
        this.comment = comment;
        this.praiseNumber = praiseNumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getDID() {
        return DID;
    }

    public void setDID(int DID) {
        this.DID = DID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public String getReplyerID() {
        return replyerID;
    }

    public void setReplyerID(String replyerID) {
        this.replyerID = replyerID;
    }

    public String getRelay() {
        return relay;
    }

    public void setRelay(String relay) {
        this.relay = relay;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterAvatar() {
        return commenterAvatar;
    }

    public void setCommenterAvatar(String commenterAvatar) {
        this.commenterAvatar = commenterAvatar;
    }
}