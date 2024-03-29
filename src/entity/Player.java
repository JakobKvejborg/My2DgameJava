package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int hasKey = 0;

    // Constructor :
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2); // I believe this places the player in the middle of the screen
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

//      This is the collision of the middle of the player:
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        // old player position
//        worldX = 100; // pixels. This is the players' position on the world map
//        worldY = 100;
        // new player position
        worldX = gp.tileSize * 23; // players starting position on the world map
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void update() {

        // This line makes the player stand still when not moving:
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            // Increase the speed when shift is pressed (sprinting) MAYBE DELETE TODO
            if (keyH.shiftPressed) {
                if (keyH.upPressed) {
                    direction = "up";
                    worldY -= (speed + 2); // Sprinting speed, here multiplied by 2 for example
                } else if (keyH.downPressed) {
                    direction = "down";
                    worldY += (speed + 2);
                } else if (keyH.leftPressed) {
                    direction = "left";
                    worldX -= (speed + 2);
                } else if (keyH.rightPressed) {
                    direction = "right";
                    worldX += (speed + 2);
                }
            } else {
                // Normal movement when shift is not pressed
                if (keyH.upPressed) {
                    direction = "up";

                } else if (keyH.downPressed) {
                    direction = "down";

                } else if (keyH.leftPressed) {
                    direction = "left";

                } else if (keyH.rightPressed) {
                    direction = "right";

                }
            }

            // Check tile collision:
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check object collesion
            int objIndex = gp.cChecker.checkObject(this, true);
            pickObject(objIndex);

            // If collision is false, player can move:
            if (collisionOn == false) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }


            }

            spriteCounter++;
            if (spriteCounter > 12) { // character image changes every 12 frames, changes how fast the walking animation looks
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickObject(int i) {

        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); // make screenX and screenY just x and y to make the player move freely instead of fixed camera
    }


}
