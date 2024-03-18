public final class Viewport {

    private int row;
    public int getRow() {return row;}
    private int col;
    public int getCol(){return col;}
    private int numRows;
    public int getnumRows(){return numRows;}
    private int numCols;
    public int getnumCols(){return numCols;}

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public Point viewportToWorld( int col, int row) {
        return new Point(col + this.col, row + this.row);  //should i put this or not ?
    }

    public Point worldToViewport( int col, int row) {
        return new Point(col - this.col, row - this.row);
    }

    public boolean contains( Point p) {
        return p.y >= row && p.y < row + numRows && p.x >= col && p.x < col + numCols;
    }
    public void shift( int col, int row) {
        this.col = col;
        this.row = row;
    }



}
