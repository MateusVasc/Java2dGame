package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tiles;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
        getTileImage();
        loadMap("res/maps/map01.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/tiles/grass.png"));
            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/tiles/wall.png"));
            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/tiles/water.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;

            while (column < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                String line = bufferedReader.readLine();

                while (column < gamePanel.maxScreenCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[column]);
                    mapTileNum[column][row] = num;
                    column++;
                }
                if (column == gamePanel.maxScreenCol) {
                    column = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
        }
    }

    public void draw(Graphics2D graphics2D) {
        int column = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (column < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
            int tileNum = mapTileNum[column][row];
            graphics2D.drawImage(tiles[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            column++;
            x += gamePanel.tileSize;

            if (column == gamePanel.maxScreenCol) {
                column = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }
        }
    }
}
