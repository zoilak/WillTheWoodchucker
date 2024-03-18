public class Node {

    private int fvalue;
    private int gvalue;
    private Point cur_pos;


    private Node previous;
    public Node(int fvalue, int gvalue, Point cur_pos, Node previous ){
        this.fvalue=fvalue;
        this.gvalue=gvalue;
        this.cur_pos=cur_pos;
        this.previous=previous;
    }
    public int getGvalue() {
        return gvalue;
    }

    public void setGvalue(int gvalue) {
        this.gvalue = gvalue;
    }
    public int getFvalue() {
        return fvalue;
    }
    public Point getCur_pos() {
        return cur_pos;
    }

    public void setCur_pos(Point cur_pos) {
        this.cur_pos = cur_pos;
    }
    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

}
