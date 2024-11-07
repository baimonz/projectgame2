public class Cat {

    private int x, y;
    private boolean isJumping;
    private int jumpHeight;
    

    public Cat(int x, int y) {
        this.x = x;
        this.y = y;
        this.isJumping = false;
        this.jumpHeight = 0;
    }

    public int getX() { 
        return x; 
    }
    public int getY() {
        return y - jumpHeight;
    } 
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y; 
    }

    public void resetPosition() {
        this.x = 50; // Default X position
        this.y = 350; // Default Y position
        this.jumpHeight = 0;
        this.isJumping = false;
    }

    public void startJump() {
        if (!isJumping) {
            isJumping = true;
            jumpHeight = 0;
        }
    }

    public void updateJump(int speed) {
        if (isJumping) {
            jumpHeight += speed;
            if (jumpHeight > 180) {
                isJumping = false;
            }
        } else if (jumpHeight > 0) {
            jumpHeight -= speed;
        }
    }

    public boolean isJumping() { 
        return isJumping;
    }
}
