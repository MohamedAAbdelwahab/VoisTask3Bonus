public class Post {
    int userid;
    String body;
    String title;

    public Post(int userid, String body, String title) {
        this.userid = userid;
        this.body = body;
        this.title = title;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
