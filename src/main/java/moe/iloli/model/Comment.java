package moe.iloli.model;

public class Comment {
    private int commentid;
    private String username;
    private int bookid;
    private String comment;
    private String ip;
    private String commenttime;
    private String avatarurl;
    public int getCommentid() {
        return commentid;
    }
    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getBookid() {
        return bookid;
    }
    public void setBookid(int bookid) {
        this.bookid = bookid;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getCommenttime() {
        return commenttime;
    }
    public void setCommenttime(String commenttime) {
        this.commenttime = commenttime;
    }
    public String getAvatarurl() {
        return avatarurl;
    }
    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }
}
