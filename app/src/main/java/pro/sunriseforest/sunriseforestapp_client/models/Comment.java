package pro.sunriseforest.sunriseforestapp_client.models;

public class Comment {

    private String mComment;
    private User mUser;

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getComment() {

        return mComment;
    }

    public User getUser() {
        return mUser;
    }

    public Comment(String comment, User user) {

        mComment = comment;
        mUser = user;
    }
    public Comment copy(){
        return new Comment(mComment, mUser.copy());
    }
}
