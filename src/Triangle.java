public class Triangle {
    public static void main(String[] args) {
        for (int row = 1; row <= height; ++row ) {
            for(int i = 0, i < height - row; ++i) System.out.println(" ");
            for(int i = 0, i < 2*row - 1; ++i) System.out.println("#");
            System.out.println();
        }
    }
}
